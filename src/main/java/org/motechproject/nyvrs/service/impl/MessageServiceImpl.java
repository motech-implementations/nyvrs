package org.motechproject.nyvrs.service.impl;

import org.apache.commons.io.FileUtils;
import org.motechproject.nyvrs.domain.ClientRegistration;
import org.motechproject.nyvrs.domain.MessageRequest;
import org.motechproject.nyvrs.domain.MessageRequestStatus;
import org.motechproject.nyvrs.domain.SettingsDto;
import org.motechproject.nyvrs.service.ClientRegistrationService;
import org.motechproject.nyvrs.service.MessageRequestService;
import org.motechproject.nyvrs.service.MessageService;
import org.motechproject.nyvrs.service.SchedulerService;
import org.motechproject.server.config.SettingsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * Implementation of the {@link org.motechproject.nyvrs.service.MessageService} interface.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private SettingsFacade settingsFacade;

    @Autowired
    private ClientRegistrationService clientRegistrationService;

    @Autowired
    private MessageRequestService messageRequestService;

    @Autowired
    private SchedulerService schedulerService;

    @Autowired
    public MessageServiceImpl(final SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @Override
    public synchronized Boolean playMessage(MessageRequest messageRequest) {

        String sipName = settingsFacade.getProperty(SettingsDto.ASTERISK_SIP_NAME);
        String maxRetries = settingsFacade.getProperty(SettingsDto.ASTERISK_MAX_RETRIES);
        String retryInterval = settingsFacade.getProperty(SettingsDto.ASTERISK_RETRY_INTERVAL);
        String contextName = settingsFacade.getProperty(SettingsDto.ASTERISK_MESSAGE_CONTEXT_NAME);
        String callerId = messageRequest.getCallerId();
        ClientRegistration client = clientRegistrationService.findClientRegistrationByNumber(callerId);
        if (client == null) {
            LOG.error("Could not find a client with caller id: " + callerId);
        } else {
            String language = client.getLanguage();
            // e.g. Set1Day0Week03
            String filename = String.format("%sDay%dWeek%02d", client.getCampaignType().getValue(), messageRequest.getDay(), messageRequest.getWeek());
            String callContent = String.format("Channel: SIP/%s/%s\n" +
                    "MaxRetries: %s\n" +
                    "RetryTime: %s\n" +
                    "Context: %s\n" +
                    "Extension: s\n" +
                    "SetVar: language=%s\n" +
                    "SetVar: filename=%s\n" +
                    "SetVar: callerId=%s\n", sipName, callerId, maxRetries, retryInterval, contextName, language, filename, callerId);

            if (schedulerService.isBusy()) {
                LOG.info(String.format("Scheduling call, callerId: %s", callerId));
                schedulerService.schedule(messageRequest);
            } else {
                String callDir = settingsFacade.getProperty(SettingsDto.ASTERISK_CALL_DIR);
                File callFile = new File(callerId + ".call");
                try {
                    LOG.info(String.format("Moving the call file to the outgoing directory (callerId: %s)", callerId));
                    FileUtils.writeStringToFile(callFile, callContent);
                    callFile.setReadable(true, false);
                    callFile.setExecutable(true, false);
                    callFile.setWritable(true, false);
                    File callFileDest = new File(callDir, callerId + ".call");
                    FileUtils.moveFile(callFile, callFileDest);
                    messageRequest.setStatus(MessageRequestStatus.IN_PROGRESS);
                    messageRequestService.update(messageRequest);
                    return true;
                } catch (IOException ioe) {
                    LOG.error("Error while saving the call file:\n" + ioe.getMessage());
                }
            }
        }
        return false;
    }

}

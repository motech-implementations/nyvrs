package org.motechproject.nyvrs.service.impl;

import org.motechproject.nyvrs.domain.*;
import org.motechproject.nyvrs.service.ClientRegistrationService;
import org.motechproject.nyvrs.service.MessageService;
import org.motechproject.server.config.SettingsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Implementation of the {@link org.motechproject.nyvrs.service.MessageService} interface.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {

    private static final Logger LOG = LoggerFactory.getLogger(MessageService.class);

    private SettingsFacade settingsFacade;

    @Autowired
    ClientRegistrationService clientRegistrationService;

    @Autowired
    public MessageServiceImpl(final SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @Override
    public void playMessage(MessageRequest messageRequest) {

        String sipName = settingsFacade.getProperty(SettingsDto.ASTERISK_SIP_NAME);
        Long callerId = messageRequest.getCallerId();
        ClientRegistration client = clientRegistrationService.findClientRegistrationByNumber(callerId.toString());
        if (client == null) {
            LOG.error("Could not find a client with caller id: " + callerId);
        } else {
            String language = client.getLanguage();
            // e.g. Set1Day0Week03
            String filename = String.format("%sDay0Week%02d", client.getCampaignType().getValue(), messageRequest.getWeek());
            String callContent = String.format("Channel: SIP/%s/%d\n" +
                    "MaxRetries: 0\n" +
                    "RetryTime: 60\n" +
                    "WaitTime: 30\n" +
                    "Context: playMessage\n" +
                    "Extension: s\n" +
                    "SetVar: language=%s\n" +
                    "SetVar: filename=%s\n", sipName, callerId, language, filename);

            // TODO check queue/scheduler status
            String callDir = settingsFacade.getProperty(SettingsDto.ASTERISK_CALL_DIR);

            // TODO move .call file to the asterisk outgoing call directory

        }
    }

}

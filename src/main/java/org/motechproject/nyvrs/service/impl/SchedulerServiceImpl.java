package org.motechproject.nyvrs.service.impl;

import org.motechproject.nyvrs.domain.MessageRequest;
import org.motechproject.nyvrs.domain.MessageRequestStatus;
import org.motechproject.nyvrs.domain.SettingsDto;
import org.motechproject.nyvrs.service.MessageRequestService;
import org.motechproject.nyvrs.service.MessageService;
import org.motechproject.nyvrs.service.SchedulerService;
import org.motechproject.server.config.SettingsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service("schedulerService")
public class SchedulerServiceImpl implements SchedulerService {

    private SettingsFacade settingsFacade;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRequestService messageRequestService;

    @Autowired
    public SchedulerServiceImpl(final SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @Override
    public Boolean isBusy() {
        Integer maxCalls = Integer.valueOf(settingsFacade.getProperty(SettingsDto.ASTERISK_MAX_CALLS));
        return getCurrentCallCount() >= maxCalls;
    }

    @Override
    public void schedule(MessageRequest messageRequest) {
        messageRequest.setStatus(MessageRequestStatus.WAITING);
        messageRequestService.update(messageRequest);
    }

    @Override
    public synchronized void handleScheduledRequests() {
        List<MessageRequest> scheduledRequests = messageRequestService.findScheduledRequests();
        if (scheduledRequests.size() > 0) {
            Integer callsToHandle = Integer.valueOf(settingsFacade.getProperty(SettingsDto.ASTERISK_MAX_CALLS)) - getCurrentCallCount();
            for(int i = 0; i < scheduledRequests.size() && i <= callsToHandle; i++) {
                messageService.playMessage(scheduledRequests.get(i));
            }
        }
    }

    private Integer getCurrentCallCount() {
        String callDir = settingsFacade.getProperty(SettingsDto.ASTERISK_CALL_DIR);
        return (new File(callDir).list()).length;
    }
}

package org.motechproject.nyvrs.service;

import org.motechproject.nyvrs.domain.MessageRequest;

public interface SchedulerService {

    Boolean isBusy();

    void schedule(MessageRequest messageRequest);

    void handleScheduledRequests();

}

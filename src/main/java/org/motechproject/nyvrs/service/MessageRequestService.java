package org.motechproject.nyvrs.service;

import org.motechproject.nyvrs.domain.MessageRequest;

import java.util.List;

public interface MessageRequestService {

    void add(MessageRequest messageRequest);

    MessageRequest findActiveMessageRequestByCallerId(String callerId);

    List<MessageRequest> findScheduledRequests();

    List<MessageRequest> getAll();

    void delete(MessageRequest messageRequest);

    void update(MessageRequest messageRequest);

}

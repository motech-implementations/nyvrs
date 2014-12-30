package org.motechproject.nyvrs.service.impl;

import org.motechproject.nyvrs.domain.MessageRequest;
import org.motechproject.nyvrs.domain.MessageRequestStatus;
import org.motechproject.nyvrs.repository.MessageRequestDataService;
import org.motechproject.nyvrs.service.MessageRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("messageRequestService")
public class MessageRequestServiceImpl implements MessageRequestService {

    @Autowired
    MessageRequestDataService messageRequestDataService;

    @Override
    public void add(MessageRequest messageRequest) {
        messageRequestDataService.create(messageRequest);
    }

    @Override
    public MessageRequest findActiveMessageRequestByCallerId(String callerId) {
        return messageRequestDataService.findMessageRequestByCallerIdAndStatus(callerId, MessageRequestStatus.IN_PROGRESS);
    }

    @Override
    public List<MessageRequest> findScheduledRequests() {
        return messageRequestDataService.findMessageRequestsByStatus(MessageRequestStatus.WAITING);
    }

    @Override
    public List<MessageRequest> getAll() {
        return messageRequestDataService.retrieveAll();
    }

    @Override
    public void delete(MessageRequest messageRequest) {
        messageRequestDataService.delete(messageRequest);
    }

    @Override
    public void update(MessageRequest messageRequest) {
        messageRequestDataService.update(messageRequest);
    }
}

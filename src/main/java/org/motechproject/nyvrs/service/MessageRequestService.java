package org.motechproject.nyvrs.service;

import org.motechproject.nyvrs.domain.MessageRequest;

import java.util.List;

public interface MessageRequestService {

    void add(MessageRequest messageRequest);

    MessageRequest findMessageRequestByCallerId(String callerId);

    List<MessageRequest> getAll();

    void delete(MessageRequest messageRequest);

    void update(MessageRequest messageRequest);

}

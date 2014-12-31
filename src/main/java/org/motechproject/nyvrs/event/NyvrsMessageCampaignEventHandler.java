package org.motechproject.nyvrs.event;


import org.motechproject.event.MotechEvent;
import org.motechproject.event.listener.annotations.MotechListener;
import org.motechproject.messagecampaign.EventKeys;
import org.motechproject.nyvrs.domain.ClientRegistration;
import org.motechproject.nyvrs.domain.MessageRequest;
import org.motechproject.nyvrs.domain.SettingsDto;
import org.motechproject.nyvrs.service.ClientRegistrationService;
import org.motechproject.nyvrs.service.MessageRequestService;
import org.motechproject.nyvrs.service.MessageService;
import org.motechproject.server.config.SettingsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class NyvrsMessageCampaignEventHandler {

    private static final  Logger LOG = LoggerFactory.getLogger(NyvrsMessageCampaignEventHandler.class);
    private MessageService messageService;
    private ClientRegistrationService clientRegistrationService;
    private MessageRequestService messageRequestService;

    @Autowired
    public NyvrsMessageCampaignEventHandler(MessageService messageService, ClientRegistrationService clientRegistrationService,
                                            MessageRequestService messageRequestService) {
        this.messageService = messageService;
        this.clientRegistrationService = clientRegistrationService;
        this.messageRequestService = messageRequestService;
    }

    @MotechListener(subjects = { EventKeys.SEND_MESSAGE })
    public void handleSendMessage(MotechEvent event) {
        LOG.info("Handling SEND_MESSAGE event {}: message={} from campaign={} for externalId={}", event.getSubject(),
                event.getParameters().get("MessageKey"), event.getParameters().get("CampaignName"), event.getParameters().get("ExternalID"));
        Map<String,Object> parametersMap = event.getParameters();
        String clientId = (String) parametersMap.get("ExternalID");

        ClientRegistration clientRegistration = clientRegistrationService.getById(Long.valueOf(clientId));
        MessageRequest messageRequest = new MessageRequest(clientRegistration.getNumber(), clientRegistration.getNyWeeks());
        messageRequestService.add(messageRequest);
        messageService.playMessage(messageRequest);
    }
}

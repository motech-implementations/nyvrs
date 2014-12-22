package org.motechproject.nyvrs.service.impl;

import org.motechproject.nyvrs.domain.CampaignType;
import org.motechproject.nyvrs.domain.IVRResponse;
import org.motechproject.nyvrs.domain.SettingsDto;
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
    public MessageServiceImpl(final SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @Override
    public void playMessage(Long callerId, CampaignType campaignType, Integer week) {

        // TODO check queue/scheduler status
        String ivrUrl = settingsFacade.getProperty(SettingsDto.IVR_CALL_URL);
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(ivrUrl)
                .queryParam("number", callerId)
                .queryParam("campaignType", campaignType)
                .queryParam("week", week);
        IVRResponse response = restTemplate.getForObject(builder.build().toUriString(), IVRResponse.class);

        if (response.getError()) {
            LOG.error("Failed to play message for callerId " + callerId + "\nIVR Response: " + response.getMessages());
            // TODO reschedule
        }
    }

}

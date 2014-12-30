package org.motechproject.nyvrs.service;


import org.motechproject.nyvrs.domain.ChannelType;

public interface CampaignService {

    String WEDNESDAY_MESSAGE_CAMPAIGN_NAME = "NYVRS WEDNESDAY SMS CAMPAIGN";
    String SUNDAY_MESSAGE_CAMPAIGN_NAME = "NYVRS SUNDAY IVR CAMPAIGN";
    String MESSAGE_CAMPAIGNS_FILENAME = "message-campaign.json";

    void handleNyvrsCampaignsInDb();

    void enrollToNyvrsCampaign(String externalId, ChannelType channelType);
}

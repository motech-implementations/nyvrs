package org.motechproject.nyvrs.service;


import org.motechproject.nyvrs.domain.ChannelType;

public interface CampaignService {

    String WEDNESDAY_MESSAGE_CAMPAIGN_NAME = "NYVRS WEDNESDAY IVR CAMPAIGN";
    String SATURDAY_MESSAGE_CAMPAIGN_NAME = "NYVRS SATURDAY IVR CAMPAIGN";
    String SUNDAY_MESSAGE_CAMPAIGN_NAME = "NYVRS SUNDAY SMS CAMPAIGN";
    String MESSAGE_CAMPAIGNS_FILENAME = "message-campaign.json";

    void handleNyvrsCampaignsInDb();

    void enrollToNyvrsCampaigns(String externalId, ChannelType channelType);
}

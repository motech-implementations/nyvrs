package org.motechproject.nyvrs.service;

import org.motechproject.nyvrs.domain.CampaignType;

public interface MessageService {

    void playMessage(Long callerId, CampaignType campaignType, Integer week);

}

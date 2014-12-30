package org.motechproject.nyvrs.service.impl;


import org.motechproject.messagecampaign.domain.campaign.CampaignEnrollment;
import org.motechproject.messagecampaign.loader.CampaignJsonLoader;
import org.motechproject.messagecampaign.service.EnrollmentService;
import org.motechproject.messagecampaign.service.MessageCampaignService;
import org.motechproject.messagecampaign.userspecified.CampaignRecord;
import org.motechproject.nyvrs.domain.ChannelType;
import org.motechproject.nyvrs.service.CampaignService;
import org.motechproject.server.config.SettingsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service("campaignService")
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private MessageCampaignService messageCampaignService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private SettingsFacade settingsFacade;

    private CampaignJsonLoader campaignJsonLoader = new CampaignJsonLoader();

    @Override
    public void handleNyvrsCampaignsInDb() {
        List<CampaignRecord> existingCampaignRecords = messageCampaignService.getAllCampaignRecords();
        List<CampaignRecord> nyvrsCampaignRecords;
        List<String> campaignNames = new ArrayList<>();

        for (CampaignRecord campaignRecord : existingCampaignRecords) {
            campaignNames.add(campaignRecord.getName());
        }

        if (!campaignNames.contains(CampaignService.SUNDAY_MESSAGE_CAMPAIGN_NAME) || !campaignNames.contains(CampaignService.WEDNESDAY_MESSAGE_CAMPAIGN_NAME)) {
            InputStream messageCampaignsJson = settingsFacade.getRawConfig(CampaignService.MESSAGE_CAMPAIGNS_FILENAME);
            nyvrsCampaignRecords = campaignJsonLoader.loadCampaigns(messageCampaignsJson);

            if (!campaignNames.contains(CampaignService.SUNDAY_MESSAGE_CAMPAIGN_NAME)) {
                for (CampaignRecord campaignRecord : nyvrsCampaignRecords) {
                    if (campaignRecord.getName().equals(CampaignService.SUNDAY_MESSAGE_CAMPAIGN_NAME)) {
                        messageCampaignService.saveCampaign(campaignRecord);
                    }
                }
            }

            if (!campaignNames.contains(CampaignService.WEDNESDAY_MESSAGE_CAMPAIGN_NAME)) {
                for (CampaignRecord campaignRecord : nyvrsCampaignRecords) {
                    if (campaignRecord.getName().equals(CampaignService.WEDNESDAY_MESSAGE_CAMPAIGN_NAME)) {
                        messageCampaignService.saveCampaign(campaignRecord);
                    }
                }
            }
        }
    }

    @Override
    public void enrollToNyvrsCampaign(String externalId, ChannelType channelType) {
        enrollmentService.register(new CampaignEnrollment(externalId, channelType.equals(ChannelType.V) ?
                SUNDAY_MESSAGE_CAMPAIGN_NAME : WEDNESDAY_MESSAGE_CAMPAIGN_NAME));
    }
}

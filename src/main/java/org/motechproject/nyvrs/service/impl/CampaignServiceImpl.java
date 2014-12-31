package org.motechproject.nyvrs.service.impl;


import org.joda.time.LocalDate;
import org.motechproject.messagecampaign.contract.CampaignRequest;
import org.motechproject.messagecampaign.loader.CampaignJsonLoader;
import org.motechproject.messagecampaign.service.MessageCampaignService;
import org.motechproject.messagecampaign.userspecified.CampaignRecord;
import org.motechproject.nyvrs.domain.ChannelType;
import org.motechproject.nyvrs.service.CampaignService;
import org.motechproject.server.config.SettingsFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service("campaignService")
public class CampaignServiceImpl implements CampaignService {

    @Autowired
    private MessageCampaignService messageCampaignService;

    @Autowired
    private SettingsFacade settingsFacade;

    private CampaignJsonLoader campaignJsonLoader = new CampaignJsonLoader();

    private static final Logger LOG = LoggerFactory.getLogger(CampaignService.class);


    @Override
    public void handleNyvrsCampaignsInDb() {
        List<CampaignRecord> nyvrsCampaignRecords;
        boolean isSundayPresent = true;
        boolean isWednesdayPresent = true;

        if (messageCampaignService.getCampaignRecord(CampaignService.SUNDAY_MESSAGE_CAMPAIGN_NAME) == null) {
            isSundayPresent = false;
        }

        if (messageCampaignService.getCampaignRecord(CampaignService.WEDNESDAY_MESSAGE_CAMPAIGN_NAME) == null) {
            isWednesdayPresent = false;
        }

        if (!isSundayPresent || !isWednesdayPresent) {
            InputStream messageCampaignsJson = settingsFacade.getRawConfig(CampaignService.MESSAGE_CAMPAIGNS_FILENAME);
            nyvrsCampaignRecords = campaignJsonLoader.loadCampaigns(messageCampaignsJson);

            if (!isSundayPresent) {
                for (CampaignRecord campaignRecord : nyvrsCampaignRecords) {
                    if (campaignRecord.getName().equals(CampaignService.SUNDAY_MESSAGE_CAMPAIGN_NAME)) {
                        messageCampaignService.saveCampaign(campaignRecord);
                        LOG.info("Added lacking '" + CampaignService.SUNDAY_MESSAGE_CAMPAIGN_NAME + "' " +
                                "message campaign");
                    }
                }
            }

            if (!isWednesdayPresent) {
                for (CampaignRecord campaignRecord : nyvrsCampaignRecords) {
                    if (campaignRecord.getName().equals(CampaignService.WEDNESDAY_MESSAGE_CAMPAIGN_NAME)) {
                        messageCampaignService.saveCampaign(campaignRecord);
                        LOG.info("Added lacking '" + CampaignService.WEDNESDAY_MESSAGE_CAMPAIGN_NAME + "' " +
                                "message campaign");
                    }
                }
            }
        }
    }

    @Override
    public void enrollToNyvrsCampaign(String externalId, ChannelType channelType) {
        CampaignRequest campaignRequest = new CampaignRequest(externalId, channelType.equals(ChannelType.V) ?
                SUNDAY_MESSAGE_CAMPAIGN_NAME : WEDNESDAY_MESSAGE_CAMPAIGN_NAME, new LocalDate(), null);

        messageCampaignService.enroll(campaignRequest);
    }
}

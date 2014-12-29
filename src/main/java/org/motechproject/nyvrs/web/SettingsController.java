package org.motechproject.nyvrs.web;

import org.motechproject.server.config.SettingsFacade;
import org.motechproject.nyvrs.domain.SettingsDto;
import org.osgi.framework.BundleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Controller
@RequestMapping("/api")
public class SettingsController {

    private SettingsFacade settingsFacade;

    @Autowired
    public SettingsController(final SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    @ResponseBody
    public SettingsDto getSettings() {
        SettingsDto dto = new SettingsDto();
        //dto.setIvrCallUrl(getPropertyValue(SettingsDto.IVR_CALL_URL));
        dto.setAsteriskSipName(getPropertyValue(SettingsDto.ASTERISK_SIP_NAME));
        dto.setAsteriskCallDir(getPropertyValue(SettingsDto.ASTERISK_CALL_DIR));
        dto.setAsteriskMaxRetries(getPropertyValue(SettingsDto.ASTERISK_MAX_RETRIES));
        dto.setAsteriskRetryInterval(getPropertyValue(SettingsDto.ASTERISK_RETRY_INTERVAL));
        dto.setAsteriskMessageContextName(getPropertyValue(SettingsDto.ASTERISK_MESSAGE_CONTEXT_NAME));
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public void saveSettings(@RequestBody SettingsDto settings) throws BundleException {
        if (settings.isValid()) {
            //settingsFacade.setProperty(SettingsDto.IVR_CALL_URL, settings.getIvrCallUrl());
            settingsFacade.setProperty(SettingsDto.ASTERISK_SIP_NAME, settings.getAsteriskSipName());
            settingsFacade.setProperty(SettingsDto.ASTERISK_CALL_DIR, settings.getAsteriskCallDir());
            settingsFacade.setProperty(SettingsDto.ASTERISK_MAX_RETRIES, settings.getAsteriskMaxRetries());
            settingsFacade.setProperty(SettingsDto.ASTERISK_RETRY_INTERVAL, settings.getAsteriskRetryInterval());
            settingsFacade.setProperty(SettingsDto.ASTERISK_MESSAGE_CONTEXT_NAME, settings.getAsteriskMessageContextName());
        } else {
            throw new IllegalArgumentException("Settings are not valid");
        }
    }

    private String getPropertyValue(final String propertyKey) {
        String propertyValue = settingsFacade.getProperty(propertyKey);
        return isNotBlank(propertyValue) ? propertyValue : null;
    }

}

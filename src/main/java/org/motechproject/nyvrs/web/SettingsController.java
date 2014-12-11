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
    private static final String MOTECH_BASE_URL = "motech.base.url";

    private SettingsFacade settingsFacade;

    @Autowired
    public SettingsController(final SettingsFacade settingsFacade) {
        this.settingsFacade = settingsFacade;
    }

    @RequestMapping(value = "/settings", method = RequestMethod.GET)
    @ResponseBody
    public SettingsDto getSettings() {
        SettingsDto dto = new SettingsDto();
        dto.setMotechBaseUrl(getPropertyValue(MOTECH_BASE_URL));
        return dto;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/settings", method = RequestMethod.POST)
    public void saveSettings(@RequestBody SettingsDto settings) throws BundleException {
        if (settings.isValid()) {
            settingsFacade.setProperty(MOTECH_BASE_URL, settings.getMotechBaseUrl());
        } else {
            throw new IllegalArgumentException("Settings are not valid");
        }
    }

    private String getPropertyValue(final String propertyKey) {
        String propertyValue = settingsFacade.getProperty(propertyKey);
        return isNotBlank(propertyValue) ? propertyValue : null;
    }

}

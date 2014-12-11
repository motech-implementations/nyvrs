package org.motechproject.nyvrs.domain;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

public class SettingsDto {

    private String motechBaseUrl;

    public String getMotechBaseUrl() {
        return motechBaseUrl;
    }

    public void setMotechBaseUrl(String motechBaseUrl) {
        this.motechBaseUrl = motechBaseUrl;
    }

    @JsonIgnore
    public boolean isValid() {
        if (StringUtils.isEmpty(motechBaseUrl)) {
            return false;
        }
        return true;
    }

}

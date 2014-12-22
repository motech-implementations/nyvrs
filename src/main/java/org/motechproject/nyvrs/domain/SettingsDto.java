package org.motechproject.nyvrs.domain;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

public class SettingsDto {

    public static final String IVR_CALL_URL = "ivr.call.url";

    private String ivrCallUrl;

    public String getIvrCallUrl() {
        return ivrCallUrl;
    }

    public void setIvrCallUrl(String ivrUrl) {
        this.ivrCallUrl = ivrUrl;
    }

    @JsonIgnore
    public boolean isValid() {
        if (StringUtils.isEmpty(ivrCallUrl)) {
            return false;
        }
        return true;
    }

}

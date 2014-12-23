package org.motechproject.nyvrs.domain;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

public class SettingsDto {

    public static final String IVR_CALL_URL = "ivr.call.url";
    public static final String ASTERISK_SIP_NAME = "asterisk.sip.name";
    public static final String ASTERISK_CALL_DIR = "asterisk.call.dir";

    private String ivrCallUrl;
    private String asteriskSipName;
    private String asteriskCallDir;

    public String getIvrCallUrl() {
        return ivrCallUrl;
    }

    public void setIvrCallUrl(String ivrUrl) {
        this.ivrCallUrl = ivrUrl;
    }

    public String getAsteriskSipName() {
        return asteriskSipName;
    }

    public void setAsteriskSipName(String asteriskSipName) {
        this.asteriskSipName = asteriskSipName;
    }

    public String getAsteriskCallDir() {
        return asteriskCallDir;
    }

    public void setAsteriskCallDir(String asteriskCallDir) {
        this.asteriskCallDir = asteriskCallDir;
    }

    @JsonIgnore
    public boolean isValid() {
        if (StringUtils.isEmpty(asteriskSipName) || StringUtils.isEmpty(asteriskCallDir)) {
            return false;
        }
        return true;
    }

}

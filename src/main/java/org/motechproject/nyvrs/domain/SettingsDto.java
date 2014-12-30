package org.motechproject.nyvrs.domain;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnore;

public class SettingsDto {

    public static final String IVR_CALL_URL = "ivr.call.url";
    public static final String ASTERISK_SIP_NAME = "asterisk.sip.name";
    public static final String ASTERISK_CALL_DIR = "asterisk.call.dir";
    public static final String ASTERISK_MAX_RETRIES = "asterisk.maxRetries";
    public static final String ASTERISK_RETRY_INTERVAL = "asterisk.retryInterval";
    public static final String ASTERISK_MESSAGE_CONTEXT_NAME = "asterisk.message.contextName";
    public static final String ASTERISK_MAX_CALLS = "asterisk.maxCalls";

    private String ivrCallUrl;
    private String asteriskSipName;
    private String asteriskCallDir;
    private String asteriskMessageContextName;
    private String asteriskMaxRetries;
    private String asteriskRetryInterval;
    private String asteriskMaxCalls;

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

    public String getAsteriskRetryInterval() {
        return asteriskRetryInterval;
    }

    public void setAsteriskRetryInterval(String asteriskRetryInterval) {
        this.asteriskRetryInterval = asteriskRetryInterval;
    }

    public String getAsteriskMaxRetries() {
        return asteriskMaxRetries;
    }

    public void setAsteriskMaxRetries(String asteriskMaxRetries) {
        this.asteriskMaxRetries = asteriskMaxRetries;
    }

    public String getAsteriskMessageContextName() {
        return asteriskMessageContextName;
    }

    public void setAsteriskMessageContextName(String asteriskMessageContextName) {
        this.asteriskMessageContextName = asteriskMessageContextName;
    }

    public String getAsteriskMaxCalls() {
        return asteriskMaxCalls;
    }

    public void setAsteriskMaxCalls(String asteriskMaxCalls) {
        this.asteriskMaxCalls = asteriskMaxCalls;
    }

    @JsonIgnore
    public boolean isValid() {
        return !(StringUtils.isBlank(asteriskSipName) || StringUtils.isBlank(asteriskCallDir) || StringUtils.isBlank(asteriskMessageContextName) ||
                StringUtils.isBlank(asteriskMaxRetries) || StringUtils.isBlank(asteriskRetryInterval) || StringUtils.isBlank(asteriskMaxCalls) ||
                !StringUtils.isNumeric(asteriskMaxRetries) || !StringUtils.isNumeric(asteriskRetryInterval) || !StringUtils.isNumeric(asteriskMaxCalls));
    }

}

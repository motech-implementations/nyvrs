package org.motechproject.nyvrs.domain;

public enum ChannelType {
    V("V"),
    SMS("SMS");

    private String value;

    private ChannelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

package org.motechproject.nyvrs.domain;

public enum CampaignType {
    KIKI("Set1"),
    RONALD("Set2"),
    RITA("Set3");

    private String value;

    private CampaignType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

package org.motechproject.nyvrs.domain;


public enum EducationLevel {
    NA("NA"),
    JHS("JHS"),
    SHS("SHS"),
    TER("TER"),
    OTH("OTH");

    private String value;

    private EducationLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

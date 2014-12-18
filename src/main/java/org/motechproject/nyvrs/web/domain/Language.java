package org.motechproject.nyvrs.web.domain;

public enum Language {
    ENGLISH("ENGLISH"),
    TWI("TWI"),
    EWE("EWE"),
    HAUSA("HAUSA");

    private String value;

    private Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

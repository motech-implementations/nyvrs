package org.motechproject.nyvrs.domain;

public class IVRResponse {

    private Boolean error;
    private String messages;

    public IVRResponse(Boolean error, String messages) {
        this.error = error;
        this.messages = messages;
    }

    public Boolean getError() {
        return error;
    }

    public String getMessages() {
        return messages;
    }

}

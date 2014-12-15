package org.motechproject.nyvrs.web.domain;

import org.codehaus.jackson.annotate.JsonProperty;

public class ValidationError {

    private String message;

    public ValidationError(String message) {
        this.message = message;
    }

    @JsonProperty("error")
    public String getMessage() {
        return message;
    }

}

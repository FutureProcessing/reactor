package org.reactor.jira.request;

import org.reactor.annotation.ReactorRequestParameter;

public class UppercaseRequestData {

    @ReactorRequestParameter(shortName = "m", description = "Text to be uppercased", required = true)
    private String message;

    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public void setMessage(String message) {
        this.message = message;
    }
}

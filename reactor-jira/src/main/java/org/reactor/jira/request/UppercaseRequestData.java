package org.reactor.jira.request;

import org.reactor.annotation.ReactorRequestParameter;

public class UppercaseRequestData {

    @ReactorRequestParameter(shortName = "m", required = true)
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

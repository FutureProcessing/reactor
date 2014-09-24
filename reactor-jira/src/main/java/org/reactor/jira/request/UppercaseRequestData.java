package org.reactor.jira.request;

import org.reactor.annotation.ReactorRequestParameter;

public class UppercaseRequestData {

    @ReactorRequestParameter(shortName = "m", description = "Text to be uppercased", required = true)
    private String message;

    @ReactorRequestParameter(shortName = "d", description = "Response delay in seconds", required = false)
    private int secondsDelay;

    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public void setMessage(String message) {
        this.message = message;
    }

    public int getSecondsDelay() {
        return secondsDelay;
    }

    @SuppressWarnings("unused")
    public void setSecondsDelay(int secondsDelay) {
        this.secondsDelay = secondsDelay;
    }
}

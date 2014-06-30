package org.reactor.data;

import org.reactor.annotation.ReactorRequestParameter;

public class RequiredArgumentData {

    @ReactorRequestParameter(shortName = "r", required = true)
    private String requiredArgument;

    public String getRequiredArgument() {
        return requiredArgument;
    }

    public void setRequiredArgument(String requiredArgument) {
        this.requiredArgument = requiredArgument;
    }
}
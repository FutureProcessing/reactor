package org.reactor.data;

import org.reactor.annotation.ReactorRequestParameter;

public class ArgumentAData {

    @ReactorRequestParameter(shortName = "a")
    private String argumentA;

    public String getArgumentA() {
        return argumentA;
    }

    public void setArgumentA(String argumentA) {
        this.argumentA = argumentA;
    }
}
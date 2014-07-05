package org.reactor.data;

import org.reactor.annotation.ReactorRequestParameter;

public class ArgumentABCData {

    @ReactorRequestParameter(shortName = "a")
    private String argumentA;

    @ReactorRequestParameter(shortName = "b")
    private String argumentB;

    @ReactorRequestParameter(shortName = "c")
    private String argumentC;

    public String getArgumentA() {
        return argumentA;
    }

    public void setArgumentA(String argumentA) {
        this.argumentA = argumentA;
    }

    public String getArgumentB() {
        return argumentB;
    }

    public void setArgumentB(String argumentB) {
        this.argumentB = argumentB;
    }

    public String getArgumentC() {
        return argumentC;
    }

    public void setArgumentC(String argumentC) {
        this.argumentC = argumentC;
    }
}

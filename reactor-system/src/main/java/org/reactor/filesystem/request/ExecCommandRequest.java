package org.reactor.filesystem.request;

import org.reactor.annotation.ReactorRequestParameter;

public class ExecCommandRequest {

    @ReactorRequestParameter(required = true, shortName = "c")
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}

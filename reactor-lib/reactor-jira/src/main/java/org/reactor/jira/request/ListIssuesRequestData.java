package org.reactor.jira.request;

import org.reactor.annotation.ReactorRequestParameter;

public class ListIssuesRequestData {

    @ReactorRequestParameter(shortName = "s", description = "If given, will print out only issues matching given status")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

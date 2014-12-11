package org.reactor.jira.request;

import org.reactor.annotation.ReactorRequestParameter;

public class SprintDetailsRequestData {

    @ReactorRequestParameter(required = true, shortName = "i", description = "Sprint id details to be displayed")
    private int sprintId;

    public int getSprintId() {
        return sprintId;
    }

    public void setSprintId(int sprintId) {
        this.sprintId = sprintId;
    }
}

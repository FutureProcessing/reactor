package org.reactor.jira.request;

import org.reactor.annotation.ReactorRequestParameter;

public class IssueDetailsRequestData {

    @ReactorRequestParameter(required = true, shortName = "k", description = "Issue key details to be displayed")
    private String issueKey;

    @ReactorRequestParameter(shortName = "s", required = false, description = "If set, will print out only current status of issue with given key")
    private boolean statusOnly = false;

    public IssueDetailsRequestData() {
    }

    public IssueDetailsRequestData(String issueKey, boolean statusOnly) {
        this.issueKey = issueKey;
        this.statusOnly = statusOnly;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public boolean isStatusOnly() {
        return statusOnly;
    }

    public void setStatusOnly(boolean statusOnly) {
        this.statusOnly = statusOnly;
    }
}

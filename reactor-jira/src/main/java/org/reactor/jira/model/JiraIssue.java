package org.reactor.jira.model;

public class JiraIssue {

    private String key;
    private String summary;
    private String status;

    public JiraIssue(String key, String summary, String status) {
        this.key = key;
        this.summary = summary;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public String getSummary() {
        return summary;
    }

    public String getStatus() {
        return status;
    }
}

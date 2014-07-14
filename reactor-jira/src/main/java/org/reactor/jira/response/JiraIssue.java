package org.reactor.jira.response;

public class JiraIssue {

    private String key;
    private String summary;
    private String description;
    private String status;

    public JiraIssue(String key, String summary, String description, String status) {
        this.key = key;
        this.summary = summary;
        this.description = description;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

}

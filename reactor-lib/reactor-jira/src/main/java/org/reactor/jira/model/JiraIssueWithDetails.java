package org.reactor.jira.model;

public class JiraIssueWithDetails extends JiraIssue {

    private final String description;
    private final String asignee;
    private final String url;

    public JiraIssueWithDetails(String key, String summary, String description, String status, String asignee, String url) {
        super(key, summary, status);
        this.description = description;
        this.asignee = asignee;
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public String getAsignee() {
        return asignee;
    }

    public String getUrl() {
        return url;
    }
}

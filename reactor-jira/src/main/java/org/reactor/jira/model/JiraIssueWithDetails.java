package org.reactor.jira.model;

public class JiraIssueWithDetails extends JiraIssue {

    private String description;
    private String asignee;

    public JiraIssueWithDetails(String key, String summary, String description, String status, String asignee) {
        super(key, summary, status);
        this.description = description;
        this.asignee = asignee;
    }

    public String getDescription() {
        return description;
    }

    public String getAsignee() {
        return asignee;
    }
}

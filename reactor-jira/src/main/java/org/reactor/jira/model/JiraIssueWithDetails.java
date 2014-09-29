package org.reactor.jira.model;

public class JiraIssueWithDetails extends JiraIssue {

    private String description;

    private String asignee;

    public JiraIssueWithDetails(String key, String summary, String description, String status) {
        super(key, summary, status);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getAsignee() {
        return asignee;
    }

    public void setAsignee(String asignee) {
        this.asignee = asignee;
    }
}

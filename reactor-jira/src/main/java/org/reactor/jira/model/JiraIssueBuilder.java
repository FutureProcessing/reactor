package org.reactor.jira.model;

import net.rcarz.jiraclient.Issue;

import com.google.common.base.Function;

public class JiraIssueBuilder {

    public static final Function<Issue, JiraIssue> FROM_JIRA_ISSUE = issue -> forKey(issue.getKey())
            .summary(issue.getSummary())
            .status(issue.getStatus().getName()).build();

    private static final String STATUS_UNKNOWN = "Unknown";
    private String summary;
    private String issueKey;

    private String status = STATUS_UNKNOWN;

    private JiraIssueBuilder(String issueKey) {
        this.issueKey = issueKey;
    }

    public static JiraIssueBuilder forKey(String issueKey) {
        return new JiraIssueBuilder(issueKey);
    }

    public JiraIssueBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public JiraIssueBuilder status(String status) {
        this.status = status;
        return this;
    }

    public JiraIssue build() {
        return new JiraIssue(issueKey, summary, status);
    }
}

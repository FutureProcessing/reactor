package org.reactor.jira.model;

import com.google.common.base.Function;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.User;

public class JiraIssueWithDetailsBuilder {

    private static final String STATUS_UNKNOWN = "Unknown";

    private String summary;
    private String issueKey;
    private String description;
    private String url;
    private String asignee;

    private String status = STATUS_UNKNOWN;
    public static final Function<Issue, JiraIssueWithDetails> FROM_JIRA_ISSUE_DETAILS = issue -> {
        JiraIssueWithDetailsBuilder builder = forKey(issue.getKey())
                .summary(issue.getSummary())
                .description(issue.getDescription())
                .status(issue.getStatus().getName())
                .url(issue.getUrl());
        User assignee = issue.getAssignee();
        if (assignee != null) {
            builder.asignee(assignee.getDisplayName());
        }
        return builder.build();
    };


    private JiraIssueWithDetailsBuilder(String issueKey) {
        this.issueKey = issueKey;
    }

    public static JiraIssueWithDetailsBuilder forKey(String issueKey) {
        return new JiraIssueWithDetailsBuilder(issueKey);
    }

    public JiraIssueWithDetailsBuilder summary(String summary) {
        this.summary = summary;
        return this;
    }

    public JiraIssueWithDetailsBuilder description(String description) {
        this.description = description;
        return this;
    }

    public JiraIssueWithDetailsBuilder status(String status) {
        this.status = status;
        return this;
    }

    public JiraIssueWithDetailsBuilder asignee(String asignee) {
        this.asignee = asignee;
        return this;
    }

    public JiraIssueWithDetailsBuilder url(String url) {
        this.url = url;
        return this;
    }

    public JiraIssueWithDetails build() {
        return new JiraIssueWithDetails(issueKey, summary, description, status, asignee, url);
    }
}
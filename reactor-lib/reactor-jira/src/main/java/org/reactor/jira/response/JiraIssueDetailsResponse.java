package org.reactor.jira.response;

import org.reactor.jira.model.JiraIssueWithDetails;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

import java.util.StringJoiner;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;

public class JiraIssueDetailsResponse implements ReactorResponse {

    public transient static final char DIVIDER_CHARACTER = '-';
    public transient static final String NOT_ASSIGNED = "- not assigned -";
    private transient boolean statusOnly;
    private JiraIssueWithDetails jiraIssueWithDetails;

    public JiraIssueDetailsResponse(JiraIssueWithDetails jiraIssueWithDetails, boolean statusOnly) {
        this.jiraIssueWithDetails = jiraIssueWithDetails;
        this.statusOnly = statusOnly;
    }

    @Override
    public String toConsoleOutput() {
        if (statusOnly) {
            return jiraIssueWithDetails.getStatus().toUpperCase();
        }
        String header = format("%s details: %s [%s]", jiraIssueWithDetails.getKey(), jiraIssueWithDetails.getSummary(), jiraIssueWithDetails.getStatus().toUpperCase());
        String url = jiraIssueWithDetails.getUrl();
        String assignee = format("Assigned to: %s", firstNonNull(jiraIssueWithDetails.getAsignee(), NOT_ASSIGNED));
        return new StringJoiner("\n").add(header).add(url).add(assignee).add(jiraIssueWithDetails.getDescription()).toString();
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        if (statusOnly) {
            printIssueStatus(responseRenderer);
            return;
        }
        printIssueHeader(responseRenderer);
        printIssueDescription(responseRenderer);
    }

    private void printIssueStatus(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderTextLine(jiraIssueWithDetails.getStatus().toUpperCase());
    }

    private void printIssueHeader(ReactorResponseRenderer responseRenderer) {
        String header = format("%s details: %s [%s]", jiraIssueWithDetails.getKey(),
                jiraIssueWithDetails.getSummary(), jiraIssueWithDetails.getStatus().toUpperCase());
        responseRenderer.renderTextLine(header);
        responseRenderer.renderTextLine(jiraIssueWithDetails.getUrl());

        printIssueAsignee(responseRenderer);

        generateDivider(responseRenderer, header.length());
    }

    private void printIssueAsignee(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderTextLine("Assigned to: %s", firstNonNull(jiraIssueWithDetails.getAsignee(), NOT_ASSIGNED));
    }

    private void printIssueDescription(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderTextLine(jiraIssueWithDetails.getDescription());
    }

    private void generateDivider(ReactorResponseRenderer responseRenderer, int length) {
        StringBuilder dividerBuffer = new StringBuilder();
        for (int index = 0; index < length; index++) {
            dividerBuffer.append(DIVIDER_CHARACTER);
        }
        responseRenderer.renderTextLine(dividerBuffer.toString());
    }
}

package org.reactor.jira.response;

import org.reactor.jira.model.JiraIssueWithDetails;
import org.reactor.response.StringReactorResponse;

import java.io.PrintWriter;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;

public class JiraIssueDetailsResponse extends StringReactorResponse {

    public static final char DIVIDER_CHARACTER = '-';
    public static final String NOT_ASSIGNED = "- not assigned -";
    private JiraIssueWithDetails jiraIssueWithDetails;
    private boolean statusOnly;

    public JiraIssueDetailsResponse(JiraIssueWithDetails jiraIssueWithDetails, boolean statusOnly) {
        this.jiraIssueWithDetails = jiraIssueWithDetails;
        this.statusOnly = statusOnly;
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        if (statusOnly) {
            printIssueStatus(printWriter);
            return;
        }
        printIssueHeader(printWriter);
        printIssueDescription(printWriter);
    }

    private void printIssueStatus(PrintWriter printWriter) {
        printWriter.println(jiraIssueWithDetails.getStatus().toUpperCase());
    }

    private void printIssueHeader(PrintWriter printWriter) {
        String header = format("%s details: %s [%s]", jiraIssueWithDetails.getKey(),
                jiraIssueWithDetails.getSummary(), jiraIssueWithDetails.getStatus().toUpperCase());
        printWriter.println(header);
        printIssueAsignee(printWriter);

        generateDivider(printWriter, header.length());
    }

    private void printIssueAsignee(PrintWriter printWriter) {
            printWriter.println(format("Assigned to: %s", firstNonNull(jiraIssueWithDetails.getAsignee(), NOT_ASSIGNED)));
    }

    private void printIssueDescription(PrintWriter printWriter) {
        printWriter.println(jiraIssueWithDetails.getDescription());
    }

    private void generateDivider(PrintWriter printWriter, int length) {
        StringBuilder dividerBuffer = new StringBuilder();
        for (int index = 0; index < length; index++) {
            dividerBuffer.append(DIVIDER_CHARACTER);
        }
        printWriter.println(dividerBuffer.toString());
    }
}

package org.reactor.jira.response;

import static java.lang.String.format;

import java.io.PrintWriter;

import org.reactor.jira.model.JiraSprintWithDetails;
import org.reactor.response.StringReactorResponse;

public class JiraSprintDetailsResponse extends StringReactorResponse {

    public static final char DIVIDER_CHARACTER = '-';
    private JiraSprintWithDetails jiraSprintWithDetails;

    public JiraSprintDetailsResponse(JiraSprintWithDetails jiraSprintWithDetails) {
        this.jiraSprintWithDetails = jiraSprintWithDetails;
    }

    @Override
    protected void printResponse(PrintWriter printWriter) {
        printSprintHeader(printWriter);
        printSprintDates(printWriter);
    }

    private void printSprintDates(PrintWriter printWriter) {
        printWriter.println(format("Start date: %s", jiraSprintWithDetails.getStartDate()));
        if (jiraSprintWithDetails.isActive()) {
            printWriter.println("This sprint is still active!");
        } else {
            printWriter.println(format("Completed date: %s", jiraSprintWithDetails.getCompleteDate()));
        }
    }

    private void printSprintHeader(PrintWriter printWriter) {
        String header = jiraSprintWithDetails.getName();
        printWriter.println(header);
        generateDivider(printWriter, header.length());
    }

    private void generateDivider(PrintWriter printWriter, int length) {
        StringBuilder dividerBuffer = new StringBuilder();
        for (int index = 0; index < length; index++) {
            dividerBuffer.append(DIVIDER_CHARACTER);
        }
        printWriter.println(dividerBuffer.toString());
    }
}

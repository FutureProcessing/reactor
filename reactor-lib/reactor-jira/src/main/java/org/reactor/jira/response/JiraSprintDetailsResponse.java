package org.reactor.jira.response;

import org.reactor.annotation.ToBeDeleted;
import org.reactor.jira.model.JiraSprintWithDetails;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

import java.util.StringJoiner;

import static java.lang.String.format;

public class JiraSprintDetailsResponse implements ReactorResponse {

    private transient static final char DIVIDER_CHARACTER = '-';
    private JiraSprintWithDetails jiraSprintWithDetails;

    public JiraSprintDetailsResponse(JiraSprintWithDetails jiraSprintWithDetails) {
        this.jiraSprintWithDetails = jiraSprintWithDetails;
    }

    @Override
    public String toConsoleOutput() {
        String header = jiraSprintWithDetails.getName();
        String startDate = format("Start date: %s", jiraSprintWithDetails.getStartDate());
        String completeDate = (jiraSprintWithDetails.isActive())? "This sprint is still active!" : format("Completed date: %s", jiraSprintWithDetails.getCompleteDate());
        return new StringJoiner("\n").add(header).add(startDate).add(completeDate).toString();
    }

    @ToBeDeleted
    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        printSprintHeader(responseRenderer);
        printSprintDates(responseRenderer);
    }

    private void printSprintDates(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderTextLine("Start date: %s", jiraSprintWithDetails.getStartDate());
        if (jiraSprintWithDetails.isActive()) {
            responseRenderer.renderTextLine("This sprint is still active!");
        } else {
            responseRenderer.renderTextLine("Completed date: %s", jiraSprintWithDetails.getCompleteDate());
        }
    }

    private void printSprintHeader(ReactorResponseRenderer responseRenderer) {
        String header = jiraSprintWithDetails.getName();
        responseRenderer.renderTextLine(header);
        generateDivider(responseRenderer, header.length());
    }

    private void generateDivider(ReactorResponseRenderer responseRenderer, int length) {
        StringBuilder dividerBuffer = new StringBuilder();
        for (int index = 0; index < length; index++) {
            dividerBuffer.append(DIVIDER_CHARACTER);
        }
        responseRenderer.renderTextLine(dividerBuffer.toString());
    }
}

package org.reactor.jira.response;

import org.reactor.jira.model.JiraSprintWithDetails;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;

public class JiraSprintDetailsResponse implements ReactorResponse {

    private static final char DIVIDER_CHARACTER = '-';
    private JiraSprintWithDetails jiraSprintWithDetails;

    public JiraSprintDetailsResponse(JiraSprintWithDetails jiraSprintWithDetails) {
        this.jiraSprintWithDetails = jiraSprintWithDetails;
    }

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

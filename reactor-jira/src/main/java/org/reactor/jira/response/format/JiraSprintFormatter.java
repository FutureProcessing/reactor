package org.reactor.jira.response.format;

import static java.lang.String.format;

import org.reactor.jira.model.JiraSprint;
import org.reactor.response.list.ListElementFormatter;

public class JiraSprintFormatter implements ListElementFormatter<JiraSprint> {

    @Override
    public String formatListElement(long elementIndex, JiraSprint jiraSprint) {
        if (jiraSprint.isActive()) {
            return formatActiveSprint(elementIndex, jiraSprint);
        }
        return formatClosedSprint(elementIndex, jiraSprint);
    }

    private String formatClosedSprint(long elementIndex, JiraSprint jiraSprint) {
        return format("%d. Name: %s, start date: %s, complete date: %s CLOSED", elementIndex, jiraSprint.getName(),
                jiraSprint.getStartDate() != null ? jiraSprint.getStartDate() : "",
                jiraSprint.getStartDate() != null ? jiraSprint.getCompleteDate() : "");
    }

    private String formatActiveSprint(long elementIndex, JiraSprint jiraSprint) {
        return format("%d. Name: %s, start date: %s [ACTIVE]", elementIndex, jiraSprint.getName(),
                jiraSprint.getStartDate() != null ? jiraSprint.getStartDate() : "");
    }
}

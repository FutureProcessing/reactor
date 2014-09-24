package org.reactor.jira.response;

import static java.lang.String.format;
import org.reactor.response.list.ListElementFormatter;

public class JiraSprintFormatter implements ListElementFormatter<JiraSprint> {

    @Override
    public String formatListElement(long elementIndex, JiraSprint jiraSprint) {
        return format("name: %s, start date: %s, complete date: %s,", jiraSprint.getName(),
            jiraSprint.getStartDate() != null ? jiraSprint.getStartDate() : "",
            jiraSprint.getStartDate() != null ? jiraSprint.getCompleteDate() : "");
    }

}

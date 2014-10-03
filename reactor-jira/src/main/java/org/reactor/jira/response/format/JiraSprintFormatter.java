package org.reactor.jira.response.format;

import static java.lang.String.format;

import org.reactor.jira.model.JiraSprint;
import org.reactor.response.list.ListElementFormatter;

public class JiraSprintFormatter implements ListElementFormatter<JiraSprint> {

    @Override
    public String formatListElement(long elementIndex, JiraSprint jiraSprint) {
        return format("%d. [%d] %s - <%s>", elementIndex, jiraSprint.getId(), jiraSprint.getName(), (jiraSprint.isActive() ? "OPEN"
                : "CLOSED"));
    }
}

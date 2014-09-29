package org.reactor.jira.response.format;

import org.reactor.jira.model.JiraIssue;
import org.reactor.response.list.ListElementFormatter;

import static java.lang.String.format;

public class JiraIssueFormatter implements ListElementFormatter<JiraIssue> {

    @Override
    public String formatListElement(long elementIndex, JiraIssue listElement) {
        return format("%d. [%s] %s - <%s>", elementIndex, listElement.getKey(), listElement.getSummary(),
                listElement.getStatus().toUpperCase());
    }
}

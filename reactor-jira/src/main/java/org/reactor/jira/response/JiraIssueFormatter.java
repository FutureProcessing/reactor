package org.reactor.jira.response;

import static java.lang.String.format;
import org.reactor.response.list.ListElementFormatter;

public class JiraIssueFormatter implements ListElementFormatter<JiraIssue> {

    @Override
    public String formatListElement(long elementIndex, JiraIssue listElement) {
        return format("%d. key: '%s', summary: '%s', status: '%s', description: [%s]", elementIndex, listElement.getKey(), listElement.getSummary(), listElement.getStatus(), listElement.getDescription());
    }

}

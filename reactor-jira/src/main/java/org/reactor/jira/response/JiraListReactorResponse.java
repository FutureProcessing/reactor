package org.reactor.jira.response;

import com.google.common.collect.Lists;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import java.util.List;


public class JiraListReactorResponse extends ListReactorResponse<JiraIssue> {

    List<JiraIssue> issues = Lists.newArrayList();
    
    @Override
    protected Iterable<JiraIssue> getElements() {
        return issues;
    }

    @Override
    protected ListElementFormatter<JiraIssue> getElementFormatter() {
        return new JiraIssueFormatter();
    }

    public JiraListReactorResponse(List<JiraIssue> issues) {
        this.issues = issues;
    }

}

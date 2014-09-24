package org.reactor.jira;

import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorInitializationException;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.jira.command.BeHappyReactor;
import org.reactor.jira.command.EnvironmentVariableReactor;
import org.reactor.jira.command.PingReactor;
import org.reactor.jira.request.UppercaseRequestData;
import org.reactor.jira.response.JiraIssue;
import org.reactor.jira.response.JiraIssueFormatter;
import org.reactor.jira.response.JiraListReactorResponse;
import org.reactor.jira.response.JiraSprint;
import org.reactor.jira.response.JiraSprintFormatter;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

import java.net.URISyntaxException;
import java.util.List;

@ReactOn(value = "jira", description = "Jira reactor - some description will be here")
public class JiraReactor extends AbstractNestingReactor {

    private JiraService jiraService;

    @ReactOn(value = "uppercase", description = "Prints given text in uppercase")
    public ReactorResponse uppercase(ReactorRequest<UppercaseRequestData> message) {
        return new StringReactorResponse(message.getRequestData().getMessage().toUpperCase());
    }

    public void initNestingJiraReactorReactor(JiraReactorProperties reactorProperties) {
        registerNestedReactor(new BeHappyReactor());
        registerNestedReactor(new EnvironmentVariableReactor());
        registerNestedReactor(new PingReactor());
        try {
            jiraService = JiraService.forServerDetails(reactorProperties.getUrl(), reactorProperties.getUsername(),
                reactorProperties.getPassword(), reactorProperties.getProjectName());
        } catch (URISyntaxException e) {
            throw new ReactorInitializationException(e);
        }

    }

    @Override
    protected void initNestingReactor(ReactorProperties properties) {
        initNestingJiraReactorReactor(new JiraReactorProperties(properties));
    }

    @ReactOn(value = "issues", description = "Lists all issues")
    public ReactorResponse listAllIssues(ReactorRequest<Void> reactorRequest) {
        List<JiraIssue> issues = jiraService.getAllIssues();
        return new JiraListReactorResponse<JiraIssue>(issues, new JiraIssueFormatter());
    }
    
    @ReactOn(value = "sprints", description = "Lists all sprints (started and completed)")
    public ReactorResponse listAllSprints(ReactorRequest<Void> reactorRequest) {
        List<JiraSprint> issues = jiraService.getAllSprints();
        return new JiraListReactorResponse<JiraSprint>(issues, new JiraSprintFormatter());
    }

}

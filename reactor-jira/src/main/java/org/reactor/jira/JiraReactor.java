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
import org.reactor.jira.response.JiraListReactorResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import org.reactor.jira.JiraService;
import java.net.URISyntaxException;
import java.util.List;

@ReactOn(value = "jira", description = "Jira reactor - some description will be here")
public class JiraReactor extends AbstractNestingReactor {

    JiraService jiraService;

    @ReactOn(value = "uppercase", description = "Prints given text in uppercase")
    public ReactorResponse uppercase(ReactorRequest<UppercaseRequestData> message) {
        return new StringReactorResponse(message.getRequestData().getMessage().toUpperCase());
    }

    public void initNestingReactor(JiraReactorProperties reactorProperties) {
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
        initNestingReactor(new JiraReactorProperties(properties));
    }

    @ReactOn(value = "issues", description = "List all issues")
    public ReactorResponse listAllIssues(ReactorRequest<Void> reactorRequest) {
        List<JiraIssue> issues = jiraService.getAllIssues();
        return new JiraListReactorResponse(issues);
    }
}

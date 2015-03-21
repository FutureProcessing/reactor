package org.reactor.jira;

import static org.reactor.jira.JiraService.forProperties;

import java.net.URISyntaxException;
import java.util.List;

import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorInitializationException;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.jira.model.JiraIssue;
import org.reactor.jira.model.JiraSprint;
import org.reactor.jira.request.IssueDetailsRequestData;
import org.reactor.jira.request.ListIssuesRequestData;
import org.reactor.jira.request.SprintDetailsRequestData;
import org.reactor.jira.response.JiraIssueDetailsResponse;
import org.reactor.jira.response.JiraListReactorResponse;
import org.reactor.jira.response.JiraSprintDetailsResponse;
import org.reactor.jira.response.format.JiraIssueFormatter;
import org.reactor.jira.response.format.JiraSprintFormatter;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

@ReactOn(value = "jira", description = "Jira reactor")
public class JiraReactor extends AbstractNestingReactor {

    private JiraService jiraService;

    public void initNestingJiraReactorReactor(JiraReactorProperties reactorProperties) {
        try {
            jiraService = forProperties(reactorProperties.getUrl(), reactorProperties.getUsername(),
                reactorProperties.getPassword(), reactorProperties.getProjectName(),
                reactorProperties.getAgileBoardId(), reactorProperties.getServerLocale());
        } catch (URISyntaxException e) {
            throw new ReactorInitializationException(e);
        }
    }

    @Override
    protected void initNestingReactor(ReactorProperties properties) {
        initNestingJiraReactorReactor(new JiraReactorProperties(properties));
    }

    @ReactOn(value = "issues", description = "Lists all issues")
    public ReactorResponse listIssues(ReactorRequest<ListIssuesRequestData> reactorRequest) {
        List<JiraIssue> issues = jiraService.findIssues(reactorRequest.getRequestData().getStatus());
        return new JiraListReactorResponse<>(issues, new JiraIssueFormatter());
    }

    @ReactOn(value = "issue", description = "Displays details of issue with given key")
    public ReactorResponse displayIssueDetails(ReactorRequest<IssueDetailsRequestData> reactorRequest) {
        IssueDetailsRequestData requestData = reactorRequest.getRequestData();
        return new JiraIssueDetailsResponse(jiraService.getIssueWithDetails(requestData.getIssueKey()),
            requestData.isStatusOnly());
    }

    @ReactOn(value = "sprints", description = "Lists all sprints (started and completed)")
    public ReactorResponse listSprints(ReactorRequest<Void> reactorRequest) {
        List<JiraSprint> sprints = jiraService.getAllSprints();
        return new JiraListReactorResponse<>(sprints, new JiraSprintFormatter());
    }

    @ReactOn(value = "sprint", description = "Displays details of sprint with given id")
    public ReactorResponse displaySprintDetails(ReactorRequest<SprintDetailsRequestData> reactorRequest) {
        return new JiraSprintDetailsResponse(jiraService.getSprintWithDetails(reactorRequest.getRequestData().getSprintId()));
    }
}

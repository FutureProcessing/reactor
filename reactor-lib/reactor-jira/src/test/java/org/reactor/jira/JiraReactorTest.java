package org.reactor.jira;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.jira.model.JiraIssue;
import org.reactor.jira.model.JiraIssueWithDetails;
import org.reactor.jira.model.JiraSprint;
import org.reactor.jira.model.JiraSprintWithDetails;
import org.reactor.jira.request.IssueDetailsRequestData;
import org.reactor.jira.request.ListIssuesRequestData;
import org.reactor.jira.request.SprintDetailsRequestData;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

import java.util.Date;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class JiraReactorTest extends AbstractUnitTest {

    private static final String ISSUE_KEY = "issueKey";
    private static final String ISSUE_SUMMARY = "issueSummary";
    private static final String ISSUE_STATUS = "issueStatus";
    private static final String ISSUE_DESCRIPTION = "issueDescription";
    private static final String ISSUE_ASSIGNEE = "issueAssignee";
    private static final String ISSUE_URL = "issueUrl";
    private static final boolean ALL_DATA = false;
    private static final boolean STATUS_ONLY = true;

    private static final int SPRINT_ID = 1;
    private static final String SPRINT_NAME = "sprintName";
    private static final boolean SPRINT_ACTIVE = true;
    private static final boolean SPRINT_INACTIVE = false;
    public static final String SPRINT_OPEN = "OPEN";
    public static final String SPRINT_CLOSED = "CLOSED";
    public static final Date SPRINT_START_DATE = new Date();
    @Mock
    JiraService mockedJiraService;

    @InjectMocks
    JiraReactor jiraReactor;

    @Test
    public void shouldListIssues() {
        // given
        given(mockedJiraService.findIssues(ISSUE_STATUS)).willReturn(newArrayList(new JiraIssue(ISSUE_KEY, ISSUE_SUMMARY, ISSUE_STATUS)));

        // when
        ReactorResponse result = jiraReactor.listIssues(reactorRequest(new ListIssuesRequestData(ISSUE_STATUS)));

        // then
        assertThat(result.toConsoleOutput()).contains(ISSUE_STATUS.toUpperCase()).contains(ISSUE_SUMMARY).contains(ISSUE_KEY);
    }

    @Test
    public void shouldDisplayIssueDetails() {
        // given
        given(mockedJiraService.getIssueWithDetails(ISSUE_KEY)).willReturn(new JiraIssueWithDetails(ISSUE_KEY, ISSUE_SUMMARY, ISSUE_DESCRIPTION, ISSUE_STATUS, ISSUE_ASSIGNEE, ISSUE_URL));

        // when
        ReactorResponse result = jiraReactor.displayIssueDetails(reactorRequest(new IssueDetailsRequestData(ISSUE_KEY, ALL_DATA)));

        // then
        assertThat(result.toConsoleOutput()).contains(ISSUE_KEY).contains(ISSUE_SUMMARY).contains(ISSUE_STATUS.toUpperCase()).contains(ISSUE_URL).contains(ISSUE_ASSIGNEE).contains(ISSUE_DESCRIPTION);
    }

    @Test
    public void shouldDisplayIssueDetailsWithStatusOnly() {
        // given
        given(mockedJiraService.getIssueWithDetails(ISSUE_KEY)).willReturn(new JiraIssueWithDetails(ISSUE_KEY, ISSUE_SUMMARY, ISSUE_DESCRIPTION, ISSUE_STATUS, ISSUE_ASSIGNEE, ISSUE_URL));

        // when
        ReactorResponse result = jiraReactor.displayIssueDetails(reactorRequest(new IssueDetailsRequestData(ISSUE_KEY, STATUS_ONLY)));

        // then
        assertThat(result.toConsoleOutput()).contains(ISSUE_STATUS.toUpperCase()).doesNotContain(ISSUE_KEY).doesNotContain(ISSUE_SUMMARY).doesNotContain(ISSUE_URL).doesNotContain(ISSUE_ASSIGNEE).doesNotContain(ISSUE_DESCRIPTION);
    }

    @Test
    public void shouldListSprints() {
        // given
        given(mockedJiraService.getAllSprints()).willReturn(newArrayList(new JiraSprint(SPRINT_ID, SPRINT_NAME, SPRINT_ACTIVE)));

        // when
        ReactorResponse result = jiraReactor.listSprints(reactorRequest());

        // then
        assertThat(result.toConsoleOutput()).contains(String.valueOf(SPRINT_ID)).contains(SPRINT_NAME).contains(SPRINT_OPEN);
    }

    @Test
    public void shouldListInactiveSprints() {
        // given
        given(mockedJiraService.getAllSprints()).willReturn(newArrayList(new JiraSprint(SPRINT_ID, SPRINT_NAME, SPRINT_INACTIVE)));

        // when
        ReactorResponse result = jiraReactor.listSprints(reactorRequest());

        // then
        assertThat(result.toConsoleOutput()).contains(String.valueOf(SPRINT_ID)).contains(SPRINT_NAME).contains(SPRINT_CLOSED);
    }

    @Test
    public void shouldDisplaySprintDetails() {
        // given
        given(mockedJiraService.getSprintWithDetails(SPRINT_ID)).willReturn(new JiraSprintWithDetails(SPRINT_ID, SPRINT_NAME, SPRINT_ACTIVE, SPRINT_START_DATE, null));

        // when
        ReactorResponse result = jiraReactor.displaySprintDetails(reactorRequest(new SprintDetailsRequestData(SPRINT_ID)));

        // then
        assertThat(result.toConsoleOutput()).contains(SPRINT_NAME).contains(SPRINT_START_DATE.toString()).contains("This sprint is still active!");
    }

    private <T> ReactorRequest<T> reactorRequest() {
        return new ReactorRequest<>(null, null, null);
    }

    private <T> ReactorRequest<T> reactorRequest(T data) {
        return new ReactorRequest<>(null, null, data);
    }
}
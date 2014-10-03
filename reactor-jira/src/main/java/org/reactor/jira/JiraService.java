package org.reactor.jira;

import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.transform;
import static org.reactor.jira.JiraQueryBuilder.forProject;
import static org.reactor.jira.model.JiraIssueBuilder.FROM_JIRA_ISSUE;
import static org.reactor.jira.model.JiraIssueWithDetailsBuilder.FROM_JIRA_ISSUE_DETAILS;
import static org.reactor.jira.model.JiraSprintBuilder.FROM_GREENHOPPER_SPRINT;
import static org.reactor.jira.model.JiraSprintWithDetailsBuilder.FROM_GREENHOPPER_SPRINT_DETAILS;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Locale;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Issue.SearchResult;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;
import net.rcarz.jiraclient.greenhopper.SprintReport;

import org.reactor.ReactorProcessingException;
import org.reactor.jira.model.JiraIssue;
import org.reactor.jira.model.JiraIssueWithDetails;
import org.reactor.jira.model.JiraSprint;
import org.reactor.jira.model.JiraSprintWithDetails;

public class JiraService {

    private static final String QUERY_STATUS = "status";

    private final GreenHopperClient greenHopperClient;
    private final JiraClient jiraClient;
    private final String projectName;
    private final int agileBoardId;

    private JiraService(String serverUrl, String username, String password, String projectName, int agileBoardId,
                        Locale serverLocale) throws URISyntaxException {
        this.projectName = projectName;
        this.agileBoardId = agileBoardId;

        jiraClient = new JiraClient(serverUrl, serverLocale, new BasicCredentials(username, password));
        greenHopperClient = new GreenHopperClient(jiraClient);
    }

    public static JiraService forProperties(String serverUrl, String username, String password, String projectName,
                                            int agileBoardId, Locale serverLocale) throws URISyntaxException {
        return new JiraService(serverUrl, username, password, projectName, agileBoardId, serverLocale);
    }

    public List<JiraIssue> findIssues(String status) {
        try {
            JiraQueryBuilder queryBuilder = forProject(projectName);
            if (!isNullOrEmpty(status)) {
                queryBuilder.andFor(QUERY_STATUS, status);
            }
            SearchResult searchResult = jiraClient.searchIssues(queryBuilder.build());
            return transform(searchResult.issues, FROM_JIRA_ISSUE);
        } catch (JiraException e) {
            throw new ReactorProcessingException(e);
        }
    }

    public JiraIssueWithDetails getIssueWithDetails(String issueKey) {
        try {
            return FROM_JIRA_ISSUE_DETAILS.apply(jiraClient.getIssue(issueKey));
        } catch (JiraException e) {
            throw new ReactorProcessingException(e);
        }
    }

    public List<JiraSprint> getAllSprints() {
        try {
            RapidView board = greenHopperClient.getRapidView(agileBoardId);
            return transform(board.getSprints(), FROM_GREENHOPPER_SPRINT);
        } catch (JiraException e) {
            throw new ReactorProcessingException(e);
        }
    }

    public JiraSprintWithDetails getSprintWithDetails(int sprintId) {
        try {
            RapidView board = greenHopperClient.getRapidView(agileBoardId);
            SprintReport sprintReport = board.getSprintReport(sprintId);
            return FROM_GREENHOPPER_SPRINT_DETAILS.apply(sprintReport.getSprint());
        } catch (JiraException e) {
            throw new ReactorProcessingException(e);
        }
    }
}

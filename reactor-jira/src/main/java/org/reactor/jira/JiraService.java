package org.reactor.jira;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Lists.newArrayList;
import static org.reactor.jira.JiraQueryBuilder.forProject;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import net.rcarz.jiraclient.*;
import net.rcarz.jiraclient.Issue.SearchResult;
import net.rcarz.jiraclient.greenhopper.GreenHopperClient;
import net.rcarz.jiraclient.greenhopper.RapidView;
import net.rcarz.jiraclient.greenhopper.Sprint;
import net.rcarz.jiraclient.greenhopper.SprintReport;

import org.reactor.ReactorProcessingException;
import org.reactor.jira.model.JiraIssue;
import org.reactor.jira.model.JiraIssueWithDetails;
import org.reactor.jira.model.JiraSprint;

public class JiraService {

    private static final String STATUS_UNKNOWN = "Unknown";
    private static final String QUERY_STATUS = "status";

    private final GreenHopperClient greenHopperClient;
    private final JiraClient jiraClient;
    private final String projectName;
    private final int agileBoardId;

    private JiraService(String serverUrl, String username, String password, String projectName, int agileBoardId)
            throws URISyntaxException {
        this.projectName = projectName;
        this.agileBoardId = agileBoardId;

        jiraClient = new JiraClient(serverUrl, new BasicCredentials(username, password));
        greenHopperClient = new GreenHopperClient(jiraClient);
    }

    public static JiraService forProperties(String serverUrl, String username, String password, String projectName,
                                            int agileBoardId) throws URISyntaxException {
        return new JiraService(serverUrl, username, password, projectName, agileBoardId);
    }

    public List<JiraIssue> findIssues(String status) {
        List<JiraIssue> result = newArrayList();
        try {
            JiraQueryBuilder queryBuilder = forProject(projectName);
            if (!isNullOrEmpty(status)) {
                queryBuilder.andFor(QUERY_STATUS, status);
            }
            SearchResult searchResult = jiraClient.searchIssues(queryBuilder.build());
            for (Issue foundIssue : searchResult.issues) {
                result.add(new JiraIssue(foundIssue.getKey(), foundIssue.getSummary(),
                    foundIssue.getStatus() != null ? foundIssue.getStatus().getName() : STATUS_UNKNOWN));
            }
            return result;
        } catch (JiraException e) {
            throw new ReactorProcessingException(e);
        }
    }

    public JiraIssueWithDetails getIssueWithDetails(String issueKey) {
        try {
            Issue foundIssue = jiraClient.getIssue(issueKey);
            User asignee = foundIssue.getAssignee();

            JiraIssueWithDetails jiraIssueWithDetails = new JiraIssueWithDetails(issueKey, foundIssue.getSummary(),
                foundIssue.getDescription(), foundIssue.getStatus().getName());
            if (asignee != null) {
                jiraIssueWithDetails.setAsignee(asignee.getDisplayName());
            }
            return jiraIssueWithDetails;
        } catch (JiraException e) {
            throw new ReactorProcessingException(e);
        }
    }

    public List<JiraSprint> getAllSprints() {
        List<JiraSprint> result = newArrayList();
        try {
            RapidView board = greenHopperClient.getRapidView(agileBoardId);

            List<Sprint> sprints = board.getSprints();
            for (Sprint sprint : sprints) {
                SprintReport sprintReport = board.getSprintReport(sprint);
                Sprint sprintData = firstNonNull(sprintReport.getSprint(), sprint);

                Date startDate = sprintData.getStartDate() != null ? sprintData.getStartDate().toDate() : null;
                Date completeDate = sprintData.getCompleteDate() != null ? sprintData.getCompleteDate().toDate() : null;
                result.add(new JiraSprint(sprintData.getName(), startDate, completeDate, !sprintData.isClosed()));
            }
        } catch (JiraException e) {
            throw new ReactorProcessingException(e);
        }
        return result;
    }
}
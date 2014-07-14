package org.reactor.jira;

import static com.google.common.collect.Lists.newArrayList;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.SearchResult;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import org.reactor.jira.response.JiraIssue;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class JiraService {

    JiraRestClient jiraClient;
    private String projectName;

    public static JiraService forServerDetails(String serverUrl, String username, String password, String projectName)
            throws URISyntaxException {
        return new JiraService(serverUrl, username, password, projectName);
    }

    private JiraService() {}

    public JiraService(String serverUrl, String username, String password, String projectName)
            throws URISyntaxException {
        this.projectName = projectName;
        JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
        URI uri = new URI(serverUrl);
        jiraClient = factory.createWithBasicHttpAuthentication(uri, username, password);
    }

    public List<JiraIssue> getAllIssues() {
        List<JiraIssue> result = newArrayList();
        String jql = String.format("project = %s AND status = 'In Progress'", projectName);
        Promise<SearchResult> searchResultPromise = jiraClient.getSearchClient().searchJql(jql);
        SearchResult searchResult = searchResultPromise.claim();
        for (Issue issue : searchResult.getIssues()) {
            JiraIssue jiraIssue = new JiraIssue(issue.getKey(), issue.getSummary(), issue.getDescription(), issue
                .getStatus().getName());
            result.add(jiraIssue);
        }
        return result;
    }

}

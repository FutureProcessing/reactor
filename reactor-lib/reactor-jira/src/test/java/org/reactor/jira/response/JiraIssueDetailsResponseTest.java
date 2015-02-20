package org.reactor.jira.response;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.jira.model.JiraIssueWithDetails;

import static org.fest.assertions.Assertions.assertThat;

public class JiraIssueDetailsResponseTest extends AbstractUnitTest {

    private static final String KEY = "key";
    private static final String SUMMARY = "summary";
    private static final String DESCRIPTION = "descrption";
    private static final String STATUS = "status";
    private static final String ASSIGNEE = "assignee";
    private static final String URL = "url";
    private JiraIssueWithDetails jiraIssueWithDetails = new JiraIssueWithDetails(KEY, SUMMARY, DESCRIPTION, STATUS, ASSIGNEE, URL);

    @Test
    public void shouldPrintToConsoleOutput() {
        // given
        JiraIssueDetailsResponse jiraIssueDetailsResponse = new JiraIssueDetailsResponse(jiraIssueWithDetails, false);

        // when
        String result = jiraIssueDetailsResponse.toConsoleOutput();

        // then
        assertThat(result).contains(KEY).contains(SUMMARY).contains(DESCRIPTION).contains(STATUS.toUpperCase()).contains(ASSIGNEE).contains(URL);
    }

    @Test
    public void shouldPrintToConsoleOutputStatusOnly() {
        // given
        JiraIssueDetailsResponse jiraIssueDetailsResponse = new JiraIssueDetailsResponse(jiraIssueWithDetails, true);

        // when
        String result = jiraIssueDetailsResponse.toConsoleOutput();

        // then
        assertThat(result).contains(STATUS.toUpperCase()).doesNotContain(KEY).doesNotContain(SUMMARY).doesNotContain(DESCRIPTION).doesNotContain(ASSIGNEE).doesNotContain(URL);
    }
}
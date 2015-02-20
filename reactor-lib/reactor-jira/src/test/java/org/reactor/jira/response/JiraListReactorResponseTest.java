package org.reactor.jira.response;


import org.junit.Test;
import org.reactor.jira.model.JiraIssue;
import org.reactor.jira.response.format.JiraIssueFormatter;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.fest.assertions.Assertions.assertThat;

public class JiraListReactorResponseTest {

    private static final String KEY = "key";
    private static final String SUMMARY = "summary";
    private static final String STATUS = "status";
    public static final String TWO_ISSUES = "2";

    @Test
    public void shouldPrintToConsoleOutput() {
        // given
        JiraIssue jiraIssue = new JiraIssue(KEY, SUMMARY, STATUS);
        List<JiraIssue> issues = newArrayList(jiraIssue, jiraIssue);
        JiraIssueFormatter issueFormatter = new JiraIssueFormatter();
        JiraListReactorResponse<JiraIssue> response = new JiraListReactorResponse<>(issues, issueFormatter);

        // when
        String result = response.toConsoleOutput();

        // then
        assertThat(result).contains(TWO_ISSUES).contains(KEY).contains(SUMMARY).contains(STATUS.toUpperCase());
    }
}
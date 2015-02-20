package org.reactor.jira.response;


import org.junit.Test;
import org.reactor.jira.model.JiraSprintWithDetails;

import java.util.Date;

import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;

public class JiraSprintDetailsResponseTest {

    private static final int ID = 1;
    private static final String NAME = "name";
    private static final Date START_DATE = new Date();
    private static final Date COMPLETE_DATE = new Date();
    public static final boolean ACTIVE = true;
    public static final boolean INACTIVE = false;

    @Test
    public void shouldPrintActiveSprintToConsoleOutput() {
        // given
        JiraSprintWithDetails jiraSprintWithDetails = new JiraSprintWithDetails(ID, NAME, ACTIVE, START_DATE, null);
        JiraSprintDetailsResponse response = new JiraSprintDetailsResponse(jiraSprintWithDetails);

        // when
        String result = response.toConsoleOutput();

        // then
        assertThat(result).contains(NAME).contains(format("Start date: %s", START_DATE)).contains("This sprint is still active!");
    }

    @Test
    public void shouldPrintInactiveSprintToConsoleOutput() {
        // given
        JiraSprintWithDetails jiraSprintWithDetails = new JiraSprintWithDetails(ID, NAME, INACTIVE, START_DATE, COMPLETE_DATE);
        JiraSprintDetailsResponse response = new JiraSprintDetailsResponse(jiraSprintWithDetails);

        // when
        String result = response.toConsoleOutput();

        // then
        assertThat(result).contains(NAME).contains(format("Start date: %s", START_DATE)).contains(format("Completed date: %s", COMPLETE_DATE));
    }
}
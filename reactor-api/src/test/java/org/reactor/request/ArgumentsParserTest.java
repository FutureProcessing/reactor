package org.reactor.request;

import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.request.ArgumentsParser.parseArguments;

import org.junit.Test;
import org.reactor.AbstractUnitTest;

public class ArgumentsParserTest extends AbstractUnitTest {

    public static final String JIRA_TRIGGER = "jira";
    public static final String JIRA_COMMAND = JIRA_TRIGGER
            + " ticketadd -t dupa -b \"To jest jakis ticket jeszcze nie wiem jaki\"";
    public static final String JIRA_COMMAND_SPACES = "               " + JIRA_TRIGGER
            + "         ticketadd -t dupa -b \"To jest jakis ticket jeszcze nie wiem jaki\"";

    @Test
    public void shouldParseAllArguments() {
        // when
        String[] arguments = parseArguments(JIRA_COMMAND);

        // then
        assertThat(arguments).hasSize(6);
    }

    @Test
    public void shouldIgnoreAdditionalSpaces() {
        // when
        String[] arguments = parseArguments(JIRA_COMMAND_SPACES);

        // then
        assertThat(arguments[0]).isEqualTo(JIRA_TRIGGER);
    }
}

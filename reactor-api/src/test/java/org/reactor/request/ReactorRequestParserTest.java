package org.reactor.request;

import static org.fest.assertions.Assertions.assertThat;
import static org.reactor.request.ArgumentsParser.parseArguments;
import static org.reactor.request.ReactorRequestParser.parseRequestFromLine;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.request.ReactorRequest;
import org.reactor.request.ReactorRequestParsingException;

public class ReactorRequestParserTest extends AbstractUnitTest {

    private static final String SEPARATOR = " ";
    private static final String TRIGGER = "trigger";
    private static final String ARGUMENT1 = "argument1";
    private static final String ARGUMENT2 = "argument2";
    private static final String ARGUMENTS = ARGUMENT1 + SEPARATOR + ARGUMENT2;
    private static final String NOTHING = "";
    private static final String SENDER = "TEST";

    @Test
    public void shouldNotParseRequestWhenArgumentLengthIsLessThanTwo() {
        // then
        expectedException.expect(ReactorRequestParsingException.class);

        // when
        parseRequestFromLine(SENDER, NOTHING);
    }

    @Test
    public void shouldParseRequestWithMinimalArgumentLength() {
        // when
        ReactorRequest reactorRequest = parseRequestFromLine(SENDER, TRIGGER);

        // then
        assertThat(reactorRequest.getTrigger()).isEqualTo(TRIGGER);
    }

    @Test
    public void shouldParseRequestWithMoreArguments() {
        // when
        ReactorRequest reactorRequest = parseRequestFromLine(SENDER, TRIGGER + SEPARATOR + ARGUMENTS);

        // then
        assertThat(reactorRequest.getTrigger()).isEqualTo(TRIGGER);
        assertThat(reactorRequest.getArguments()).isEqualTo(parseArguments(ARGUMENTS));
    }

}

package org.reactor.request.parser;

import static java.lang.Integer.parseInt;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.request.ReactorRequest;

public class ReactorRequestPrimitiveDataParserTest extends AbstractUnitTest {

    private static final String SENDER = "sender";
    private static final String TRIGGER = "trigger";

    private static final String VALUE_STRING = "string value";
    private static final String VALUE_INTEGER = "123";
    private static final String VALUE_VOID = null;

    public static final int INTEGER_ZERO = 0;


    @Test
    public void shouldParseStringWhenDataTypeIsString() {
        // given
        ReactorRequestPrimitiveDataParser<String> dataParser = givenPrimitiveDataParser(String.class);

        // when
        ReactorRequest<String> reactorRequest = dataParser.parseRequestWithData(SENDER, TRIGGER, VALUE_STRING);

        // then
        assertThat(reactorRequest.getRequestData()).isEqualTo(VALUE_STRING);
    }

    /* @Test
    public void shouldParseVoidWhenDataTypeIsVoid() {
        // given
        ReactorRequestPrimitiveDataParser<Void> dataParser = givenPrimitiveDataParser(Void.class);

        // when
        ReactorRequest<Void> reactorRequest = dataParser.parseRequestWithData(SENDER, TRIGGER, null);

        // then
        assertThat(reactorRequest.getRequestData()).isNull();
    }*/

    @Test
    public void shouldParseIntegerWhenDataTypeIsInteger() {
        // given
        ReactorRequestPrimitiveDataParser<Integer> dataParser = givenPrimitiveDataParser(Integer.class);

        // when
        ReactorRequest<Integer> reactorRequest = dataParser.parseRequestWithData(SENDER, TRIGGER, VALUE_INTEGER);

        // then
        assertThat(reactorRequest.getRequestData()).isEqualTo(parseInt(VALUE_INTEGER));
    }

    @Test
    public void shouldSetZeroWhenDataTypeIsIntegerAndInputDataMismatch() {
        // given
        ReactorRequestPrimitiveDataParser<Integer> dataParser = givenPrimitiveDataParser(Integer.class);

        // when
        ReactorRequest<Integer> reactorRequest = dataParser.parseRequestWithData(SENDER, TRIGGER, VALUE_STRING);

        // then
        assertThat(reactorRequest.getRequestData()).isEqualTo(INTEGER_ZERO);
    }

    private <T> ReactorRequestPrimitiveDataParser<T> givenPrimitiveDataParser(Class<T> dataType) {
        return new ReactorRequestPrimitiveDataParser<T>(dataType);
    }
}

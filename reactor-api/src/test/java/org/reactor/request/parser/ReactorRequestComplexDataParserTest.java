package org.reactor.request.parser;

import static java.lang.String.format;
import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.request.ReactorRequest;
import org.reactor.request.ReactorRequestParsingException;

public class ReactorRequestComplexDataParserTest extends AbstractUnitTest {

    private static final String SENDER = "sender";
    private static final String TRIGGER = "trigger";

    private static final String VALUE_STRING = "string";
    private static final String VALUE_INTEGER = "123";
    private static final String VALUE_BOOLEAN = "true";


    @Test
    public void shouldParseAllGivenParameters() {
        // given
        ReactorRequestComplexDataParser<ReactorRequestComplexDataWithAnnotations> dataParser = givenDataParser(ReactorRequestComplexDataWithAnnotations.class);

        // when
        ReactorRequest<ReactorRequestComplexDataWithAnnotations> reactorRequest = dataParser.parseRequestWithData(
            SENDER, TRIGGER, format("--f1 %s --f3 %s --f2 %s", VALUE_STRING, VALUE_INTEGER, VALUE_BOOLEAN));

        // then
        ReactorRequestComplexDataWithAnnotations requestData = reactorRequest.getRequestData();
        assertThat(requestData.getField1()).isEqualTo(VALUE_STRING);
        assertThat(requestData.isField2()).isEqualTo(Boolean.valueOf(VALUE_BOOLEAN));
        assertThat(requestData.getField3()).isEqualTo(Integer.valueOf(VALUE_INTEGER));
    }

    @Test
    public void shouldThrowAnExceptionWhenRequiredParameterIsMissing() {
        // given
        ReactorRequestComplexDataParser<ReactorRequestComplexDataWithAnnotations> dataParser = givenDataParser(ReactorRequestComplexDataWithAnnotations.class);

        // then
        expectedException.expect(ReactorRequestParsingException.class);

        // when
        dataParser.parseRequestWithData(
                SENDER, TRIGGER, format("--f1 %s --f2 %s", VALUE_STRING, VALUE_BOOLEAN));
    }

    private <T> ReactorRequestComplexDataParser<T> givenDataParser(Class<T> dataType) {
        return new ReactorRequestComplexDataParser(dataType);
    }
}

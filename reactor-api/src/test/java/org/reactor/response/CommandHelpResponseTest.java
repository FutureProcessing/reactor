package org.reactor.response;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import java.io.PrintWriter;
import org.junit.Test;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.Reactor;
import org.reactor.request.parser.ReactorRequestParameterDefinition;

public class CommandHelpResponseTest extends AbstractUnitTest {

    private static final String REASON = "Some reason";
    private static final String PARAM1_NAME = "param1";
    private static final String PARAM1_SHORT_NAME = "p1";
    private static final String PARAM1_DESCRIPTION = "Description for parameter 1";
    private static final String PARAM2_NAME = "param2";
    private static final String PARAM2_SHORT_NAME = "p2";
    private static final String PARAM2_DESCRIPTION = "Description for parameter 2";
    private static final String PARAM3_NAME = "param3";
    private static final String PARAM3_SHORT_NAME = "p3";
    private static final String PARAM3_DESCRIPTION = "Description for parameter 3";
    private static final String TRIGGERING_EXPRESSION = "expression";

    @Mock
    private Reactor reactor;

    private CommandHelpResponse commandHelpResponse;

    @Test
    public void shouldIncludeAllParametersOnHelpText() {
        // given
        givenReactor();
        givenCommandHelpResponse(givenArguments());

        // when
        PrintWriter writer = spy(new PrintWriter(System.out));
        commandHelpResponse.printResponse(writer);
        writer.flush();

        // then
        verify(writer).print("usage: expression -p1 <arg> -p2 <arg> -p3 <arg>");
    }

    private void givenReactor() {
        given(reactor.getTriggeringExpression()).willReturn(TRIGGERING_EXPRESSION);
    }

    private void givenCommandHelpResponse(Iterable<ReactorRequestParameterDefinition> arguments) {
        commandHelpResponse = new CommandHelpResponse(REASON, reactor, arguments);
    }

    private Iterable<ReactorRequestParameterDefinition> givenArguments() {
        return newArrayList(
            givenParameterDefinition(PARAM1_NAME, PARAM1_SHORT_NAME, PARAM1_DESCRIPTION, String.class, true),
            givenParameterDefinition(PARAM2_NAME, PARAM2_SHORT_NAME, PARAM2_DESCRIPTION, int.class, true),
            givenParameterDefinition(PARAM3_NAME, PARAM3_SHORT_NAME, PARAM3_DESCRIPTION, boolean.class, true));
    }

    private ReactorRequestParameterDefinition givenParameterDefinition(String name, String shortName,
                                                                       String description, Class type, boolean required) {
        ReactorRequestParameterDefinition parameterDefinition = mock(ReactorRequestParameterDefinition.class);
        given(parameterDefinition.getName()).willReturn(name);
        given(parameterDefinition.getShortName()).willReturn(shortName);
        given(parameterDefinition.getDescription()).willReturn(description);
        given(parameterDefinition.getType()).willReturn(type);
        given(parameterDefinition.isRequired()).willReturn(required);
        return parameterDefinition;
    }
}

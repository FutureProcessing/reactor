package org.reactor.renderer;

import org.junit.Test;
import org.reactor.AbstractUnitTest;
import org.reactor.renderer.test.stub.ResponseObject;

import static org.fest.assertions.Assertions.assertThat;

public class JSONReactorResponseRendererTest extends AbstractUnitTest {

    private static final String HEADER = "header";
    private static final Double DOUBLE = 1.1d;
    private static final Long LONG = 200L;
    private static final String STRING = "text line";
    public static final Object TRANSIENT = new Object();
    private JSONReactorResponseRenderer jsonRenderer = new JSONReactorResponseRenderer();

    @Test
    public void shouldHaveAllEntriesInOutputJSON() {
        // given
        ResponseObject responseObject = new ResponseObject();
        responseObject.setHeader(HEADER);
        responseObject.setDoubleValue(DOUBLE);
        responseObject.setLongValue(LONG);
        responseObject.setString(STRING);
        responseObject.setTransientValue(TRANSIENT);

        // when
        String result = jsonRenderer.render(responseObject);

        // then
        assertThat(result).contains(HEADER).contains(DOUBLE.toString()).contains(LONG.toString()).contains(STRING).doesNotContain(TRANSIENT.toString());
    }
}

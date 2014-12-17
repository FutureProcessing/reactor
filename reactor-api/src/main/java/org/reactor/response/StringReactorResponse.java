package org.reactor.response;

import org.reactor.response.renderer.ReactorResponseRenderer;

import static java.lang.String.format;

public class StringReactorResponse implements ReactorResponse {

    private static final String EMPTY_RESPONSE = "";

    private final StringBuffer response;

    public StringReactorResponse(String response) {
        this.response = new StringBuffer(response);
    }

    public StringReactorResponse() {
        this(EMPTY_RESPONSE);
    }

    public StringReactorResponse(String responseTemplate, Object... templateVariables) {
        this.response = new StringBuffer(format(responseTemplate, templateVariables));
    }

    @Override
    public final void renderResponse(ReactorResponseRenderer responseRenderer) throws ResponseRenderingException {
        responseRenderer.renderTextLine(response.toString());
    }
}

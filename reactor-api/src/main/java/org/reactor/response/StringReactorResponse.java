package org.reactor.response;

import static java.lang.String.format;
import java.io.PrintWriter;
import java.io.Writer;

public class StringReactorResponse implements ReactorResponse {

    private static final String EMPTY_RESPONSE = "";

    private final StringBuffer response;

    public StringReactorResponse(String response) {
        this.response = new StringBuffer(response);
    }

    public StringReactorResponse() {
        this(EMPTY_RESPONSE);
    }

    public StringReactorResponse(String responseTemplate, String... templateVariables) {
        this.response = new StringBuffer(format(responseTemplate, templateVariables));
    }

    @Override
    public final void renderResponse(Writer responseWriter) throws ResponseRenderingException {
        PrintWriter printWriter = new PrintWriter(responseWriter);
        printWriter.print(response.toString());
        printResponse(printWriter);

        printWriter.flush();
    }

    protected void printResponse(PrintWriter printWriter) {

    }
}

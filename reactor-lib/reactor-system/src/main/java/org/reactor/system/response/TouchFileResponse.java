package org.reactor.system.response;

import org.reactor.response.StringReactorResponse;

public class TouchFileResponse extends StringReactorResponse {

    public TouchFileResponse(String fileName, boolean touched) {
        super("File '%s' touched: %s", fileName, "" + touched);
    }
}

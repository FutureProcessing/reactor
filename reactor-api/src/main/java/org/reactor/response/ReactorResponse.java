package org.reactor.response;

import java.io.Writer;

public interface ReactorResponse {

    void renderResponse(Writer responseWriter) throws Exception;
}
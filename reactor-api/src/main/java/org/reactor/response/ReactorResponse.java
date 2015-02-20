package org.reactor.response;

import org.reactor.annotation.ToBeDeleted;
import org.reactor.response.renderer.ReactorResponseRenderer;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang3.builder.ToStringStyle.SIMPLE_STYLE;

public interface ReactorResponse {

    default String toConsoleOutput() {
        return reflectionToString(this, SIMPLE_STYLE);
    }

    @ToBeDeleted
    default void renderResponse(ReactorResponseRenderer responseRenderer) throws Exception {
    }
}
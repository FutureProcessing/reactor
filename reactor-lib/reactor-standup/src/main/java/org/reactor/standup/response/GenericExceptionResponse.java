package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;

public class GenericExceptionResponse extends StringReactorResponse {

    public GenericExceptionResponse(Throwable throwable) {
        super(throwable.getMessage());
    }
}

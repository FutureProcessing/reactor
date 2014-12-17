package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;

public class StandupAlreadyBeganResponse extends StringReactorResponse {

    private static final String STANDUP_ALREADY_BEGAN_MESSAGE = "Standup session have been already started.";

    public StandupAlreadyBeganResponse() {
        super(STANDUP_ALREADY_BEGAN_MESSAGE);
    }
}

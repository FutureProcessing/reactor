package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;

public class StandupAlreadyRunnningResponse extends StringReactorResponse {

    private static final String STANDUP_ALREADY_RUNNING_MESSAGE = "There is already a standup running";

    public StandupAlreadyRunnningResponse() {
        super(STANDUP_ALREADY_RUNNING_MESSAGE);
    }
}

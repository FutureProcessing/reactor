package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;

public class StandupNotRunnningYetResponse extends StringReactorResponse {

    private static final String STANDUP_NOT_YET_RUNNING_MESSAGE = "Standup session is not yet running, wait for it.";

    public StandupNotRunnningYetResponse() {
        super(STANDUP_NOT_YET_RUNNING_MESSAGE);
    }
}

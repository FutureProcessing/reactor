package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;

public class NoStandupResponse extends StringReactorResponse {

    private static final String NO_STANDUP_MESSAGE = "There is no standup currently running.";

    public NoStandupResponse() {
        super(NO_STANDUP_MESSAGE);
    }
}

package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;

public class StandupSessionUserJoinedResponse extends StringReactorResponse {

    public StandupSessionUserJoinedResponse(String userId) {
        super("User %s joined session.", userId);
    }
}

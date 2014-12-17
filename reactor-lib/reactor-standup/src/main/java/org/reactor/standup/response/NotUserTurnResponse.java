package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;

public class NotUserTurnResponse extends StringReactorResponse {

    private static final String NOT_YOUR_TURN_MESSAGE = "It's not your turn, %s.";

    public NotUserTurnResponse(String userId) {
        super(NOT_YOUR_TURN_MESSAGE, userId);
    }
}

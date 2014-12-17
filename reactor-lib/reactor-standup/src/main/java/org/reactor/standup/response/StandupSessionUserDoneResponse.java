package org.reactor.standup.response;

import org.reactor.response.StringReactorResponse;
import org.reactor.standup.StandupSession;

public class StandupSessionUserDoneResponse extends StringReactorResponse {

    public StandupSessionUserDoneResponse(String userId, StandupSession standupSession) {
        super("User %s finished speaking, %s please go next.", userId, standupSession.getCurrentUser().getId());
    }
}

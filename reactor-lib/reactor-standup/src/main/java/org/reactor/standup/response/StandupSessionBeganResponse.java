package org.reactor.standup.response;

import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.standup.StandupSession;

public class StandupSessionBeganResponse implements ReactorResponse {

    private final StandupSession currentSession;

    public StandupSessionBeganResponse(StandupSession currentSession) {
        this.currentSession = currentSession;
    }

    @Override
    public void renderResponse(ReactorResponseRenderer responseRenderer) {
        responseRenderer.renderHeadLine("Standup session with %d participant(s) starting just now.",
                currentSession.getStandupUsersCount());
        responseRenderer.renderTextLine("Please go first, %s.", currentSession.getCurrentUser().getId());
    }
}

package org.reactor.standup.response;

import static java.lang.String.format;

import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import org.reactor.standup.StandupSession;
import org.reactor.standup.StandupUser;

public class StandupSessionStatusResponse extends ListReactorResponse<StandupUser> {

    private final StandupSession currentSession;

    public StandupSessionStatusResponse(StandupSession currentSession) {
        super("Standup session created at %tc by %s, %d participant(s).", currentSession.getCreationDate(),
            currentSession.getSessionOwner(), currentSession.getStandupUsersCount());

        this.currentSession = currentSession;
    }

    @Override
    protected Iterable<StandupUser> getElements() {
        return currentSession.getStandupUsers();
    }

    @Override
    protected ListElementFormatter<StandupUser> getElementFormatter() {
        return (elementIndex, listElement) -> format("%d. %s - %s", elementIndex, listElement.getId(),
            getStatusText(listElement));
    }

    private String getStatusText(StandupUser listElement) {
        if (currentSession.isRunning() && currentSession.isCurrentUser(listElement.getId())) {
            return format("[speaking %d seconds already]", listElement.getSpeakingDurationInSeconds());
        }
        if (listElement.hasSpoken()) {
            return format("spoken (%d seconds)", listElement.getSpeakingDurationInSeconds());
        }
        return "not spoken yet";
    }
}

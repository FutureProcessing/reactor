package org.reactor.standup.response;

import static java.lang.String.format;

import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import org.reactor.standup.StandupSession;
import org.reactor.standup.StandupUser;

public class StandupSessionFinishedResponse extends ListReactorResponse<StandupUser> {

    private final StandupSession currentSession;

    public StandupSessionFinishedResponse(StandupSession currentSession) {
        super("Standup session finished at %tc, took %d seconds, %d participant(s).", currentSession.getFinishedDate(),
            currentSession.getDurationInSeconds(), currentSession.getStandupUsersCount());

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
        if (listElement.hasSpoken()) {
            return format("spoken (%d seconds)", listElement.getSpeakingDurationInSeconds());
        }
        return "not spoken yet";
    }
}

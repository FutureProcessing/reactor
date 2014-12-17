package org.reactor.standup.response;

import static java.lang.String.format;

import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import org.reactor.standup.StandupSession;
import org.reactor.standup.StandupUser;

public class StandupSessionCreatedResponse extends ListReactorResponse<StandupUser> {

    private final StandupSession currentSession;

    public StandupSessionCreatedResponse(StandupSession currentSession) {
        super("Standup session with (0) participants created by %s, please join at wish.", currentSession.getSessionOwner());

        this.currentSession = currentSession;
    }

    @Override
    protected Iterable<StandupUser> getElements() {
        return currentSession.getStandupUsers();
    }

    @Override
    protected ListElementFormatter<StandupUser> getElementFormatter() {
        return (elementIndex, listElement) -> format("%n. %s %s", elementIndex, listElement.getId(),
                getStatusText(listElement));
    }

    private String getStatusText(StandupUser listElement) {
        if (listElement.hasSpoken()) {
            return "[SPOKEN]";
        }
        return " - not yet spoken -";
    }
}

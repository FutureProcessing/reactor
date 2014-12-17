package org.reactor.standup;

public class StandupSessionFactory {

    public StandupSession newSession(String sessionOwner) {
        return new StandupSession(sessionOwner);
    }
}

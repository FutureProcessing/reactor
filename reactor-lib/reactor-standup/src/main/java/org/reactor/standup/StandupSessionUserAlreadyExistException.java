package org.reactor.standup;

import static java.lang.String.format;

public class StandupSessionUserAlreadyExistException extends Throwable {

    public StandupSessionUserAlreadyExistException(String standupUserId) {
        super(format("User with id '%s' already exists in current standup session.", standupUserId));
    }
}

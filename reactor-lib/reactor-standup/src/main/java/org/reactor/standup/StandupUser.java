package org.reactor.standup;

import java.util.Date;

import static com.google.common.base.Objects.firstNonNull;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class StandupUser {

    private final Date spokenStarted;
    private Date spokenFinished;

    private final String id;
    private boolean spoken;

    public StandupUser(String id) {
        spokenStarted = new Date();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSpoken() {
        if (hasSpoken()) {
            return;
        }
        this.spoken = true;
        spokenFinished = new Date();
    }

    public boolean hasSpoken() {
        return spoken;
    }

    public boolean equalsId(String userId) {
        return id.equals(userId);
    }

    public long getSpeakingDurationInSeconds() {
        return MILLISECONDS.toSeconds(firstNonNull(spokenFinished, new Date()).getTime() - spokenStarted.getTime());
    }
}

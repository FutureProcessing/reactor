package org.reactor.standup;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.tryFind;
import static com.google.common.collect.Lists.newLinkedList;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class StandupSession {

    private final Date creationDate;
    private final List<StandupUser> notSpoken = newLinkedList();
    private final List<StandupUser> spoken = newLinkedList();
    private final String sessionOwner;

    private boolean running;
    private int currentUserIndex;
    private Date finishedDate;

    public StandupSession(String sessionOwner) {
        this.sessionOwner = sessionOwner;
        creationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Iterable<StandupUser> getStandupUsers() {
        return concat(notSpoken, spoken);
    }

    public String getSessionOwner() {
        return sessionOwner;
    }

    public void beginStandup() {
        selectNextUser();
        running = true;
    }

    private boolean hasNextUser() {
        return !notSpoken.isEmpty();
    }

    private void selectNextUser() {
        currentUserIndex = new Random().nextInt(notSpoken.size());
    }

    public boolean isRunning() {
        return running;
    }

    public void join(String standupUserId) throws StandupSessionUserAlreadyExistException {
        if (tryFind(notSpoken, standupUser -> standupUser.equalsId(standupUserId)).isPresent()) {
            throw new StandupSessionUserAlreadyExistException(standupUserId);
        }
        notSpoken.add(new StandupUser(standupUserId));
    }

    public boolean isCurrentUser(String userId) {
        StandupUser currentUser = getCurrentUser();
        return currentUser.equalsId(userId);
    }

    public StandupUser getCurrentUser() {
        return notSpoken.get(currentUserIndex);
    }

    public void userDone() {
        StandupUser spokenUser = notSpoken.remove(currentUserIndex);
        spokenUser.setSpoken();
        spoken.add(spokenUser);

        if (hasNextUser()) {
            selectNextUser();
        } else {
            finishStandup();
        }
    }

    public Date getFinishedDate() {
        return finishedDate;
    }

    private void finishStandup() {
        if (!isRunning()) {
            return;
        }
        running = false;
        finishedDate = new Date();
    }

    public int getStandupUsersCount() {
        return notSpoken.size() + spoken.size();
    }

    public long getDurationInSeconds() {
        return MILLISECONDS.toSeconds(firstNonNull(finishedDate, new Date()).getTime() - creationDate.getTime());
    }
}

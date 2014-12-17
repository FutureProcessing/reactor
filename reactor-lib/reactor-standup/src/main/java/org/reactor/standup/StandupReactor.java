package org.reactor.standup;

import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.standup.response.GenericExceptionResponse;
import org.reactor.standup.response.NoStandupResponse;
import org.reactor.standup.response.NotUserTurnResponse;
import org.reactor.standup.response.StandupAlreadyBeganResponse;
import org.reactor.standup.response.StandupAlreadyRunnningResponse;
import org.reactor.standup.response.StandupNotRunnningYetResponse;
import org.reactor.standup.response.StandupSessionBeganResponse;
import org.reactor.standup.response.StandupSessionCreatedResponse;
import org.reactor.standup.response.StandupSessionFinishedResponse;
import org.reactor.standup.response.StandupSessionStatusResponse;
import org.reactor.standup.response.StandupSessionUserDoneResponse;
import org.reactor.standup.response.StandupSessionUserJoinedResponse;

@ReactOn(value = "standup", description = "Reactor for handling standup process")
public class StandupReactor extends AbstractNestingReactor {

    private StandupSessionFactory sessionFactory;
    private StandupSession currentSession;

    @ReactOn(value = "new", description = "Creates new standup process and awaits it's participants")
    public ReactorResponse newStandup(ReactorRequest<Void> reactorRequest) {
        if (isSessionAvailable() && currentSession.isRunning()) {
            return new StandupAlreadyRunnningResponse();
        }
        currentSession = sessionFactory.newSession(reactorRequest.getSender());
        return new StandupSessionCreatedResponse(currentSession);
    }

    @ReactOn(value = "begin", description = "Begins standup with all registered participants")
    public ReactorResponse beginStandup(ReactorRequest<Void> reactorRequest) {
        if (!isSessionAvailable()) {
            return new NoStandupResponse();
        }
        if (currentSession.isRunning()) {
            return new StandupAlreadyBeganResponse();
        }
        currentSession.beginStandup();
        return new StandupSessionBeganResponse(currentSession);
    }

    @ReactOn(value = "finish", description = "Finishes currently running standup process")
    public ReactorResponse finishStandup(ReactorRequest<Void> reactorRequest) {
        return null;
    }

    @ReactOn(value = "status", description = "Prints out information about currently running standup process")
    public ReactorResponse standupStatus(ReactorRequest<Void> reactorRequest) {
        if (!isSessionAvailable()) {
            return new NoStandupResponse();
        }
        return new StandupSessionStatusResponse(currentSession);
    }

    @ReactOn(value = "join", description = "Joining as session participant")
    public ReactorResponse joinStandup(ReactorRequest<Void> reactorRequest) {
        if (!isSessionAvailable()) {
            return new NoStandupResponse();
        }
        if (currentSession.isRunning()) {
            return new StandupAlreadyBeganResponse();
        }
        try {
            currentSession.join(reactorRequest.getSender());
        } catch (StandupSessionUserAlreadyExistException e) {
            return new GenericExceptionResponse(e);
        }
        return new StandupSessionUserJoinedResponse(reactorRequest.getSender());
    }

    @ReactOn(value = "done", description = "Notify system that you have just finished speaking")
    public ReactorResponse done(ReactorRequest<Void> reactorRequest) {
        if (!isSessionAvailable()) {
            return new NoStandupResponse();
        }
        if (!currentSession.isRunning()) {
            return new StandupNotRunnningYetResponse();
        }
        if (!currentSession.isCurrentUser(reactorRequest.getSender())) {
            return new NotUserTurnResponse(reactorRequest.getSender());
        }
        currentSession.userDone();
        if (currentSession.isRunning()) {
            return new StandupSessionUserDoneResponse(reactorRequest.getSender(), currentSession);
        }
        return new StandupSessionFinishedResponse(currentSession);
    }

    private boolean isSessionAvailable() {
        return currentSession != null;
    }

    @Override
    protected void initNestingReactor(ReactorProperties properties) {
        sessionFactory = new StandupSessionFactory();
    }
}

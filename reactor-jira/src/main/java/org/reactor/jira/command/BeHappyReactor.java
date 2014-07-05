package org.reactor.jira.command;

import org.reactor.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "behappy_now", description = "Prints out some nice info :)")
public class BeHappyReactor extends AbstractAnnotatedReactor<Void> {

    public BeHappyReactor() {
        super(Void.class);
    }

    @Override
    protected ReactorResponse doReact(ReactorRequest<Void> reactorRequest) {
        return new StringReactorResponse("I am so much happy :-D");

    }
}

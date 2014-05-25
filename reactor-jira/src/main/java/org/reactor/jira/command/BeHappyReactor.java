package org.reactor.jira.command;


import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "behappy_now", description = "Prints out some nice info :)")
public class BeHappyReactor extends AbstractAnnotatedReactor {

    @Override
    public ReactorResponse doReact(ReactorRequest request) {
        return new StringReactorResponse("I am so much happy :-D");
    }
}

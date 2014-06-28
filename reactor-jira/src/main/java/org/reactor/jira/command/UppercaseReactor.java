package org.reactor.jira.command;

import org.reactor.AbstractReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "uppercase", description = "Prints given text in upper case")
public class UppercaseReactor extends AbstractReactor<String> {

    public UppercaseReactor() {
        super(String.class);
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<String> request) {
        return new StringReactorResponse(request.getRequestData().toUpperCase());
    }

}

package org.reactor.jira.command;

import static com.google.common.base.Joiner.on;

import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "uppercase", description = "Prints given text in upper case")
public class UppercaseReactor extends AbstractAnnotatedReactor {

    @Override
    public ReactorResponse doReact(ReactorRequest request) {
        return new StringReactorResponse(on(' ').join(request.getArguments()).toUpperCase());
    }

}

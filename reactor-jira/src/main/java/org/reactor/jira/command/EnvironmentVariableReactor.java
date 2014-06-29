package org.reactor.jira.command;


import org.reactor.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.jira.response.EnvEntriesTableResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

@ReactOn(value = "env", description = "Prints out list of environmental variables")
public class EnvironmentVariableReactor extends AbstractAnnotatedReactor<Void> {

    public EnvironmentVariableReactor() {
        super(Void.class);
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<Void> request) {
        return new EnvEntriesTableResponse();
    }
}

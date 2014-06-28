package org.reactor.jira;

import org.reactor.AbstractNestingReactor;
import org.reactor.InitializingReactor;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.command.PrintNestedReactorsReactor;
import org.reactor.jira.command.BeHappyReactor;
import org.reactor.jira.command.EnvironmentVariableReactor;
import org.reactor.jira.command.PingReactor;
import org.reactor.jira.request.UppercaseRequestData;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "!jira", description = "Jira reactor - some description will be here")
public class JiraReactor extends AbstractNestingReactor implements InitializingReactor {

    @ReactOn(value = "uppercase", description = "Prints given text in uppercase")
    public ReactorResponse uppercase(ReactorRequest<UppercaseRequestData> message) {
        return new StringReactorResponse(message.getRequestData().getMessage().toUpperCase());
    }

    @Override
    public void initReactor(ReactorProperties reactorProperties) {
        registerNestedReactor(new PrintNestedReactorsReactor(this));

        registerNestedReactor(new BeHappyReactor());
        registerNestedReactor(new EnvironmentVariableReactor());
        registerNestedReactor(new PingReactor());
    }

}

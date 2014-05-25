package org.reactor.jira;

import org.reactor.jira.command.BeHappyReactor;
import org.reactor.jira.command.EnvironmentVariableReactor;
import org.reactor.jira.command.PingReactor;
import org.reactor.InitializingReactor;
import org.reactor.ReactorProperties;
import org.reactor.annotation.AbstractAnnotatedNestingReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.annotation.ReactorRequestParameter;
import org.reactor.command.PrintNestedReactorsReactor;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "!jira", description = "Jira reactor - some description will be here")
public class JiraReactor extends AbstractAnnotatedNestingReactor implements InitializingReactor {

    @ReactOn(value = "uppercase", description = "Prints given text in uppercase")
    public ReactorResponse uppercase(@ReactorRequestParameter(name = "message", shortName = "m", required = true) String message) {
        return new StringReactorResponse(message.toUpperCase());
    }

    @Override
    public void initReactor(ReactorProperties reactorProperties) {
        registerNestedReactor(new PrintNestedReactorsReactor(this));

        registerNestedReactor(new BeHappyReactor());
        registerNestedReactor(new EnvironmentVariableReactor());
        registerNestedReactor(new PingReactor());
    }

}

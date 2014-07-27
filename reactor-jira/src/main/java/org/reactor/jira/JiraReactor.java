package org.reactor.jira;

import static java.lang.System.currentTimeMillis;
import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.jira.command.BeHappyReactor;
import org.reactor.jira.command.EnvironmentVariableReactor;
import org.reactor.jira.command.PingReactor;
import org.reactor.jira.request.UppercaseRequestData;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@ReactOn(value = "jira", description = "Jira reactor - some description will be here")
public class JiraReactor extends AbstractNestingReactor {

    private final static Logger LOG = LoggerFactory.getLogger(JiraReactor.class);

    @ReactOn(value = "uppercase", description = "Prints given text in uppercase")
    public ReactorResponse uppercase(ReactorRequest<UppercaseRequestData> message) {
        sleep(message.getRequestData().getSecondsDelay());
        return new StringReactorResponse(String.format("%s %d", message.getRequestData().getMessage().toUpperCase(), currentTimeMillis()));
    }

    private void sleep(int delayInSeconds) {
        if (delayInSeconds > 0) {
            try {
                Thread.sleep(delayInSeconds * 1000);
            } catch (InterruptedException e) {
                LOG.error("An error occurred while pausing delay", e);
            }
        }
    }

    @Override
    public void initNestingReactor(ReactorProperties reactorProperties) {
        registerNestedReactor(new BeHappyReactor());
        registerNestedReactor(new EnvironmentVariableReactor());
        registerNestedReactor(new PingReactor());
    }
}

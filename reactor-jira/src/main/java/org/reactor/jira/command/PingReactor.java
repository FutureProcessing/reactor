package org.reactor.jira.command;

import static com.google.common.base.Joiner.on;
import org.reactor.Reactor;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

public class PingReactor implements Reactor {

    @Override
    public String getTriggeringExpression() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Sends back passed list of arguments";
    }

    @Override
    public ReactorResponse react(String sender, ReactorRequestInput requestInput) {
        return new StringReactorResponse(on(' ').join(requestInput.getArguments()));
    }

    @Override
    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {

    }
}

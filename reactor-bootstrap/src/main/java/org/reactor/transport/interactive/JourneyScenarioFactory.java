package org.reactor.transport.interactive;

import static org.reactor.travelling.JourneyScenarioBuilder.forSubject;

import org.reactor.Reactor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.travelling.JourneyScenario;
import org.reactor.travelling.JourneyScenarioBuilder;

import java.io.Writer;

public class JourneyScenarioFactory {

    private Reactor reactor;

    private JourneyScenarioFactory(Reactor reactor) {
        this.reactor = reactor;
    }

    public static JourneyScenario<ReactorRequestInput> prepareReactorJourneyScenario(Reactor reactor, Writer responseWriter) {
        return new JourneyScenarioFactory(reactor).prepareJourneyScenario(responseWriter);
    }

    public JourneyScenario<ReactorRequestInput> prepareJourneyScenario(Writer responseWriter) {
        ReactorRequestInput requestInput = new ReactorRequestInput(reactor.getTriggeringExpression());
        JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder = forSubject(requestInput);
        prepareScenarioBuilder(scenarioBuilder, responseWriter);
        return scenarioBuilder.build();
    }

    private void prepareScenarioBuilder(JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder, Writer responseWriter) {
        JourneyScenarioReactorTopologyVisitor topologyVisitor = new JourneyScenarioReactorTopologyVisitor(
            scenarioBuilder, responseWriter);
        reactor.accept(topologyVisitor);
    }
}

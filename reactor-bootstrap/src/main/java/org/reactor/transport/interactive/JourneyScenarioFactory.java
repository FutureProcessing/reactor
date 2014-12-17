package org.reactor.transport.interactive;

import static org.reactor.travelling.JourneyScenarioBuilder.forSubject;

import org.reactor.Reactor;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.travelling.JourneyScenario;
import org.reactor.travelling.JourneyScenarioBuilder;

public class JourneyScenarioFactory {

    private Reactor reactor;

    private JourneyScenarioFactory(Reactor reactor) {
        this.reactor = reactor;
    }

    public static JourneyScenario<ReactorRequestInput> prepareReactorJourneyScenario(Reactor reactor,
                                                                                     ReactorResponseRenderer responseRenderer) {
        return new JourneyScenarioFactory(reactor).prepareJourneyScenario(responseRenderer);
    }

    public JourneyScenario<ReactorRequestInput> prepareJourneyScenario(ReactorResponseRenderer responseRenderer) {
        ReactorRequestInput requestInput = new ReactorRequestInput(reactor.getTriggeringExpression());
        JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder = forSubject(requestInput);
        prepareScenarioBuilder(scenarioBuilder, responseRenderer);
        return scenarioBuilder.build();
    }

    private void prepareScenarioBuilder(JourneyScenarioBuilder<ReactorRequestInput> scenarioBuilder,
                                        ReactorResponseRenderer responseRenderer) {
        JourneyScenarioReactorTopologyVisitor topologyVisitor = new JourneyScenarioReactorTopologyVisitor(
            scenarioBuilder, responseRenderer);
        reactor.accept(topologyVisitor);
    }
}

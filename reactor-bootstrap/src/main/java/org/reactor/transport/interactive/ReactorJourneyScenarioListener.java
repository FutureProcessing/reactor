package org.reactor.transport.interactive;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;

public interface ReactorJourneyScenarioListener {

    void reactorJourneyEnded(ReactorRequestInput generatedInput, String sender, ReactorResponseRenderer responseRenderer);
}

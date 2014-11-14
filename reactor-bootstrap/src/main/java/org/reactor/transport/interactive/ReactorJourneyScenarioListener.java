package org.reactor.transport.interactive;

import java.io.Writer;

import org.reactor.request.ReactorRequestInput;

public interface ReactorJourneyScenarioListener {

    void reactorJourneyEnded(ReactorRequestInput generatedInput, String sender, Writer responseWriter);
}

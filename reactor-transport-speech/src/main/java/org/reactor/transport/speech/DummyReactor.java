package org.reactor.transport.speech;

import org.reactor.AbstractNestingReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "lights", description = "Controls lights in house")
public class DummyReactor extends AbstractNestingReactor {

    private boolean on = false;

    @ReactOn(value = "shutdown", description = "Turns lights off")
    public ReactorResponse off(ReactorRequest<Void> reactorRequest) {
        if (!on) {
            return new StringReactorResponse("Lights are already turned off");
        }
        on = false;
        return new StringReactorResponse("Lights turned off");
    }

    @ReactOn(value = "turn on", description = "Turns lights on")
    public ReactorResponse on(ReactorRequest<Void> reactorRequest) {
        if (on) {
            return new StringReactorResponse("Lights are already turned on");
        }
        on = true;
        return new StringReactorResponse("Lights turned on");
    }

    @ReactOn(value = "status", description = "Checks current lights status")
    public ReactorResponse status(ReactorRequest<Void> reactorRequest) {
        return new StringReactorResponse("Lights are turned " + ((on) ? "on" : "off"));
    }
}
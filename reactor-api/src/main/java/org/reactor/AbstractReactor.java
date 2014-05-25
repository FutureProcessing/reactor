package org.reactor;

import static org.reactor.response.NoResponse.NO_RESPONSE;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractReactor implements Reactor {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractReactor.class);

    @Override
    public final ReactorResponse react(ReactorRequest reactorRequest) {
        if (!reactorRequest.triggerMatches(getTriggeringExpression())) {
            LOG.warn("Trigger does not match triggering expression: {} != {}", reactorRequest.getTrigger(),
                getTriggeringExpression());
            return NO_RESPONSE;
        }
        return doReact(reactorRequest);

    }

    protected abstract ReactorResponse doReact(ReactorRequest reactorRequest);
}

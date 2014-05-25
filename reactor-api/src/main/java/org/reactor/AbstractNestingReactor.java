package org.reactor;

import static com.google.common.collect.Lists.newArrayList;
import static org.reactor.request.ReactorRequestParser.parseNestedRequest;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import java.util.List;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNestingReactor extends AbstractReactor {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractNestingReactor.class);

    private List<Reactor> nestedReactors = newArrayList();

    public final ReactorResponse doReact(ReactorRequest reactorRequest) {
        ReactorRequest nestedRequest = parseNestedRequest(reactorRequest);
        for (Reactor nestedReactor : nestedReactors) {
            if (nestedRequest.triggerMatches(nestedReactor.getTriggeringExpression()))
                return nestedReactor.react(nestedRequest);
        }
        LOG.debug("Unable to find nested reactor for trigger {}", nestedRequest.getTrigger());
        return NO_RESPONSE;
    }

    protected final void registerNestedReactor(Reactor nestedReactor) {
        validateNewNestedReactor(nestedReactor);
        nestedReactors.add(nestedReactor);
    }

    public final List<Reactor> getNestedReactors() {
        return nestedReactors;
    }

    private void validateNewNestedReactor(Reactor nestedReactor) {
        if (this.equals(nestedReactor)) {
            throw new ReactorInitializationException("Can not register itself as nested reactor!");
        }
    }
}

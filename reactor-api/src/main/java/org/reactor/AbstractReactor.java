package org.reactor;

import static org.reactor.response.NoResponse.NO_RESPONSE;

import org.reactor.annotation.ReactOn;
import org.reactor.annotation.ReactorAnnotationMissingException;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;
import org.reactor.request.parser.AbstractRequestDataParser;
import org.reactor.request.parser.ReactorRequestDataParser;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractReactor<T> implements Reactor {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractReactor.class);
    private final AbstractRequestDataParser<T> dataParser;

    private String reactorTrigger;
    private String reactorDescription;

    public AbstractReactor(Class<T> requestDataType) {
        this.dataParser = new ReactorRequestDataParser<>(requestDataType);
        readReactorAnnotations();
    }

    private void readReactorAnnotations() {
        Class<?> reactorClass = this.getClass();
        if (!reactorClass.isAnnotationPresent(ReactOn.class)) {
            throw new ReactorAnnotationMissingException(this);
        }
        ReactOn reactOn = reactorClass.getAnnotation(ReactOn.class);
        reactorTrigger = reactOn.value();
        reactorDescription = reactOn.description();
    }

    @Override
    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {
        dataParser.accept(topologyVisitor);
    }

    @Override
    public ReactorResponse react(String sender, String reactorInput) {
        ReactorRequest<T> reactorRequest = dataParser.parseRequestWithData(sender, reactorTrigger, reactorInput);
        return react(reactorRequest);
    }

    private ReactorResponse react(ReactorRequest<T> reactorRequest) {
        if (!reactorRequest.triggerMatches(getTriggeringExpression())) {
            LOG.warn("Trigger does not match triggering expression: {} != {}", reactorRequest.getTrigger(),
                getTriggeringExpression());
            return NO_RESPONSE;
        }
        return doReact(reactorRequest);

    }

    protected abstract ReactorResponse doReact(ReactorRequest<T> reactorRequest);

    @Override
    public final String getTriggeringExpression() {
        return reactorTrigger;
    }

    @Override
    public final String getDescription() {
        return reactorDescription;
    }
}

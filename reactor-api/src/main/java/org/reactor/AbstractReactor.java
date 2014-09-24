package org.reactor;

import static com.google.common.base.Throwables.getRootCause;
import static org.reactor.request.parser.AbstractReactorRequestDataParser.forDataType;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;
import org.reactor.request.ReactorRequestInput;
import org.reactor.request.ReactorRequestParsingException;
import org.reactor.request.parser.AbstractReactorRequestDataParser;
import org.reactor.response.CommandHelpResponse;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractReactor<T> implements Reactor {

    private final static Logger LOG = LoggerFactory.getLogger(AbstractReactor.class);
    private final AbstractReactorRequestDataParser<T> dataParser;

    public AbstractReactor(Class<T> requestDataType) {
        this.dataParser = forDataType(requestDataType);
    }

    @Override
    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {
        dataParser.accept(topologyVisitor);
    }

    @Override
    public final ReactorResponse react(String sender, ReactorRequestInput requestInput) {
        try {
            return react(dataParser.parseRequestWithData(sender, getTriggeringExpression(), requestInput.popArguments()));
        } catch (ReactorRequestParsingException e) {
            LOG.error("An error occurred while parsing Request", e);
            return new CommandHelpResponse(getRootCause(e).getMessage(), this);
        }
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
}

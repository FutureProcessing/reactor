package org.reactor.request.parser;

import static org.apache.commons.beanutils.ConvertUtils.convert;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;

public class ReactorRequestPrimitiveDataParser<T> extends AbstractReactorRequestDataParser<T> {

    private final Class<T> primitiveType;

    public ReactorRequestPrimitiveDataParser(Class<T> primitiveType) {
        this.primitiveType = primitiveType;
    }

    public ReactorRequest<T> parseRequestWithData(String sender, String trigger, String reactorInput) {
        // just converting to one of possible primitive type wrappers
        T typedReactorInput = (T) convert(reactorInput, primitiveType);
        return new ReactorRequest<>(sender, trigger, typedReactorInput);
    }

    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {
        // TODO propagate topology information for primitive types
    }
}

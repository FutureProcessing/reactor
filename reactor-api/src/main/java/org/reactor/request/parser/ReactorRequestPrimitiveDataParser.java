package org.reactor.request.parser;

import static org.apache.commons.beanutils.ConvertUtils.convert;

import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;
import org.reactor.request.ReactorRequestInput;

public class ReactorRequestPrimitiveDataParser<T> extends AbstractReactorRequestDataParser<T> {

    private final Class<T> primitiveType;

    public ReactorRequestPrimitiveDataParser(Class<T> primitiveType) {
        this.primitiveType = primitiveType;
    }

    public ReactorRequest<T> parseRequestWithData(String sender, String trigger, ReactorRequestInput requestInput) {
        T typedReactorInput = (T) convert(requestInput.getArgumentsAsString(), primitiveType);
        return new ReactorRequest<>(sender, trigger, typedReactorInput);
    }

    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {
        if (isVoidType()) {
            return;
        }
        topologyVisitor.visitReactorRequestInput(new ReactorRequestInputDefinition(primitiveType));
    }

    private boolean isVoidType() {
        return Void.class.isAssignableFrom(primitiveType);
    }
}
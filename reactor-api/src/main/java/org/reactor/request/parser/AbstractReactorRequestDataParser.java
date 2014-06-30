package org.reactor.request.parser;

import static org.reactor.utils.ClassUtils.isPrimitive;
import org.reactor.discovery.ReactorTopologyDiscoveringVisitor;
import org.reactor.request.ReactorRequest;
import org.reactor.request.ReactorRequestInput;

public abstract class AbstractReactorRequestDataParser<T> {

    public static <T> AbstractReactorRequestDataParser<T> forDataType(Class<T> dataType) {
        if (isPrimitive(dataType)) {
            return new ReactorRequestPrimitiveDataParser<>(dataType);
        }
        return new ReactorRequestComplexDataParser<>(dataType);
    }

    public abstract ReactorRequest<T> parseRequestWithData(String sender, String trigger, ReactorRequestInput requestInput);

    public void accept(ReactorTopologyDiscoveringVisitor topologyVisitor) {

    }
}

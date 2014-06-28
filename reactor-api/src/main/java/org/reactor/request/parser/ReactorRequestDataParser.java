package org.reactor.request.parser;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import java.lang.reflect.Field;
import java.util.Arrays;
import org.reactor.request.ReactorRequest;

public class ReactorRequestDataParser<T> extends AbstractRequestDataParser<T> {

    public ReactorRequestDataParser(Class<T> type) {
        prepareDataParser(type);
    }

    private void prepareDataParser(Class<T> type) {
        if (type.isPrimitive()) {
            // TODO do something with primitives
            return;
        }

        Field[] dataTypeFields = type.getDeclaredFields();

        FluentIterable.from(Arrays.asList(dataTypeFields)).transform(new Function<Field, ReactorRequestParameterDefinition>() {
            @Override
            public ReactorRequestParameterDefinition apply(Field input) {
                return null;
                // TODO
            }
        });

        for (Field dataTypeField : dataTypeFields) {

        }
    }

    @Override
    public ReactorRequest<T> parseRequestWithData(String sender, String trigger, String reactorInput) {
        return null;
        // TODO
    }
}

package org.reactor.event;

import com.google.common.base.Function;
import org.reactor.response.ReactorResponse;

public interface ReactorEventConsumerFactory {

    <T extends ReactorEvent> ReactorEventConsumer<T> createEventConsumer(Class<T> eventType,
                                                                         Function<T, ReactorResponse> responseCreator);
}

package org.reactor.transport;

import static java.lang.String.format;
import com.google.common.util.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransportInitializationCallback implements FutureCallback<ReactorMessageTransport> {

    private final static Logger LOG = LoggerFactory.getLogger(TransportInitializationCallback.class);
    private final ReactorMessageTransport transport;

    public TransportInitializationCallback(ReactorMessageTransport transport) {
        this.transport = transport;
    }

    @Override
    public void onSuccess(ReactorMessageTransport transport) {
        LOG.debug("Transport initialized successfully: {}", transport.getClass().getName());
    }

    @Override
    public void onFailure(Throwable throwable) {
        LOG.error(format("An exception occurred while starting transport: %s", transport.getClass().getName()),
            throwable);
    }
}

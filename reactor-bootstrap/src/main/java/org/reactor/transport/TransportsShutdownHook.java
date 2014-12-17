package org.reactor.transport;

import static java.lang.Runtime.getRuntime;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class TransportsShutdownHook {

    private final static Logger LOG = LoggerFactory.getLogger(TransportsShutdownHook.class);

    private final TransportController transportController;

    public TransportsShutdownHook(TransportController transportController) {
        this.transportController = transportController;
    }

    public void initHook() {
        LOG.debug("Initializing shutdown hook");
        getRuntime().addShutdownHook(new Thread() {
            public void run() {
                LOG.debug("Shutting down transports ...");
                transportController.stopTransports();
            }
        });
    }
}

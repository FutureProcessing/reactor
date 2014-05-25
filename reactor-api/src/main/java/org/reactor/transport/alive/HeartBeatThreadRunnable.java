package org.reactor.transport.alive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartBeatThreadRunnable implements Runnable {

    private final static Logger LOG = LoggerFactory.getLogger(HeartBeatThreadRunnable.class);
    private final HeartBeatAlivePolicy alivePolicy;

    public HeartBeatThreadRunnable(HeartBeatAlivePolicy alivePolicy) {
        this.alivePolicy = alivePolicy;
    }

    @Override
    public void run() {
        LOG.debug("Heart beat thread started.");
        while (alivePolicy.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOG.debug("", e);
            }
        }

    }
}

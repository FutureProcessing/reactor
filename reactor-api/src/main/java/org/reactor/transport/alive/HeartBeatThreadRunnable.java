package org.reactor.transport.alive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.Thread.sleep;

public class HeartBeatThreadRunnable implements Runnable {

    private static final int SLEEP_TIME = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(HeartBeatThreadRunnable.class);
    private final HeartBeatAlivePolicy alivePolicy;

    public HeartBeatThreadRunnable(HeartBeatAlivePolicy alivePolicy) {
        this.alivePolicy = alivePolicy;
    }

    @Override
    public void run() {
        LOGGER.debug("Heart beat thread started.");
        while (alivePolicy.isAlive()) {
            try {
                sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                LOGGER.debug("An unexpected exception occurred.", e);
            }
        }
    }
}

package org.reactor.transport.alive;

public interface HeartBeatAlivePolicy {

    public static final HeartBeatAlivePolicy ALIVE_ALWAYS = new HeartBeatAlivePolicy() {
        @Override
        public boolean isAlive() {
            return true;
        }
    };

    boolean isAlive();
}

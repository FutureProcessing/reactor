package org.reactor.transport.alive;

public interface HeartBeatAlivePolicy {

    public static final HeartBeatAlivePolicy ALIVE_ALWAYS = () -> true;

    boolean isAlive();
}

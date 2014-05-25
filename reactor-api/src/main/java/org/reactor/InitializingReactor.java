package org.reactor;

public interface InitializingReactor extends Reactor {

    void initReactor(ReactorProperties properties);
}

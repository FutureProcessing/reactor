package org.reactor.transport.irc;

import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;

public class IrcBot extends PircBotX {

    public IrcBot(Configuration<? extends PircBotX> configuration) {
        super(configuration);
    }

    @Override
    public void shutdown() {
        super.shutdown();
    }
}

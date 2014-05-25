package org.reactor.transport.irc.listener;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.ConnectEvent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class IrcTransportInitializationListener extends ListenerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(IrcTransportInitializationListener.class);
    
    private boolean connected;

    @Override
    public final void onConnect(ConnectEvent event) throws Exception {
        LOG.debug("Connected.");
        connected = true;
    }

    public final boolean isConnected() {
        return connected;
    }
}

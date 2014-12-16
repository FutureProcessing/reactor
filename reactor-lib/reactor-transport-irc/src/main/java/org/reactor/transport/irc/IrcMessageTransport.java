package org.reactor.transport.irc;

import static org.pircbotx.PircBotX.State.DISCONNECTED;
import com.google.common.base.Function;
import java.io.IOException;
import org.pircbotx.Configuration;
import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.managers.ListenerManager;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorMessageTransportInitializationException;
import org.reactor.transport.ReactorRequestHandler;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;
import org.reactor.transport.irc.listener.IrcTransportInitializationListener;
import org.reactor.transport.irc.listener.IrcBotReactorListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IrcMessageTransport extends KeepAliveReactorMessageTransport {

    private final static Logger LOG = LoggerFactory.getLogger(IrcMessageTransport.class);
    private static final long SLEEP_DELAY = 3000;

    private IrcTransportInitializationListener initializationListener = new IrcTransportInitializationListener();

    public final static Function<IrcTransportProperties, Configuration> TO_CONFIG = new Function<IrcTransportProperties, Configuration>() {

        @Override
        public Configuration apply(IrcTransportProperties properties) {
            Configuration.Builder configurationBuilder = new Configuration.Builder().setName(properties.getNickname())
                .setServerHostname(properties.getServerHost()).setAutoNickChange(true);
            for (String joinChannel : properties.getJoinChannels()) {
                configurationBuilder.addAutoJoinChannel(joinChannel);
            }
            return configurationBuilder.buildConfiguration();
        }
    };

    private IrcBot botInstance;

    @Override
    protected void startTransportKeptAlive(TransportProperties transportProperties,
                                        ReactorRequestHandler requestHandler) {
        LOG.debug("Starting IRC message transport ...");

        Configuration configuration = TO_CONFIG.apply(new IrcTransportProperties(transportProperties));
        ListenerManager listenerManager = configuration.getListenerManager();
        listenerManager.addListener(initializationListener);
        listenerManager.addListener(new IrcBotReactorListener(requestHandler));
        botInstance = new IrcBot(configuration);
        try {
            new Thread(new BotInstanceRunnable()).start();
            while (!initializationListener.isConnected()) {
                LOG.debug("Waiting for IRC connection establishment");
                Thread.sleep(SLEEP_DELAY);
            }
        } catch (Throwable e) {
            throw new ReactorMessageTransportInitializationException(e);
        }
    }

    @Override
    protected void stopTransportKeptAlive() {
        if (botInstance.getState() == DISCONNECTED) {
            LOG.debug("IRC transport already disconnected!");
            return;
        }
        botInstance.shutdown();
    }

    @Override
    public void broadcast(ReactorResponse reactorResponse) {
        // TODO
    }

    @Override
    public boolean isRunning() {
        return botInstance != null && botInstance.isConnected();
    }

    private class BotInstanceRunnable implements Runnable {

        @Override
        public void run() {
            try {
                botInstance.startBot();
            } catch (IOException | IrcException e) {
                LOG.error("An error ocurred while starting IRC transport", e);
            }
        }
    }

}

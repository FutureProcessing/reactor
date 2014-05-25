package org.reactor.transport.irc.listener;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.transport.irc.IrcReactorCommandResponseWriter;

public class IrcBotReactorListener extends ListenerAdapter {

    private final ReactorMessageTransportProcessor messageProcessor;

    public IrcBotReactorListener(ReactorMessageTransportProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Override
    public final void onMessage(MessageEvent event) throws Exception {
        messageProcessor.processTransportMessage(event.getMessage(), event.getUser().getNick(),
            new IrcReactorCommandResponseWriter(event));
    }

    @Override
    public final void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        messageProcessor.processTransportMessage(event.getMessage(), event.getUser().getNick(),
            new IrcReactorCommandResponseWriter(event));
    }
}

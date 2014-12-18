package org.reactor.transport.irc.listener;

import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.reactor.transport.irc.IrcReactorCommandResponseWriter;

public class IrcBotReactorListener extends ListenerAdapter {

    private final ReactorRequestHandler requestHandler;

    public IrcBotReactorListener(ReactorRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public final void onMessage(MessageEvent event) throws Exception {
        ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
        requestHandler.handleReactorRequest(new ReactorRequestInput(event.getMessage()), event.getUser().getNick(),
            responseRenderer);
        responseRenderer.commit(new IrcReactorCommandResponseWriter(event));
    }

    @Override
    public final void onPrivateMessage(PrivateMessageEvent event) throws Exception {
        ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
        requestHandler.handleReactorRequest(new ReactorRequestInput(event.getMessage()), event.getUser().getNick(),
            responseRenderer);
        responseRenderer.commit(new IrcReactorCommandResponseWriter(event));
    }
}

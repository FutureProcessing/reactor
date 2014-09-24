package org.reactor.transport.websockets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorMessageTransport;
import org.reactor.transport.ReactorMessageTransportInitializationException;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.transport.TransportProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketsMessageTransport implements ReactorMessageTransport {

    private final static Logger LOG = LoggerFactory.getLogger(WebSocketsMessageTransport.class);

    public static final String STATIC_DIR = "static";

    private Server server;
    private ReactorWebsocketHandler webSocketHandler;

    @Override
    public void startTransport(TransportProperties transportProperties, ReactorMessageTransportProcessor messageProcessor) {
        startWebSocketsTransport(new WebSocketsTransportProperties(transportProperties), messageProcessor);
    }

    private void startWebSocketsTransport(WebSocketsTransportProperties transportProperties,
                                          ReactorMessageTransportProcessor messageProcessor) {
        try {
            server = new Server(transportProperties.getPortNumber());
            server.setHandler(createWebSocketsHandler(messageProcessor));
            server.start();
        } catch (Exception e) {
            throw new ReactorMessageTransportInitializationException(e);
        }
    }

    private ReactorWebsocketHandler createWebSocketsHandler(ReactorMessageTransportProcessor messageProcessor) {
        webSocketHandler = new ReactorWebsocketHandler(messageProcessor);
        webSocketHandler.setHandler(createResourceHandler());
        return webSocketHandler;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(Resource.newClassPathResource(STATIC_DIR));
        //resourceHandler.setResourceBase(STATIC_DIR);
        return resourceHandler;
    }

    @Override
    public void stopTransport() {
        try {
            server.stop();
        } catch (Exception e) {
            LOG.error("An error occurred while stopping Websockets transport", e);
            return;
        }
        server = null;
    }

    @Override
    public void broadcast(ReactorResponse reactorResponse) {
        webSocketHandler.broadcast(reactorResponse);
    }

    @Override
    public boolean isRunning() {
        return server != null && server.isRunning();
    }

}

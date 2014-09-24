package org.reactor.transport.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.util.resource.Resource;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorMessageTransport;
import org.reactor.transport.ReactorMessageTransportInitializationException;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.http.rest.RestHandler;
import org.reactor.transport.http.websockets.ReactorWebsocketsHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpMessageTransport implements ReactorMessageTransport {

    private final static Logger LOG = LoggerFactory.getLogger(HttpMessageTransport.class);

    private static final String STATIC_DIR = "static";

    private static final String CONTEXT_PATH_REST = "/rest";
    private static final String CONTEXT_PATH_WEBSOCKETS = "/websockets";
    private static final String CONTEXT_PATH_RESOURCES = "/";

    private Server server;
    private ReactorWebsocketsHandler webSocketHandler;

    @Override
    public void startTransport(TransportProperties transportProperties, ReactorMessageTransportProcessor messageProcessor) {
        startWebSocketsTransport(new HttpTransportProperties(transportProperties), messageProcessor);
    }

    private void startWebSocketsTransport(HttpTransportProperties transportProperties,
                                          ReactorMessageTransportProcessor messageProcessor) {
        try {
            server = new Server(transportProperties.getPortNumber());
            server.setHandler(createHandlersCollection(messageProcessor));
            server.start();
        } catch (Exception e) {
            throw new ReactorMessageTransportInitializationException(e);
        }
    }

    private ContextHandlerCollection createHandlersCollection(ReactorMessageTransportProcessor messageProcessor) {
        ContextHandlerCollection handlerCollection = new ContextHandlerCollection();
        handlerCollection.addHandler(createStaticResourcesHandler());
        handlerCollection.addHandler(createRestHandler(messageProcessor));
        handlerCollection.addHandler(createWebSocketsHandler(messageProcessor));
        return handlerCollection;
    }

    private ContextHandler createStaticResourcesHandler() {
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath(CONTEXT_PATH_RESOURCES);
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setBaseResource(Resource.newClassPathResource(STATIC_DIR));
        contextHandler.setHandler(resourceHandler);
        return contextHandler;
    }

    private ContextHandler createWebSocketsHandler(ReactorMessageTransportProcessor messageProcessor) {
        webSocketHandler = new ReactorWebsocketsHandler(messageProcessor);
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath(CONTEXT_PATH_WEBSOCKETS);
        contextHandler.setHandler(webSocketHandler);
        return contextHandler;
    }

    private ContextHandler createRestHandler(ReactorMessageTransportProcessor messageProcessor) {
        ContextHandler contextHandler = new ContextHandler();
        contextHandler.setContextPath(CONTEXT_PATH_REST);
        contextHandler.setHandler(new RestHandler(messageProcessor));
        return contextHandler;
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

package org.reactor.transport.http.websockets;

import static com.google.common.collect.Lists.newArrayList;
import static org.eclipse.jetty.websocket.WebSocket.Connection;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketHandler;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ReactorWebsocketsHandler extends WebSocketHandler implements WebSocketsConnectionListener {

    private final static Logger LOG = LoggerFactory.getLogger(ReactorWebsocketsHandler.class);
    private static final List<Connection> CONNECTIONS = newArrayList();
    private final ReactorRequestHandler requestHandler;

    public ReactorWebsocketsHandler(ReactorRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String protocol) {
        return new ReactorWebSocket(requestHandler, this);
    }

    @Override
    public void connectionOpened(Connection openedConnection) {
        synchronized (CONNECTIONS) {
            CONNECTIONS.add(openedConnection);
            LOG.debug("Added new Connection, total connections: {}", CONNECTIONS.size());
        }
    }

    @Override
    public void connectionClosed(Connection closedConnection) {
        synchronized (CONNECTIONS) {
            int connectionIndex = CONNECTIONS.indexOf(closedConnection);
            if (connectionIndex < 0) {
                LOG.debug("Given Connection instance is not managed by ReactorWebsocketHandler");
                return;
            }
            CONNECTIONS.remove(connectionIndex);
            LOG.debug("Removed closed Connection, total connections: {}", CONNECTIONS.size());
        }
    }

    public final void broadcast(ReactorResponse reactorResponse) {
        for (Connection connection : CONNECTIONS) {
            try {
                broadcastConnectionResponse(reactorResponse, connection);
            } catch (Exception e) {
                LOG.error("An error occurred while broadcasting reactor response", e);
            }
        }
    }

    private void broadcastConnectionResponse(ReactorResponse reactorResponse, Connection connection) throws Exception {
        if (!validateConnection(connection)) {
            LOG.debug("Can't broadcast to given Connection, skipping");
            return;
        }
        reactorResponse.renderResponse(new WebSocketResponseWriter(connection, WebSocketResponseType.BROADCAST));
    }

    private boolean validateConnection(Connection connection) {
        if (!connection.isOpen()) {
            LOG.debug("Connection is closed: ");
            return false;
        }
        return true;
    }
}

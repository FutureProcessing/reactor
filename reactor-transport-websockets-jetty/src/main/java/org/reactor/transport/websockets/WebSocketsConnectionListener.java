package org.reactor.transport.websockets;

import static org.eclipse.jetty.websocket.WebSocket.Connection;

public interface WebSocketsConnectionListener {

    void connectionOpened(Connection connection);

    void connectionClosed(Connection connection);
}

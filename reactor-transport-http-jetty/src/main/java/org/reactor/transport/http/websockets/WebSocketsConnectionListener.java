package org.reactor.transport.http.websockets;

import static org.eclipse.jetty.websocket.WebSocket.Connection;

public interface WebSocketsConnectionListener {

    void connectionOpened(Connection connection);

    void connectionClosed(Connection connection);
}

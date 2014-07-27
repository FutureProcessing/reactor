package org.reactor.transport.http.websockets;

import org.eclipse.jetty.websocket.WebSocket;
import org.reactor.transport.ReactorMessageTransportProcessor;

public class ReactorWebSocket implements WebSocket.OnTextMessage {

    public static final String SENDER = "UNKNOWN";

    private final ReactorMessageTransportProcessor messageProcessor;
    private final WebSocketsConnectionListener connectionListener;

    private Connection connection = null;

    public ReactorWebSocket(ReactorMessageTransportProcessor messageProcessor,
                            WebSocketsConnectionListener connectionListener) {
        this.messageProcessor = messageProcessor;
        this.connectionListener = connectionListener;
    }

    @Override
    public void onMessage(String message) {
        messageProcessor.processTransportMessage(message, SENDER, new WebSocketResponseWriter(connection, WebSocketResponseType.RESPONSE));
    }

    @Override
    public void onOpen(Connection connection) {
        this.connection = connection;
        connectionListener.connectionOpened(connection);
    }

    @Override
    public void onClose(int closeCode, String message) {
        connectionListener.connectionClosed(connection);
        this.connection = null;
    }
}

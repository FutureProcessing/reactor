package org.reactor.transport.http.websockets;

import static org.reactor.transport.http.websockets.WebSocketResponseType.RESPONSE;

import java.io.IOException;
import java.io.Writer;

import org.eclipse.jetty.websocket.WebSocket;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.reactor.transport.http.ResponseAwaiter.awaitResponse;

public class ReactorWebSocket implements WebSocket.OnTextMessage {

    private final static Logger LOGGER = LoggerFactory.getLogger(ReactorWebSocket.class);

    private static final String SENDER = "UNKNOWN";
    private static final String MESSAGE_INTERACTIVE_TOGGLE = "!interactive";

    private final ReactorRequestHandler requestHandler;
    private final WebSocketsConnectionListener connectionListener;
    private boolean interactive;

    private Connection connection = null;

    public ReactorWebSocket(ReactorRequestHandler requestHandler, WebSocketsConnectionListener connectionListener) {
        this.requestHandler = requestHandler;
        this.connectionListener = connectionListener;
    }

    @Override
    public void onMessage(String message) {
        if (isInteractiveToggleMessage(message)) {
            toggleSessionInteractive();
            return;
        }

        ReactorRequestInput requestInput = new ReactorRequestInput(message);
        requestInput.setInteractive(interactive);
        SimpleReactorResponseRenderer renderer = new SimpleReactorResponseRenderer(new WebSocketResponseWriter(connection, RESPONSE));
        awaitResponse(requestHandler.handleReactorRequest(requestInput, SENDER, renderer));
    }

    private boolean isInteractiveToggleMessage(String textMessage) {
        return MESSAGE_INTERACTIVE_TOGGLE.equals(textMessage);
    }

    private void toggleSessionInteractive() {
        Writer writer = new WebSocketResponseWriter(connection, RESPONSE);
        try {
            if (!interactive) {
                writer.write("Turning ON interactive mode for session");
            } else {
                writer.write("Turning OFF interactive mode for session");
            }
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("An error occurred while sending response");
        }
        interactive = !interactive;
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

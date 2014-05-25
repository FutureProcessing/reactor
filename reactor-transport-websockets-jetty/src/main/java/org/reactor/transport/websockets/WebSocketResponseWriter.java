package org.reactor.transport.websockets;

import static org.eclipse.jetty.websocket.WebSocket.Connection;

import org.reactor.writer.BufferedMultilineSplittingWriter;

import java.io.IOException;

public class WebSocketResponseWriter extends BufferedMultilineSplittingWriter {

    private final WebSocketJsonResponseWriter jsonWriter;
    public WebSocketResponseWriter(Connection connection, WebSocketResponseType responseType) {
        this.jsonWriter = new WebSocketJsonResponseWriter(connection, responseType);
    }

    @Override
    protected void writeLines(String lines) {
        jsonWriter.writeJsonResponse(lines);
    }

    @Override
    public void close() throws IOException {
        // do nothing
    }

}

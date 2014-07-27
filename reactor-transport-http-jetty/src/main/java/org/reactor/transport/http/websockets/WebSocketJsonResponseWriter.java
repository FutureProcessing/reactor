package org.reactor.transport.http.websockets;

import static org.eclipse.jetty.websocket.WebSocket.Connection;

import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;

public class WebSocketJsonResponseWriter {

    private final static Logger LOG = LoggerFactory.getLogger(WebSocketJsonResponseWriter.class);

    private static final String KEY_TYPE = "type";
    private static final String KEY_MESSAGE = "message";

    private final Connection connection;
    private final WebSocketResponseType responseType;

    public WebSocketJsonResponseWriter(Connection connection, WebSocketResponseType responseType) {
        this.connection = connection;
        this.responseType = responseType;
    }

    public void writeJsonResponse(String message) {
        JSONObject jsonObject = prepareJSONObject(message);
        try {
            connection.sendMessage(jsonObject.toString());
        } catch (IOException e) {
            LOG.error("An error occurred while generating JSON response", e);
        }
    }

    private JSONObject prepareJSONObject(String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(KEY_TYPE, responseType.name());
        jsonObject.put(KEY_MESSAGE, message);
        return jsonObject;
    }
}

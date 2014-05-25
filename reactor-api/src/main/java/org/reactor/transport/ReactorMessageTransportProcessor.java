package org.reactor.transport;

import java.io.Writer;

public interface ReactorMessageTransportProcessor {

    public void processTransportMessage(String messageText, String sender, Writer responseWriter);
}

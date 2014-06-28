package org.reactor.transport.telnet;

import static java.lang.String.valueOf;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.reactor.transport.ReactorMessageTransportProcessor;

public class TelnetMessageProcessorHandler extends IoHandlerAdapter {

    private final ReactorMessageTransportProcessor messageTransportProcessor;

    public TelnetMessageProcessorHandler(ReactorMessageTransportProcessor messageTransportProcessor) {
        this.messageTransportProcessor = messageTransportProcessor;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        messageTransportProcessor.processTransportMessage(message.toString(), valueOf(session.getId()),
            new TelnetSessionResponseWriter(session));
    }
}

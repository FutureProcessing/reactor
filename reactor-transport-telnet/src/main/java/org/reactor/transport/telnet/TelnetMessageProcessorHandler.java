package org.reactor.transport.telnet;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.reactor.transport.ReactorMessageTransportProcessor;

public class TelnetMessageProcessorHandler extends IoHandlerAdapter {

    private final ReactorMessageTransportProcessor messageTransport;

    public TelnetMessageProcessorHandler(ReactorMessageTransportProcessor messageTransport) {
        this.messageTransport = messageTransport;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        messageTransport.processTransportMessage(message.toString(), "", new TelnetSessionResponseWriter(session));
    }
}

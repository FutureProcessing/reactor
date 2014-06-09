package org.reactor.transport.telnet;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelnetMessageProcessorHandler extends IoHandlerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(TelnetMessageProcessorHandler.class);
    
    private final ReactorMessageTransportProcessor messageTransport;

    public TelnetMessageProcessorHandler(ReactorMessageTransportProcessor messageTransport) {
        this.messageTransport = messageTransport;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        messageTransport.processTransportMessage(message.toString(), "", new TelnetSessionResponseWriter(session));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        LOG.error("An exception occurred on Telnet transport", cause);
    }
}

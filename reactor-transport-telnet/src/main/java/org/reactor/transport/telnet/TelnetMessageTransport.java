package org.reactor.transport.telnet;

import static org.apache.mina.core.session.IdleStatus.BOTH_IDLE;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorMessageTransportInitializationException;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;

public class TelnetMessageTransport extends KeepAliveReactorMessageTransport {

    private final static Logger LOG = LoggerFactory.getLogger(TelnetMessageTransport.class);
    private NioSocketAcceptor acceptor;

    private void startTelnetTransport(TelnetTransportProperties transportProperties, ReactorMessageTransportProcessor messageTransport) {
        LOG.debug("Starting telnet message transport on port {} ...", transportProperties.getPortNumber());
        acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        acceptor.getFilterChain().addLast("codec",
            new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        acceptor.setHandler(new TelnetMessageProcessorHandler(messageTransport));
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(BOTH_IDLE, 10);
        try {
            acceptor.bind(new InetSocketAddress(transportProperties.getPortNumber()));
        } catch (IOException e) {
            throw new ReactorMessageTransportInitializationException(e);
        }
    }

    @Override
    public void startTransportKeptAlive(TransportProperties transportProperties, ReactorMessageTransportProcessor messageProcessor) {
        startTelnetTransport(new TelnetTransportProperties(transportProperties), messageProcessor);
    }

    @Override
    public void stopTransport() {
        acceptor.unbind();
        acceptor = null;
    }

    @Override
    public void broadcast(ReactorResponse reactorResponse) {
        Map<Long, IoSession> sessions = acceptor.getManagedSessions();
        if (sessions == null) {
            return;
        }
        for (IoSession session : acceptor.getManagedSessions().values()) {
            try {
                reactorResponse.renderResponse(new TelnetSessionResponseWriter(session));
            } catch (Exception e) {
                LOG.error("Unable to send response to session: {}", session.getId());
            }
        }
    }

    @Override
    public boolean isRunning() {
        return acceptor != null && acceptor.isActive();
    }
}

package org.reactor.transport.telnet;

import static org.apache.mina.core.session.IdleStatus.BOTH_IDLE;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorMessageTransportInitializationException;
import org.reactor.transport.ReactorRequestHandler;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;

public class TelnetMessageTransport extends KeepAliveReactorMessageTransport {

    private static final int READ_BUFFER_SIZE = 2048;
    private static final int IDLE_TIME = 10;
    private static final String FILTER_LOGGER = "logger";
    private static final String FILTER_CODEC = "codec";

    private final static Logger LOG = LoggerFactory.getLogger(TelnetMessageTransport.class);

    private NioSocketAcceptor acceptor;

    private void startTelnetTransport(TelnetTransportProperties transportProperties,
                                      ReactorRequestHandler messageTransportProcessor) {
        LOG.debug("Starting telnet message transport on port {} ...", transportProperties.getPortNumber());
        acceptor = new NioSocketAcceptor();
        acceptor.setHandler(new TelnetMessageProcessorHandler(messageTransportProcessor));

        configureFilterChain(acceptor.getFilterChain());
        configureSession(acceptor.getSessionConfig());
        try {
            acceptor.bind(new InetSocketAddress(transportProperties.getPortNumber()));
        } catch (IOException e) {
            throw new ReactorMessageTransportInitializationException(e);
        }
    }

    private void configureFilterChain(DefaultIoFilterChainBuilder filterChain) {
        filterChain.addLast(FILTER_LOGGER, new LoggingFilter());
        filterChain.addLast(FILTER_CODEC, new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
    }

    private void configureSession(SocketSessionConfig sessionConfig) {
        sessionConfig.setReadBufferSize(READ_BUFFER_SIZE);
        sessionConfig.setIdleTime(BOTH_IDLE, IDLE_TIME);
    }

    @Override
    protected void startTransportKeptAlive(TransportProperties transportProperties,
                                        ReactorRequestHandler requestHandler) {
        startTelnetTransport(new TelnetTransportProperties(transportProperties), requestHandler);
    }

    @Override
    protected void stopTransportKeptAlive() {
        acceptor.dispose();
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
                ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
                reactorResponse.renderResponse(responseRenderer);
                responseRenderer.commit(new TelnetSessionResponseWriter(session));
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

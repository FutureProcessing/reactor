package org.reactor.jenkins.event;

import static org.apache.mina.core.session.IdleStatus.BOTH_IDLE;

import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.reactor.event.ReactorEventConsumer;
import org.reactor.jenkins.JenkinsReactorProperties;
import org.reactor.jenkins.decoder.JenkinsProtocolCodecFactory;
import org.reactor.transport.ReactorMessageTransportInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class JobActivityEventListener {

    private final static Logger LOG = LoggerFactory.getLogger(JobActivityEventListener.class);

    public static final String CODEC = "codec";
    private final JenkinsReactorProperties reactorProperties;
    private NioSocketAcceptor acceptor;

    public JobActivityEventListener(JenkinsReactorProperties reactorProperties) {
        this.reactorProperties = reactorProperties;
    }

    public void startConsumingEvents(ReactorEventConsumer<JobActivityEvent> eventConsumer) {
        if (!reactorProperties.isListenerEnabled()) {
            return;
        }
        LOG.debug("Initializing Jenkins event listener on port {}", reactorProperties.getListenerPort());
        acceptor = new NioSocketAcceptor();
        acceptor.getFilterChain().addLast(CODEC, new ProtocolCodecFilter(new JenkinsProtocolCodecFactory()));
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(BOTH_IDLE, 10);
        try {
            acceptor.setHandler(new JobActivityEventHandler(eventConsumer));
            acceptor.bind(new InetSocketAddress(reactorProperties.getListenerPort()));
        } catch (IOException e) {
            throw new ReactorMessageTransportInitializationException(e);
        }
    }
}

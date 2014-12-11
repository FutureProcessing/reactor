package org.reactor.jenkins.decoder;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class JenkinsProtocolCodecFactory implements ProtocolCodecFactory {

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        // returns nothing, not planning to send responses
        return null;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return new JenkinsMessageDecoder();
    }
}

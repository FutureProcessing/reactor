package org.reactor.jenkins.decoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class JenkinsMessageDecoder implements ProtocolDecoder {

    @Override
    public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
        StringBuffer buffer = new StringBuffer();
        while (in.hasRemaining()) {
            buffer.append(new String(new byte[] {
                in.get()
            }));
        }
        out.write(buffer.toString());
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {
        session.close(false);
    }

    @Override
    public void dispose(IoSession session) throws Exception {

    }
}

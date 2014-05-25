package org.reactor.transport.speech;

import java.util.Timer;
import org.reactor.response.ReactorResponse;
import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;
import org.reactor.transport.speech.listen.MicrophoneListener;
import org.reactor.transport.speech.recognize.VoiceRecognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeechMessageTransport extends KeepAliveReactorMessageTransport {

    private final static Logger LOG = LoggerFactory.getLogger(SpeechMessageTransport.class);
    private Timer timer;
    private MicrophoneListener microphoneListener;
    private VoiceRecognizer recognizer;

    private void startSpeechTransport(SpeechTransportProperties transportProperties,
                                      ReactorMessageTransportProcessor messageTransport) {
        LOG.debug("Starting Speech message transport");

        initializeVoiceRecognizer(transportProperties, messageTransport);
        initializeMicrophoneListener(transportProperties);
    }

    private void initializeVoiceRecognizer(SpeechTransportProperties transportProperties,
                                           ReactorMessageTransportProcessor messageTransport) {
        LOG.debug("Initializing voice recognizer");
        recognizer = new VoiceRecognizer(transportProperties.getApiKey(), transportProperties.getLanguage(),
            transportProperties.getSampleRate(), new SpeechRecognizedListener(messageTransport));
    }

    private void initializeMicrophoneListener(SpeechTransportProperties transportProperties) {
        LOG.debug("Initializing microphone listener");
        microphoneListener = new MicrophoneListener(10, new SpeechRecordedListener(recognizer,
            transportProperties.getSampleRate()));
    }

    @Override
    public void startTransportKeptAlive(TransportProperties transportProperties,
                                        ReactorMessageTransportProcessor messageProcessor) {
        startSpeechTransport(new SpeechTransportProperties(transportProperties), messageProcessor);
    }

    @Override
    public void stopTransport() {}

    @Override
    public void broadcast(ReactorResponse reactorResponse) {

    }

    @Override
    public boolean isRunning() {
        return true;
    }
}

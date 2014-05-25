package org.reactor.transport.speech;

import org.reactor.transport.ReactorMessageTransportProcessor;
import org.reactor.transport.speech.recognize.VoiceRecognizedListener;

import java.io.StringWriter;

public class SpeechRecognizedListener implements VoiceRecognizedListener {

    private final ReactorMessageTransportProcessor messageTransport;

    public SpeechRecognizedListener(ReactorMessageTransportProcessor messageTransport) {
        this.messageTransport = messageTransport;
    }

    @Override
    public void voiceRecognized(String recognizedText) {
        System.out.println(">>> " + recognizedText);

        StringWriter writer = new StringWriter();
        messageTransport.processTransportMessage(recognizedText, "", writer);
        System.out.println(">>> response: " + writer.toString());
    }
}

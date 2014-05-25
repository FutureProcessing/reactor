package org.reactor.transport.speech.recognize;

import com.darkprograms.speech.recognizer.RecognizerChunked;
import java.io.File;
import java.io.IOException;

public class VoiceRecognizer {

    private final String apiKey;
    private final String language;
    private final int sampleRate;
    private final VoiceRecognizedListener listener;

    private RecognizerChunked recognizer;

    public VoiceRecognizer(String apiKey, String language, int sampleRate, VoiceRecognizedListener listener) {
        this.apiKey = apiKey;
        this.language = language;
        this.sampleRate = sampleRate;
        this.listener = listener;

        initializeRecognizer();
    }

    private void initializeRecognizer() {
        recognizer = new RecognizerChunked(apiKey, language);
        recognizer.addResponseListener(new GoogleVoiceRecognizedListener(listener));
    }

    public void recognizeVoiceFile(File voiceFile) throws IOException {
        recognizer.getRecognizedDataForFlac(voiceFile, sampleRate);
    }

}

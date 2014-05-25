package org.reactor.transport.speech.recognize;

import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

public class GoogleVoiceRecognizedListener implements GSpeechResponseListener {

    private final VoiceRecognizedListener listener;

    public GoogleVoiceRecognizedListener(VoiceRecognizedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onResponse(GoogleResponse googleResponse) {
        listener.voiceRecognized(googleResponse.getResponse());
    }
}

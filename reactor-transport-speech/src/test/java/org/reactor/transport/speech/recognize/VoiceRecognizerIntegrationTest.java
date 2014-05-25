package org.reactor.transport.speech.recognize;

import static java.lang.Thread.sleep;
import static org.fest.assertions.Assertions.assertThat;
import java.io.File;
import java.io.IOException;
import org.junit.Test;
import org.reactor.AbstractUnitTest;

public class VoiceRecognizerIntegrationTest extends AbstractUnitTest {

    private static final long SECOND = 1000;
    private static final String API_KEY = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw";
    private static final String LANGUAGE = "en";
    private static final int SAMPLE_RATE = 44100;
    private static final String VOICE_FILE = "/good-morning-google.flac";

    private VoiceRecognizer recognizer;
    private File voiceFile;
    private String recognizedText;

    @Test
    public void shouldRecognizeSpeechFromFlacFile() throws IOException, InterruptedException {
        // given
        recognizer = givenRecognizer();
        voiceFile = givenVoiceFile();

        // when
        recognizer.recognizeVoiceFile(voiceFile);
        sleepInSeconds(5);

        // then
        assertThat(recognizedText).isEqualToIgnoringCase("good morning Google how are you feeling today");
    }

    private void sleepInSeconds(int seconds) throws InterruptedException {
        sleep(seconds * SECOND);
    }

    private File givenVoiceFile() {
        return new File(this.getClass().getResource(VOICE_FILE).getFile());
    }

    private VoiceRecognizer givenRecognizer() {
        return new VoiceRecognizer(API_KEY, LANGUAGE, SAMPLE_RATE, new TestVoiceRecognizedListener());
    }

    private class TestVoiceRecognizedListener implements VoiceRecognizedListener {

        @Override
        public void voiceRecognized(String response) {
            recognizedText = response;
        }
    }
}

package org.reactor.transport.speech;

import static org.reactor.voice.VoiceCapture.capture;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;
import org.reactor.voice.VoiceCapture;
import org.reactor.voice.VoiceCaptureListener;
import org.reactor.voice.recognition.AbstractVoiceRecognizer;
import org.reactor.voice.recognition.VoiceRecognitionData;
import org.reactor.voice.recognition.VoiceRecognitionListener;
import org.reactor.voice.recognition.google.GoogleVoiceRecognizer;
import org.reactor.voice.synthesis.VoiceSynthesiser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SpeechMessageTransport extends KeepAliveReactorMessageTransport {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpeechMessageTransport.class);

    private static final String SOUND_RECORDING_STARTED = "start.mp3";
    private static final String SOUND_RECORDING_STOPPED = "stop.mp3";

    private VoiceCapture voiceCapture;
    private AbstractVoiceRecognizer recognizer;
    private VoiceSynthesiser synthesiser;
    private ReactorRequestHandler requestHandler;

    private void startSpeechTransport(SpeechTransportProperties transportProperties,
                                      ReactorRequestHandler requestHandler) {
        LOGGER.debug("Starting Speech message transport");
        this.requestHandler = requestHandler;

        initializeCapture(transportProperties.getMinimumVolume(), transportProperties.getSilenceDuration());
        initializeSynthesiser(transportProperties.getLanguage());
        initializeRecognizer(transportProperties.getApiKey(), transportProperties.getLanguage());
    }

    private void initializeCapture(long minVolume, long silenceDuration) {
        voiceCapture = capture(minVolume, silenceDuration, new VoiceCaptureListener() {

            @Override
            public void voiceCaptureStarted() {
                LOGGER.debug("STARTED SPEAKING");
                playSound(SOUND_RECORDING_STARTED);
            }

            @Override
            public void voiceCaptureEnded(File voiceFile) {
                LOGGER.debug("SPEAKING FINISHED");
                voiceCapture.pause();

                playSound(SOUND_RECORDING_STOPPED);
                recognizeVoiceInFile(voiceFile);
            }
        });
    }

    private void initializeSynthesiser(String language) {
        synthesiser = new VoiceSynthesiser();
        synthesiser.setLanguage(language);
    }

    private void initializeRecognizer(String apiKey, String language) {
        recognizer = new GoogleVoiceRecognizer(apiKey, language);
        recognizer.addListener(new VoiceRecognitionListener() {

            @Override
            public void onVoiceRecognized(VoiceRecognitionData recognitionData) {
                Writer responseWriter = new SpeechTransportResponseWriter(synthesiser) {

                    @Override
                    protected void onResponseSynthesisDone() {
                        voiceCapture.resume();
                    }
                };
                ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
                requestHandler.handleReactorRequest(createRequestInput(recognitionData), "VOICE", responseRenderer);
                responseRenderer.commit(responseWriter);
            }

            private ReactorRequestInput createRequestInput(VoiceRecognitionData recognitionData) {
                logRecognizedVoice(recognitionData);

                ReactorRequestInput requestInput = new ReactorRequestInput(recognitionData.getRecognizedVoice()
                    .toLowerCase());
                requestInput.setInteractive(true);
                return requestInput;
            }

            private void logRecognizedVoice(VoiceRecognitionData recognitionData) {
                LOGGER.debug("Recognized voice: {}", recognitionData.getRecognizedVoice());
            }

            @Override
            public void onError() {
                voiceCapture.resume();
            }
        });
    }

    private void recognizeVoiceInFile(File voiceFile) {
        try {
            LOGGER.debug("Recognizing recorded speech");
            recognizer.recognizeVoice(voiceFile, voiceCapture.getSampleRate());
        } catch (IOException e) {
            LOGGER.error("An error occurred while recognizing voice", e);
        }
    }

    @Override
    protected void startTransportKeptAlive(TransportProperties transportProperties, ReactorRequestHandler requestHandler) {
        startSpeechTransport(new SpeechTransportProperties(transportProperties), requestHandler);
    }

    @Override
    protected void stopTransportKeptAlive() {
    }

    @Override
    public void broadcast(ReactorResponse reactorResponse) {

    }

    @Override
    public boolean isRunning() {
        return true;
    }

    private void playSound(String soundFile) {
        try {
            playStream(getAudioFileStream(soundFile));
        } catch (Exception e) {
            LOGGER.error("An error occurred while playing sound {}", soundFile, e);
        }
    }

    private void playStream(InputStream stream) throws JavaLayerException {
        Player player = new Player(stream);
        player.play();
        player.close();
    }

    private InputStream getAudioFileStream(String soundFile) {
        return SpeechMessageTransport.class.getClassLoader().getResourceAsStream(soundFile);
    }
}

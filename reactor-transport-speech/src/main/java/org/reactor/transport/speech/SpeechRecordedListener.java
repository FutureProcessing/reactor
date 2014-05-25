package org.reactor.transport.speech;

import com.darkprograms.speech.recognizer.FlacEncoder;

import org.reactor.transport.speech.listen.MicrophoneFileRecordedEventListener;
import org.reactor.transport.speech.recognize.VoiceRecognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class SpeechRecordedListener implements MicrophoneFileRecordedEventListener {

    private final static Logger LOG = LoggerFactory.getLogger(SpeechRecordedListener.class);
    
    private final VoiceRecognizer recognizer;
    private final FlacEncoder flacEncoder;

    public SpeechRecordedListener(VoiceRecognizer recognizer, int sampleRate) {
        this.recognizer = recognizer;
        this.flacEncoder = new FlacEncoder(sampleRate);
    }

    @Override
    public void voiceFileRecorded(File voiceFile) {
        try {
            recognizer.recognizeVoiceFile(encodeToFlac(voiceFile));
            removeTemporaryVoiceFile(voiceFile);
        } catch (IOException e) {
            LOG.error("An error occurred while recognizing voice file", e);
        }
    }

    private void removeTemporaryVoiceFile(File voiceFile) {
        if (!voiceFile.delete()) {
            LOG.error("Unable to remove temporary voice file: {}", voiceFile.getAbsolutePath());
        }
    }

    private File encodeToFlac(File voiceFile) throws IOException {
        File tempFlacFile = File.createTempFile("record", ".flac");
        flacEncoder.convertWaveToFlac(voiceFile, tempFlacFile);
        return tempFlacFile;
    }
}

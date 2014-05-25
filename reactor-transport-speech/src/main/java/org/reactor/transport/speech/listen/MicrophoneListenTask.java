package org.reactor.transport.speech.listen;

import static java.io.File.createTempFile;
import static javax.sound.sampled.AudioFileFormat.Type.WAVE;
import com.darkprograms.speech.microphone.Microphone;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.sound.sampled.LineUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MicrophoneListenTask extends TimerTask {

    private final static Logger LOG = LoggerFactory.getLogger(MicrophoneListenTask.class);
    private final static int SLEEP_DELAY = 1000;
    private final static int MAX_TRIES = 5;
    private final static String TEM_FILE_PREFIX = "record";
    private final MicrophoneFileRecordedEventListener recordedEventListener;

    private Microphone microphone;

    public MicrophoneListenTask(MicrophoneFileRecordedEventListener recordedEventListener) {
        this.recordedEventListener = recordedEventListener;
        initializeMicrophone();
    }

    private void initializeMicrophone() {
        microphone = new Microphone(WAVE);
    }

    @Override
    public void run() {
        try {
            File recordFile = newRecordFile();
            LOG.debug("Recording into temporary file: {}", recordFile.getAbsolutePath());
            microphone.captureAudioToFile(recordFile);

            waitForRecord();

            microphone.close();
            recordedEventListener.voiceFileRecorded(recordFile);
        } catch (IOException e) {
            LOG.error("An error occurred while creating new record file", e);
        } catch (LineUnavailableException e) {
            LOG.error("Unable to create microphone handle", e);
        }
    }

    private void waitForRecord() {
        for (int run = MAX_TRIES; run > 0; run--) {
            LOG.debug("Finishing recording in {}", run);
            try {
                Thread.sleep(SLEEP_DELAY);
            } catch (InterruptedException e) {
                LOG.error("Something went wrong while sleeping microphone listening thread", e);
            }
        }
    }

    private File newRecordFile() throws IOException {
        return createTempFile(TEM_FILE_PREFIX, "");
    }
}

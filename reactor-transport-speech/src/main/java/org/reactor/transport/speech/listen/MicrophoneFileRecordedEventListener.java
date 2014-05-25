package org.reactor.transport.speech.listen;

import java.io.File;

public interface MicrophoneFileRecordedEventListener {

    void voiceFileRecorded(File voiceFile);

}

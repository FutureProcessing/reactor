package org.reactor.transport.speech.listen;

import java.util.Timer;

public class MicrophoneListener {

    public static final int START_IN_5_SEC = 5;
    private final int listenDelay;
    private final MicrophoneFileRecordedEventListener recordedEventListener;
    private Timer timer;

    public MicrophoneListener(int listenDelay, MicrophoneFileRecordedEventListener recordedEventListener) {
        this.listenDelay = listenDelay;
        this.recordedEventListener = recordedEventListener;
        initializeTimer();
    }

    private void initializeTimer() {
        timer = new Timer();
        timer.schedule(new MicrophoneListenTask(recordedEventListener), START_IN_5_SEC, listenDelay);
    }
}

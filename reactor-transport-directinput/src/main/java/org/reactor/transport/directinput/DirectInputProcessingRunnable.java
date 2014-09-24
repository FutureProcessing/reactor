package org.reactor.transport.directinput;

import static java.lang.System.in;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirectInputProcessingRunnable implements Runnable {

    private final static Logger LOG = LoggerFactory.getLogger(DirectInputProcessingRunnable.class);
    
    private final DirectInputListener inputListener;

    public DirectInputProcessingRunnable(DirectInputListener inputListener) {
        this.inputListener = inputListener;
    }

    @Override
    public void run() {
        while (isProcessing()) {
            processDirectInput();
        }
    }

    protected boolean isProcessing() {
        return true;
    }

    private void processDirectInput() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(in));
        try {
            inputListener.directInputGiven(bufferRead.readLine());
        } catch (IOException e) {
            LOG.error("An error occurred while processing direct input", e);
        }
        printCursor();
    }

    private void printCursor() {
        System.out.println();
        System.out.print("reactor> ");
    }
}

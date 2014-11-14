package org.reactor.transport.speech;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.reactor.voice.synthesis.VoiceSynthesiser;
import org.reactor.writer.MultilineSplittingWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class SpeechTransportResponseWriter extends MultilineSplittingWriter {

    private final static Logger LOGGER = LoggerFactory.getLogger(SpeechTransportResponseWriter.class);

    private final VoiceSynthesiser synthesiser;

    public SpeechTransportResponseWriter(VoiceSynthesiser synthesiser) {
        this.synthesiser = synthesiser;
    }

    private void playResponseText(String responseText) {
        try {
            InputStream mp3File = synthesiser.getMP3Data(responseText);
            new Player(mp3File).play();
        } catch (IOException | JavaLayerException e) {
            LOGGER.error("An error occurred while playing recognized voice back", e);
        }
    }

    @Override
    protected void writeLine(String line) throws IOException {
        playResponseText(line);
    }

    @Override
    public void flush() throws IOException {
        onResponseSynthesisDone();
    }

    @Override
    public void close() throws IOException {

    }

    protected void onResponseSynthesisDone() {

    }
}

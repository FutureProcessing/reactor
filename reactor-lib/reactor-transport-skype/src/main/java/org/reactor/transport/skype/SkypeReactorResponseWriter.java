package org.reactor.transport.skype;

import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

import org.reactor.writer.BufferedMultilineSplittingWriter;

import java.io.IOException;

public class SkypeReactorResponseWriter extends BufferedMultilineSplittingWriter {

    private final SkypeMessageSender sender;

    public SkypeReactorResponseWriter(User sender) {
        this.sender = new SkypeUserMessageSender(sender);
    }

    public SkypeReactorResponseWriter(Chat chat) {
        this.sender = new SkypeChatMessageSender(chat);
    }

    @Override
    protected void writeLines(String lines) throws IOException {
        try {
            sender.sendMessage(lines);
        } catch (SkypeException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void close() throws IOException {
        // do nothing
    }
}

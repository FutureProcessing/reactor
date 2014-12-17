package org.reactor.transport.skype;

import com.skype.SkypeException;
import com.skype.User;

public class SkypeUserMessageSender implements SkypeMessageSender {

    private final User sender;

    public SkypeUserMessageSender(User sender) {
        this.sender = sender;
    }

    @Override
    public void sendMessage(String message) throws SkypeException {
        sender.send(message);
    }
}

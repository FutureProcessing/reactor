package org.reactor.transport.skype;

import com.skype.Chat;
import com.skype.SkypeException;

public class SkypeChatMessageSender implements SkypeMessageSender {

    private final Chat chat;

    public SkypeChatMessageSender(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void sendMessage(String message) throws SkypeException {
        chat.send(message);
    }
}

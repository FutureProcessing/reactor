package org.reactor.transport.skype;

import com.skype.Chat;
import com.skype.SkypeException;
import com.skype.User;

public class SkypeMessageSender {

    private User user;
    private Chat chat;

    public SkypeMessageSender(User user) {
        this.user = user;
    }

    public SkypeMessageSender(Chat chat) {
        this.chat = chat;
    }

    public void sendMessage(String message) throws SkypeException {
        if (user != null) {
            user.send(message);
            return;
        }
        if (chat != null) {
            chat.send(message);
            return;
        }
    }
}

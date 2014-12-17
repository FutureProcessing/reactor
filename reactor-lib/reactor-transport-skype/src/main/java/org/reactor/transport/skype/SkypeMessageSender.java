package org.reactor.transport.skype;

import com.skype.SkypeException;

public interface SkypeMessageSender {

    void sendMessage(String message) throws SkypeException;
}

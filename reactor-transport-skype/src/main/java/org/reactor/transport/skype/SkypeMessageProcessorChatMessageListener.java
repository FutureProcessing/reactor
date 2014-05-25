package org.reactor.transport.skype;

import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.SkypeException;
import com.skype.User;

import org.reactor.transport.ReactorMessageTransportProcessor;

public class SkypeMessageProcessorChatMessageListener extends ChatMessageAdapter {

    private final ReactorMessageTransportProcessor messageTransport;

    public SkypeMessageProcessorChatMessageListener(ReactorMessageTransportProcessor messageTransport) {
        this.messageTransport = messageTransport;
    }

    @Override
    public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException {
        User sender = receivedChatMessage.getSender();
        messageTransport.processTransportMessage(receivedChatMessage.getContent(), sender.getId(),
            new SkypeReactorResponseWriter(receivedChatMessage.getChat()));

    }
}

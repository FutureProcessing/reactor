package org.reactor.transport.skype;

import com.skype.ChatMessage;
import com.skype.ChatMessageAdapter;
import com.skype.SkypeException;
import com.skype.User;

import org.reactor.request.ReactorRequestInput;
import org.reactor.transport.ReactorRequestHandler;

public class SkypeMessageProcessorChatMessageListener extends ChatMessageAdapter {

    private final ReactorRequestHandler requestHandler;

    public SkypeMessageProcessorChatMessageListener(ReactorRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException {
        User sender = receivedChatMessage.getSender();
        requestHandler.handleReactorRequest(new ReactorRequestInput(receivedChatMessage.getContent()), sender.getId(),
                new SkypeReactorResponseWriter(receivedChatMessage.getChat()));

    }
}

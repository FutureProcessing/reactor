package org.reactor.transport.skype;

import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;

import com.skype.ChatMessage;
import com.skype.ChatMessageListener;
import com.skype.SkypeException;
import com.skype.User;

public class SkypeMessageProcessorChatMessageListener implements ChatMessageListener {

    private final static String REACT_PREFIX = "!";

    private final ReactorRequestHandler requestHandler;

    public SkypeMessageProcessorChatMessageListener(ReactorRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void chatMessageReceived(ChatMessage receivedChatMessage) throws SkypeException {
        processChatMessage(receivedChatMessage);
    }

    @Override
    public void chatMessageSent(ChatMessage sentChatMessage) throws SkypeException {
        processChatMessage(sentChatMessage);
    }

    private void processChatMessage(ChatMessage chatMessage) throws SkypeException {
        User sender = chatMessage.getSender();
        String chatMessageContent = chatMessage.getContent();
        if (!reactPrefixMatches(chatMessageContent)) {
            return;
        }
        String chatMessageNoPrefix = chatMessageContent.substring(1);
        ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
        requestHandler.handleReactorRequest(new ReactorRequestInput(chatMessageNoPrefix), sender.getId(),
                responseRenderer);
        responseRenderer.commit(new SkypeReactorResponseWriter(chatMessage.getChat()));
    }

    private boolean reactPrefixMatches(String chatMessageContent) {
        return chatMessageContent.startsWith(REACT_PREFIX);
    }
}

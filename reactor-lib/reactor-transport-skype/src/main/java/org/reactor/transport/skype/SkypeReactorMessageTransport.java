package org.reactor.transport.skype;

import com.skype.Application;
import com.skype.ContactList;
import com.skype.Friend;
import com.skype.Skype;
import com.skype.SkypeException;

import org.reactor.response.ReactorResponse;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.reactor.transport.TransportProperties;
import org.reactor.transport.alive.KeepAliveReactorMessageTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkypeReactorMessageTransport extends KeepAliveReactorMessageTransport {

    private final static Logger LOG = LoggerFactory.getLogger(SkypeReactorMessageTransport.class);

    private Application skypeApplication;
    private Iterable<String> broadcastList;

    private void startSkypeTransport(SkypeTransportProperties transportProperties,
                                     ReactorRequestHandler messageTransport) {
        LOG.debug("Starting Skype message transport ...");
        initializeSkypeConnection(transportProperties);
        initializeMessageListener(messageTransport);
        initializeBroadcastList(transportProperties);
    }

    private void initializeSkypeConnection(SkypeTransportProperties transportProperties) {
        LOG.debug("Requesting Skype access, please have a look at your Skype window instance ...");
        try {
            skypeApplication = Skype.addApplication(transportProperties.getApplicationName());
        } catch (SkypeException e) {
            LOG.error("Skype access failed ;(");
            return;
        }
        LOG.debug("Request accepted :)");
    }

    private void initializeMessageListener(ReactorRequestHandler messageTransport) {
        try {
            Skype.addChatMessageListener(new SkypeMessageProcessorChatMessageListener(messageTransport));
        } catch (SkypeException e) {
            LOG.error("Unable to initializeObjectMethodInvoker message listener", e);
        }
    }

    private void initializeBroadcastList(SkypeTransportProperties transportProperties) {
        broadcastList = transportProperties.getBroadcastList();
    }

    @Override
    protected void startTransportKeptAlive(TransportProperties transportProperties,
                                        ReactorRequestHandler requestHandler) {
        startSkypeTransport(new SkypeTransportProperties(transportProperties), requestHandler);
    }

    @Override
    protected void stopTransportKeptAlive() {
        try {
            skypeApplication.finish();
            skypeApplication = null;
        } catch (SkypeException e) {
            LOG.error("Error while stopping Skype transport", e);
        } catch (IllegalStateException e) {
            LOG.error("Something went wrong while stopping Skype transport, it's own shutdown hook invoked?", e);
        }
    }

    @Override
    public void broadcast(ReactorResponse reactorResponse) {
        try {
            ContactList contactList = Skype.getContactList();
            Friend[] friends = contactList.getAllFriends();
            for (Friend friend : friends) {
                try {
                    if (!validateFriendForBroadcast(friend)) {
                        continue;
                    }
                    ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
                    reactorResponse.renderResponse(responseRenderer);
                    responseRenderer.commit(new SkypeReactorResponseWriter(friend));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SkypeException e) {
            LOG.error("An error occurred while broadcasting Skype message", e);
        }
    }

    private boolean validateFriendForBroadcast(Friend friend) {
        for (String userId : broadcastList) {
            if (userId.equals(friend.getId())) {
                return true;
            }
        }
        LOG.debug("User with id = {} is not on a broadcast list", friend.getId());
        return false;
    }

    @Override
    public boolean isRunning() {
        return skypeApplication != null;
    }
}

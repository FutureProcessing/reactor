package org.reactor.transport.telnet;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.String.valueOf;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.google.common.util.concurrent.ListenableFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.response.renderer.simple.SimpleReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class TelnetMessageProcessorHandler extends IoHandlerAdapter {

    private static final String SESSION_INTERACTIVE = "INTERACTIVE";
    private static final String MESSAGE_INTERACTIVE_TOGGLE = "!interactive";
    private static final int REQUEST_TIMEOUT = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(TelnetMessageProcessorHandler.class);

    private final ReactorRequestHandler requestHandler;

    public TelnetMessageProcessorHandler(ReactorRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String textMessage = (String) message;
        if (isInteractiveToggleMessage(textMessage)) {
            toggleSessionInteractive(session);
            return;
        }
        ReactorRequestInput input = new ReactorRequestInput(textMessage);
        if (isSessionInteractive(session)) {
            input.setInteractive(true);
        }
        ReactorResponseRenderer responseRenderer = new SimpleReactorResponseRenderer();
        ListenableFuture<?> futureResponse = requestHandler.handleReactorRequest(input, valueOf(session.getId()), responseRenderer);
        awaitResponse(futureResponse);
        responseRenderer.commit(new TelnetSessionResponseWriter(session));
    }

    private void awaitResponse(ListenableFuture<?> request) {
        try {
            request.get(REQUEST_TIMEOUT, SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("An error occurred while handling request", e);
        }
    }

    private void toggleSessionInteractive(IoSession session) {
        if (isSessionInteractive(session)) {
            session.write("Turning OFF interactive mode for session");
            session.setAttribute(SESSION_INTERACTIVE, FALSE);
            return;
        }
        session.write("Turning ON interactive mode for session");
        session.setAttribute(SESSION_INTERACTIVE, TRUE);
    }

    private boolean isSessionInteractive(IoSession session) {
        Boolean interactive = (Boolean) session.getAttribute(SESSION_INTERACTIVE);
        return interactive != null && interactive;
    }

    private boolean isInteractiveToggleMessage(String textMessage) {
        return MESSAGE_INTERACTIVE_TOGGLE.equals(textMessage);
    }
}

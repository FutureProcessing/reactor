package org.reactor.transport.http.rest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.reactor.renderer.JSONReactorResponseRenderer;
import org.reactor.request.ReactorRequestInput;
import org.reactor.transport.ReactorRequestHandler;

import static org.reactor.transport.http.ResponseAwaiter.awaitResponse;

public class RestHandler extends AbstractHandler {

    private static final String SENDER = "HTTP";
    private static final String CONTENT_TYPE = "application/json";

    private final ReactorRequestHandler requestHandler;

    public RestHandler(ReactorRequestHandler requestHandler) {
        this.requestHandler = requestHandler;
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String reactorInput = IOUtils.toString(baseRequest.getInputStream());
        processTransportMessage(reactorInput, response);
        markRequestHandled(baseRequest);
    }

    private void processTransportMessage(String reactorInput, HttpServletResponse response) throws IOException {
        prepareContentType(response);

        JSONReactorResponseRenderer renderer = new JSONReactorResponseRenderer(response.getWriter());
        awaitResponse(requestHandler.handleReactorRequest(new ReactorRequestInput(reactorInput), SENDER, renderer));
    }

    private void markRequestHandled(Request request) {
        request.setHandled(true);
    }

    private void prepareContentType(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
    }
}

package org.reactor.transport.http.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.reactor.renderer.JSONReactorResponseRenderer;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.renderer.ReactorResponseRenderer;
import org.reactor.transport.ReactorRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

public class RestHandler extends AbstractHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestHandler.class);
    private static final String SENDER = "HTTP";
    private static final String CONTENT_TYPE = "application/json";
    private static final int REQUEST_TIMEOUT = 5;

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

        PrintWriter writer = response.getWriter();
        ReactorResponseRenderer renderer = new JSONReactorResponseRenderer();
        awaitResponse(requestHandler.handleReactorRequest(new ReactorRequestInput(reactorInput), SENDER, renderer));
        renderer.commit(writer);
        writer.flush();
    }

    private void awaitResponse(Future<?> request) {
        try {
            request.get(REQUEST_TIMEOUT, SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("An error occurred while handling request", e);
        }
    }

    private void markRequestHandled(Request request) {
        request.setHandled(true);
    }

    private void prepareContentType(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
    }
}

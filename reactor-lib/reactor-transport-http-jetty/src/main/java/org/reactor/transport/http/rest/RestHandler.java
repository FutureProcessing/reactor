package org.reactor.transport.http.rest;

import java.io.IOException;
import java.io.PrintWriter;

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

public class RestHandler extends AbstractHandler {

    private static final String SENDER = "HTTP";
    private static final String CONTENT_TYPE = "text/plain";

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
        requestHandler.handleReactorRequest(new ReactorRequestInput(reactorInput), SENDER, renderer);
        renderer.commit(writer);
        writer.flush();
    }

    private void markRequestHandled(Request request) {
        request.setHandled(true);
    }

    private void prepareContentType(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
    }
}

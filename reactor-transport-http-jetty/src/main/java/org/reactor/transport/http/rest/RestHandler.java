package org.reactor.transport.http.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.reactor.transport.ReactorMessageTransportProcessor;

public class RestHandler extends AbstractHandler {

    private static final String SENDER = "HTTP";
    private static final String CONTENT_TYPE = "text/plain";

    private final ReactorMessageTransportProcessor messageProcessor;

    public RestHandler(ReactorMessageTransportProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
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
        messageProcessor.processTransportMessage(reactorInput, SENDER, writer);
        writer.flush();
    }

    private void markRequestHandled(Request request) {
        request.setHandled(true);
    }

    private void prepareContentType(HttpServletResponse response) {
        response.setContentType(CONTENT_TYPE);
    }
}

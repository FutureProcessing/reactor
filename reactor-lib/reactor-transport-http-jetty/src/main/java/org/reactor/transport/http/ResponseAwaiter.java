package org.reactor.transport.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ResponseAwaiter {

    private static final int REQUEST_TIMEOUT = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAwaiter.class);

    public static String awaitResponse(Future<String> request) {
        try {
            return request.get(REQUEST_TIMEOUT, SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("An error occurred while handling request", e);
        }
        throw new IllegalStateException("Expected request to end by response or exception.");
    }
}

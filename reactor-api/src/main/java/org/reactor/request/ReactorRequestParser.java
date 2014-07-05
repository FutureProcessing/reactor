package org.reactor.request;

import static java.util.Arrays.copyOfRange;
import static org.reactor.request.ArgumentsParser.parseArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactorRequestParser {

    private final static Logger LOG = LoggerFactory.getLogger(ReactorRequestParser.class);

    private final String[] requestArguments;
    private final String sender;

    public static <T> ReactorRequest<T> parseRequest(String sender, String requestString) {
        return new ReactorRequestParser(sender, parseArguments(requestString)).parseRequest();
    }

    public static <T> ReactorRequest<T> parseNestedRequest(ReactorRequest<String> reactorRequest) {
        return new ReactorRequestParser(reactorRequest.getSender(), reactorRequest.getRequestData()).parseRequest();
    }

    private ReactorRequest parseRequest() {
        if (!validateArguments(requestArguments)) {
            throw new ReactorRequestParsingException("Wrong arguments count");
        }
        return new ReactorRequest(sender, requestArguments[0],
            copyOfRange(requestArguments, 1, requestArguments.length));
    }

    private ReactorRequestParser(String sender, String... requestArguments) {
        this.requestArguments = requestArguments;
        this.sender = sender;
    }

    private boolean validateArguments(String[] arguments) {
        return validateMinArgumentsLength(arguments, 1);
    }

    private boolean validateMinArgumentsLength(String[] arguments, int minLength) {
        if (arguments.length < minLength) {
            LOG.debug("Not enough arguments to parse request message: {}, required min length: {}", arguments.length,
                minLength);
            return false;
        }
        return true;
    }

}

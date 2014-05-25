package org.reactor.request;

import static java.util.Arrays.copyOfRange;
import static org.reactor.request.ArgumentsParser.parseArguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactorRequestParser {

    private final static Logger LOG = LoggerFactory.getLogger(ReactorRequestParser.class);

    private final String[] requestArguments;
    private final String sender;

    public static ReactorRequest parseRequestFromLine(String sender, String requestLine) {
        return new ReactorRequestParser(sender, parseArguments(requestLine)).parseRequest();
    }

    public static ReactorRequest parseRequestFromArguments(String sender, String... requestArguments) {
        return new ReactorRequestParser(sender, requestArguments).parseRequest();
    }

    public static ReactorRequest parseNestedRequest(ReactorRequest reactorRequest) {
        return new ReactorRequestParser(reactorRequest.getSender(), reactorRequest.getArguments()).parseRequest();
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

package org.reactor;

import static org.reactor.properties.PropertiesBuilder.propertiesBuilder;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import com.google.common.base.Optional;
import java.io.StringWriter;
import org.reactor.reactor.ReactorController;
import org.reactor.request.ReactorRequestInput;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactorRunner {

    private final static String REACTOR_PROPERTIES = "reactor.properties";
    private final static Logger LOG = LoggerFactory.getLogger(ReactorRunner.class);
    public static final String SENDER_SYSTEM = "SYSTEM";

    private ReactorResponse invokeReactorController(ReactorProperties reactorProperties,
                                                    ReactorRequestInput requestInput) throws IllegalAccessException,
            InstantiationException, ClassNotFoundException {
        if (!validateMinArgumentsLength(requestInput, 1)) {
            return NO_RESPONSE;
        }
        ReactorController reactorController = initializeController(reactorProperties);
        return processWithReactorController(reactorController, requestInput);
    }

    private ReactorController initializeController(ReactorProperties reactorProperties) {
        LOG.debug("Initializing Reactor Controller ...");
        ReactorController reactorController = new ReactorController();
        reactorController.initReactors(reactorProperties);
        return reactorController;
    }

    private ReactorResponse processWithReactorController(ReactorController reactorController,
                                                         ReactorRequestInput requestInput) {
        Optional<Reactor> reactorOptional = reactorController.reactorMatchingInput(requestInput);
        if (reactorOptional.isPresent()) {
            Reactor reactor = reactorOptional.get();
            return reactor.react(SENDER_SYSTEM, requestInput);
        }
        LOG.warn("Unable to find reactor matching input: {}", requestInput.getArguments());
        return NO_RESPONSE;
    }

    private boolean validateMinArgumentsLength(ReactorRequestInput arguments, int minLength) {
        if (arguments.argumentsLength() < minLength) {
            LOG.debug("Not enough arguments to process message: {}, required min length: {}",
                arguments.argumentsLength(), minLength);
            return false;
        }
        return true;
    }

    public static void main(String[] arguments) {
        try {
            ReactorRequestInput requestInput = new ReactorRequestInput(arguments);
            ReactorResponse response = new ReactorRunner().invokeReactorController(new ReactorProperties(
                propertiesBuilder().loadFromResourceStream(REACTOR_PROPERTIES).build()), requestInput);
            StringWriter writer = new StringWriter();
            response.renderResponse(writer);
            System.out.println(writer.toString());
        } catch (Exception e) {
            LOG.error("An exception has occurred while running reactor command", e);
        }
    }
}

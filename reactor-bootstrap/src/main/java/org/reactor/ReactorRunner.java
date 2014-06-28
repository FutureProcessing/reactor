package org.reactor;

import static com.google.common.base.Joiner.on;
import static org.reactor.properties.PropertiesBuilder.propertiesBuilder;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import com.google.common.base.Optional;
import java.io.StringWriter;
import org.reactor.reactor.ReactorController;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactorRunner {

    private final static String REACTOR_PROPERTIES = "reactor.properties";
    private final static Logger LOG = LoggerFactory.getLogger(ReactorRunner.class);
    public static final String SENDER_SYSTEM = "SYSTEM";

    private ReactorResponse invokeReactorController(ReactorProperties reactorProperties, String... arguments)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!validateMinArgumentsLength(arguments, 1)) {
            return NO_RESPONSE;
        }
        ReactorController reactorController = initializeController(reactorProperties);
        return processWithReactorController(reactorController, on(' ').join(arguments));
    }

    private ReactorController initializeController(ReactorProperties reactorProperties) {
        LOG.debug("Initializing Reactor Controller ...");
        ReactorController reactorController = new ReactorController();
        reactorController.initReactors(reactorProperties);
        return reactorController;
    }

    private ReactorResponse processWithReactorController(ReactorController reactorController, String reactorInput) {
        Optional<Reactor> reactorOptional = reactorController.reactorMatchingInput(reactorInput);
        if (reactorOptional.isPresent()) {
            Reactor reactor = reactorOptional.get();
            return reactor.react(SENDER_SYSTEM, reactorInput);
        }
        LOG.warn("Unable to find reactor matching input: {}", reactorInput);
        return NO_RESPONSE;
    }

    private boolean validateMinArgumentsLength(String[] arguments, int minLength) {
        if (arguments.length < minLength) {
            LOG.debug("Not enough arguments to process message: {}, required min length: {}", arguments.length,
                minLength);
            return false;
        }
        return true;
    }

    public static void main(String[] arguments) {
        try {
            ReactorResponse response = new ReactorRunner().invokeReactorController(new ReactorProperties(
                    propertiesBuilder().loadFromResourceStream(REACTOR_PROPERTIES).build()), arguments);
            StringWriter writer = new StringWriter();
            response.renderResponse(writer);
            System.out.println(writer.toString());
        } catch (Exception e) {
            LOG.error("An exception has occurred while running reactor command", e);
        }
    }
}

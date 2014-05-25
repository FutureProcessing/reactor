package org.reactor;

import static org.reactor.properties.PropertiesBuilder.propertiesBuilder;
import static org.reactor.request.ReactorRequestParser.parseRequestFromArguments;
import static org.reactor.response.NoResponse.NO_RESPONSE;
import static org.reactor.utils.ClassUtils.newInstance;
import static org.reactor.utils.ClassUtils.tryCall;
import java.io.StringWriter;
import org.reactor.response.ReactorResponse;
import org.reactor.utils.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReactorRunner {

    private final static String REACTOR_PROPERTIES = "reactor.properties";
    private final static Logger LOG = LoggerFactory.getLogger(ReactorRunner.class);
    public static final String SENDER_SYSTEM = "SYSTEM";

    private ReactorResponse runReactorCommand(ReactorProperties reactorProperties, String... commandArguments)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (!validateMinArgumentsLength(commandArguments, 1)) {
            return NO_RESPONSE;
        }
        LOG.debug("Trying to use reactor implementation: {}", reactorProperties.getReactorImplementation());
        Reactor reactor = newInstance(reactorProperties.getReactorImplementation(), Reactor.class);
        tryInitReactor(reactor, reactorProperties);
        return reactor.react(parseRequestFromArguments(SENDER_SYSTEM, commandArguments));
    }

    private void tryInitReactor(final Reactor reactor, final ReactorProperties reactorProperties) {
        tryCall(reactor, InitializingReactor.class, new ClassUtils.PossibleTypeAction<InitializingReactor, Void>() {
            @Override
            public Void invokeAction(InitializingReactor subject) {
                subject.initReactor(new ReactorProperties(reactorProperties));
                return null;
            }
        });
    }

    private boolean validateMinArgumentsLength(String[] arguments, int minLength) {
        if (arguments.length < minLength) {
            LOG.debug("Not enough arguments to process message: {}, required min length: {}", arguments.length,
                minLength);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            ReactorResponse response = new ReactorRunner().runReactorCommand(new ReactorProperties(
                propertiesBuilder().loadFromResourceStream(REACTOR_PROPERTIES).build()), args);
            StringWriter writer = new StringWriter();
            response.renderResponse(writer);
            System.out.println(writer.toString());
        } catch (Exception e) {
            LOG.error("An exception has occurred while running reactor command", e);
        }
    }
}

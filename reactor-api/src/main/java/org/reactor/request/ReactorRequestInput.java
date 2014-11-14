package org.reactor.request;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newLinkedList;
import static java.util.Arrays.asList;
import static org.reactor.request.ArgumentsParser.parseArguments;
import static org.reactor.utils.StringUtils.quotedIterable;
import com.google.common.base.Predicate;

import java.util.List;
import org.reactor.Reactor;

// TODO needs testing!
public class ReactorRequestInput {

    public static Predicate<Reactor> TRIGGER_MATCHES_INPUT(ReactorRequestInput reactorInput) {
        return reactor -> reactorInput.matchesTriggeringExpression(reactor.getTriggeringExpression());
    }

    public static Predicate<Reactor> TRIGGER_MATCHES(String reactorTrigger) {
        return reactor -> reactor.getTriggeringExpression().matches(reactorTrigger);
    }

    private final List<String> argumentsList;
    private boolean interactive;

    public ReactorRequestInput(String inputData) {
        this(parseArguments(inputData));
    }

    public ReactorRequestInput(String... inputDataArguments) {
        argumentsList = newLinkedList(asList(inputDataArguments));
    }

    public ReactorRequestInput popArguments() {
        if (argumentsLength() > 0) {
            argumentsList.remove(0);
        }
        return new ReactorRequestInput(getArguments());
    }

    public ReactorRequestInput pushArguments(String... arguments) {
        argumentsList.addAll(asList(arguments));
        return this;
    }

    public String[] getArguments() {
        return from(argumentsList).toArray(String.class);
    }

    public boolean matchesTriggeringExpression(String triggeringExpression) {
        return validateArgumentsLength(argumentsList) && triggeringExpression.matches(argumentsList.get(0));
    }

    private boolean validateArgumentsLength(List<String> argumentsList) {
        return argumentsList.size() > 0;
    }

    public boolean isEmpty() {
        return argumentsList.isEmpty();
    }

    public int argumentsLength() {
        return argumentsList.size();
    }

    public String getArgumentsAsString() {
        return on(' ').join(quotedIterable(argumentsList));
    }

    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }
}

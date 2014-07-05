package org.reactor.request;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.FluentIterable.from;
import static com.google.common.collect.Lists.newArrayList;
import static org.reactor.request.ArgumentsParser.parseArguments;
import static org.reactor.utils.StringUtils.quotedIterable;
import com.google.common.base.Predicate;
import java.util.List;
import org.reactor.Reactor;

// TODO needs testing!
public class ReactorRequestInput {

    public final static Predicate<Reactor> TRIGGER_MATCHES(final ReactorRequestInput reactorInput) {
        return reactor -> reactorInput.matchesTriggeringExpression(reactor.getTriggeringExpression());
    }

    private final List<String> argumentsList;

    public ReactorRequestInput(String inputData) {
        argumentsList = newArrayList(parseArguments(inputData));
    }

    public ReactorRequestInput(String... inputDataArguments) {
        argumentsList = newArrayList(inputDataArguments);
    }

    public ReactorRequestInput popArguments() {
        argumentsList.remove(0);
        return new ReactorRequestInput(getArguments());
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
}

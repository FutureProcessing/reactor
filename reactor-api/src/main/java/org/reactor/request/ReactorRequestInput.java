package org.reactor.request;

import static com.google.common.base.Joiner.on;
import static com.google.common.collect.Lists.newArrayList;
import static org.reactor.request.ArgumentsParser.parseArguments;
import com.google.common.base.Predicate;
import java.util.List;
import org.reactor.Reactor;

// TODO needs testing!
public class ReactorRequestInput {

    public final static Predicate<Reactor> TRIGGER_MATCHES(final ReactorRequestInput reactorInput) {
        return new Predicate<Reactor>() {

            @Override
            public boolean apply(Reactor reactor) {
                return reactorInput.matchesTriggeringExpression(reactor.getTriggeringExpression());
            }
        };
    }

    private final List<String> argumentsList;

    public ReactorRequestInput(String inputData) {
        argumentsList = newArrayList(parseArguments(inputData));
    }
    
    private ReactorRequestInput(List<String> inputDataArguments) {
        argumentsList = inputDataArguments;
    }

    public ReactorRequestInput subRequest() {
        argumentsList.remove(0);
        return new ReactorRequestInput(argumentsList);
    }

    public String getArguments() {
        return on(' ').join(argumentsList);
    }

    public boolean matchesTriggeringExpression(String triggeringExpression) {
        return triggeringExpression.matches(argumentsList.get(0));
    }
}

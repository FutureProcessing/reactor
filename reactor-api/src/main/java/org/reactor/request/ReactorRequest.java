package org.reactor.request;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReactorRequest {

    private final String sender;
    private final String trigger;
    private final String[] arguments;

    public ReactorRequest(String sender, String trigger, String... arguments) {
        this.sender = sender;
        this.trigger = trigger;
        this.arguments = arguments;
    }

    public String[] getArguments() {
        return arguments;
    }

    public String getTrigger() {
        return trigger;
    }

    public boolean triggerMatches(String triggeringExpression) {
        checkNotNull(triggeringExpression);
        return trigger.matches(triggeringExpression);
    }

    public String getSender() {
        return sender;
    }
}

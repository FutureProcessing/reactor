package org.reactor.request;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReactorRequest<T> {

    private final String sender;
    private final String trigger;
    private final T requestData;

    public  ReactorRequest(String sender, String trigger, T requestData) {
        this.sender = sender;
        this.trigger = trigger;
        this.requestData = requestData;
    }

    public T getRequestData() {
        return requestData;
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

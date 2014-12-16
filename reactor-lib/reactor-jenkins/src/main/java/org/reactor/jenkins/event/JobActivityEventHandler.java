package org.reactor.jenkins.event;

import static org.reactor.jenkins.event.JobActivityEvent.FROM_JSON;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.json.JSONObject;
import org.reactor.event.ReactorEventConsumer;

public class JobActivityEventHandler extends IoHandlerAdapter {

    private final ReactorEventConsumer<JobActivityEvent> eventConsumer;

    public JobActivityEventHandler(ReactorEventConsumer<JobActivityEvent> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        String jobActivity = message.toString();
        eventConsumer.consumeEvent(FROM_JSON.apply(new JSONObject(jobActivity)));
    }
}

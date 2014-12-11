package org.reactor.jenkins.response;

import static java.lang.String.format;
import com.offbytwo.jenkins.model.Job;
import org.reactor.response.list.ListElementFormatter;

public class JobListElementFormatter implements ListElementFormatter<Job> {

    @Override
    public String formatListElement(long elementIndex, Job listElement) {
        return format("%d. [%s] %s", elementIndex, listElement.getName(), listElement.getUrl());
    }
}

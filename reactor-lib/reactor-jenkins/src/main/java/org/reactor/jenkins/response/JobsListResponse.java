package org.reactor.jenkins.response;

import static com.google.common.collect.Lists.newArrayList;

import com.offbytwo.jenkins.model.Job;

import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;

import java.util.List;

public class JobsListResponse extends ListReactorResponse<Job> {

    private List<Job> jobs = newArrayList();

    @Override
    protected List<Job> getElements() {
        return jobs;
    }

    public JobsListResponse addJob(Job job) {
        jobs.add(job);
        return this;
    }

    @Override
    protected ListElementFormatter<Job> getElementFormatter() {
        return new JobListElementFormatter();
    }
}

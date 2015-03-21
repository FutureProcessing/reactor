package org.reactor.jenkins.response;

import com.offbytwo.jenkins.model.Job;
import org.reactor.response.ReactorResponse;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;

public class JobsListResponse implements ReactorResponse {

    private final List<String> jobsNames;

    public JobsListResponse() {
        this.jobsNames = newArrayList();
    }

    public JobsListResponse(Iterable<Job> jobs) {
        this.jobsNames = stream(jobs.spliterator(), false).map(Job::getName).collect(toList());
    }

    public JobsListResponse(List<Job> jobs) {
        this.jobsNames = jobs.stream().map(Job::getName).collect(toList());
    }

    public List<String> getJobsNames() {
        return jobsNames;
    }
}

package org.reactor.jenkins.command;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import org.reactor.ReactorProcessingException;
import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.jenkins.response.JobsListResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

import java.io.IOException;
import java.util.Map;

@ReactOn(value = "jobs", description = "Prints list of all defined jobs")
public class JenkinsJobsReactor extends AbstractAnnotatedReactor {

    JenkinsServer jenkins;

    public JenkinsJobsReactor(JenkinsServer jenkins) {
        this.jenkins = jenkins;
    }

    @Override
    public ReactorResponse doReact(ReactorRequest request) {
        JobsListResponse listResponse = new JobsListResponse();
        try {
            Map<String, Job> jobs = jenkins.getJobs();
            for (Job job : jobs.values()) {
                listResponse.addJob(job.details());
            }
        } catch (IOException e) {
            throw new ReactorProcessingException(e);
        }
        return listResponse;
    }
}

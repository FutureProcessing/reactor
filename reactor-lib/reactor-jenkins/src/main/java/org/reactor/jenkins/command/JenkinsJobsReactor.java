package org.reactor.jenkins.command;

import com.offbytwo.jenkins.JenkinsServer;
import org.reactor.AbstractAnnotatedReactor;
import org.reactor.ReactorProcessingException;
import org.reactor.annotation.ReactOn;
import org.reactor.jenkins.response.JobsListResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

import java.io.IOException;

@ReactOn(value = "jobs", description = "Prints list of all defined jobs")
public class JenkinsJobsReactor extends AbstractAnnotatedReactor<Void> {

    JenkinsServer jenkins;

    public JenkinsJobsReactor(JenkinsServer jenkins) {
        super(Void.class);
        this.jenkins = jenkins;
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<Void> request) {
        try {
            return new JobsListResponse(jenkins.getJobs().values());
        } catch (IOException e) {
            throw new ReactorProcessingException(e);
        }
    }
}

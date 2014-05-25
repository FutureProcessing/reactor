package org.reactor.jenkins.command;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;

import org.reactor.ReactorProcessingException;
import org.reactor.annotation.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.jenkins.response.JobDetailsResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

import java.io.IOException;

@ReactOn(value = "job", description = "Prints detailed information about given job")
public class JenkinsJobReactor extends AbstractAnnotatedReactor {

    private JenkinsServer jenkins;

    public JenkinsJobReactor(JenkinsServer jenkins) {
        this.jenkins = jenkins;
    }

    @Override
    public ReactorResponse doReact(ReactorRequest request) {
        try {
            JobWithDetails job = jenkins.getJob(request.getArguments()[0]);
            return new JobDetailsResponse(job);
        } catch (IOException e) {
            throw new ReactorProcessingException(e);
        }
    }
}

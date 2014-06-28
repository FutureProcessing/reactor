package org.reactor.jenkins.command;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import java.io.IOException;
import org.reactor.AbstractReactor;
import org.reactor.ReactorProcessingException;
import org.reactor.annotation.ReactOn;
import org.reactor.jenkins.response.JobDetailsResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

@ReactOn(value = "job", description = "Prints detailed information about given job")
public class JenkinsJobReactor extends AbstractReactor<String> {

    private JenkinsServer jenkins;

    public JenkinsJobReactor(JenkinsServer jenkins) {
        super(String.class);
        this.jenkins = jenkins;
    }

    @Override
    public ReactorResponse doReact(ReactorRequest<String> request) {
        try {
            JobWithDetails job = jenkins.getJob(request.getRequestData());
            return new JobDetailsResponse(job);
        } catch (IOException e) {
            throw new ReactorProcessingException(e);
        }
    }
}

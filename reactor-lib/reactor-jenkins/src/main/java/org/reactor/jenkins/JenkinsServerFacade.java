package org.reactor.jenkins;

import static java.lang.String.format;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.reactor.ReactorProcessingException;

public class JenkinsServerFacade {

    private final JenkinsServer jenkinsServer;

    public static JenkinsServerFacade forServerDetails(String serverUrl, String username, String password)
            throws URISyntaxException {
        return new JenkinsServerFacade(new JenkinsServer(new URI(serverUrl), username, password));
    }

    private JenkinsServerFacade(JenkinsServer jenkinsServer) {
        this.jenkinsServer = jenkinsServer;
    }

    public Iterable<Job> getJobs() throws IOException {
        return jenkinsServer.getJobs().values();
    }

    public JobWithDetails getJob(String jobName) throws IOException {
        return jenkinsServer.getJob(jobName);
    }

    public void buildJob(String jobName) throws IOException {
        JobWithDetails job = getJob(jobName);
        validateJobForBuilding(jobName, job);
        job.build();
    }

    private void validateJobForBuilding(String jobName, JobWithDetails job) {
        validateJobExistence(jobName, job);
        if (!job.isBuildable()) {
            throw new ReactorProcessingException(format("Given job is not buildable: %s", jobName));
        }
    }

    private void validateJobExistence(String jobName, Job job) {
        if (job == null) {
            throw new ReactorProcessingException(format("Unable to find job with name %s", jobName));
        }
    }
}

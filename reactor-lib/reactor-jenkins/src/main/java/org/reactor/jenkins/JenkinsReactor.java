package org.reactor.jenkins;

import static org.reactor.jenkins.JenkinsServerFacade.forServerDetails;
import static org.reactor.jenkins.event.JobActivityEvent.TO_RESPONSE;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import java.io.IOException;
import java.net.URISyntaxException;
import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorInitializationException;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.event.EventProducingReactor;
import org.reactor.event.ReactorEventConsumerFactory;
import org.reactor.jenkins.event.JobActivityEvent;
import org.reactor.jenkins.event.JobActivityEventListener;
import org.reactor.jenkins.request.JenkinsJobDetailsRequestData;
import org.reactor.jenkins.request.JenkinsJobRequestData;
import org.reactor.jenkins.response.JobBuildQueuedResponse;
import org.reactor.jenkins.response.JobDetailsResponse;
import org.reactor.jenkins.response.JobsListResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.reactor.response.StringReactorResponse;

@ReactOn(value = "jenkins", description = "Jenkins reactor")
public class JenkinsReactor extends AbstractNestingReactor implements EventProducingReactor {

    private JenkinsServerFacade jenkinsServerFacade;
    private JobActivityEventListener jobActivityEventListener;

    @ReactOn(value = "jobs", description = "Prints list of defined jobs on given Jenkins instance")
    public ReactorResponse listJobs(ReactorRequest<Void> reactorRequest) throws IOException {
        JobsListResponse listResponse = new JobsListResponse();
        Iterable<Job> jobs = jenkinsServerFacade.getJobs();
        for (Job job : jobs) {
            listResponse.addJob(job);
        }
        return listResponse;
    }

    @ReactOn(value = "job", description = "Prints basic information about given job")
    public ReactorResponse getJobDetails(ReactorRequest<JenkinsJobDetailsRequestData> jenkinsJobRequest)
            throws IOException {
        JobWithDetails job = jenkinsServerFacade.getJob(jenkinsJobRequest.getRequestData().getJobName());
        if (job == null) {
            throw new JobNotFoundException(jenkinsJobRequest.getRequestData().getJobName());
        }
        if (jenkinsJobRequest.getRequestData().isJobStatusOnly()) {
            return new StringReactorResponse(job.getLastBuild().details().getResult().name());
        }
        return new JobDetailsResponse(job);
    }

    @ReactOn(value = "run", description = "Triggers build of job with given name")
    public ReactorResponse buildJob(ReactorRequest<JenkinsJobRequestData> jenkinsJobRequest)
            throws IOException {
        String jobName = jenkinsJobRequest.getRequestData().getJobName();
        jenkinsServerFacade.buildJob(jobName);
        return new JobBuildQueuedResponse(jobName);
    }

    private void initJenkinsReactor(JenkinsReactorProperties reactorProperties) {
        try {
            jenkinsServerFacade = forServerDetails(reactorProperties.getUrl(), reactorProperties.getUsername(),
                reactorProperties.getPassword());
            jobActivityEventListener = new JobActivityEventListener(reactorProperties);
        } catch (URISyntaxException e) {
            throw new ReactorInitializationException(e);
        }
    }

    @Override
    public void initNestingReactor(ReactorProperties reactorProperties) {
        initJenkinsReactor(new JenkinsReactorProperties(reactorProperties));
    }

    @Override
    public void initReactorEventConsumers(ReactorEventConsumerFactory factory) {
        jobActivityEventListener.startConsumingEvents(factory.createEventConsumer(JobActivityEvent.class, TO_RESPONSE));
    }
}

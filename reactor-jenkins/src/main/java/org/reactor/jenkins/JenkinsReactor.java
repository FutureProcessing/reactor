package org.reactor.jenkins;

import static org.reactor.jenkins.JenkinsServerFacade.forServerDetails;
import static org.reactor.jenkins.event.JobActivityEvent.TO_RESPONSE;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import java.io.IOException;
import java.net.URISyntaxException;
import org.reactor.InitializingReactor;
import org.reactor.ReactorInitializationException;
import org.reactor.ReactorProperties;
import org.reactor.annotation.AbstractAnnotatedNestingReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.annotation.ReactorRequestParameter;
import org.reactor.command.PrintNestedReactorsReactor;
import org.reactor.event.EventProducingReactor;
import org.reactor.event.ReactorEventConsumerFactory;
import org.reactor.jenkins.event.JobActivityEvent;
import org.reactor.jenkins.event.JobActivityEventListener;
import org.reactor.jenkins.response.JobBuildQueuedResponse;
import org.reactor.jenkins.response.JobDetailsResponse;
import org.reactor.jenkins.response.JobsListResponse;
import org.reactor.response.ReactorResponse;

@ReactOn(value = "!jenkins", description = "Jenkins reactor")
public class JenkinsReactor extends AbstractAnnotatedNestingReactor implements InitializingReactor, EventProducingReactor {

    private JenkinsServerFacade jenkinsServerFacade;
    private JobActivityEventListener jobActivityEventListener;

    @ReactOn(value = "jobs", description = "Prints list of defined jobs on given Jenkins instance")
    public ReactorResponse listJobs() throws IOException {
        JobsListResponse listResponse = new JobsListResponse();
        Iterable<Job> jobs = jenkinsServerFacade.getJobs();
        for (Job job : jobs) {
            listResponse.addJob(job);
        }
        return listResponse;
    }

    @ReactOn(value = "job", description = "Prints basic information about given job")
    public ReactorResponse getJobDetails(@ReactorRequestParameter(required = true, name = "jobName", shortName = "j") String jobName)
            throws IOException {
        JobWithDetails job = jenkinsServerFacade.getJob(jobName);
        return new JobDetailsResponse(job);
    }

    @ReactOn(value = "run", description = "Triggers build of job with given name")
    public ReactorResponse buildJob(@ReactorRequestParameter(required = true, name = "jobName", shortName = "j") String jobName)
            throws IOException {
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
    public void initReactor(ReactorProperties reactorProperties) {
        initJenkinsReactor(new JenkinsReactorProperties(reactorProperties));

        registerNestedReactor(new PrintNestedReactorsReactor(this));
    }

    @Override
    public void initReactorEventConsumers(ReactorEventConsumerFactory factory) {
        jobActivityEventListener.startConsumingEvents(factory.createEventConsumer(JobActivityEvent.class, TO_RESPONSE));
    }
}

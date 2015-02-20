package org.reactor.jenkins;

import com.offbytwo.jenkins.model.MockedJobWithDetails;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactor.AbstractUnitTest;
import org.reactor.jenkins.request.JenkinsJobDetailsRequestData;
import org.reactor.jenkins.request.JenkinsJobRequestData;
import org.reactor.jenkins.response.JobBuildQueuedResponse;
import org.reactor.jenkins.response.JobDetailsResponse;
import org.reactor.jenkins.response.JobsListResponse;
import org.reactor.request.ReactorRequest;

import java.io.IOException;
import java.lang.reflect.Field;

import static com.google.common.collect.Lists.newArrayList;
import static junit.framework.Assert.fail;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class JenkinsReactorTest extends AbstractUnitTest {

    private static final String JOB_DISPLAY_NAME = "some job";
    private static final String JOB_NAME = "mockedJob";
    @Mock
    JenkinsServerFacade mockedJenkinsServerFacade;

    @InjectMocks
    JenkinsReactor jenkinsReactor = new JenkinsReactor();

    @Test
    public void shouldGetJobDetails() throws IOException {
        // given
        given(mockedJenkinsServerFacade.getJob(JOB_NAME)).willReturn(new MockedJobWithDetails(JOB_DISPLAY_NAME));

        // when
        JobDetailsResponse result = (JobDetailsResponse) jenkinsReactor.getJobDetails(reactorRequest(new JenkinsJobDetailsRequestData(JOB_NAME)));

        // then
        assertThat(result).isNotNull();
        assertThat(result.toConsoleOutput()).contains(JOB_DISPLAY_NAME);
    }

    @Test
    public void shouldListJobs() throws IOException {
        // given
        MockedJobWithDetails mockedJobWithDetails = new MockedJobWithDetails();
        setNameField(mockedJobWithDetails, JOB_DISPLAY_NAME);
        given(mockedJenkinsServerFacade.getJobs()).willReturn(newArrayList(mockedJobWithDetails));

        // when
        JobsListResponse result = (JobsListResponse) jenkinsReactor.listJobs(reactorRequest());

        // then
        assertThat(result).isNotNull();
        assertThat(result.getJobsNames()).contains(JOB_DISPLAY_NAME);
    }

    private void setNameField(MockedJobWithDetails mockedJobWithDetails, String name) {
        try {
            Field field = mockedJobWithDetails.getClass().getSuperclass().getSuperclass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(mockedJobWithDetails, name);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void shouldBuildJob() throws IOException {
        // given
        given(mockedJenkinsServerFacade.getJob(JOB_NAME)).willReturn(new MockedJobWithDetails(JOB_DISPLAY_NAME));
        ReactorRequest<JenkinsJobRequestData> request = reactorRequest(new JenkinsJobRequestData(JOB_NAME));

        // when
        JobBuildQueuedResponse result = (JobBuildQueuedResponse) jenkinsReactor.buildJob(request);

        // then
        verify(mockedJenkinsServerFacade).buildJob(JOB_NAME);
        assertThat(result.getJobName()).isEqualTo(JOB_NAME);
        assertThat(result.toConsoleOutput()).contains(JOB_NAME);
    }

    private <T> ReactorRequest<T> reactorRequest() {
        return reactorRequest(null);
    }

    private <T> ReactorRequest<T> reactorRequest(T request) {
        return new ReactorRequest<>(null, null, request);
    }
}
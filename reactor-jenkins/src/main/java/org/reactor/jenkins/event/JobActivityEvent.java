package org.reactor.jenkins.event;

import com.google.common.base.Function;

import org.json.JSONObject;
import org.reactor.event.ReactorEvent;
import org.reactor.jenkins.response.JobActivityEventResponse;
import org.reactor.response.ReactorResponse;

public class JobActivityEvent implements ReactorEvent {

    public static final Function<JobActivityEvent, ReactorResponse> TO_RESPONSE = JobActivityEventResponse::new;

    public static final Function<JSONObject, JobActivityEvent> FROM_JSON = new Function<JSONObject, JobActivityEvent>() {

        private static final String KEY_BUILD_DATA = "build";
        private static final String KEY_NAME = "name";
        private static final String KEY_FULL_URL = "full_url";
        private static final String KEY_PHASE = "phase";
        private static final String KEY_STATUS = "status";
        private static final String DEFAULT_VALUE = "";

        @Override
        public JobActivityEvent apply(JSONObject input) {
            JSONObject buildData = input.getJSONObject(KEY_BUILD_DATA);
            return new JobActivityEvent(input.optString(KEY_NAME, DEFAULT_VALUE), buildData.optString(KEY_FULL_URL,
                DEFAULT_VALUE), buildData.optString(KEY_PHASE, DEFAULT_VALUE), buildData.optString(KEY_STATUS,
                DEFAULT_VALUE));
        }
    };

    private final String jobName;
    private final String buildUrl;
    private final String phase;
    private final String status;

    public JobActivityEvent(String jobName, String buildUrl, String phase, String status) {
        this.jobName = jobName;
        this.buildUrl = buildUrl;
        this.phase = phase;
        this.status = status;
    }

    public String getJobName() {
        return jobName;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public String getPhase() {
        return phase;
    }

    public String getStatus() {
        return status;
    }
}

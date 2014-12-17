package org.reactor.system.cpu;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

import org.reactor.AbstractAnnotatedReactor;
import org.reactor.annotation.ReactOn;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;

@ReactOn("cpu")
public class CpuUsageReactor extends AbstractAnnotatedReactor<Void> {

    private final static OperatingSystemMXBean OS_BEAN = ManagementFactory
        .getPlatformMXBean(OperatingSystemMXBean.class);

    public CpuUsageReactor() {
        super(Void.class);
    }

    @Override
    protected ReactorResponse doReact(ReactorRequest<Void> reactorRequest) {
        return responseRenderer -> responseRenderer.renderDoubleLine(OS_BEAN.getSystemLoadAverage());
    }
}

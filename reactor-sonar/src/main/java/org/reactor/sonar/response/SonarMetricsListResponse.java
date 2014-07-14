package org.reactor.sonar.response;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static org.reactor.utils.StringUtils.matchesWildcard;
import java.util.List;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import org.sonar.wsclient.services.Metric;

public class SonarMetricsListResponse extends ListReactorResponse<Metric> {

    public static final String FILTER_DEFAULT = "*";
    private final List<Metric> metricList;
    private final String filter;

    public SonarMetricsListResponse(List<Metric> metricList, String filter) {
        this.metricList = metricList;
        this.filter = firstNonNull(filter, FILTER_DEFAULT);
    }

    @Override
    protected Iterable<Metric> getElements() {
        return newArrayList(metricList.stream().filter(metric -> matchesWildcard(metric.getKey(), filter)).iterator());
    }

    @Override
    protected ListElementFormatter<Metric> getElementFormatter() {
        return (elementIndex, metric) -> format("%s. %s - %s", elementIndex, metric.getKey(), metric.getDescription());
    }
}

package org.reactor.sonar.response;

import static com.google.common.base.Objects.firstNonNull;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.reactor.utils.StringUtils.matchesWildcard;
import java.util.List;
import org.reactor.response.list.ListElementFormatter;
import org.reactor.response.list.ListReactorResponse;
import org.sonar.wsclient.services.Metric;

public class SonarMetricsListResponse extends ListReactorResponse<Metric> {

    public transient static final String FILTER_DEFAULT = "*";
    private transient final String filter;
    private final List<Metric> metricList;

    public SonarMetricsListResponse(List<Metric> metricList, String filter) {
        this.metricList = metricList;
        this.filter = firstNonNull(filter, FILTER_DEFAULT);
    }

    @Override
    public String toConsoleOutput() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < metricList.size(); i++) {
            builder.append(getElementFormatter().formatListElement(i + 1, metricList.get(i))).append("\n");
        }
        return builder.toString();
    }

    @Override
    protected Iterable<Metric> getElements() {
        return metricList.stream().filter(metric -> matchesWildcard(metric.getKey(), filter)).collect(toList());
    }

    @Override
    protected ListElementFormatter<Metric> getElementFormatter() {
        return (elementIndex, metric) -> format("%s. %s - %s", elementIndex, metric.getKey(), metric.getDescription());
    }
}

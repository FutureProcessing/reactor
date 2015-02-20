package org.reactor.jira;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.lang.String.format;

public class JiraQueryBuilder {

    public static final String QUERY_STATUS = "status";
    private StringBuffer queryBuffer = new StringBuffer();

    public static JiraQueryBuilder forProject(String projectName) {
        return new JiraQueryBuilder(projectName);
    }

    private JiraQueryBuilder(String project) {
        queryBuffer = new StringBuffer(format("project = %s", project));
    }

    public ConditionFor and(String property) {
        queryBuffer.append(" AND ");
        return new ConditionFor(property);
    }

    public String build() {
        return queryBuffer.toString();
    }

    public final class ConditionFor {

        private final String property;

        public ConditionFor(String property) {
            this.property = property;
        }

        public JiraQueryBuilder isEqualTo(String value) {
            if (!isNullOrEmpty(property) && !isNullOrEmpty(value)) {
                queryBuffer.append(format(" %s = %s ", property, value));
            }
            return JiraQueryBuilder.this;
        }
    }
}

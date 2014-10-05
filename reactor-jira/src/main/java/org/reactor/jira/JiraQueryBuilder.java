package org.reactor.jira;

import static java.lang.String.format;

public class JiraQueryBuilder {

    private StringBuffer queryBuffer = new StringBuffer();

    public static JiraQueryBuilder forProject(String projectName) {
        return new JiraQueryBuilder(projectName);
    }

    private JiraQueryBuilder(String project) {
        queryBuffer = new StringBuffer(format("project = %s", project));
    }

    public JiraQueryBuilder andFor(String key, String value) {
        queryBuffer.append(format(" AND %s = %s", key, value));
        return this;
    }

    public String build() {
        return queryBuffer.toString();
    }
}

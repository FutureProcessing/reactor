package org.reactor.transport.http.config;

/**
 * @author grabslu
 */
public class ConfigContentBuilder {

    private String contextPath;

    private ConfigContentBuilder() {
    }

    public static ConfigContentBuilder configContent() {
        return new ConfigContentBuilder();
    }

    public ConfigContentBuilder contextPath(String contextPath) {
        this.contextPath = contextPath;
        return this;
    }

    public ConfigContent build() {
        ConfigContent configContent = new ConfigContent();
        configContent.CONTEXT_PATH = contextPath;
        return configContent;
    }
}

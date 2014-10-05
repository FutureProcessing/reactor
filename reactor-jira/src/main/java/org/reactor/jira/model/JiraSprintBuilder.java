package org.reactor.jira.model;

import net.rcarz.jiraclient.greenhopper.Sprint;

import com.google.common.base.Function;

public class JiraSprintBuilder {

    public static final Function<Sprint, JiraSprint> FROM_GREENHOPPER_SPRINT = sprint -> forId(sprint.getId())
            .name(sprint.getName())
            .active(!sprint.isClosed()).build();

    private int id;
    private String name;
    private boolean active;

    private JiraSprintBuilder(int id) {
        this.id = id;
    }

    public static JiraSprintBuilder forId(int id) {
        return new JiraSprintBuilder(id);
    }

    public JiraSprintBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JiraSprintBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public JiraSprint build() {
        return new JiraSprint(id, name, active);
    }
}

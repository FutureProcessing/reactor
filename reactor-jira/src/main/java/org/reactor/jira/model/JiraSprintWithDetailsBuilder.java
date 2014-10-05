package org.reactor.jira.model;

import java.util.Date;

import net.rcarz.jiraclient.greenhopper.Sprint;

import com.google.common.base.Function;

public class JiraSprintWithDetailsBuilder {


    public static final Function<Sprint, JiraSprintWithDetails> FROM_GREENHOPPER_SPRINT_DETAILS = sprint -> {
        JiraSprintWithDetailsBuilder builder = forId(sprint.getId())
                .name(sprint.getName())
                .active(!sprint.isClosed());
        if (sprint.getStartDate() != null) {
            builder.startDate(sprint.getStartDate().toDate());
        }
        if (sprint.getCompleteDate() != null) {
            builder.completeDate(sprint.getCompleteDate().toDate());
        }
        return builder.build();
    };
    private int id;

    private String name;

    private boolean active;
    private Date startDate;
    private Date completeDate;
    private JiraSprintWithDetailsBuilder(int id) {
        this.id = id;
    }

    public static JiraSprintWithDetailsBuilder forId(int id) {
        return new JiraSprintWithDetailsBuilder(id);
    }

    public JiraSprintWithDetailsBuilder name(String name) {
        this.name = name;
        return this;
    }

    public JiraSprintWithDetailsBuilder active(boolean active) {
        this.active = active;
        return this;
    }

    public JiraSprintWithDetailsBuilder startDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    private JiraSprintWithDetailsBuilder completeDate(Date completeDate) {
        this.completeDate = completeDate;
        return this;
    }

    public JiraSprintWithDetails build() {
        return new JiraSprintWithDetails(id, name, active, startDate, completeDate);
    }
}

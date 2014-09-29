package org.reactor.jira.model;

import java.util.Date;

public class JiraSprint {

    private String name;
    private Date startDate;
    private Date completeDate;
    private boolean active;

    public JiraSprint(String name, Date startDate, Date completeDate, boolean active) {
        this.name = name;
        this.startDate = startDate;
        this.completeDate = completeDate;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

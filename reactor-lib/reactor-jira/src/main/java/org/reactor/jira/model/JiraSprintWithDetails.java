package org.reactor.jira.model;

import java.util.Date;

public class JiraSprintWithDetails extends JiraSprint {

    private Date startDate;
    private Date completeDate;

    public JiraSprintWithDetails(int id, String name, boolean active, Date startDate, Date completeDate) {
        super(id, name, active);
        this.startDate = startDate;
        this.completeDate = completeDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }
}

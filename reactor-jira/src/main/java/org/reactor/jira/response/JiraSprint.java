package org.reactor.jira.response;

import java.util.Date;

public class JiraSprint {

    private String name;
    private Date startDate;
    private Date completeDate;

    public JiraSprint(String name, Date startDate, Date completeDate) {
        this.name = name;
        this.startDate = startDate;
        this.completeDate = completeDate;
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

}

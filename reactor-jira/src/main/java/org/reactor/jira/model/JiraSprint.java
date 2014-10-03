package org.reactor.jira.model;

public class JiraSprint {

    private int id;
    private String name;
    private boolean active;

    public JiraSprint(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public int getId() {
        return id;
    }
}

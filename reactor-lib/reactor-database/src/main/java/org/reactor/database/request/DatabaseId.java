package org.reactor.database.request;

import org.reactor.annotation.ReactorRequestParameter;

public class DatabaseId {

    @ReactorRequestParameter(required = true, description = "Database ID")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

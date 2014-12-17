package org.reactor.database.callback;

public class DatabaseSize {

    private final long databaseSize;

    public DatabaseSize(long databaseSize) {
        this.databaseSize = databaseSize;
    }

    public long getDatabaseSize() {
        return databaseSize;
    }
}

package org.reactor.database;

import static java.lang.String.format;

public class DatabaseIdAlreadyPresent extends RuntimeException {

    private static final String TEMPLATE_MESSAGE = "Duplicate db id: %s";

    public DatabaseIdAlreadyPresent(String dbId) {
        super(format(TEMPLATE_MESSAGE, dbId));
    }
}

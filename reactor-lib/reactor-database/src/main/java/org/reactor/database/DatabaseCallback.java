package org.reactor.database;

import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseCallback<T> {

    T performDatabaseOperation(Statement statement) throws SQLException;
}

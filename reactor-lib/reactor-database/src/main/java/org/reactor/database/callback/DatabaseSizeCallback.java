package org.reactor.database.callback;

import org.reactor.database.DatabaseCallback;
import org.reactor.database.DatabaseType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSizeCallback implements DatabaseCallback<DatabaseSize> {

    private static final int SIZE_DEFAULT = 0;
    private final DatabaseType databaseType;

    public DatabaseSizeCallback(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    @Override
    public DatabaseSize performDatabaseOperation(Statement statement) throws SQLException {
        statement.execute(databaseType.getDatabaseSizeQuery());
        ResultSet resultSet = statement.getResultSet();
        while (resultSet.next()) {
            return new DatabaseSize(resultSet.getLong(1));
        }
        return new DatabaseSize(SIZE_DEFAULT);
    }
}

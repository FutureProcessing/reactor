package org.reactor.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.reactor.database.callback.DatabaseSize;
import org.reactor.database.callback.DatabaseSizeCallback;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private final DatabaseType databaseType;
    private BasicDataSource dataSource;

    public DatabaseConnection(DatabaseType databaseType, String url, String username, String password) {
        this.databaseType = databaseType;
        initConnection(url, username, password);
    }

    private void initConnection(String url, String username, String password) {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(databaseType.getDriverClass());
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
    }

    public long getDatabaseSize() throws SQLException {
        DatabaseSize dbSize = doInDatabase(new DatabaseSizeCallback(databaseType));
        return dbSize.getDatabaseSize();
    }

    private <T> T doInDatabase(DatabaseCallback<T> callback) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            return callback.performDatabaseOperation(statement);
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
}

package org.reactor.database;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Optional;

import org.reactor.AbstractNestingReactor;
import org.reactor.ReactorProperties;
import org.reactor.annotation.ReactOn;
import org.reactor.database.request.DatabaseId;
import org.reactor.database.response.DatabaseDoesntExistResponse;
import org.reactor.database.response.DatabaseListResponse;
import org.reactor.database.response.DatabaseSizeResponse;
import org.reactor.request.ReactorRequest;
import org.reactor.response.ReactorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ReactOn(value = "db", description = "Provides relational database access")
public class DatabaseAccessReactor extends AbstractNestingReactor {

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseAccessReactor.class);
    private DatabaseConnections databaseConnections;

    @Override
    protected void initNestingReactor(ReactorProperties properties) {
        initDatabaseAccessReactor(new DatabaseAccessProperties(properties));
    }

    private void initDatabaseAccessReactor(DatabaseAccessProperties databaseAccessProperties) {
        InputStream connectionsFile = databaseAccessProperties.getConnectionsFileStream();
        if (connectionsFile == null) {
            LOGGER.error("Unable to locate database connections descriptor file.");
            return;
        }
        initDatabaseConnections(connectionsFile);
    }

    private void initDatabaseConnections(InputStream connectionsFileStream) {
        databaseConnections = new DatabaseConnections();
        try {
            databaseConnections.loadFromStream(connectionsFileStream);
        } catch (Exception e) {
            LOGGER.error("An error occurred while initializing database connections", e);
        }
    }

    @ReactOn(value = "size", description = "Prints database size (MB)")
    public ReactorResponse getDatabaseSize(ReactorRequest<DatabaseId> reactorRequest) throws SQLException {
        String databaseId = reactorRequest.getRequestData().getId();
        Optional<DatabaseConnection> connection = databaseConnections.getDatabaseConnection(databaseId);
        if (!connection.isPresent()) {
            return new DatabaseDoesntExistResponse(databaseId);
        }
        return new DatabaseSizeResponse(connection.get().getDatabaseSize());
    }

    @ReactOn(value = "list", description = "Displays a list of managed db connections")
    public ReactorResponse listDatabaseConnections(ReactorRequest<Void> reactorRequest) {
        Iterable<String> dbNames = databaseConnections.getDatabaseIds();
        return new DatabaseListResponse(dbNames);
    }
}

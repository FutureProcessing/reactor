package org.reactor.database;

import static com.google.common.collect.Maps.newHashMap;
import static java.util.Optional.empty;
import static org.reactor.database.DatabaseType.valueOf;

import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DatabaseConnections {

    private static final String TAG_DATABASE = "database";
    private static final String ATTRIBUTE_TYPE = "type";
    private static final String ATTRIBUTE_ID = "id";
    private static final String TAG_CONNECTION_URL = "connection-url";
    private static final String TAG_PASSWORD = "password";
    private static final String TAG_USERNAME = "username";

    private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseConnections.class);

    private Map<String, DatabaseConnection> dbConnections = newHashMap();

    public void addDatabase(String dbId, DatabaseType databaseType, String connectionUrl, String username, String password) {
        validateDbId(dbId);
        LOGGER.debug("Initializing db with id = {}", dbId);
        dbConnections.put(dbId, new DatabaseConnection(databaseType, connectionUrl, username, password));
    }

    public void loadFromStream(InputStream connectionsDescriptor) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document connectionsXML = dBuilder.parse(connectionsDescriptor);

        NodeList databases = connectionsXML.getElementsByTagName(TAG_DATABASE);
        for (int index = 0; index < databases.getLength(); index++) {
            Node databaseNode = databases.item(index);
            addDatabaseFromXMLNode((Element) databaseNode);
        }
    }

    private void addDatabaseFromXMLNode(Element databaseNode) {
        String dbId = databaseNode.getAttribute(ATTRIBUTE_ID);
        String type = databaseNode.getAttribute(ATTRIBUTE_TYPE);
        String connectionUrl = getFirstTagContent(databaseNode, TAG_CONNECTION_URL);
        String username = getFirstTagContent(databaseNode, TAG_USERNAME);
        String password = getFirstTagContent(databaseNode, TAG_PASSWORD);
        addDatabase(dbId, valueOf(type.toUpperCase()), connectionUrl, username, password);
    }

    private void validateDbId(String dbId) {
        if (dbConnections.containsKey(dbId)) {
            throw new DatabaseIdAlreadyPresent(dbId);
        }
    }

    private String getFirstTagContent(Element databaseNode, String tagName) {
        return databaseNode.getElementsByTagName(tagName).item(0).getTextContent();
    }

    public Optional<DatabaseConnection> getDatabaseConnection(String databaseId) {
        if (!dbConnections.containsKey(databaseId)) {
            return empty();
        }
        return Optional.of(dbConnections.get(databaseId));
    }

    public Iterable<String> getDatabaseIds() {
        return dbConnections.keySet();
    }
}

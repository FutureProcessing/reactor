Database reactor
================
Reactor implementation is intended to be used with any relational database with respect to basic database operations like checking db size, running basic queries, etc.
Right now there is only one reactor trigger implemented, you can call it like this:

```
db size --id database_id
```

Reactor configuration
---------------------
There is only one configuration entry in reactor.properties file for that reactor pointing to Database Connections descriptor location. By default it's a database-connections.xml file located under etc folder of reactor-bootstrap distribution package but you can override it's value by changing one property:

```
reactor.database.connections = database-connections.xml
```

Change database-connections.xml to anything applicable and that's it. To define new connections just follow up comments located in provided example of database-connections.xml file.

Requirements
------------

Before building reactor-database package you need to have following dependencies in your maven repository first:

**com.microsoft.sqlserver.sqljdbc4-4.0**
If doesn't exist, go to http://msdn.microsoft.com/pl-pl/sqlserver/aa937724.aspx, download jar file and install it in maven repository with following command:

```
mvn install:install-file -Dfile=sqljdbc4.jar -Dpackaging=jar -DgroupId=com.microsoft.sqlserver -DartifactId=sqljdbc4 -Dversion=4.0
```

**com.oracle.ojdbc14-10.2.0.3.0**
If doesn't exist, go to http://www.oracle.com/technetwork/database/features/jdbc/index-091264.html, download jar file and install it in maven repository with following command:

```
install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.3.0 -Dpackaging=jar -Dfile=ojdbc.jar -DgeneratePom=true
```
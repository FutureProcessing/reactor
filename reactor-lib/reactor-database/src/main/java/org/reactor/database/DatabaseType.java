package org.reactor.database;

public enum DatabaseType {

    ORACLE {

        @Override
        public String getDatabaseSizeQuery() {
            return "select sum(bytes/1024/1024) from v$datafile";
        }

        @Override
        public String getDriverClass() {
            return "oracle.jdbc.driver.OracleDriver";
        }
    },

    MSSQL {

        @Override
        public String getDatabaseSizeQuery() {
            return "SELECT sum((size*8)/1024) as SizeMB FROM sys.master_files WHERE DB_NAME(database_id) = DB_NAME()";
        }

        @Override
        public String getDriverClass() {
            return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        }
    };

    public abstract String getDatabaseSizeQuery();

    public abstract String getDriverClass();
}

package Connectivity;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnect {
    private static final String CONFIG_FILE = "dbconfig.properties";
    private Connection conn;
    private Statement stmnt;

    public DBConnect() throws SQLException, IOException {
        this(null); // Call the main constructor with null to use default config
    }

    public DBConnect(String customDbUrl) throws SQLException, IOException {
        // Load properties from file
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(CONFIG_FILE)) {
            props.load(in);
        }

        String dbUrl = customDbUrl != null ? customDbUrl : props.getProperty("db.url");
        String dbUser = props.getProperty("db.user");
        String dbPass = props.getProperty("db.password");
        String dbName = props.getProperty("db.name", "bank"); // Default to "bank" if not specified

        connect(dbUrl, dbUser, dbPass, dbName);
    }

    public DBConnect(String user, String pass, String database) throws SQLException, IOException {
        this(user, pass, database, "jdbc:mysql://localhost:3306");
    }

    public DBConnect(String user, String pass, String database, String dbUrl) throws SQLException, IOException {
        connect(dbUrl, user, pass, database);
    }

    private void connect(String dbUrl, String user, String pass, String database) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found", e);
        }

        String connectionUrl = dbUrl + (database != null ? "/" + database : "");
        conn = DriverManager.getConnection(connectionUrl, user, pass);
        stmnt = conn.createStatement();
    }

    public Connection getConnection() {
        return conn;
    }

    public Statement getStatement() {
        return stmnt;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}

package Connectivity;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnect
{
    private static final String DEFAULT_USER = "bank_admin";
    private static final String DEFAULT_PASS = "bank123";
    private static final String DB_URL = "jdbc:mysql://localhost:3306";
    private static final String DEFAULT_DB = "bank";

    private Connection conn;
    private Statement stmnt;

    public DBConnect () throws SQLException {
        this(DEFAULT_USER, DEFAULT_PASS, DEFAULT_DB);
    }

    public DBConnect (int x) throws SQLException
    {
        this(DEFAULT_USER, DEFAULT_PASS, DEFAULT_DB);
    }

    public DBConnect(String user, String pass, String database) throws SQLException
    {
        connect(user, pass, database);
    }

    public DBConnect(String user, String pass) throws SQLException
    {
        connect(user, pass, DEFAULT_DB);
    }

    public void connect(String user, String pass, String database) throws SQLException
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(DB_URL + "/" + database, user, pass);
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
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package Connectivity;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DBConnect
{
    private static String DEFAULT_USER = "";
    private static String DEFAULT_PASS = "";
    private static String DB_URL = "";
    private static String DEFAULT_DB = "";

    private Connection conn;

    public DB_connect() {
        try {
            Class.forName("org.mysql.jdbc.Driver");
            System.out.println("Connected");
        }
        catch (Exception e){
            System.out.println("Error");
        }
        connection = null;
        pstat = null;
    }

    public void getDefaultCred(){
        try (BufferedReader br = new BufferedReader(new FileReader("DefaultCredentials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2); // Split into key and value
                if (parts.length < 2) continue; // Skip malformed lines

                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "DefaultUser":
                        this.DEFAULT_USER = value;
                        break;
                    case "Defaultpass":
                        this.DEFAULT_PASS= value;
                        break;
                    case "DB_URL":
                        this.DB_URL = value;
                        break;
                    case "Default_DB":
                        this.DEFAULT_DB = value;
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(PreparedStatement pstat){
        try{
            if(!pstat.execute()){
                return null;
            }
            results = pstat.executeQuery();
            return results;
        }
        catch (Exceptions e){
          System.out.println(e);
            return null;
        }
    }

    public ResultSet getResults(){
        return results;
    } 

    public void executeUpdate(PreparedStatement pstat){
        try{
            int rowsAffected = pstat.executeUpdate();

            System.out.println("Rows: " + rowsAffected);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}

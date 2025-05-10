package Connectivity;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBConnect {
    Connection conn;
    String USER = "";
    String PASS = "";
    String DB_URL = "";

    public DBConnect() {
        try (BufferedReader br = new BufferedReader(new FileReader("DefaultCredentials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2); // Split into key and value
                if (parts.length < 2) continue; // Skip malformed lines

                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "DefaultUser":
                        USER = value;
                        break;
                    case "Defaultpass":
                        PASS= value;
                        break;
                    case "DB_URL":
                        DB_URL = value;
                        break;
                    case "Default_DB":
                        //DB_URL += "/" + value;
                        break;
                }
                System.out.println(USER + DB_URL + PASS);
            }
        }
        catch (FileNotFoundException e) {
            try {
                FileWriter fWriter = new FileWriter("DefaultCredentials.txt");
                fWriter.write("DefaultUser: exampleuser \n Defaultpass: examplepass \n DB_URL: jdbc:mariadb://localhost:3306/bank \n Default_DB: bank");
                fWriter.close();
                JOptionPane.showMessageDialog(null, "Check your DefaultCredentials.txt file (somewhere in the root), and input your database credentials", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            catch (IOException error) {
            }
        }
        catch (IOException e) {
            System.out.println("wtf");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database successfully!");
            conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Database connection failed. Check your DefaultCredentials.txt file (wherever it is).\nError MSG: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public Connection getConnection() {
        return conn;
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

    public ResultSet executeQuery(PreparedStatement pstat){
        return executeQuery(pstat);
    }
    public int executeUpdate(PreparedStatement pstat){
        return executeUpdate(pstat);
    }
}

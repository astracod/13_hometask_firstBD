package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {


    private String connectionURL;
    private String userName;
    private String password;


    public DBConnect(String connectionURL, String userName, String password) {
        this.connectionURL = connectionURL;
        this.userName = userName;
        this.password = password;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (SQLException throwables) {
            throw new RuntimeException(" Database connection failed ", throwables);
        }
    }

}

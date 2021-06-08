package ru.sberstart.project.bl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DB_URL = "jdbc:h2:~/best";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection OK");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }
}

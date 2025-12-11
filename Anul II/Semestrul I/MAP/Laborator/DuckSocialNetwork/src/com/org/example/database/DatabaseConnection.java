package com.org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Using an in-memory H2 database that can be configured for file-based if needed
    private static final String URL = "jdbc:h2:./database/duck_social_network;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            // Load the H2 driver
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("H2 Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to get an in-memory connection for testing
    public static Connection getInMemoryConnection() throws SQLException {
        try {
            // Load the H2 driver
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("H2 Driver not found", e);
        }
        return DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", USER, PASSWORD);
    }
}

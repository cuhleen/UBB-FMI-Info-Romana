package com.org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/duckdb";
    private static final String USER = "duckusr";
    private static final String PASSWORD = "parola123";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("PostgreSQL connection failed: " + e.getMessage());
            throw new SQLException("PostgreSQL connection failed: " + e.getMessage());
        }
    }
}
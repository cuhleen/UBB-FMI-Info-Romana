package com.org.example.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    public static void initializeAndPopulateDatabase() {
        try {
            // First, get an in-memory connection
            Connection conn = DatabaseConnection.getInMemoryConnection();
            
            // Initialize the database tables
            DatabaseInitializer.initializeInMemoryDatabase(conn);
            
            // Populate the database with sample data
            DatabasePopulation.populateWithSampleData(conn);
            
            // Close the connection
            conn.close();
            
            System.out.println("Database initialized and populated with sample data successfully!");
        } catch (SQLException e) {
            System.err.println("Error initializing and populating database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnectionForApp() throws SQLException {
        // Return an in-memory connection that's already initialized
        Connection conn = DatabaseConnection.getInMemoryConnection();
        
        // Ensure tables exist
        DatabaseInitializer.initializeInMemoryDatabase(conn);
        
        return conn;
    }

    public static void initializeFileBasedDatabase() {
        try {
            // Initialize a file-based database
            Connection conn = DatabaseConnection.getConnection();
            
            // Initialize the database tables
            DatabaseInitializer.initializeDatabase(conn);
            
            // Populate with sample data if it's a fresh database
            DatabasePopulation.populateWithSampleData(conn);
            
            // Close the connection
            conn.close();
            
            System.out.println("File-based database initialized and populated with sample data successfully!");
        } catch (SQLException e) {
            System.err.println("Error initializing file-based database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Initializing and populating database with sample data...");
        
        // Initialize and populate in-memory database for testing
        initializeAndPopulateDatabase();
        
        System.out.println("\nInitializing file-based database...");
        
        // Initialize and populate file-based database
        initializeFileBasedDatabase();
        
        System.out.println("\nDatabase setup completed!");
    }
}
package com.org.example.tests;

import java.sql.SQLException;

/**
 * Test runner to execute all CRUD and functionality tests
 */
public class TestRunner {
    
    public static void main(String[] args) {
        try {
            System.out.println("Starting comprehensive test suite...\n");
            
            // Run all CRUD tests
            DuckCRUDTest.runAllTests();
            PersonCRUDTest.runAllTests();
            CardCRUDTest.runAllTests();
            FriendshipCRUDTest.runAllTests();
            RaceEventCRUDTest.runAllTests();
            
            // Run specific functionality tests
            DuckDeletionWithFriendshipCleanupTest.runTest();
            DuckUpdateWithCardReflectionTest.runTest();
            PersonUpdateWithPersistenceTest.runTest();
            DataPersistenceAfterCRUDTest.runTest();
            
            System.out.println("All tests completed successfully!");
            
        } catch (SQLException e) {
            System.err.println("SQL Error during testing: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
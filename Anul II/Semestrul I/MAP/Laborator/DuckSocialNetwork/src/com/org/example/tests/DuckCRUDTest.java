package com.org.example.tests;

import com.org.example.enums.TipRata;
import com.org.example.repo.RepoDuckDB;
import com.org.example.service.DuckService;
import com.org.example.users.Duck;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive CRUD tests for Duck entities
 */
public class DuckCRUDTest {

    public static void runAllTests() throws SQLException {
        System.out.println("\n=== Running Duck CRUD Tests ===");

        testCreateDuck();
        testReadDuck();
        testUpdateDuck();
        testDeleteDuck();
        testReadAllDucks();

        System.out.println("=== Duck CRUD Tests Completed ===\n");
    }

    public static void testCreateDuck() throws SQLException {
        System.out.println("Running testCreateDuck...");

        TestHelper.TestServices services = TestHelper.createTestServices();
        DuckService duckService = services.duckService;

        // Create a duck
        Duck duck = new Duck(1, "testDuck", "test@duck.com", "password123", TipRata.FLYING, 10.5, 8.2);
        duckService.addDuck(duck);

        // Verify duck was saved by retrieving it
        Optional<Duck> retrievedDuck = duckService.getDuck(1);
        assertBoolean("Duck should be present in the database", retrievedDuck.isPresent());
        assertStringEquals("Username should match", "testDuck", retrievedDuck.get().getUsername());
        assertStringEquals("Email should match", "test@duck.com", retrievedDuck.get().getEmail());
        assertObjectEquals("Type should match", TipRata.FLYING, retrievedDuck.get().getTip());
        assertDoubleEquals("Speed should match", 10.5, retrievedDuck.get().getViteza());
        assertDoubleEquals("Endurance should match", 8.2, retrievedDuck.get().getRezistenta());

        System.out.println("✓ testCreateDuck passed");
    }

    private static void assertBoolean(String message, boolean actual) {
        if (!actual) {
            throw new AssertionError(message + " - Expected true, but got false");
        }
    }

    private static void assertStringEquals(String message, String expected, String actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: " + expected + ", but got: " + actual);
        }
    }

    private static void assertObjectEquals(String message, Object expected, Object actual) {
        if (expected == null ? actual != null : !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: " + expected + ", but got: " + actual);
        }
    }

    private static void assertDoubleEquals(String message, double expected, double actual) {
        if (Math.abs(expected - actual) > 0.001) {
            throw new AssertionError(message + " - Expected: " + expected + ", but got: " + actual);
        }
    }

    private static void assertIntEquals(String message, int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", but got: " + actual);
        }
    }

    private static void assertFalse(String message, boolean actual) {
        if (actual) {
            throw new AssertionError(message + " - Expected false, but got true");
        }
    }

    public static void testReadDuck() throws SQLException {
        System.out.println("Running testReadDuck...");

        TestHelper.TestServices services = TestHelper.createTestServices();
        DuckService duckService = services.duckService;

        // Create and save a duck
        Duck duck = new Duck(1, "readDuck", "read@duck.com", "password123", TipRata.SWIMMING, 5.0, 6.0);
        duckService.addDuck(duck);

        // Retrieve the duck by ID
        Optional<Duck> retrievedDuck = duckService.getDuck(1);
        assertBoolean("Duck should be found", retrievedDuck.isPresent());
        assertStringEquals("Username should match", "readDuck", retrievedDuck.get().getUsername());
        assertObjectEquals("Type should match", TipRata.SWIMMING, retrievedDuck.get().getTip());

        // Test non-existent duck
        Optional<Duck> nonExistentDuck = duckService.getDuck(999);
        assertFalse("Non-existent duck should not be found", nonExistentDuck.isPresent());

        System.out.println("✓ testReadDuck passed");
    }

    public static void testUpdateDuck() throws SQLException {
        System.out.println("Running testUpdateDuck...");

        TestHelper.TestServices services = TestHelper.createTestServices();
        DuckService duckService = services.duckService;

        // Create and save a duck
        Duck duck = new Duck(1, "originalName", "original@email.com", "password123", TipRata.FLYING, 7.0, 8.0);
        duckService.addDuck(duck);

        // Verify initial values
        Optional<Duck> retrievedDuck = duckService.getDuck(1);
        assertBoolean("Duck should be found", retrievedDuck.isPresent());
        assertStringEquals("Initial username should match", "originalName", retrievedDuck.get().getUsername());

        // Modify the duck
        Duck modifiedDuck = retrievedDuck.get();
        modifiedDuck.setUsername("updatedName");
        modifiedDuck.setEmail("updated@email.com");
        modifiedDuck.setTip(TipRata.SWIMMING);
        modifiedDuck.setViteza(9.0);
        modifiedDuck.setRezistenta(7.5);

        // Update the duck
        boolean updateSuccess = duckService.updateDuck(modifiedDuck);
        assertBoolean("Duck update should succeed", updateSuccess);

        // Verify the update in the database
        Optional<Duck> updatedDuck = duckService.getDuck(1);
        assertBoolean("Updated duck should be found", updatedDuck.isPresent());
        assertStringEquals("Updated username should match", "updatedName", updatedDuck.get().getUsername());
        assertStringEquals("Updated email should match", "updated@email.com", updatedDuck.get().getEmail());
        assertObjectEquals("Updated type should match", TipRata.SWIMMING, updatedDuck.get().getTip());
        assertDoubleEquals("Updated speed should match", 9.0, updatedDuck.get().getViteza());
        assertDoubleEquals("Updated endurance should match", 7.5, updatedDuck.get().getRezistenta());

        System.out.println("✓ testUpdateDuck passed");
    }

    public static void testDeleteDuck() throws SQLException {
        System.out.println("Running testDeleteDuck...");

        TestHelper.TestServices services = TestHelper.createTestServices();
        DuckService duckService = services.duckService;

        // Create and save a duck
        Duck duck = new Duck(1, "deleteDuck", "delete@duck.com", "password123", TipRata.SWIMMING, 6.5, 7.5);
        duckService.addDuck(duck);

        // Verify the duck exists
        Optional<Duck> retrievedDuck = duckService.getDuck(1);
        assertBoolean("Duck should be found before deletion", retrievedDuck.isPresent());

        // Delete the duck
        boolean deleteSuccess = duckService.deleteDuck(1);
        assertBoolean("Duck deletion should succeed", deleteSuccess);

        // Verify the duck is gone
        Optional<Duck> deletedDuck = duckService.getDuck(1);
        assertFalse("Duck should not be found after deletion", deletedDuck.isPresent());

        System.out.println("✓ testDeleteDuck passed");
    }

    public static void testReadAllDucks() throws SQLException {
        System.out.println("Running testReadAllDucks...");

        TestHelper.TestServices services = TestHelper.createTestServices();
        DuckService duckService = services.duckService;

        // Create and save multiple ducks
        Duck duck1 = new Duck(1, "duck1", "duck1@email.com", "password1", TipRata.FLYING, 1.0, 2.0);
        Duck duck2 = new Duck(2, "duck2", "duck2@email.com", "password2", TipRata.SWIMMING, 3.0, 4.0);
        Duck duck3 = new Duck(3, "duck3", "duck3@email.com", "password3", TipRata.SWIMMING, 5.0, 6.0);

        duckService.addDuck(duck1);
        duckService.addDuck(duck2);
        duckService.addDuck(duck3);

        // Retrieve all ducks
        List<Duck> allDucks = duckService.findAll();

        assertIntEquals("Should have 3 ducks in the database", 3, allDucks.size());

        // Check that all ducks are present
        boolean foundDuck1 = allDucks.stream().anyMatch(d -> d.getId() == 1 && d.getUsername().equals("duck1"));
        boolean foundDuck2 = allDucks.stream().anyMatch(d -> d.getId() == 2 && d.getUsername().equals("duck2"));
        boolean foundDuck3 = allDucks.stream().anyMatch(d -> d.getId() == 3 && d.getUsername().equals("duck3"));

        assertBoolean("Duck 1 should be in the list", foundDuck1);
        assertBoolean("Duck 2 should be in the list", foundDuck2);
        assertBoolean("Duck 3 should be in the list", foundDuck3);

        System.out.println("✓ testReadAllDucks passed");
    }
}
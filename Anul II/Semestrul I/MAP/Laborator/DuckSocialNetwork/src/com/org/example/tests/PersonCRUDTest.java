package com.org.example.tests;

import com.org.example.repo.RepoPersonDB;
import com.org.example.service.PersonService;
import com.org.example.users.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive CRUD tests for Person entities
 */
public class PersonCRUDTest {

    public static void runAllTests() throws SQLException {
        System.out.println("\n=== Running Person CRUD Tests ===");

        testCreatePerson();
        testReadPerson();
        testUpdatePerson();
        testDeletePerson();
        testReadAllPersons();

        System.out.println("=== Person CRUD Tests Completed ===\n");
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

    public static void testCreatePerson() throws SQLException {
        System.out.println("Running testCreatePerson...");

        TestHelper.TestServices services = TestHelper.createTestServices();
        PersonService personService = services.personService;

        // Create a person
        Person person = new Person(1, "testUser", "test@person.com", "password123",
                                  "Popescu", "Ana", LocalDate.of(1990, 5, 15),
                                  "Engineer", 8.5);
        personService.addPerson(person);

        // Verify person was saved by retrieving it
        Optional<Person> retrievedPerson = personService.getPerson(1);
        assertBoolean("Person should be present in the database", retrievedPerson.isPresent());
        assertStringEquals("Username should match", "testUser", retrievedPerson.get().getUsername());
        assertStringEquals("Email should match", "test@person.com", retrievedPerson.get().getEmail());
        assertStringEquals("Last name should match", "Popescu", retrievedPerson.get().getNume());
        assertStringEquals("First name should match", "Ana", retrievedPerson.get().getPrenume());
        assertObjectEquals("Birth date should match", LocalDate.of(1990, 5, 15), retrievedPerson.get().getDataNasterii());
        assertStringEquals("Occupation should match", "Engineer", retrievedPerson.get().getOcupatie());
        assertDoubleEquals("Empathy level should match", 8.5, retrievedPerson.get().getNivelEmpatie());

        System.out.println("✓ testCreatePerson passed");
    }
    
    public static void testReadPerson() throws SQLException {
        System.out.println("Running testReadPerson...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        PersonService personService = services.personService;
        
        // Create and save a person
        Person person = new Person(1, "readUser", "read@person.com", "password123",
                                  "Ionescu", "Maria", LocalDate.of(1985, 3, 20),
                                  "Teacher", 7.2);
        personService.addPerson(person);
        
        // Retrieve the person by ID
        Optional<Person> retrievedPerson = personService.getPerson(1);
        assertTrue(retrievedPerson.isPresent(), "Person should be found");
        assertEquals("readUser", retrievedPerson.get().getUsername(), "Username should match");
        assertEquals("Ionescu", retrievedPerson.get().getNume(), "Last name should match");
        
        // Test non-existent person
        Optional<Person> nonExistentPerson = personService.getPerson(999);
        assertFalse(nonExistentPerson.isPresent(), "Non-existent person should not be found");
        
        System.out.println("✓ testReadPerson passed");
    }
    
    public static void testUpdatePerson() throws SQLException {
        System.out.println("Running testUpdatePerson...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        PersonService personService = services.personService;
        
        // Create and save a person
        Person person = new Person(1, "originalUser", "original@email.com", "password123",
                                  "Original", "Name", LocalDate.of(1990, 1, 1),
                                  "Original Job", 5.0);
        personService.addPerson(person);
        
        // Verify initial values
        Optional<Person> retrievedPerson = personService.getPerson(1);
        assertTrue(retrievedPerson.isPresent(), "Person should be found");
        assertEquals("originalUser", retrievedPerson.get().getUsername(), "Initial username should match");
        
        // Modify the person
        Person modifiedPerson = retrievedPerson.get();
        modifiedPerson.setUsername("updatedUser");
        modifiedPerson.setEmail("updated@email.com");
        modifiedPerson.setNume("Updated");
        modifiedPerson.setPrenume("Person");
        modifiedPerson.setOcupatie("Updated Job");
        modifiedPerson.setNivelEmpatie(9.0);
        
        // Update the person
        boolean updateSuccess = personService.updatePerson(modifiedPerson);
        assertTrue(updateSuccess, "Person update should succeed");
        
        // Verify the update in the database
        Optional<Person> updatedPerson = personService.getPerson(1);
        assertTrue(updatedPerson.isPresent(), "Updated person should be found");
        assertEquals("updatedUser", updatedPerson.get().getUsername(), "Updated username should match");
        assertEquals("updated@email.com", updatedPerson.get().getEmail(), "Updated email should match");
        assertEquals("Updated", updatedPerson.get().getNume(), "Updated last name should match");
        assertEquals("Person", updatedPerson.get().getPrenume(), "Updated first name should match");
        assertEquals("Updated Job", updatedPerson.get().getOcupatie(), "Updated occupation should match");
        assertEquals(9.0, updatedPerson.get().getNivelEmpatie(), "Updated empathy level should match");
        
        System.out.println("✓ testUpdatePerson passed");
    }
    
    public static void testDeletePerson() throws SQLException {
        System.out.println("Running testDeletePerson...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        PersonService personService = services.personService;
        
        // Create and save a person
        Person person = new Person(1, "deleteUser", "delete@person.com", "password123",
                                  "Delete", "User", LocalDate.of(1980, 7, 10),
                                  "Tester", 6.5);
        personService.addPerson(person);
        
        // Verify the person exists
        Optional<Person> retrievedPerson = personService.getPerson(1);
        assertTrue(retrievedPerson.isPresent(), "Person should be found before deletion");
        
        // Delete the person
        boolean deleteSuccess = personService.deletePerson(1);
        assertTrue(deleteSuccess, "Person deletion should succeed");
        
        // Verify the person is gone
        Optional<Person> deletedPerson = personService.getPerson(1);
        assertFalse(deletedPerson.isPresent(), "Person should not be found after deletion");
        
        System.out.println("✓ testDeletePerson passed");
    }
    
    public static void testReadAllPersons() throws SQLException {
        System.out.println("Running testReadAllPersons...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        PersonService personService = services.personService;
        
        // Create and save multiple persons
        Person person1 = new Person(1, "person1", "person1@email.com", "password1",
                                   "Last1", "First1", LocalDate.of(1990, 1, 1),
                                   "Job1", 7.0);
        Person person2 = new Person(2, "person2", "person2@email.com", "password2",
                                   "Last2", "First2", LocalDate.of(1991, 2, 2),
                                   "Job2", 8.0);
        Person person3 = new Person(3, "person3", "person3@email.com", "password3",
                                   "Last3", "First3", LocalDate.of(1992, 3, 3),
                                   "Job3", 9.0);
        
        personService.addPerson(person1);
        personService.addPerson(person2);
        personService.addPerson(person3);
        
        // Retrieve all persons
        List<Person> allPersons = personService.findAll();
        
        assertEquals(3, allPersons.size(), "Should have 3 persons in the database");
        
        // Check that all persons are present
        boolean foundPerson1 = allPersons.stream().anyMatch(p -> p.getId() == 1 && p.getUsername().equals("person1"));
        boolean foundPerson2 = allPersons.stream().anyMatch(p -> p.getId() == 2 && p.getUsername().equals("person2"));
        boolean foundPerson3 = allPersons.stream().anyMatch(p -> p.getId() == 3 && p.getUsername().equals("person3"));
        
        assertTrue(foundPerson1, "Person 1 should be in the list");
        assertTrue(foundPerson2, "Person 2 should be in the list");
        assertTrue(foundPerson3, "Person 3 should be in the list");
        
        System.out.println("✓ testReadAllPersons passed");
    }
}
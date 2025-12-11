package com.org.example.tests;

import com.org.example.enums.TipRata;
import com.org.example.service.FriendshipService;
import com.org.example.users.Duck;
import com.org.example.users.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive CRUD tests for Friendship entities
 */
public class FriendshipCRUDTest {
    
    public static void runAllTests() throws SQLException {
        System.out.println("\n=== Running Friendship CRUD Tests ===");
        
        testCreateFriendship();
        testReadFriendships();
        testDeleteFriendship();
        testDeleteFriendshipsForUser();
        
        System.out.println("=== Friendship CRUD Tests Completed ===\n");
    }
    
    public static void testCreateFriendship() throws SQLException {
        System.out.println("Running testCreateFriendship...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        var duckService = services.duckService;
        var personService = services.personService;
        var friendshipService = services.friendshipService;
        
        // Create two users
        Duck duck = new Duck(1, "duckFriend", "duck@friend.com", "password123", TipRata.FLYING, 10.0, 8.0);
        Person person = new Person(2, "personFriend", "person@friend.com", "password123",
                                  "Friend", "Person", LocalDate.of(1990, 1, 1),
                                  "Friend Job", 7.0);
                                  
        duckService.addDuck(duck);
        personService.addPerson(person);
        
        // Create a friendship
        friendshipService.addFriendship(1, 2);
        
        // Verify friendship was created by getting all friendships
        List<long[]> allFriendships = friendshipService.getAllFriendships();
        assertEquals(1, allFriendships.size(), "Should have 1 friendship in the database");
        
        long[] friendship = allFriendships.get(0);
        assertEquals(1, Math.min(friendship[0], friendship[1]), "One user in friendship should be duck (ID 1)");
        assertEquals(2, Math.max(friendship[0], friendship[1]), "Other user in friendship should be person (ID 2)");
        
        System.out.println("✓ testCreateFriendship passed");
    }
    
    public static void testReadFriendships() throws SQLException {
        System.out.println("Running testReadFriendships...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        var duckService = services.duckService;
        var personService = services.personService;
        var friendshipService = services.friendshipService;
        
        // Create multiple users
        Duck duck1 = new Duck(1, "duck1", "duck1@friend.com", "password1", TipRata.FLYING, 10.0, 8.0);
        Person person1 = new Person(2, "person1", "person1@friend.com", "password1",
                                   "Friend1", "Person1", LocalDate.of(1990, 1, 1),
                                   "Friend Job 1", 7.0);
        Duck duck2 = new Duck(3, "duck2", "duck2@friend.com", "password2", TipRata.SWIMMING, 5.0, 6.0);
        
        duckService.addDuck(duck1);
        personService.addPerson(person1);
        duckService.addDuck(duck2);
        
        // Create multiple friendships
        friendshipService.addFriendship(1, 2);
        friendshipService.addFriendship(1, 3);
        friendshipService.addFriendship(2, 3);
        
        // Verify friendships were created
        List<long[]> allFriendships = friendshipService.getAllFriendships();
        assertEquals(3, allFriendships.size(), "Should have 3 friendships in the database");
        
        // Check that all expected friendships exist
        boolean found12 = allFriendships.stream()
            .anyMatch(f -> Math.min(f[0], f[1]) == 1 && Math.max(f[0], f[1]) == 2);
        boolean found13 = allFriendships.stream()
            .anyMatch(f -> Math.min(f[0], f[1]) == 1 && Math.max(f[0], f[1]) == 3);
        boolean found23 = allFriendships.stream()
            .anyMatch(f -> Math.min(f[0], f[1]) == 2 && Math.max(f[0], f[1]) == 3);
        
        assertTrue(found12, "Friendship 1-2 should exist");
        assertTrue(found13, "Friendship 1-3 should exist");
        assertTrue(found23, "Friendship 2-3 should exist");
        
        System.out.println("✓ testReadFriendships passed");
    }
    
    public static void testDeleteFriendship() throws SQLException {
        System.out.println("Running testDeleteFriendship...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        var duckService = services.duckService;
        var personService = services.personService;
        var friendshipService = services.friendshipService;
        
        // Create two users
        Duck duck = new Duck(1, "deleteDuck", "duck@delete.com", "password123", TipRata.FLYING, 10.0, 8.0);
        Person person = new Person(2, "deletePerson", "person@delete.com", "password123",
                                  "Delete", "Person", LocalDate.of(1990, 1, 1),
                                  "Delete Job", 7.0);
                                  
        duckService.addDuck(duck);
        personService.addPerson(person);
        
        // Create a friendship
        friendshipService.addFriendship(1, 2);
        
        // Verify friendship exists
        List<long[]> allFriendshipsBefore = friendshipService.getAllFriendships();
        assertEquals(1, allFriendshipsBefore.size(), "Should have 1 friendship before deletion");
        
        // Delete the friendship
        boolean deleteSuccess = friendshipService.removeFriendship(1, 2);
        assertTrue(deleteSuccess, "Friendship deletion should succeed");
        
        // Verify friendship is gone
        List<long[]> allFriendshipsAfter = friendshipService.getAllFriendships();
        assertEquals(0, allFriendshipsAfter.size(), "Should have 0 friendships after deletion");
        
        System.out.println("✓ testDeleteFriendship passed");
    }
    
    public static void testDeleteFriendshipsForUser() throws SQLException {
        System.out.println("Running testDeleteFriendshipsForUser...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        var duckService = services.duckService;
        var personService = services.personService;
        var friendshipService = services.friendshipService;
        
        // Create multiple users
        Duck duck1 = new Duck(1, "user1", "user1@friend.com", "password1", TipRata.FLYING, 10.0, 8.0);
        Person person1 = new Person(2, "user2", "user2@friend.com", "password2",
                                   "User2", "Person2", LocalDate.of(1990, 1, 1),
                                   "User2 Job", 7.0);
        Person person2 = new Person(3, "user3", "user3@friend.com", "password3",
                                   "User3", "Person3", LocalDate.of(1990, 1, 1),
                                   "User3 Job", 8.0);
        Duck duck2 = new Duck(4, "user4", "user4@friend.com", "password4", TipRata.SWIMMING, 5.0, 6.0);
        
        duckService.addDuck(duck1);
        personService.addPerson(person1);
        personService.addPerson(person2);
        duckService.addDuck(duck2);
        
        // Create friendships involving user 1
        friendshipService.addFriendship(1, 2);
        friendshipService.addFriendship(1, 3);
        friendshipService.addFriendship(1, 4);
        
        // Verify friendships exist
        List<long[]> allFriendshipsBefore = friendshipService.getAllFriendships();
        assertEquals(3, allFriendshipsBefore.size(), "Should have 3 friendships before deletion");
        
        // Delete all friendships for user 1
        int deletedCount = friendshipService.removeAllForUser(1);
        assertEquals(3, deletedCount, "Should have deleted 3 friendships for user 1");
        
        // Verify friendships for user 1 are gone
        List<long[]> allFriendshipsAfter = friendshipService.getAllFriendships();
        assertEquals(0, allFriendshipsAfter.size(), "Should have 0 friendships after deletion for user 1");
        
        System.out.println("✓ testDeleteFriendshipsForUser passed");
    }
}
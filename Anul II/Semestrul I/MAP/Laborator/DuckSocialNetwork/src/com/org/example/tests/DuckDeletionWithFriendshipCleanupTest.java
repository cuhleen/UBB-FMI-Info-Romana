package com.org.example.tests;

import com.org.example.enums.TipRata;
import com.org.example.service.AppService;
import com.org.example.users.Duck;
import com.org.example.users.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for deleting a duck and ensuring all adjacent friendships are removed
 */
public class DuckDeletionWithFriendshipCleanupTest {
    
    public static void runTest() throws SQLException {
        System.out.println("\n=== Running Duck Deletion With Friendship Cleanup Test ===");
        
        testDeleteDuckRemovesFriendships();
        
        System.out.println("=== Duck Deletion With Friendship Cleanup Test Completed ===\n");
    }
    
    public static void testDeleteDuckRemovesFriendships() throws SQLException {
        System.out.println("Running testDeleteDuckRemovesFriendships...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        AppService appService = services.appService;
        var duckService = services.duckService;
        var personService = services.personService;
        
        // Create a duck and multiple persons
        Duck duck = new Duck(1, "testDuck", "duck@friend.com", "password123", TipRata.FLYING, 10.0, 8.0);
        Person person1 = new Person(2, "friend1", "friend1@person.com", "password123",
                                   "Friend", "One", LocalDate.of(1990, 1, 1),
                                   "Job 1", 7.0);
        Person person2 = new Person(3, "friend2", "friend2@person.com", "password123",
                                   "Friend", "Two", LocalDate.of(1990, 1, 1),
                                   "Job 2", 8.0);
        Duck otherDuck = new Duck(4, "otherDuck", "other@duck.com", "password123", TipRata.SWIMMING, 5.0, 6.0);
        
        duckService.addDuck(duck);
        personService.addPerson(person1);
        personService.addPerson(person2);
        duckService.addDuck(otherDuck);
        
        // Create friendships with the duck
        appService.addFriendship(1, 2);  // duck - person1
        appService.addFriendship(1, 3);  // duck - person2
        appService.addFriendship(1, 4);  // duck - otherDuck
        
        // Verify friendships exist before deletion
        List<long[]> allFriendshipsBefore = services.friendshipService.getAllFriendships();
        assertEquals(3, allFriendshipsBefore.size(), "Should have 3 friendships before duck deletion");
        
        // Check that specific friendships exist
        boolean foundDuckPerson1 = allFriendshipsBefore.stream()
            .anyMatch(f -> Math.min(f[0], f[1]) == 1 && Math.max(f[0], f[1]) == 2);
        boolean foundDuckPerson2 = allFriendshipsBefore.stream()
            .anyMatch(f -> Math.min(f[0], f[1]) == 1 && Math.max(f[0], f[1]) == 3);
        boolean foundDuckOtherDuck = allFriendshipsBefore.stream()
            .anyMatch(f -> Math.min(f[0], f[1]) == 1 && Math.max(f[0], f[1]) == 4);
        
        assertTrue(foundDuckPerson1, "Friendship duck-person1 should exist before deletion");
        assertTrue(foundDuckPerson2, "Friendship duck-person2 should exist before deletion");
        assertTrue(foundDuckOtherDuck, "Friendship duck-otherDuck should exist before deletion");
        
        // Delete the duck using AppService (which should handle friendship cleanup)
        appService.deleteUser(1);
        
        // Verify duck is deleted
        var deletedDuck = duckService.getDuck(1);
        assertFalse(deletedDuck.isPresent(), "Duck should be deleted");
        
        // Verify that all friendships involving the duck are removed
        List<long[]> allFriendshipsAfter = services.friendshipService.getAllFriendships();
        assertEquals(0, allFriendshipsAfter.size(), "Should have 0 friendships after duck deletion");
        
        // Ensure the other users still exist and can have friendships
        var remainingPerson1 = personService.getPerson(2);
        assertTrue(remainingPerson1.isPresent(), "Person 1 should still exist");
        
        var remainingPerson2 = personService.getPerson(3);
        assertTrue(remainingPerson2.isPresent(), "Person 2 should still exist");
        
        var remainingDuck = duckService.getDuck(4);
        assertTrue(remainingDuck.isPresent(), "Other duck should still exist");
        
        // Create a new friendship between remaining users to ensure system still works
        appService.addFriendship(2, 3);
        
        List<long[]> friendshipsAfterNew = services.friendshipService.getAllFriendships();
        assertEquals(1, friendshipsAfterNew.size(), "Should have 1 new friendship after duck deletion");
        
        System.out.println("✓ testDeleteDuckRemovesFriendships passed");
    }
}
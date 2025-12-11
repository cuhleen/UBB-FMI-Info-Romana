package com.org.example.tests;

import com.org.example.enums.TipRata;
import com.org.example.model.RaceEvent;
import com.org.example.service.AppService;
import com.org.example.users.Card;
import com.org.example.users.Duck;
import com.org.example.users.Person;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for data persistence after CRUD operations across all entity types
 */
public class DataPersistenceAfterCRUDTest {
    
    public static void runTest() throws SQLException {
        System.out.println("\n=== Running Data Persistence After CRUD Operations Test ===");
        
        testDataPersistenceAfterCRUDOperations();
        
        System.out.println("=== Data Persistence After CRUD Operations Test Completed ===\n");
    }
    
    public static void testDataPersistenceAfterCRUDOperations() throws SQLException {
        System.out.println("Running testDataPersistenceAfterCRUDOperations...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        AppService appService = services.appService;
        var duckService = services.duckService;
        var personService = services.personService;
        var cardService = services.cardService;
        var friendshipService = services.friendshipService;
        var raceEventService = services.raceEventService;
        
        // 1. Create entities
        // Create ducks
        Duck duck1 = new Duck(1, "duck1", "duck1@test.com", "pass1", TipRata.FLYING, 10.0, 8.0);
        Duck duck2 = new Duck(2, "duck2", "duck2@test.com", "pass2", TipRata.SWIMMING, 5.0, 6.0);
        duckService.addDuck(duck1);
        duckService.addDuck(duck2);
        
        // Create persons
        Person person1 = new Person(3, "person1", "person1@test.com", "pass1",
                                   "Last1", "First1", LocalDate.of(1990, 1, 1),
                                   "Job1", 7.0);
        Person person2 = new Person(4, "person2", "person2@test.com", "pass2",
                                   "Last2", "First2", LocalDate.of(1991, 2, 2),
                                   "Job2", 8.0);
        personService.addPerson(person1);
        personService.addPerson(person2);
        
        // Create cards
        Card card1 = new Card(1, "Flying Team");
        Card card2 = new Card(2, "Swimming Team");
        cardService.addCard(card1);
        cardService.addCard(card2);
        
        // Create friendships
        appService.addFriendship(1, 3);  // duck1 - person1
        appService.addFriendship(2, 4);  // duck2 - person2
        appService.addFriendship(1, 2);  // duck1 - duck2
        
        // Create race events
        java.util.ArrayList<Double> balize1 = new java.util.ArrayList<>();
        balize1.add(1.0);
        balize1.add(2.0);
        RaceEvent event1 = raceEventService.createRaceEvent("Race 1", "First race", 1, balize1);
        
        java.util.ArrayList<Double> balize2 = new java.util.ArrayList<>();
        balize2.add(3.0);
        balize2.add(4.0);
        balize2.add(5.0);
        RaceEvent event2 = raceEventService.createRaceEvent("Race 2", "Second race", 2, balize2);
        
        // Verify initial state
        assertEquals(2, duckService.findAll().size(), "Should have 2 ducks");
        assertEquals(2, personService.findAll().size(), "Should have 2 persons");
        assertEquals(2, cardService.getAllCards().size(), "Should have 2 cards");
        assertEquals(3, friendshipService.getAllFriendships().size(), "Should have 3 friendships");
        assertEquals(2, raceEventService.findAll().size(), "Should have 2 race events");
        
        // 2. Update entities
        // Update a duck
        duck1.setUsername("updatedDuck1");
        duck1.setViteza(12.0);
        duckService.updateDuck(duck1);
        
        // Update a person
        person1.setNume("UpdatedLast1");
        person1.setNivelEmpatie(9.0);
        personService.updatePerson(person1);
        
        // Update a card
        card1.setNumeCard("Updated Flying Team");
        cardService.updateCard(card1);
        
        // Verify updates persisted
        Optional<Duck> updatedDuck = duckService.getDuck(1);
        assertTrue(updatedDuck.isPresent(), "Updated duck should exist");
        assertEquals("updatedDuck1", updatedDuck.get().getUsername(), "Duck username should be updated");
        assertEquals(12.0, updatedDuck.get().getViteza(), "Duck speed should be updated");
        
        Optional<Person> updatedPerson = personService.getPerson(3);
        assertTrue(updatedPerson.isPresent(), "Updated person should exist");
        assertEquals("UpdatedLast1", updatedPerson.get().getNume(), "Person last name should be updated");
        assertEquals(9.0, updatedPerson.get().getNivelEmpatie(), "Person empathy should be updated");
        
        Optional<Card> updatedCard = cardService.getCard(1);
        assertTrue(updatedCard.isPresent(), "Updated card should exist");
        assertEquals("Updated Flying Team", updatedCard.get().getNumeCard(), "Card name should be updated");
        
        // 3. Add duck to card
        appService.addDuckToCard(1, 1);  // Add duck1 to card1
        Optional<Card> cardWithDuck = cardService.getCard(1);
        assertTrue(cardWithDuck.isPresent(), "Card should exist");
        assertEquals(1, cardWithDuck.get().getMembri().size(), "Card should have 1 member");
        
        // 4. Delete some entities
        // Delete a friendship
        appService.removeFriendship(2, 4);  // Remove friendship between duck2 and person2
        assertEquals(2, friendshipService.getAllFriendships().size(), "Should have 2 friendships after deletion");
        
        // Delete a person (and their friendships)
        appService.deleteUser(4);  // Delete person2
        assertEquals(1, personService.findAll().size(), "Should have 1 person after deletion");
        assertEquals(1, friendshipService.getAllFriendships().size(), "Should have 1 friendship after person deletion");
        
        // 5. Verify final state after all CRUD operations
        List<Duck> remainingDucks = duckService.findAll();
        assertEquals(2, remainingDucks.size(), "Should still have 2 ducks");
        
        List<Person> remainingPersons = personService.findAll();
        assertEquals(1, remainingPersons.size(), "Should have 1 person after deletion");
        assertEquals("person1", remainingPersons.get(0).getUsername(), "Remaining person should be person1");
        
        List<Card> remainingCards = cardService.getAllCards();
        assertEquals(2, remainingCards.size(), "Should still have 2 cards");
        
        List<RaceEvent> remainingEvents = raceEventService.findAll();
        assertEquals(2, remainingEvents.size(), "Should still have 2 race events");
        
        // Verify that the duck is still in the card
        Optional<Card> cardAfterDeletions = cardService.getCard(1);
        assertTrue(cardAfterDeletions.isPresent(), "Card should still exist");
        assertEquals(1, cardAfterDeletions.get().getMembri().size(), "Card should still have 1 member");
        assertEquals("updatedDuck1", cardAfterDeletions.get().getMembri().get(0).getUsername(), "Card should contain updated duck");
        
        // Final verification - all remaining data should be consistent
        Optional<Duck> finalDuck1 = duckService.getDuck(1);
        Optional<Duck> finalDuck2 = duckService.getDuck(2);
        Optional<Person> finalPerson1 = personService.getPerson(3);
        
        assertTrue(finalDuck1.isPresent(), "Duck 1 should still exist");
        assertTrue(finalDuck2.isPresent(), "Duck 2 should still exist");
        assertTrue(finalPerson1.isPresent(), "Person 1 should still exist");
        
        // Verify the properties are still correct after all operations
        assertEquals("updatedDuck1", finalDuck1.get().getUsername(), "Duck 1 username should match");
        assertEquals(12.0, finalDuck1.get().getViteza(), "Duck 1 speed should match");
        assertEquals("person1", finalPerson1.get().getUsername(), "Person 1 username should match");
        
        System.out.println("✓ testDataPersistenceAfterCRUDOperations passed");
    }
}
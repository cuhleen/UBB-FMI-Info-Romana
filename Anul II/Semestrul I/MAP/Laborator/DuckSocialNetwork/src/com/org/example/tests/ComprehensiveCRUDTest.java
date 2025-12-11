package com.org.example.tests;

import com.org.example.enums.TipRata;
import com.org.example.model.RaceEvent;
import com.org.example.repo.*;
import com.org.example.service.*;
import com.org.example.users.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive test suite for all CRUD operations and functionality
 */
public class ComprehensiveCRUDTest {
    
    public static void main(String[] args) {
        try {
            System.out.println("Starting comprehensive CRUD and functionality tests...\n");
            
            // Test Duck CRUD operations
            testDuckCRUD();
            
            // Test Person CRUD operations
            testPersonCRUD();
            
            // Test Card CRUD operations
            testCardCRUD();
            
            // Test Friendship CRUD operations
            testFriendshipCRUD();
            
            // Test RaceEvent CRUD operations
            testRaceEventCRUD();
            
            // Test specific functionality: deleting duck removes friendships
            testDeleteDuckRemovesFriendships();
            
            // Test updating duck reflects in Cards
            testUpdateDuckReflectsInCard();
            
            // Test data persistence across operations
            testDataPersistenceAfterCRUD();
            
            System.out.println("\nAll tests completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void testDuckCRUD() throws SQLException {
        System.out.println("Testing Duck CRUD operations...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        DuckService duckService = services.duckService;
        
        // Create
        Duck duck = new Duck(1, "testDuck", "test@duck.com", "password123", TipRata.FLYING, 10.5, 8.2);
        duckService.addDuck(duck);
        
        // Read
        Optional<Duck> retrievedDuck = duckService.getDuck(1);
        if (!retrievedDuck.isPresent()) {
            throw new AssertionError("Duck should be present in the database");
        }
        if (!"testDuck".equals(retrievedDuck.get().getUsername())) {
            throw new AssertionError("Username should match");
        }
        
        // Update
        retrievedDuck.get().setUsername("updatedDuck");
        retrievedDuck.get().setViteza(12.0);
        boolean updateSuccess = duckService.updateDuck(retrievedDuck.get());
        if (!updateSuccess) {
            throw new AssertionError("Duck update should succeed");
        }
        
        // Verify update
        Optional<Duck> updatedDuck = duckService.getDuck(1);
        if (!"updatedDuck".equals(updatedDuck.get().getUsername())) {
            throw new AssertionError("Updated username should match");
        }
        if (Math.abs(updatedDuck.get().getViteza() - 12.0) > 0.001) {
            throw new AssertionError("Updated speed should match");
        }
        
        // Read all
        List<Duck> allDucks = duckService.findAll();
        if (allDucks.size() != 1) {
            throw new AssertionError("Should have 1 duck in the database");
        }
        
        // Delete
        boolean deleteSuccess = duckService.deleteDuck(1);
        if (!deleteSuccess) {
            throw new AssertionError("Duck deletion should succeed");
        }
        
        // Verify deletion
        Optional<Duck> deletedDuck = duckService.getDuck(1);
        if (deletedDuck.isPresent()) {
            throw new AssertionError("Duck should not be found after deletion");
        }
        
        System.out.println("✓ Duck CRUD operations passed\n");
    }
    
    public static void testPersonCRUD() throws SQLException {
        System.out.println("Testing Person CRUD operations...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        PersonService personService = services.personService;
        
        // Create
        Person person = new Person(1, "testUser", "test@person.com", "password123",
                                  "Popescu", "Ana", LocalDate.of(1990, 5, 15),
                                  "Engineer", 8.5);
        personService.addPerson(person);
        
        // Read
        Optional<Person> retrievedPerson = personService.getPerson(1);
        if (!retrievedPerson.isPresent()) {
            throw new AssertionError("Person should be present in the database");
        }
        if (!"testUser".equals(retrievedPerson.get().getUsername())) {
            throw new AssertionError("Username should match");
        }
        
        // Update
        retrievedPerson.get().setUsername("updatedUser");
        retrievedPerson.get().setNivelEmpatie(9.0);
        boolean updateSuccess = personService.updatePerson(retrievedPerson.get());
        if (!updateSuccess) {
            throw new AssertionError("Person update should succeed");
        }
        
        // Verify update
        Optional<Person> updatedPerson = personService.getPerson(1);
        if (!"updatedUser".equals(updatedPerson.get().getUsername())) {
            throw new AssertionError("Updated username should match");
        }
        if (Math.abs(updatedPerson.get().getNivelEmpatie() - 9.0) > 0.001) {
            throw new AssertionError("Updated empathy level should match");
        }
        
        // Read all
        List<Person> allPersons = personService.findAll();
        if (allPersons.size() != 1) {
            throw new AssertionError("Should have 1 person in the database");
        }
        
        // Delete
        boolean deleteSuccess = personService.deletePerson(1);
        if (!deleteSuccess) {
            throw new AssertionError("Person deletion should succeed");
        }
        
        // Verify deletion
        Optional<Person> deletedPerson = personService.getPerson(1);
        if (deletedPerson.isPresent()) {
            throw new AssertionError("Person should not be found after deletion");
        }
        
        System.out.println("✓ Person CRUD operations passed\n");
    }
    
    public static void testCardCRUD() throws SQLException {
        System.out.println("Testing Card CRUD operations...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        CardService cardService = services.cardService;
        
        // Create
        Card card = new Card(1, "Swimming Team");
        cardService.addCard(card);
        
        // Read
        Optional<Card> retrievedCard = cardService.getCard(1);
        if (!retrievedCard.isPresent()) {
            throw new AssertionError("Card should be present in the database");
        }
        if (!"Swimming Team".equals(retrievedCard.get().getNumeCard())) {
            throw new AssertionError("Card name should match");
        }
        
        // Update
        retrievedCard.get().setNumeCard("Flying Team");
        boolean updateSuccess = cardService.updateCard(retrievedCard.get());
        if (!updateSuccess) {
            throw new AssertionError("Card update should succeed");
        }
        
        // Verify update
        Optional<Card> updatedCard = cardService.getCard(1);
        if (!"Flying Team".equals(updatedCard.get().getNumeCard())) {
            throw new AssertionError("Updated card name should match");
        }
        
        // Read all
        List<Card> allCards = cardService.getAllCards();
        if (allCards.size() != 1) {
            throw new AssertionError("Should have 1 card in the database");
        }
        
        // Delete
        boolean deleteSuccess = cardService.deleteCard(1);
        if (!deleteSuccess) {
            throw new AssertionError("Card deletion should succeed");
        }
        
        // Verify deletion
        Optional<Card> deletedCard = cardService.getCard(1);
        if (deletedCard.isPresent()) {
            throw new AssertionError("Card should not be found after deletion");
        }
        
        System.out.println("✓ Card CRUD operations passed\n");
    }
    
    public static void testFriendshipCRUD() throws SQLException {
        System.out.println("Testing Friendship CRUD operations...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        DuckService duckService = services.duckService;
        PersonService personService = services.personService;
        FriendshipService friendshipService = services.friendshipService;
        
        // Create users
        Duck duck = new Duck(1, "duckFriend", "duck@friend.com", "password123", TipRata.FLYING, 10.0, 8.0);
        Person person = new Person(2, "personFriend", "person@friend.com", "password123",
                                  "Friend", "Person", LocalDate.of(1990, 1, 1),
                                  "Friend Job", 7.0);
        duckService.addDuck(duck);
        personService.addPerson(person);
        
        // Create friendship
        friendshipService.addFriendship(1, 2);
        
        // Read friendships
        List<long[]> allFriendships = friendshipService.getAllFriendships();
        if (allFriendships.size() != 1) {
            throw new AssertionError("Should have 1 friendship in the database");
        }
        
        // Delete friendship
        boolean deleteSuccess = friendshipService.removeFriendship(1, 2);
        if (!deleteSuccess) {
            throw new AssertionError("Friendship deletion should succeed");
        }
        
        // Verify deletion
        List<long[]> remainingFriendships = friendshipService.getAllFriendships();
        if (remainingFriendships.size() != 0) {
            throw new AssertionError("Should have 0 friendships after deletion");
        }
        
        System.out.println("✓ Friendship CRUD operations passed\n");
    }
    
    public static void testRaceEventCRUD() throws SQLException {
        System.out.println("Testing RaceEvent CRUD operations...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        RaceEventService raceEventService = services.raceEventService;
        
        // Create race event
        java.util.ArrayList<Double> balizeCoordinates = new java.util.ArrayList<>();
        balizeCoordinates.add(1.0);
        balizeCoordinates.add(2.0);
        RaceEvent raceEvent = raceEventService.createRaceEvent("Test Race", "A test race event", 1, balizeCoordinates);
        
        // Read all race events
        List<RaceEvent> allEvents = raceEventService.findAll();
        if (allEvents.size() != 1) {
            throw new AssertionError("Should have 1 race event in the database");
        }
        
        RaceEvent retrievedEvent = allEvents.get(0);
        if (!"Test Race".equals(retrievedEvent.getTitle())) {
            throw new AssertionError("Event title should match");
        }
        
        System.out.println("✓ RaceEvent CRUD operations passed\n");
    }
    
    public static void testDeleteDuckRemovesFriendships() throws SQLException {
        System.out.println("Testing that deleting duck removes friendships...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        AppService appService = services.appService;
        DuckService duckService = services.duckService;
        PersonService personService = services.personService;
        
        // Create a duck and multiple persons
        Duck duck = new Duck(1, "testDuck", "duck@friend.com", "password123", TipRata.FLYING, 10.0, 8.0);
        Person person1 = new Person(2, "friend1", "friend1@person.com", "password123",
                                   "Friend", "One", LocalDate.of(1990, 1, 1),
                                   "Job 1", 7.0);
        Person person2 = new Person(3, "friend2", "friend2@person.com", "password123",
                                   "Friend", "Two", LocalDate.of(1990, 1, 1),
                                   "Job 2", 8.0);
        
        duckService.addDuck(duck);
        personService.addPerson(person1);
        personService.addPerson(person2);
        
        // Create friendships with the duck
        appService.addFriendship(1, 2);  // duck - person1
        appService.addFriendship(1, 3);  // duck - person2
        
        // Verify friendships exist before deletion
        List<long[]> allFriendshipsBefore = services.friendshipService.getAllFriendships();
        if (allFriendshipsBefore.size() != 2) {
            throw new AssertionError("Should have 2 friendships before duck deletion");
        }
        
        // Delete the duck using AppService (which should handle friendship cleanup)
        appService.deleteUser(1);
        
        // Verify duck is deleted
        Optional<Duck> deletedDuck = duckService.getDuck(1);
        if (deletedDuck.isPresent()) {
            throw new AssertionError("Duck should be deleted");
        }
        
        // Verify that all friendships involving the duck are removed
        List<long[]> allFriendshipsAfter = services.friendshipService.getAllFriendships();
        if (allFriendshipsAfter.size() != 0) {
            throw new AssertionError("Should have 0 friendships after duck deletion");
        }
        
        System.out.println("✓ Duck deletion with friendship cleanup passed\n");
    }
    
    public static void testUpdateDuckReflectsInCard() throws SQLException {
        System.out.println("Testing that updating duck reflects changes in Cards...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        AppService appService = services.appService;
        DuckService duckService = services.duckService;
        CardService cardService = services.cardService;
        
        // Create a duck and a card
        Duck duck = new Duck(1, "originalDuck", "duck@original.com", "password123", TipRata.FLYING, 10.0, 8.0);
        Card card = new Card(1, "Flying Team");
        
        duckService.addDuck(duck);
        cardService.addCard(card);
        
        // Add duck to card
        appService.addDuckToCard(1, 1);
        
        // Verify duck is in card
        Optional<Card> cardWithDuck = cardService.getCard(1);
        if (!cardWithDuck.isPresent()) {
            throw new AssertionError("Card should exist");
        }
        if (cardWithDuck.get().getMembri().size() != 1) {
            throw new AssertionError("Card should have 1 member");
        }
        if (!"originalDuck".equals(cardWithDuck.get().getMembri().get(0).getUsername())) {
            throw new AssertionError("Card should contain the original duck");
        }
        
        // Update the duck
        Optional<Duck> retrievedDuck = duckService.getDuck(1);
        if (!retrievedDuck.isPresent()) {
            throw new AssertionError("Duck should exist before update");
        }
        
        Duck updatedDuck = retrievedDuck.get();
        updatedDuck.setUsername("updatedDuck");
        updatedDuck.setViteza(15.0);
        updatedDuck.setRezistenta(12.0);
        
        boolean updateSuccess = duckService.updateDuck(updatedDuck);
        if (!updateSuccess) {
            throw new AssertionError("Duck update should succeed");
        }
        
        // Verify the duck in the card still reflects the updated properties
        Optional<Card> cardAfterUpdate = cardService.getCard(1);
        if (!cardAfterUpdate.isPresent()) {
            throw new AssertionError("Card should still exist after duck update");
        }
        if (cardAfterUpdate.get().getMembri().size() != 1) {
            throw new AssertionError("Card should still have 1 member after duck update");
        }
        
        // The duck in the card should have the updated properties
        Duck duckInCard = cardAfterUpdate.get().getMembri().get(0);
        if (!"updatedDuck".equals(duckInCard.getUsername())) {
            throw new AssertionError("Duck in card should have updated username");
        }
        if (Math.abs(duckInCard.getViteza() - 15.0) > 0.001) {
            throw new AssertionError("Duck in card should have updated speed");
        }
        if (Math.abs(duckInCard.getRezistenta() - 12.0) > 0.001) {
            throw new AssertionError("Duck in card should have updated endurance");
        }
        
        System.out.println("✓ Duck update reflecting in card passed\n");
    }
    
    public static void testDataPersistenceAfterCRUD() throws SQLException {
        System.out.println("Testing data persistence after CRUD operations...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        AppService appService = services.appService;
        DuckService duckService = services.duckService;
        PersonService personService = services.personService;
        CardService cardService = services.cardService;
        FriendshipService friendshipService = services.friendshipService;
        RaceEventService raceEventService = services.raceEventService;
        
        // Create entities
        Duck duck1 = new Duck(1, "duck1", "duck1@test.com", "pass1", TipRata.FLYING, 10.0, 8.0);
        Duck duck2 = new Duck(2, "duck2", "duck2@test.com", "pass2", TipRata.SWIMMING, 5.0, 6.0);
        duckService.addDuck(duck1);
        duckService.addDuck(duck2);
        
        Person person1 = new Person(3, "person1", "person1@test.com", "pass1",
                                   "Last1", "First1", LocalDate.of(1990, 1, 1),
                                   "Job1", 7.0);
        Person person2 = new Person(4, "person2", "person2@test.com", "pass2",
                                   "Last2", "First2", LocalDate.of(1991, 2, 2),
                                   "Job2", 8.0);
        personService.addPerson(person1);
        personService.addPerson(person2);
        
        Card card1 = new Card(1, "Flying Team");
        Card card2 = new Card(2, "Swimming Team");
        cardService.addCard(card1);
        cardService.addCard(card2);
        
        appService.addFriendship(1, 3);  // duck1 - person1
        appService.addFriendship(2, 4);  // duck2 - person2
        
        java.util.ArrayList<Double> balize1 = new java.util.ArrayList<>();
        balize1.add(1.0);
        balize1.add(2.0);
        RaceEvent event1 = raceEventService.createRaceEvent("Race 1", "First race", 1, balize1);
        
        // Verify initial state
        if (duckService.findAll().size() != 2) {
            throw new AssertionError("Should have 2 ducks");
        }
        if (personService.findAll().size() != 2) {
            throw new AssertionError("Should have 2 persons");
        }
        if (cardService.getAllCards().size() != 2) {
            throw new AssertionError("Should have 2 cards");
        }
        if (friendshipService.getAllFriendships().size() != 2) {
            throw new AssertionError("Should have 2 friendships");
        }
        if (raceEventService.findAll().size() != 1) {
            throw new AssertionError("Should have 1 race event");
        }
        
        // Perform updates
        duck1.setUsername("updatedDuck1");
        duckService.updateDuck(duck1);
        
        person1.setNume("UpdatedLast1");
        personService.updatePerson(person1);
        
        // Perform deletions
        appService.removeFriendship(2, 4);  // Remove friendship
        
        // Verify final state
        if (duckService.findAll().size() != 2) {
            throw new AssertionError("Should still have 2 ducks");
        }
        if (personService.findAll().size() != 2) {
            throw new AssertionError("Should still have 2 persons");
        }
        if (cardService.getAllCards().size() != 2) {
            throw new AssertionError("Should still have 2 cards");
        }
        if (friendshipService.getAllFriendships().size() != 1) {
            throw new AssertionError("Should have 1 friendship after deletion");
        }
        if (raceEventService.findAll().size() != 1) {
            throw new AssertionError("Should still have 1 race event");
        }
        
        // Verify specific updates persisted
        Optional<Duck> finalDuck1 = duckService.getDuck(1);
        if (!finalDuck1.isPresent() || !"updatedDuck1".equals(finalDuck1.get().getUsername())) {
            throw new AssertionError("Updated duck should persist");
        }
        
        Optional<Person> finalPerson1 = personService.getPerson(3);
        if (!finalPerson1.isPresent() || !"UpdatedLast1".equals(finalPerson1.get().getNume())) {
            throw new AssertionError("Updated person should persist");
        }
        
        System.out.println("✓ Data persistence after CRUD operations passed\n");
    }
}
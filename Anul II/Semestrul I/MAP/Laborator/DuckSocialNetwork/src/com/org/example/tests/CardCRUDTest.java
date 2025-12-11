package com.org.example.tests;

import com.org.example.enums.TipRata;
import com.org.example.repo.RepoCardDB;
import com.org.example.service.CardService;
import com.org.example.service.DuckService;
import com.org.example.users.Card;
import com.org.example.users.Duck;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive CRUD tests for Card entities
 */
public class CardCRUDTest {
    
    public static void runAllTests() throws SQLException {
        System.out.println("\n=== Running Card CRUD Tests ===");
        
        testCreateCard();
        testReadCard();
        testUpdateCard();
        testDeleteCard();
        testReadAllCards();
        
        System.out.println("=== Card CRUD Tests Completed ===\n");
    }
    
    public static void testCreateCard() throws SQLException {
        System.out.println("Running testCreateCard...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        CardService cardService = services.cardService;
        
        // Create a card
        Card card = new Card(1, "Swimming Team");
        cardService.addCard(card);
        
        // Verify card was saved by retrieving it
        Optional<Card> retrievedCard = cardService.getCard(1);
        assertTrue(retrievedCard.isPresent(), "Card should be present in the database");
        assertEquals("Swimming Team", retrievedCard.get().getNumeCard(), "Card name should match");
        assertEquals(0, retrievedCard.get().getMembri().size(), "Card should have no members initially");
        
        System.out.println("✓ testCreateCard passed");
    }
    
    public static void testReadCard() throws SQLException {
        System.out.println("Running testReadCard...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        CardService cardService = services.cardService;
        
        // Create and save a card
        Card card = new Card(1, "Flying Team");
        cardService.addCard(card);
        
        // Retrieve the card by ID
        Optional<Card> retrievedCard = cardService.getCard(1);
        assertTrue(retrievedCard.isPresent(), "Card should be found");
        assertEquals("Flying Team", retrievedCard.get().getNumeCard(), "Card name should match");
        
        // Test non-existent card
        Optional<Card> nonExistentCard = cardService.getCard(999);
        assertFalse(nonExistentCard.isPresent(), "Non-existent card should not be found");
        
        System.out.println("✓ testReadCard passed");
    }
    
    public static void testUpdateCard() throws SQLException {
        System.out.println("Running testUpdateCard...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        CardService cardService = services.cardService;
        
        // Create and save a card
        Card card = new Card(1, "Original Team");
        cardService.addCard(card);
        
        // Verify initial values
        Optional<Card> retrievedCard = cardService.getCard(1);
        assertTrue(retrievedCard.isPresent(), "Card should be found");
        assertEquals("Original Team", retrievedCard.get().getNumeCard(), "Initial card name should match");
        
        // Modify the card
        retrievedCard.get().setNumeCard("Updated Team");
        
        // Update the card
        boolean updateSuccess = cardService.updateCard(retrievedCard.get());
        assertTrue(updateSuccess, "Card update should succeed");
        
        // Verify the update in the database
        Optional<Card> updatedCard = cardService.getCard(1);
        assertTrue(updatedCard.isPresent(), "Updated card should be found");
        assertEquals("Updated Team", updatedCard.get().getNumeCard(), "Updated card name should match");
        
        System.out.println("✓ testUpdateCard passed");
    }
    
    public static void testDeleteCard() throws SQLException {
        System.out.println("Running testDeleteCard...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        CardService cardService = services.cardService;
        
        // Create and save a card
        Card card = new Card(1, "Delete Team");
        cardService.addCard(card);
        
        // Verify the card exists
        Optional<Card> retrievedCard = cardService.getCard(1);
        assertTrue(retrievedCard.isPresent(), "Card should be found before deletion");
        
        // Delete the card
        boolean deleteSuccess = cardService.deleteCard(1);
        assertTrue(deleteSuccess, "Card deletion should succeed");
        
        // Verify the card is gone
        Optional<Card> deletedCard = cardService.getCard(1);
        assertFalse(deletedCard.isPresent(), "Card should not be found after deletion");
        
        System.out.println("✓ testDeleteCard passed");
    }
    
    public static void testReadAllCards() throws SQLException {
        System.out.println("Running testReadAllCards...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        CardService cardService = services.cardService;
        
        // Create and save multiple cards
        Card card1 = new Card(1, "Card 1");
        Card card2 = new Card(2, "Card 2");
        Card card3 = new Card(3, "Card 3");
        
        cardService.addCard(card1);
        cardService.addCard(card2);
        cardService.addCard(card3);
        
        // Retrieve all cards
        List<Card> allCards = cardService.getAllCards();
        
        assertEquals(3, allCards.size(), "Should have 3 cards in the database");
        
        // Check that all cards are present
        boolean foundCard1 = allCards.stream().anyMatch(c -> c.getId() == 1 && c.getNumeCard().equals("Card 1"));
        boolean foundCard2 = allCards.stream().anyMatch(c -> c.getId() == 2 && c.getNumeCard().equals("Card 2"));
        boolean foundCard3 = allCards.stream().anyMatch(c -> c.getId() == 3 && c.getNumeCard().equals("Card 3"));
        
        assertTrue(foundCard1, "Card 1 should be in the list");
        assertTrue(foundCard2, "Card 2 should be in the list");
        assertTrue(foundCard3, "Card 3 should be in the list");
        
        System.out.println("✓ testReadAllCards passed");
    }
}
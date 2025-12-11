package com.org.example.tests;

import com.org.example.enums.TipRata;
import com.org.example.service.AppService;
import com.org.example.users.Card;
import com.org.example.users.Duck;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for updating a duck and ensuring changes are reflected in Cards and friendships
 */
public class DuckUpdateWithCardReflectionTest {
    
    public static void runTest() throws SQLException {
        System.out.println("\n=== Running Duck Update With Card Reflection Test ===");
        
        testUpdateDuckReflectsInCard();
        
        System.out.println("=== Duck Update With Card Reflection Test Completed ===\n");
    }
    
    public static void testUpdateDuckReflectsInCard() throws SQLException {
        System.out.println("Running testUpdateDuckReflectsInCard...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        AppService appService = services.appService;
        var duckService = services.duckService;
        var cardService = services.cardService;
        
        // Create a duck and a card
        Duck duck = new Duck(1, "originalDuck", "duck@original.com", "password123", TipRata.FLYING, 10.0, 8.0);
        Card card = new Card(1, "Flying Team");
        
        duckService.addDuck(duck);
        cardService.addCard(card);
        
        // Add duck to card
        appService.addDuckToCard(1, 1);
        
        // Verify duck is in card
        var cardWithDuck = cardService.getCard(1);
        assertTrue(cardWithDuck.isPresent(), "Card should exist");
        assertEquals(1, cardWithDuck.get().getMembri().size(), "Card should have 1 member");
        assertEquals("originalDuck", cardWithDuck.get().getMembri().get(0).getUsername(), "Card should contain the original duck");
        
        // Update the duck
        var retrievedDuck = duckService.getDuck(1);
        assertTrue(retrievedDuck.isPresent(), "Duck should exist before update");
        
        Duck updatedDuck = retrievedDuck.get();
        updatedDuck.setUsername("updatedDuck");
        updatedDuck.setEmail("duck@updated.com");
        updatedDuck.setTip(TipRata.SWIMMING);
        updatedDuck.setViteza(15.0);
        updatedDuck.setRezistenta(12.0);
        
        boolean updateSuccess = duckService.updateDuck(updatedDuck);
        assertTrue(updateSuccess, "Duck update should succeed");
        
        // Verify the duck was updated in the database
        var updatedDuckFromDB = duckService.getDuck(1);
        assertTrue(updatedDuckFromDB.isPresent(), "Updated duck should exist");
        assertEquals("updatedDuck", updatedDuckFromDB.get().getUsername(), "Duck username should be updated");
        assertEquals("duck@updated.com", updatedDuckFromDB.get().getEmail(), "Duck email should be updated");
        assertEquals(TipRata.SWIMMING, updatedDuckFromDB.get().getTip(), "Duck type should be updated");
        assertEquals(15.0, updatedDuckFromDB.get().getViteza(), "Duck speed should be updated");
        assertEquals(12.0, updatedDuckFromDB.get().getRezistenta(), "Duck endurance should be updated");
        
        // Verify the duck in the card still reflects the original data in terms of membership
        // but has the updated properties when accessed directly
        var cardAfterUpdate = cardService.getCard(1);
        assertTrue(cardAfterUpdate.isPresent(), "Card should still exist after duck update");
        assertEquals(1, cardAfterUpdate.get().getMembri().size(), "Card should still have 1 member after duck update");
        
        // The duck in the card should have the updated properties
        Duck duckInCard = cardAfterUpdate.get().getMembri().get(0);
        assertEquals("updatedDuck", duckInCard.getUsername(), "Duck in card should have updated username");
        assertEquals(TipRata.SWIMMING, duckInCard.getTip(), "Duck in card should have updated type");
        assertEquals(15.0, duckInCard.getViteza(), "Duck in card should have updated speed");
        assertEquals(12.0, duckInCard.getRezistenta(), "Duck in card should have updated endurance");
        
        // Test that card performance is updated based on the duck's new stats
        Card.Performance performance = cardAfterUpdate.get().getPerformantaMedie();
        assertEquals(15.0, performance.vitezaMedie, "Card average speed should reflect updated duck speed");
        assertEquals(12.0, performance.rezistentaMedie, "Card average endurance should reflect updated duck endurance");
        
        System.out.println("✓ testUpdateDuckReflectsInCard passed");
    }
}
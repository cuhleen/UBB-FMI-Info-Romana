package com.org.example.tests;

import com.org.example.model.RaceEvent;
import com.org.example.service.RaceEventService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive CRUD tests for RaceEvent entities
 */
public class RaceEventCRUDTest {
    
    public static void runAllTests() throws SQLException {
        System.out.println("\n=== Running RaceEvent CRUD Tests ===");
        
        testCreateRaceEvent();
        testReadRaceEvent();
        testReadAllRaceEvents();
        
        System.out.println("=== RaceEvent CRUD Tests Completed ===\n");
    }
    
    public static void testCreateRaceEvent() throws SQLException {
        System.out.println("Running testCreateRaceEvent...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        RaceEventService raceEventService = services.raceEventService;
        
        // Create a race event
        List<Double> balizeCoordinates = new ArrayList<>();
        balizeCoordinates.add(1.0);
        balizeCoordinates.add(2.0);
        balizeCoordinates.add(3.0);
        
        RaceEvent raceEvent = raceEventService.createRaceEvent("Test Race", "A test race event", 1, balizeCoordinates);
        
        // Verify race event was created by retrieving all events
        List<RaceEvent> allEvents = raceEventService.findAll();
        assertEquals(1, allEvents.size(), "Should have 1 race event in the database");
        
        RaceEvent retrievedEvent = allEvents.get(0);
        assertEquals("Test Race", retrievedEvent.getTitle(), "Event title should match");
        assertEquals("A test race event", retrievedEvent.getDescription(), "Event description should match");
        assertEquals(1, retrievedEvent.getCreatorId(), "Event creator ID should match");
        assertEquals(3, retrievedEvent.getBalize().size(), "Event should have 3 balize coordinates");
        
        System.out.println("✓ testCreateRaceEvent passed");
    }
    
    public static void testReadRaceEvent() throws SQLException {
        System.out.println("Running testReadRaceEvent...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        RaceEventService raceEventService = services.raceEventService;
        
        // Create a race event
        List<Double> balizeCoordinates = new ArrayList<>();
        balizeCoordinates.add(10.0);
        balizeCoordinates.add(20.0);
        balizeCoordinates.add(30.0);
        
        RaceEvent raceEvent = raceEventService.createRaceEvent("Read Race", "A race to read", 2, balizeCoordinates);
        
        // Verify race event exists by retrieving all events
        List<RaceEvent> allEvents = raceEventService.findAll();
        assertEquals(1, allEvents.size(), "Should have 1 race event in the database");
        
        RaceEvent retrievedEvent = allEvents.get(0);
        assertEquals("Read Race", retrievedEvent.getTitle(), "Event title should match");
        assertEquals("A race to read", retrievedEvent.getDescription(), "Event description should match");
        assertEquals(3, retrievedEvent.getBalize().size(), "Event should have 3 balize coordinates");
        
        System.out.println("✓ testReadRaceEvent passed");
    }
    
    public static void testReadAllRaceEvents() throws SQLException {
        System.out.println("Running testReadAllRaceEvents...");
        
        TestHelper.TestServices services = TestHelper.createTestServices();
        RaceEventService raceEventService = services.raceEventService;
        
        // Create multiple race events
        List<Double> balize1 = new ArrayList<>();
        balize1.add(1.0);
        balize1.add(2.0);
        
        List<Double> balize2 = new ArrayList<>();
        balize2.add(3.0);
        balize2.add(4.0);
        balize2.add(5.0);
        
        List<Double> balize3 = new ArrayList<>();
        balize3.add(6.0);
        
        raceEventService.createRaceEvent("Race 1", "First race", 1, balize1);
        raceEventService.createRaceEvent("Race 2", "Second race", 2, balize2);
        raceEventService.createRaceEvent("Race 3", "Third race", 3, balize3);
        
        // Retrieve all race events
        List<RaceEvent> allEvents = raceEventService.findAll();
        
        assertEquals(3, allEvents.size(), "Should have 3 race events in the database");
        
        // Check that all events are present
        boolean foundRace1 = allEvents.stream().anyMatch(e -> e.getTitle().equals("Race 1"));
        boolean foundRace2 = allEvents.stream().anyMatch(e -> e.getTitle().equals("Race 2"));
        boolean foundRace3 = allEvents.stream().anyMatch(e -> e.getTitle().equals("Race 3"));
        
        assertTrue(foundRace1, "Race 1 should be in the list");
        assertTrue(foundRace2, "Race 2 should be in the list");
        assertTrue(foundRace3, "Race 3 should be in the list");
        
        System.out.println("✓ testReadAllRaceEvents passed");
    }
}
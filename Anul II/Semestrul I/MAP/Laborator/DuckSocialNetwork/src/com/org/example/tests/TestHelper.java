package com.org.example.tests;

import com.org.example.database.DatabaseConnection;
import com.org.example.repo.*;
import com.org.example.service.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Helper class for setting up in-memory database for testing
 */
public class TestHelper {
    
    public static Connection getTestConnection() throws SQLException {
        // Use in-memory H2 database for testing
        return DatabaseConnection.getInMemoryConnection();
    }
    
    public static TestServices createTestServices() throws SQLException {
        Connection conn = getTestConnection();
        
        // Initialize database tables (without foreign key constraints to avoid dependency issues)
        try (conn) {
            // Create tables in proper order to avoid dependency issues
            conn.createStatement().execute("DROP TABLE IF EXISTS race_participants");
            conn.createStatement().execute("DROP TABLE IF EXISTS race_balize");
            conn.createStatement().execute("DROP TABLE IF EXISTS event_subscribers");
            conn.createStatement().execute("DROP TABLE IF EXISTS race_events");
            conn.createStatement().execute("DROP TABLE IF EXISTS events");
            conn.createStatement().execute("DROP TABLE IF EXISTS friendships");
            conn.createStatement().execute("DROP TABLE IF EXISTS persons");
            conn.createStatement().execute("DROP TABLE IF EXISTS ducks");
            conn.createStatement().execute("DROP TABLE IF EXISTS cards");
            conn.createStatement().execute("DROP TABLE IF EXISTS users");

            conn.createStatement().execute("""
                CREATE TABLE users (
                    id BIGINT PRIMARY KEY,
                    username VARCHAR(255) UNIQUE,
                    email VARCHAR(255),
                    password VARCHAR(255)
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE cards (
                    id BIGINT PRIMARY KEY,
                    nume_card VARCHAR(255)
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE ducks (
                    id BIGINT PRIMARY KEY,
                    username VARCHAR(255) UNIQUE,
                    email VARCHAR(255),
                    password VARCHAR(255),
                    tip VARCHAR(50),
                    viteza DOUBLE,
                    rezistenta DOUBLE,
                    card_id BIGINT
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE persons (
                    id BIGINT PRIMARY KEY,
                    username VARCHAR(255) UNIQUE,
                    email VARCHAR(255),
                    password VARCHAR(255),
                    nume VARCHAR(255),
                    prenume VARCHAR(255),
                    data_nasterii DATE,
                    ocupatie VARCHAR(255),
                    nivel_empatie DOUBLE
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE friendships (
                    user_a BIGINT,
                    user_b BIGINT,
                    since TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    PRIMARY KEY (user_a, user_b),
                    CHECK (user_a < user_b)
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE events (
                    id BIGINT PRIMARY KEY,
                    title VARCHAR(255),
                    description TEXT,
                    creator_id BIGINT,
                    type VARCHAR(50),
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE event_subscribers (
                    event_id BIGINT,
                    user_id BIGINT,
                    PRIMARY KEY (event_id, user_id)
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE race_events (
                    id BIGINT PRIMARY KEY,
                    title VARCHAR(255),
                    description TEXT,
                    creator_id BIGINT,
                    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE race_balize (
                    event_id BIGINT,
                    distance DOUBLE,
                    PRIMARY KEY (event_id, distance)
                )
            """);

            conn.createStatement().execute("""
                CREATE TABLE race_participants (
                    event_id BIGINT,
                    duck_id BIGINT,
                    PRIMARY KEY (event_id, duck_id)
                )
            """);

            // Create sequence for event IDs
            conn.createStatement().execute("CREATE SEQUENCE IF NOT EXISTS events_seq START WITH 1");
        }
        
        // Create repositories with test connection
        String url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        String user = "sa";
        String pass = "";

        RepoDuckDB duckRepo = new RepoDuckDB(url, user, pass);
        RepoPersonDB personRepo = new RepoPersonDB(url, user, pass);
        RepoCardDB cardRepo = new RepoCardDB(url, user, pass);
        RepoFriendshipDB friendshipRepo = new RepoFriendshipDB(url, user, pass);
        RepoEventDB eventRepo = new RepoEventDB(url, user, pass);

        // Create services
        DuckService duckService = new DuckService(duckRepo);
        PersonService personService = new PersonService(personRepo);
        CardService cardService = new CardService(cardRepo, duckRepo);
        FriendshipService friendshipService = new FriendshipService(friendshipRepo);
        RaceEventService raceEventService = new RaceEventService(eventRepo, duckRepo);
        
        return new TestServices(duckService, personService, cardService, friendshipService, raceEventService);
    }
    
    public static class TestServices {
        public final DuckService duckService;
        public final PersonService personService;
        public final CardService cardService;
        public final FriendshipService friendshipService;
        public final RaceEventService raceEventService;
        public final AppService appService;
        
        public TestServices(DuckService duckService, PersonService personService, 
                           CardService cardService, FriendshipService friendshipService, 
                           RaceEventService raceEventService) {
            this.duckService = duckService;
            this.personService = personService;
            this.cardService = cardService;
            this.friendshipService = friendshipService;
            this.raceEventService = raceEventService;
            this.appService = new AppService(duckService, personService, cardService, 
                                           friendshipService, raceEventService);
        }
    }
}
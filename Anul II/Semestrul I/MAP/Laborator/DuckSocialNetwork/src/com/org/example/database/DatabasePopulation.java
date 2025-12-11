package com.org.example.database;

import com.org.example.enums.TipRata;
import com.org.example.users.Duck;
import com.org.example.users.Person;
import com.org.example.users.Card;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DatabasePopulation {

    // Sample data for ducks (10 entries)
    private static final String[] DUCK_USERNAMES = {
        "Donald", "Daffy", "Scrooge", "Huey", "Dewey", 
        "Louie", "Mickey", "Minnie", "Goofy", "Pluto"
    };
    
    private static final String[] DUCK_EMAILS = {
        "donald@duck.com", "daffy@duck.com", "scrooge@duck.com", 
        "huey@duck.com", "dewey@duck.com", "louie@duck.com", 
        "mickey@duck.com", "minnie@duck.com", "goofy@duck.com", 
        "pluto@duck.com"
    };
    
    private static final TipRata[] DUCK_TYPES = {
        TipRata.FLYING, TipRata.SWIMMING, TipRata.FLYING_AND_SWIMMING,
        TipRata.FLYING, TipRata.SWIMMING, TipRata.FLYING_AND_SWIMMING,
        TipRata.FLYING, TipRata.SWIMMING, TipRata.FLYING_AND_SWIMMING,
        TipRata.FLYING
    };
    
    private static final double[] DUCK_SPEEDS = {5.5, 3.2, 4.8, 6.1, 3.7, 5.2, 4.5, 3.0, 5.8, 4.2};
    private static final double[] DUCK_RESISTANCES = {4.2, 6.8, 5.5, 3.9, 7.2, 6.1, 5.0, 8.0, 4.7, 5.3};

    // Sample data for people (10 entries)
    private static final String[] PERSON_USERNAMES = {
        "JohnDoe", "JaneSmith", "BobWilson", "AliceBrown", "CharlieGreen",
        "DianaGray", "EveBlue", "FrankBlack", "GraceWhite", "HenryYellow"
    };
    
    private static final String[] PERSON_EMAILS = {
        "john.doe@email.com", "jane.smith@email.com", "bob.wilson@email.com",
        "alice.brown@email.com", "charlie.green@email.com", "diana.gray@email.com",
        "eve.blue@email.com", "frank.black@email.com", "grace.white@email.com",
        "henry.yellow@email.com"
    };
    
    private static final String[] PERSON_NAMES = {
        "John", "Jane", "Bob", "Alice", "Charlie", 
        "Diana", "Eve", "Frank", "Grace", "Henry"
    };
    
    private static final String[] PERSON_SURNAMES = {
        "Doe", "Smith", "Wilson", "Brown", "Green", 
        "Gray", "Blue", "Black", "White", "Yellow"
    };
    
    private static final String[] PERSON_OCCUPATIONS = {
        "Engineer", "Doctor", "Teacher", "Artist", "Chef",
        "Lawyer", "Musician", "Architect", "Nurse", "Writer"
    };
    
    private static final double[] PERSON_EMPATHY_LEVELS = {
        0.8, 0.9, 0.7, 0.85, 0.75, 0.95, 0.88, 0.72, 0.91, 0.78
    };
    
    private static final LocalDate[] PERSON_BIRTH_DATES = {
        LocalDate.of(1985, 5, 15),
        LocalDate.of(1990, 8, 22),
        LocalDate.of(1982, 12, 3),
        LocalDate.of(1995, 3, 18),
        LocalDate.of(1988, 11, 7),
        LocalDate.of(1993, 1, 30),
        LocalDate.of(1987, 6, 12),
        LocalDate.of(1991, 9, 25),
        LocalDate.of(1989, 4, 9),
        LocalDate.of(1994, 10, 14)
    };

    // Sample data for cards (3 entries)
    private static final String[] CARD_NAMES = {"Flying Flock", "Swimming Squad", "Aqua-Aviators"};
    private static final long[] CARD_IDS = {201, 202, 203};

    public static void populateDatabase(Connection connection) throws SQLException {
        populateUsersTable(connection);
        populateCardsTable(connection);  // Create cards first
        populateDucksTable(connection);  // Then ducks with card references
        populatePersonsTable(connection);
        System.out.println("Database populated with sample data successfully!");
    }

    private static void populateUsersTable(Connection connection) throws SQLException {
        String insertUserSql = "INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertUserSql)) {
            // Insert ducks as users
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 1);
                pstmt.setString(2, DUCK_USERNAMES[i]);
                pstmt.setString(3, DUCK_EMAILS[i]);
                pstmt.setString(4, "password" + (i + 1));
                pstmt.executeUpdate();
            }
            
            // Insert persons as users (IDs 11-20)
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 11);
                pstmt.setString(2, PERSON_USERNAMES[i]);
                pstmt.setString(3, PERSON_EMAILS[i]);
                pstmt.setString(4, "password" + (i + 1));
                pstmt.executeUpdate();
            }
        }
    }

    private static void populateDucksTable(Connection connection) throws SQLException {
        String insertDuckSql = "INSERT INTO ducks (id, username, email, password, tip, viteza, rezistenta, card_id) VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertDuckSql)) {
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 1); // IDs 1-10
                pstmt.setString(2, DUCK_USERNAMES[i]);
                pstmt.setString(3, DUCK_EMAILS[i]);
                pstmt.setString(4, "password" + (i + 1));
                pstmt.setString(5, DUCK_TYPES[i].name());
                pstmt.setDouble(6, DUCK_SPEEDS[i]);
                pstmt.setDouble(7, DUCK_RESISTANCES[i]);

                pstmt.executeUpdate();
            }
        }
    }

    private static void populatePersonsTable(Connection connection) throws SQLException {
        String insertPersonSql = "INSERT INTO persons (id, username, email, password, nume, prenume, data_nasterii, ocupatie, nivel_empatie) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertPersonSql)) {
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 11); // IDs 11-20
                pstmt.setString(2, PERSON_USERNAMES[i]);
                pstmt.setString(3, PERSON_EMAILS[i]);
                pstmt.setString(4, "password" + (i + 1));
                pstmt.setString(5, PERSON_NAMES[i]);
                pstmt.setString(6, PERSON_SURNAMES[i]);
                pstmt.setDate(7, java.sql.Date.valueOf(PERSON_BIRTH_DATES[i]));
                pstmt.setString(8, PERSON_OCCUPATIONS[i]);
                pstmt.setDouble(9, PERSON_EMPATHY_LEVELS[i]);
                pstmt.executeUpdate();
            }
        }
    }

    private static void populateCardsTable(Connection connection) throws SQLException {
        String insertCardSql = "INSERT INTO cards (id, nume_card) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertCardSql)) {
            for (int i = 0; i < 3; i++) {
                pstmt.setLong(1, CARD_IDS[i]);
                pstmt.setString(2, CARD_NAMES[i]);
                pstmt.executeUpdate();
            }
        }
    }

    public static void populateDucksWithCards(Connection connection) throws SQLException {
        // Update ducks in their respective cards based on their types
        for (int i = 0; i < 10; i++) {
            long cardId;
            if (DUCK_TYPES[i] == TipRata.FLYING) {
                cardId = CARD_IDS[0]; // Flying Flock
            } else if (DUCK_TYPES[i] == TipRata.SWIMMING) {
                cardId = CARD_IDS[1]; // Swimming Squad
            } else { // FLYING_AND_SWIMMING
                cardId = CARD_IDS[2]; // Aqua-Aviators
            }

            String updateDuckCard = "UPDATE ducks SET card_id = ? WHERE id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(updateDuckCard)) {
                pstmt.setLong(1, cardId);
                pstmt.setLong(2, i + 1);
                pstmt.executeUpdate();
            }
        }
    }

    public static void createSampleFriendships(Connection connection) throws SQLException {
        String insertFriendshipSql = "INSERT INTO friendships (user_a, user_b, since) VALUES (?, ?, NOW())";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertFriendshipSql)) {
            // Create some sample friendships
            int[][] friendships = {
                {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, 
                {6, 7}, {7, 8}, {8, 9}, {9, 10}, {1, 10},
                {11, 12}, {12, 13}, {13, 14}, {14, 15}, {15, 16},
                {1, 11}, {2, 12}, {3, 13}, {4, 14}, {5, 15}
            };
            
            for (int[] friendship : friendships) {
                long userA = Math.min(friendship[0], friendship[1]);
                long userB = Math.max(friendship[0], friendship[1]);
                
                pstmt.setLong(1, userA);
                pstmt.setLong(2, userB);
                pstmt.executeUpdate();
            }
        }
        System.out.println("Sample friendships created!");
    }

    public static void createSampleRaceEvents(Connection connection) throws SQLException {
        // Insert a few sample race events
        String insertEventSql = "INSERT INTO events (id, title, description, creator_id, created_at, type) VALUES (?, ?, ?, ?, NOW(), 'RACE')";

        try (PreparedStatement pstmt = connection.prepareStatement(insertEventSql)) {
            // Create 3 sample race events
            Object[][] events = {
                {301L, "Annual Duck Race", "Annual race for all types of ducks", 1L},
                {302L, "Swimming Championship", "Swimming race for swimming ducks", 2L},
                {303L, "Flight Race", "Flying race for flying ducks", 3L}
            };

            for (Object[] event : events) {
                pstmt.setLong(1, (Long)event[0]);
                pstmt.setString(2, (String)event[1]);
                pstmt.setString(3, (String)event[2]);
                pstmt.setLong(4, (Long)event[3]);
                pstmt.executeUpdate();
            }
        }

        // Add balizes for each race event
        String insertBalizeSql = "INSERT INTO race_balize (event_id, distance) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertBalizeSql)) {
            // Add balizes for the first event
            for (int i = 1; i <= 5; i++) {
                pstmt.setLong(1, 301L);
                pstmt.setDouble(2, i * 100.0); // 100m, 200m, 300m, 400m, 500m
                pstmt.executeUpdate();
            }

            // Add balizes for the second event
            for (int i = 1; i <= 3; i++) {
                pstmt.setLong(1, 302L);
                pstmt.setDouble(2, i * 50.0); // 50m, 100m, 150m
                pstmt.executeUpdate();
            }

            // Add balizes for the third event
            for (int i = 1; i <= 4; i++) {
                pstmt.setLong(1, 303L);
                pstmt.setDouble(2, i * 200.0); // 200m, 400m, 600m, 800m
                pstmt.executeUpdate();
            }
        }

        // Add some participants to events (only ducks can participate in races)
        String insertParticipantSql = "INSERT INTO race_participants (event_id, duck_id) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertParticipantSql)) {
            // Add some ducks to the first race event (they should be swimming or flying_and_swimming ducks)
            Object[][] participants = {
                {301L, 2L}, // Swimming duck
                {301L, 3L}, // Flying_and_swimming duck
                {301L, 5L}, // Swimming duck
                {301L, 6L}, // Flying_and_swimming duck
                {301L, 8L}, // Swimming duck
                {301L, 9L}, // Flying_and_swimming duck
                {302L, 2L}, // Swimming duck
                {302L, 5L}, // Swimming duck
                {302L, 8L}, // Swimming duck
                {303L, 1L}, // Flying duck
                {303L, 3L}, // Flying_and_swimming duck
                {303L, 4L}, // Flying duck
                {303L, 6L}, // Flying_and_swimming duck
                {303L, 9L}  // Flying_and_swimming duck
            };

            for (Object[] participant : participants) {
                pstmt.setLong(1, (Long)participant[0]);
                pstmt.setLong(2, (Long)participant[1]);
                pstmt.executeUpdate();
            }
        }

        System.out.println("Sample race events created with participants!");
    }

    public static void populateWithSampleData(Connection connection) throws SQLException {
        populateDatabase(connection);
        populateDucksWithCards(connection);
        createSampleFriendships(connection);
        createSampleRaceEvents(connection); // Add sample race events
        System.out.println("Database fully populated with sample data including friendships and race events!");
    }

    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInMemoryConnection();
            DatabaseInitializer.initializeInMemoryDatabase(conn);
            populateWithSampleData(conn);
            conn.close();

            System.out.println("Sample data population completed!");
        } catch (SQLException e) {
            System.err.println("Error populating database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
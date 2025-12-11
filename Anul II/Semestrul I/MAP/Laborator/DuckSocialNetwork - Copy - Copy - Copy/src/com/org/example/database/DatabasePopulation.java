package com.org.example.database;

import com.org.example.enums.TipRata;

import java.sql.*;
import java.time.LocalDate;

public class DatabasePopulation {

    // ------- SAMPLE DATA -------

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

    private static final String[] CARD_NAMES = {"Flying Flock", "Swimming Squad", "Aqua-Aviators"};
    private static final long[] CARD_IDS = {201, 202, 203};


    // ------- POPULARE TABELE -------

    public static void populateDatabase(Connection connection) throws SQLException {
        populateUsersTable(connection);
        populateCardsTable(connection);
        populateDucksTable(connection);
        populatePersonsTable(connection);
    }

    private static void populateUsersTable(Connection connection) throws SQLException {
        String sql = "INSERT INTO users (id, username, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            // Ducks: IDs 1–10
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 1);
                pstmt.setString(2, DUCK_USERNAMES[i]);
                pstmt.setString(3, DUCK_EMAILS[i]);
                pstmt.setString(4, "password" + (i + 1));
                pstmt.executeUpdate();
            }

            // Persons: IDs 11–20
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 11);
                pstmt.setString(2, PERSON_USERNAMES[i]);
                pstmt.setString(3, PERSON_EMAILS[i]);
                pstmt.setString(4, "password" + (i + 1));
                pstmt.executeUpdate();
            }
        }
    }

    private static void populateCardsTable(Connection connection) throws SQLException {
        String sql = "INSERT INTO cards (id, nume_card) VALUES (?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < 3; i++) {
                pstmt.setLong(1, CARD_IDS[i]);
                pstmt.setString(2, CARD_NAMES[i]);
                pstmt.executeUpdate();
            }
        }
    }

    private static void populateDucksTable(Connection connection) throws SQLException {
        String sql = """
            INSERT INTO ducks (id, username, email, password, tip, viteza, rezistenta, card_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, NULL)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 1);
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
        String sql = """
            INSERT INTO persons (id, username, email, password, nume, prenume, data_nasterii, ocupatie, nivel_empatie)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < 10; i++) {
                pstmt.setLong(1, i + 11);
                pstmt.setString(2, PERSON_USERNAMES[i]);
                pstmt.setString(3, PERSON_EMAILS[i]);
                pstmt.setString(4, "password" + (i + 1));
                pstmt.setString(5, PERSON_NAMES[i]);
                pstmt.setString(6, PERSON_SURNAMES[i]);
                pstmt.setDate(7, Date.valueOf(PERSON_BIRTH_DATES[i]));
                pstmt.setString(8, PERSON_OCCUPATIONS[i]);
                pstmt.setDouble(9, PERSON_EMPATHY_LEVELS[i]);
                pstmt.executeUpdate();
            }
        }
    }

    // ------- CARD ASSIGNMENT -------

    public static void populateDucksWithCards(Connection connection) throws SQLException {
        String sql = "UPDATE ducks SET card_id = ? WHERE id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < 10; i++) {
                long cardId =
                        DUCK_TYPES[i] == TipRata.FLYING ? CARD_IDS[0] :
                                DUCK_TYPES[i] == TipRata.SWIMMING ? CARD_IDS[1] :
                                        CARD_IDS[2];

                pstmt.setLong(1, cardId);
                pstmt.setLong(2, i + 1);
                pstmt.executeUpdate();
            }
        }
    }


    // ------- FRIENDSHIPS -------

    public static void createSampleFriendships(Connection connection) throws SQLException {

        String sql = "INSERT INTO friendships (user_a, user_b, since) VALUES (?, ?, NOW())";

        int[][] friendships = {
                {1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6},
                {6, 7}, {7, 8}, {8, 9}, {9, 10}, {1, 10},
                {11, 12}, {12, 13}, {13, 14}, {14, 15}, {15, 16},
                {1, 11}, {2, 12}, {3, 13}, {4, 14}, {5, 15}
        };

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int[] pair : friendships) {
                pstmt.setLong(1, Math.min(pair[0], pair[1]));
                pstmt.setLong(2, Math.max(pair[0], pair[1]));
                pstmt.executeUpdate();
            }
        }
    }


    // ------- RACE EVENTS -------

    public static void createSampleRaceEvents(Connection connection) throws SQLException {

        // Events
        try (PreparedStatement pstmt =
                     connection.prepareStatement(
                             "INSERT INTO events (id, title, description, creator_id, created_at, type) VALUES (?, ?, ?, ?, NOW(), 'RACE')"
                     )) {

            Object[][] events = {
                    {301L, "Annual Duck Race", "Annual race for all types of ducks", 1L},
                    {302L, "Swimming Championship", "Swimming race for swimming ducks", 2L},
                    {303L, "Flight Race", "Flying race for flying ducks", 3L}
            };

            for (Object[] e : events) {
                pstmt.setLong(1, (Long) e[0]);
                pstmt.setString(2, (String) e[1]);
                pstmt.setString(3, (String) e[2]);
                pstmt.setLong(4, (Long) e[3]);
                pstmt.executeUpdate();
            }
        }

        // Balize
        try (PreparedStatement pstmt =
                     connection.prepareStatement("INSERT INTO race_balize (event_id, distance) VALUES (?, ?)")) {

            for (int i = 1; i <= 5; i++)
                insertBalize(pstmt, 301L, i * 100);

            for (int i = 1; i <= 3; i++)
                insertBalize(pstmt, 302L, i * 50);

            for (int i = 1; i <= 4; i++)
                insertBalize(pstmt, 303L, i * 200);
        }

        // Participants
        try (PreparedStatement pstmt =
                     connection.prepareStatement("INSERT INTO race_participants (event_id, duck_id) VALUES (?, ?)")) {

            Object[][] participants = {
                    {301L, 2L}, {301L, 3L}, {301L, 5L}, {301L, 6L}, {301L, 8L}, {301L, 9L},
                    {302L, 2L}, {302L, 5L}, {302L, 8L},
                    {303L, 1L}, {303L, 3L}, {303L, 4L}, {303L, 6L}, {303L, 9L}
            };

            for (Object[] p : participants) {
                pstmt.setLong(1, (Long)p[0]);
                pstmt.setLong(2, (Long)p[1]);
                pstmt.executeUpdate();
            }
        }
    }

    private static void insertBalize(PreparedStatement pstmt, long eventId, double dist) throws SQLException {
        pstmt.setLong(1, eventId);
        pstmt.setDouble(2, dist);
        pstmt.executeUpdate();
    }


    // ------- FULL POPULATE -------

    public static void populateWithSampleData(Connection connection) throws SQLException {
        populateDatabase(connection);
        populateDucksWithCards(connection);
        createSampleFriendships(connection);
        createSampleRaceEvents(connection);
    }


    // ------- MAIN (SIMPLIFICAT) -------

    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {

            DatabaseInitializer.initializeDatabase(conn); // doar PostgreSQL

            clearExistingData(conn);
            populateWithSampleData(conn);

            System.out.println("Sample data population completed!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ------- CLEAR -------

    private static void clearExistingData(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {

            stmt.execute("DELETE FROM race_participants");
            stmt.execute("DELETE FROM race_balize");
            stmt.execute("DELETE FROM event_subscribers");
            stmt.execute("DELETE FROM friendships");
            stmt.execute("DELETE FROM events");
            stmt.execute("DELETE FROM persons");
            stmt.execute("DELETE FROM ducks");
            stmt.execute("DELETE FROM cards");
            stmt.execute("DELETE FROM users");
        }
    }
}

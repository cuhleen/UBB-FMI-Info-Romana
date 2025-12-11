package com.org.example.database;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    // SQL statements for creating tables
    private static final String CREATE_USERS_TABLE = 
        "CREATE TABLE IF NOT EXISTS users (" +
        "id BIGINT PRIMARY KEY," +
        "username VARCHAR(255) NOT NULL UNIQUE," +
        "email VARCHAR(255) NOT NULL UNIQUE," +
        "password VARCHAR(255) NOT NULL" +
        ")";

    private static final String CREATE_DUCKS_TABLE = 
        "CREATE TABLE IF NOT EXISTS ducks (" +
        "id BIGINT PRIMARY KEY," +
        "username VARCHAR(255) NOT NULL UNIQUE," +
        "email VARCHAR(255) NOT NULL UNIQUE," +
        "password VARCHAR(255) NOT NULL," +
        "tip VARCHAR(50) NOT NULL," +
        "viteza DOUBLE NOT NULL," +
        "rezistenta DOUBLE NOT NULL," +
        "card_id BIGINT," +
        "FOREIGN KEY (card_id) REFERENCES cards(id)" +
        ")";

    private static final String CREATE_PERSONS_TABLE = 
        "CREATE TABLE IF NOT EXISTS persons (" +
        "id BIGINT PRIMARY KEY," +
        "username VARCHAR(255) NOT NULL UNIQUE," +
        "email VARCHAR(255) NOT NULL UNIQUE," +
        "password VARCHAR(255) NOT NULL," +
        "nume VARCHAR(255) NOT NULL," +
        "prenume VARCHAR(255) NOT NULL," +
        "data_nasterii DATE NOT NULL," +
        "ocupatie VARCHAR(255) NOT NULL," +
        "nivel_empatie DOUBLE NOT NULL" +
        ")";

    private static final String CREATE_CARDS_TABLE = 
        "CREATE TABLE IF NOT EXISTS cards (" +
        "id BIGINT PRIMARY KEY," +
        "nume_card VARCHAR(255) NOT NULL" +
        ")";

    private static final String CREATE_FRIENDSHIPS_TABLE = 
        "CREATE TABLE IF NOT EXISTS friendships (" +
        "user_a BIGINT NOT NULL," +
        "user_b BIGINT NOT NULL," +
        "since TIMESTAMP NOT NULL," +
        "PRIMARY KEY (user_a, user_b)," +
        "FOREIGN KEY (user_a) REFERENCES users(id)," +
        "FOREIGN KEY (user_b) REFERENCES users(id)" +
        ")";

    private static final String CREATE_EVENTS_TABLE = 
        "CREATE TABLE IF NOT EXISTS events (" +
        "id BIGINT PRIMARY KEY," +
        "title VARCHAR(255) NOT NULL," +
        "description TEXT," +
        "creator_id BIGINT," +
        "created_at TIMESTAMP NOT NULL," +
        "type VARCHAR(50) NOT NULL," +
        "FOREIGN KEY (creator_id) REFERENCES users(id)" +
        ")";

    private static final String CREATE_EVENT_SUBSCRIBERS_TABLE = 
        "CREATE TABLE IF NOT EXISTS event_subscribers (" +
        "event_id BIGINT NOT NULL," +
        "user_id BIGINT NOT NULL," +
        "PRIMARY KEY (event_id, user_id)," +
        "FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE," +
        "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
        ")";

    private static final String CREATE_RACE_BALIZE_TABLE = 
        "CREATE TABLE IF NOT EXISTS race_balize (" +
        "event_id BIGINT NOT NULL," +
        "distance DOUBLE NOT NULL," +
        "PRIMARY KEY (event_id, distance)," +
        "FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE" +
        ")";

    private static final String CREATE_RACE_PARTICIPANTS_TABLE = 
        "CREATE TABLE IF NOT EXISTS race_participants (" +
        "event_id BIGINT NOT NULL," +
        "duck_id BIGINT NOT NULL," +
        "PRIMARY KEY (event_id, duck_id)," +
        "FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE," +
        "FOREIGN KEY (duck_id) REFERENCES ducks(id) ON DELETE CASCADE" +
        ")";

    public static void initializeDatabase(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create sequence for event IDs
            stmt.execute("CREATE SEQUENCE IF NOT EXISTS events_seq START WITH 1");

            // Create tables in the correct order (to respect foreign key constraints)
            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_CARDS_TABLE);
            stmt.execute(CREATE_DUCKS_TABLE);
            stmt.execute(CREATE_PERSONS_TABLE);
            stmt.execute(CREATE_EVENTS_TABLE);
            stmt.execute(CREATE_EVENT_SUBSCRIBERS_TABLE);
            stmt.execute(CREATE_RACE_BALIZE_TABLE);
            stmt.execute(CREATE_RACE_PARTICIPANTS_TABLE);
            stmt.execute(CREATE_FRIENDSHIPS_TABLE);

            System.out.println("Database tables and sequences created successfully!");
        }
    }

    public static void initializeInMemoryDatabase(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create tables in the correct order (to respect foreign key constraints)
            // For in-memory databases, we need to be careful about foreign key constraints
            stmt.execute("DROP TABLE IF EXISTS race_participants");
            stmt.execute("DROP TABLE IF EXISTS race_balize");
            stmt.execute("DROP TABLE IF EXISTS event_subscribers");
            stmt.execute("DROP TABLE IF EXISTS friendships");
            stmt.execute("DROP TABLE IF EXISTS events");
            stmt.execute("DROP TABLE IF EXISTS persons");
            stmt.execute("DROP TABLE IF EXISTS ducks");
            stmt.execute("DROP TABLE IF EXISTS cards");
            stmt.execute("DROP TABLE IF EXISTS users");

            // Create sequence for event IDs
            stmt.execute("DROP SEQUENCE IF EXISTS events_seq");
            stmt.execute("CREATE SEQUENCE events_seq START WITH 1");

            stmt.execute(CREATE_USERS_TABLE);
            stmt.execute(CREATE_CARDS_TABLE);
            stmt.execute(CREATE_DUCKS_TABLE);
            stmt.execute(CREATE_PERSONS_TABLE);
            stmt.execute(CREATE_EVENTS_TABLE);
            stmt.execute(CREATE_EVENT_SUBSCRIBERS_TABLE);
            stmt.execute(CREATE_RACE_BALIZE_TABLE);
            stmt.execute(CREATE_RACE_PARTICIPANTS_TABLE);
            stmt.execute(CREATE_FRIENDSHIPS_TABLE);

            System.out.println("In-memory database tables and sequences created successfully!");
        }
    }

    public static void main(String[] args) {
        try {
            // Initialize the file-based database
            Connection conn = DatabaseConnection.getConnection();
            initializeDatabase(conn);
            conn.close();
            
            System.out.println("Database initialization completed!");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
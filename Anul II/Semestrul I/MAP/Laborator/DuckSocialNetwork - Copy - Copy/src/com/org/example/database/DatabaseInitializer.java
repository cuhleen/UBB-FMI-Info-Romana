package com.org.example.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseInitializer {

    /**
     * Metodă apelată din main().
     * Creează toate tabelele doar dacă nu există deja.
     */
    public static void initializeDatabase(Connection conn) throws SQLException {
        if (!usersTableExists(conn)) {
            System.out.println("[DB] Nu există tabele. Inițializez schema completă...");
            createAllTables(conn);
        } else {
            System.out.println("[DB] Tabelele există deja. Nu recreez schema.");
        }
    }

    /**
     * Verifică dacă tabela "users" există în schema PostgreSQL.
     */
    private static boolean usersTableExists(Connection conn) throws SQLException {
        String sql = """
            SELECT COUNT(*)
            FROM information_schema.tables
            WHERE table_schema = 'public'
              AND table_name = 'users'
        """;

        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            rs.next();
            return rs.getInt(1) > 0;
        }
    }

    /**
     * Creează toate tabelele necesare aplicației.
     * Schema este aliniată cu ce ai în PostgreSQL.
     */
    public static void createAllTables(Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {

            // ==========================
            // SEQUENCES
            // ==========================
            st.execute("CREATE SEQUENCE IF NOT EXISTS users_seq START 1;");
            st.execute("CREATE SEQUENCE IF NOT EXISTS ducks_seq START 1;");
            st.execute("CREATE SEQUENCE IF NOT EXISTS events_seq START 1;");

            // ==========================
            // USERS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
                    type VARCHAR(20) NOT NULL
                );
                """);

            // ==========================
            // DUCKS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS ducks (
                    id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
                    username VARCHAR(100) NOT NULL UNIQUE,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    tip VARCHAR(50) NOT NULL,
                    viteza DOUBLE PRECISION NOT NULL,
                    rezistenta DOUBLE PRECISION NOT NULL,
                    card_id BIGINT,
                    FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE SET NULL
                );
                """);

            // ==========================
            // PERSONS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS persons (
                    id BIGINT PRIMARY KEY REFERENCES users(id) ON DELETE CASCADE,
                    username VARCHAR(100) NOT NULL UNIQUE,
                    email VARCHAR(255) NOT NULL UNIQUE,
                    password VARCHAR(255) NOT NULL,
                    nume VARCHAR(100) NOT NULL,
                    prenume VARCHAR(100) NOT NULL,
                    data_nasterii DATE NOT NULL,
                    ocupatie VARCHAR(255),
                    nivel_empatie DOUBLE PRECISION NOT NULL
                );
                """);

            // ==========================
            // CARDS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS cards (
                    id BIGINT PRIMARY KEY,
                    nume_card VARCHAR(255) NOT NULL
                );
                """);

            // ==========================
            // FRIENDSHIPS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS friendships (
                    user_a BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                    user_b BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                    since TIMESTAMP NOT NULL,
                    PRIMARY KEY (user_a, user_b),
                    CHECK (user_a < user_b)
                );
                """);

            // ==========================
            // EVENTS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS events (
                    id BIGINT PRIMARY KEY DEFAULT nextval('events_seq'),
                    title VARCHAR(255) NOT NULL,
                    description TEXT,
                    creator_id BIGINT REFERENCES users(id) ON DELETE SET NULL,
                    created_at TIMESTAMP NOT NULL,
                    type VARCHAR(50) NOT NULL
                );
                """);

            // ==========================
            // EVENT SUBSCRIBERS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS event_subscribers (
                    event_id BIGINT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                    PRIMARY KEY (event_id, user_id)
                );
                """);

            // ==========================
            // RACE BALIZE
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS race_balize (
                    id BIGSERIAL PRIMARY KEY,
                    event_id BIGINT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                    distance DOUBLE PRECISION NOT NULL
                );
                """);

            // ==========================
            // RACE PARTICIPANTS
            // ==========================
            st.execute("""
                CREATE TABLE IF NOT EXISTS race_participants (
                    event_id BIGINT NOT NULL REFERENCES events(id) ON DELETE CASCADE,
                    duck_id BIGINT NOT NULL REFERENCES ducks(id) ON DELETE CASCADE,
                    PRIMARY KEY (event_id, duck_id)
                );
                """);

            System.out.println("[DB] Tabelele au fost create sau erau deja existente.");
        }
    }
}

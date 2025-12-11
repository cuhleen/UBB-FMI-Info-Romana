package com.org.example.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseManager {

    public static void initializeDatabaseIfNeeded() {
        try (Connection conn = DatabaseConnection.getConnection()) {

            // verificăm o tabelă critică: users
            if (!tableExists(conn, "users")) {
                System.out.println("[DB] Tabelele lipsesc. Le facem...");
                DatabaseInitializer.createAllTables(conn);
                System.out.println("[DB] Tabele create cu succes!");
            } else {
                System.out.println("[DB] Tabelele există deja. Nu fac nimic.");
            }

        } catch (SQLException e) {
            System.err.println("[DB ERROR] " + e.getMessage());
        }
    }

    private static boolean tableExists(Connection conn, String tableName) {
        try (ResultSet rs = conn.getMetaData().getTables(
                null, null, tableName, null)) {
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }
}

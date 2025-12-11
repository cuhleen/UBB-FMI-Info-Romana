package com.org.example.repo;

import com.org.example.users.Card;
import com.org.example.users.Duck;
import com.org.example.enums.TipRata;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoCardDB {

    private final String url;
    private final String user;
    private final String pass;

    public RepoCardDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // -------------------------
    // CREATE
    // -------------------------
    public void save(Card card) {
        String sql = "INSERT INTO cards (id, nume_card) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, card.getId());
            ps.setString(2, card.getNumeCard());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("[EROARE RepoCardDB.save] " + e.getMessage());
        }
    }

    // -------------------------
    // READ ALL
    // -------------------------
    public List<Card> findAll() {
        List<Card> list = new ArrayList<>();
        String sql = "SELECT * FROM cards";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Card c = new Card(
                        rs.getLong("id"),
                        rs.getString("nume_card")
                );

                loadMembers(conn, c);
                list.add(c);
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoCardDB.findAll] " + e.getMessage());
        }

        return list;
    }

    // -------------------------
    // READ BY ID
    // -------------------------
    public Optional<Card> findById(long id) {
        String sql = "SELECT * FROM cards WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Card c = new Card(
                        rs.getLong("id"),
                        rs.getString("nume_card")
                );

                loadMembers(conn, c);
                return Optional.of(c);
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoCardDB.findById] " + e.getMessage());
        }

        return Optional.empty();
    }

    // --------------------------------------
    // INTERNAL – load ducks in this card
    // --------------------------------------
    private void loadMembers(Connection conn, Card card) throws SQLException {
        String sql = "SELECT * FROM ducks WHERE card_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, card.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Duck d = new Duck(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        TipRata.valueOf(rs.getString("tip")),
                        rs.getDouble("viteza"),
                        rs.getDouble("rezistenta")
                );
                card.addMember(d);  // menținem logica ta de obiecte
            }
        }
    }

    // -------------------------
    // UPDATE
    // -------------------------
    public boolean update(Card card) {
        String sql = "UPDATE cards SET nume_card = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, card.getNumeCard());
            ps.setLong(2, card.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[EROARE RepoCardDB.update] " + e.getMessage());
            return false;
        }
    }

    // -------------------------
    // DELETE
    // -------------------------
    public boolean delete(long id) {

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            conn.setAutoCommit(false);

            // 1) Scoatem card_id la rațele din acest card
            try (PreparedStatement ps1 = conn.prepareStatement(
                    "UPDATE ducks SET card_id = NULL WHERE card_id = ?")) {
                ps1.setLong(1, id);
                ps1.executeUpdate();
            }

            // 2) Ștergem cardul efectiv
            try (PreparedStatement ps2 = conn.prepareStatement(
                    "DELETE FROM cards WHERE id = ?")) {
                ps2.setLong(1, id);
                int rows = ps2.executeUpdate();

                conn.commit();
                return rows > 0;
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoCardDB.delete] " + e.getMessage());
            return false;
        }
    }
}

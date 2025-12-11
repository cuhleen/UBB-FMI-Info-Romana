package com.org.example.repo;

import com.org.example.users.Duck;
import com.org.example.enums.TipRata;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoDuckDB {
    private final String url;
    private final String user;
    private final String pass;

    public RepoDuckDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // -----------------------------
    // CREATE
    // -----------------------------
    public void save(Duck duck) {
        String sql = "INSERT INTO ducks (id, username, email, password, tip, viteza, rezistenta) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, duck.getId());
            statement.setString(2, duck.getUsername());
            statement.setString(3, duck.getEmail());
            statement.setString(4, duck.getPassword());
            statement.setString(5, duck.getTip().name());
            statement.setDouble(6, duck.getViteza());
            statement.setDouble(7, duck.getRezistenta());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[EROARE RepoDuckDB.save] " + e.getMessage());
        }
    }

    // -----------------------------
    // READ
    // -----------------------------
    public List<Duck> findAll() {
        List<Duck> ducks = new ArrayList<>();
        String sql = "SELECT * FROM ducks";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

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
                ducks.add(d);
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoDuckDB.findAll] " + e.getMessage());
        }
        return ducks;
    }

    public Optional<Duck> findById(long id) {
        String sql = "SELECT * FROM ducks WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Duck d = new Duck(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        TipRata.valueOf(rs.getString("tip")),
                        rs.getDouble("viteza"),
                        rs.getDouble("rezistenta")
                );
                return Optional.of(d);
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoDuckDB.findById] " + e.getMessage());
        }
        return Optional.empty();
    }

    // -----------------------------
    // UPDATE
    // -----------------------------
    public boolean update(Duck duck) {
        String sql = "UPDATE ducks SET username = ?, email = ?, password = ?, tip = ?, viteza = ?, rezistenta = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, duck.getUsername());
            statement.setString(2, duck.getEmail());
            statement.setString(3, duck.getPassword());
            statement.setString(4, duck.getTip().name());
            statement.setDouble(5, duck.getViteza());
            statement.setDouble(6, duck.getRezistenta());
            statement.setLong(7, duck.getId());

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("[EROARE RepoDuckDB.update] " + e.getMessage());
            return false;
        }
    }

    // -----------------------------
    // DELETE
    // -----------------------------
    public boolean delete(long id) {
        String sql = "DELETE FROM ducks WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("[EROARE RepoDuckDB.delete] " + e.getMessage());
            return false;
        }
    }
}

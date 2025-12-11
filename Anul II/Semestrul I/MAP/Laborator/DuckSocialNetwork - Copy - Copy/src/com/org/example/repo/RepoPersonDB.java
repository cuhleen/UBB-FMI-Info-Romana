package com.org.example.repo;

import com.org.example.users.Person;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoPersonDB {
    private final String url;
    private final String user;
    private final String pass;

    public RepoPersonDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // -------------------------------------------------
    // CREATE
    // -------------------------------------------------
    public void save(Person person) {
        String sql = "INSERT INTO persons (id, username, email, password, nume, prenume, data_nasterii, ocupatie, nivel_empatie) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, person.getId());
            statement.setString(2, person.getUsername());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getPassword());
            statement.setString(5, person.getNume());
            statement.setString(6, person.getPrenume());
            statement.setDate(7, Date.valueOf(person.getDataNasterii()));
            statement.setString(8, person.getOcupatie());
            statement.setDouble(9, person.getNivelEmpatie());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[EROARE RepoPersonDB.save] " + e.getMessage());
        }
    }

    // -------------------------------------------------
    // READ
    // -------------------------------------------------
    public List<Person> findAll() {
        List<Person> persons = new ArrayList<>();
        String sql = "SELECT * FROM persons";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Person p = new Person(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getDate("data_nasterii").toLocalDate(),
                        rs.getString("ocupatie"),
                        rs.getDouble("nivel_empatie")
                );
                persons.add(p);
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoPersonDB.findAll] " + e.getMessage());
        }
        return persons;
    }

    public Optional<Person> findById(long id) {
        String sql = "SELECT * FROM persons WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Person p = new Person(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("nume"),
                        rs.getString("prenume"),
                        rs.getDate("data_nasterii").toLocalDate(),
                        rs.getString("ocupatie"),
                        rs.getDouble("nivel_empatie")
                );
                return Optional.of(p);
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoPersonDB.findById] " + e.getMessage());
        }

        return Optional.empty();
    }

    // -------------------------------------------------
    // UPDATE
    // -------------------------------------------------
    public boolean update(Person person) {
        String sql = "UPDATE persons SET username = ?, email = ?, password = ?, nume = ?, prenume = ?, data_nasterii = ?, ocupatie = ?, nivel_empatie = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, person.getUsername());
            statement.setString(2, person.getEmail());
            statement.setString(3, person.getPassword());
            statement.setString(4, person.getNume());
            statement.setString(5, person.getPrenume());
            statement.setDate(6, Date.valueOf(person.getDataNasterii()));
            statement.setString(7, person.getOcupatie());
            statement.setDouble(8, person.getNivelEmpatie());
            statement.setLong(9, person.getId());

            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("[EROARE RepoPersonDB.update] " + e.getMessage());
            return false;
        }
    }

    // -------------------------------------------------
    // DELETE
    // -------------------------------------------------
    public boolean delete(long id) {
        String sql = "DELETE FROM persons WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            int rows = statement.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("[EROARE RepoPersonDB.delete] " + e.getMessage());
            return false;
        }
    }
}

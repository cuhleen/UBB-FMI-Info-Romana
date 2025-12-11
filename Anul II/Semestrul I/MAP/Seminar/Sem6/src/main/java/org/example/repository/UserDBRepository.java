package org.example.repository;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.repository.dto.Page;
import org.example.repository.dto.Pageable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class UserDBRepository implements UserRepository{
    private final String url;
    private final String username;
    private final String password;


    @Override
    public Optional<User> findById(Integer id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                var user = getUser(resultSet);
                return Optional.of(user);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterable<User> findAll() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var statement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = statement.executeQuery();
            Set<User> users = new LinkedHashSet<>();
            while (resultSet.next()) {
                var user = getUser(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static User getUser(ResultSet resultSet) throws SQLException {
        var id = resultSet.getInt("id");
        var username = resultSet.getString("username");
        var createdAt = getCreatedAt(resultSet);
        var credits = resultSet.getInt("credits");
        var user = new User(username, createdAt, credits);
        user.setId(id);
        return user;
    }

    private static LocalDateTime getCreatedAt(ResultSet resultSet) throws SQLException {
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return createdAt == null ? null : createdAt.toLocalDateTime();
    }

    @Override
    public Optional<User> save(User entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var statement = connection.prepareStatement("INSERT INTO users (username, created_at, credits) VALUES (?, ?, ?)");
            statement.setString(1, entity.getUsername());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getCreatedAt()));
            statement.setInt(3, entity.getCredits());
            var rez = statement.executeUpdate();
            return rez == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> update(User entity) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var statement = connection.prepareStatement("UPDATE users SET username = ?, created_At = ?, credits = ? WHERE id = ?");
            statement.setString(1, entity.getUsername());
            statement.setTimestamp(2, Timestamp.valueOf(entity.getCreatedAt()));
            statement.setInt(3, entity.getCredits());
            statement.setInt(4, entity.getId());
            var rez = statement.executeUpdate();
            return rez == 0 ? Optional.empty() : Optional.of(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> delete(Integer id) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var user = findById(id);
            if (user.isEmpty()) {
                return Optional.empty();
            }
            var statement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            statement.setInt(1, id);
            var rez = statement.executeUpdate();
            return rez == 0 ? Optional.empty() : user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Page<User> findAllOnPage(Pageable pageable) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            var totalNumberOfUsers = count(connection);
            List<User> usersOnPage = totalNumberOfUsers > 0 ? findAllOnPage(connection, pageable) : List.of();
            return new Page<>(usersOnPage, totalNumberOfUsers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<User> findAllOnPage(Connection connection, Pageable pageable) {
        ResultSet resultSet;
        try (var statement = connection.prepareStatement("SELECT * FROM users limit ? offset ?")) {
            statement.setInt(1,pageable.getPageSize());
            statement.setInt(2,pageable.getPageSize()*pageable.getPageNUmber());
            resultSet = statement.executeQuery();
            List<User> users = new LinkedList<>();
            while (resultSet.next()) {
                var user = getUser(resultSet);
                users.add(user);
            }
            return users;
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int count(Connection connection){
        try (var statement = connection.prepareStatement("SELECT COUNT(*) AS count FROM users")) {
            var result =  statement.executeQuery();
            return result.next() ? result.getInt("count") : 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

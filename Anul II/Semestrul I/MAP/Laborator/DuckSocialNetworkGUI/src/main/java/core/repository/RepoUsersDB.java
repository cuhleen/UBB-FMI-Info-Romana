package core.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import core.model.UserAuthData;

public class RepoUsersDB {
    private final String url, user, pass;

    public RepoUsersDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public boolean delete(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("[EROARE RepoUsersDB.delete] " + e.getMessage());
            return false;
        }
    }

    public boolean existsById(long id) {
        String sql = "SELECT 1 FROM users WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoUsersDB.existsById] " + e.getMessage());
            return false;
        }
    }

    /** caută username fie în ducks, fie în persons (prefer ducks) */
    public Optional<String> findUsernameById(long id) {
        String sqlDuck = "SELECT username FROM ducks WHERE id = ?";
        String sqlPerson = "SELECT username FROM persons WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement psDuck = conn.prepareStatement(sqlDuck)) {
            psDuck.setLong(1, id);
            try (ResultSet rs = psDuck.executeQuery()) {
                if (rs.next()) return Optional.ofNullable(rs.getString("username"));
            }
            try (PreparedStatement psPerson = conn.prepareStatement(sqlPerson)) {
                psPerson.setLong(1, id);
                try (ResultSet rs2 = psPerson.executeQuery()) {
                    if (rs2.next()) return Optional.ofNullable(rs2.getString("username"));
                }
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoUsersDB.findUsernameById] " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<UserAuthData> findAuthDataByUsername(String username) {
        String sqlDuck = "SELECT id, password FROM ducks WHERE username = ?";
        String sqlPerson = "SELECT id, password FROM persons WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {

            try (PreparedStatement ps = conn.prepareStatement(sqlDuck)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return Optional.of(
                            new UserAuthData(rs.getLong("id"), rs.getString("password"))
                    );
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlPerson)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return Optional.of(
                            new UserAuthData(rs.getLong("id"), rs.getString("password"))
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("[RepoUsersDB.findAuthDataByUsername] " + e.getMessage());
        }
        return Optional.empty();
    }

    public static record UserBasic(long id, String username) {}

    public List<UserBasic> findAllUsersBasic() {
        List<UserBasic> list = new ArrayList<>();

        String sql = """
        SELECT id, username FROM ducks
        UNION
        SELECT id, username FROM persons
        """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new UserBasic(
                        rs.getLong("id"),
                        rs.getString("username")
                ));
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoUsersDB.findAllUsersBasic] " + e.getMessage());
        }

        return list;
    }
}

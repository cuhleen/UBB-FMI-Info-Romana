package core.repository;

import core.model.Friendship;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class RepoFriendshipDB {

    private final String url;
    private final String user;
    private final String pass;

    public RepoFriendshipDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // -------------------------------------------
    // CREATE friendship
    // -------------------------------------------
    public void save(long userA, long userB) {
        String sql = "INSERT INTO friendships (user_a, user_b, since) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, Math.min(userA, userB));
            ps.setLong(2, Math.max(userA, userB));
            ps.setTimestamp(3, Timestamp.from(Instant.now()));

            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendshipDB.save] " + e.getMessage());
        }
    }

    // -------------------------------------------
    // READ all
    // -------------------------------------------
    public List<long[]> findAll() {
        List<long[]> list = new ArrayList<>();
        String sql = "SELECT * FROM friendships";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                long a = rs.getLong("user_a");
                long b = rs.getLong("user_b");
                list.add(new long[]{a, b});
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendshipDB.findAll] " + e.getMessage());
        }
        return list;
    }


    // -------------------------------------------
    // DELETE specific friendship
    // -------------------------------------------
    public boolean delete(long userA, long userB) {
        String sql = "DELETE FROM friendships WHERE user_a = ? AND user_b = ?";

        long ua = Math.min(userA, userB);
        long ub = Math.max(userA, userB);

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, ua);
            ps.setLong(2, ub);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendshipDB.delete] " + e.getMessage());
            return false;
        }
    }

    // -------------------------------------------
    // DELETE all friendships for a user
    // -------------------------------------------
    public int deleteAllForUser(long userId) {
        String sql = "DELETE FROM friendships WHERE user_a = ? OR user_b = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setLong(2, userId);

            return ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendshipDB.deleteAllForUser] " + e.getMessage());
            return 0;
        }
    }
}

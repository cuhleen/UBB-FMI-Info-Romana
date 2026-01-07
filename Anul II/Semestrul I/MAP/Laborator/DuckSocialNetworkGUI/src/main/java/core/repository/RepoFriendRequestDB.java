package core.repository;

import core.model.FriendRequest;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoFriendRequestDB {

    private final String url;
    private final String user;
    private final String pass;

    public RepoFriendRequestDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // -----------------------------
    // SAVE
    // -----------------------------
    public FriendRequest save(FriendRequest fr) {
        String sql = """
            INSERT INTO friend_requests (from_user, to_user, status, created_at)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, fr.getFromUserId());
            ps.setLong(2, fr.getToUserId());
            ps.setString(3, fr.getStatus());
            ps.setTimestamp(4, Timestamp.valueOf(fr.getCreatedAt()));

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return new FriendRequest(
                            keys.getLong(1),
                            fr.getFromUserId(),
                            fr.getToUserId(),
                            fr.getStatus(),
                            fr.getCreatedAt()
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendRequestDB.save] " + e.getMessage());
        }
        return null;
    }

    // -----------------------------
    // FIND pending requests for user
    // -----------------------------
    public List<FriendRequest> findPendingByToUser(long userId) {
        List<FriendRequest> list = new ArrayList<>();
        String sql = """
            SELECT * FROM friend_requests
            WHERE to_user = ? AND status = 'PENDING'
            ORDER BY created_at
        """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendRequestDB.findPendingByToUser] " + e.getMessage());
        }
        return list;
    }

    public Optional<FriendRequest> findById(long id) {
        String sql = "SELECT * FROM friend_requests WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendRequestDB.findById] " + e.getMessage());
        }
        return Optional.empty();
    }


    // -----------------------------
    // FIND between two users (any direction)
    // -----------------------------
    public Optional<FriendRequest> findBetweenUsers(long userA, long userB) {
        String sql = """
            SELECT * FROM friend_requests
            WHERE (from_user = ? AND to_user = ?)
               OR (from_user = ? AND to_user = ?)
            ORDER BY created_at DESC
            LIMIT 1
        """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userA);
            ps.setLong(2, userB);
            ps.setLong(3, userB);
            ps.setLong(4, userA);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendRequestDB.findBetweenUsers] " + e.getMessage());
        }
        return Optional.empty();
    }

    // -----------------------------
    // UPDATE status
    // -----------------------------
    public void updateStatus(long requestId, String newStatus) {
        String sql = "UPDATE friend_requests SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newStatus);
            ps.setLong(2, requestId);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("[EROARE RepoFriendRequestDB.updateStatus] " + e.getMessage());
        }
    }

    // -----------------------------
    // MAPPER
    // -----------------------------
    private FriendRequest mapRow(ResultSet rs) throws SQLException {
        return new FriendRequest(
                rs.getLong("id"),
                rs.getLong("from_user"),
                rs.getLong("to_user"),
                rs.getString("status"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    // -----------------------------
    // FIND ALL
    // -----------------------------

    public List<FriendRequest> findAllByUser(long userId) {
        List<FriendRequest> list = new ArrayList<>();

        String sql = """
        SELECT * FROM friend_requests
        WHERE from_user_id = ? OR to_user_id = ?
        """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ps.setLong(2, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("[RepoFriendRequestDB.findAllByUser] " + e.getMessage());
        }

        return list;
    }

}

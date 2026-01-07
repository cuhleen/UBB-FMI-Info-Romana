package core.repository;

import core.model.Notification;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RepoNotificationDB {

    private final String url;
    private final String user;
    private final String pass;

    public RepoNotificationDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

    // -------------------------------------------------
    // ID generator
    // -------------------------------------------------
    public long generateId() {
        String sql = "SELECT nextval('notifications_id_seq')";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            rs.next();
            return rs.getLong(1);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------------------------------------
    // SAVE
    // -------------------------------------------------
    public void save(Notification n) {
        String sql = """
            INSERT INTO notifications(id, user_id, content, created_at, read)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, n.getId());
            ps.setLong(2, n.getUserId());
            ps.setString(3, n.getContent());
            ps.setTimestamp(4, Timestamp.valueOf(n.getCreatedAt()));
            ps.setBoolean(5, n.isRead());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // -------------------------------------------------
    // FIND BY USER
    // -------------------------------------------------
    public List<Notification> findByUser(long userId) {
        String sql = """
            SELECT * FROM notifications
            WHERE user_id = ?
            ORDER BY created_at DESC
        """;

        List<Notification> list = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Notification(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getBoolean("read")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    // -------------------------------------------------
    // MARK AS READ
    // -------------------------------------------------
    public void markRead(long notifId) {
        String sql = "UPDATE notifications SET read = true WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, notifId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

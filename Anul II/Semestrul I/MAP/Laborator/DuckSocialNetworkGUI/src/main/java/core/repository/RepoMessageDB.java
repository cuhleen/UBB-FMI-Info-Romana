package core.repository;

import core.model.Message;
import core.model.ReplyMessage;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepoMessageDB {
    private final String url;
    private final String user;
    private final String pass;

    public RepoMessageDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // save (daca id==0 -> foloseste DEFAULT din DB; altfel insereaza id explicit)
    public Message save(Message message) {
        String sqlWithId = "INSERT INTO messages (id, sender_id, receiver_id, content, timestamp, reply_to) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlNoId = "INSERT INTO messages (sender_id, receiver_id, content, timestamp, reply_to) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            if (message.getId() > 0) {
                try (PreparedStatement ps = conn.prepareStatement(sqlWithId)) {
                    ps.setLong(1, message.getId());
                    ps.setLong(2, message.getSenderId());
                    ps.setLong(3, message.getFirstReceiverId());
                    ps.setString(4, message.getContent());
                    ps.setTimestamp(5, Timestamp.valueOf(message.getTimestamp()));
                    if (message.getReplyToId() != null) ps.setLong(6, message.getReplyToId());
                    else ps.setNull(6, Types.BIGINT);
                    ps.executeUpdate();
                    return message;
                }
            } else {
                try (PreparedStatement ps = conn.prepareStatement(sqlNoId, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, message.getSenderId());
                    ps.setLong(2, message.getFirstReceiverId());
                    ps.setString(3, message.getContent());
                    ps.setTimestamp(4, Timestamp.valueOf(message.getTimestamp()));
                    if (message.getReplyToId() != null) ps.setLong(5, message.getReplyToId());
                    else ps.setNull(5, Types.BIGINT);
                    ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) {
                        if (keys.next()) {
                            long generatedId = keys.getLong(1);
                            return new Message(generatedId, message.getSenderId(), message.getFirstReceiverId(),
                                    message.getContent(), message.getTimestamp(), message.getReplyToId());
                        } else {
                            return message;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoMessageDB.save] " + e.getMessage());
            return null;
        }
    }

    public Optional<Message> findById(long id) {
        String sql = "SELECT * FROM messages WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRowToMessage(rs));
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoMessageDB.findById] " + e.getMessage());
        }
        return Optional.empty();
    }

    public List<Message> findByReceiver(long receiverId) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE receiver_id = ? ORDER BY timestamp ASC";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, receiverId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowToMessage(rs));
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoMessageDB.findByReceiver] " + e.getMessage());
        }
        return list;
    }

    /** Conversație între 2 utilizatori (toate mesajele între A și B) */
    public List<Message> findConversation(long userA, long userB) {
        List<Message> list = new ArrayList<>();
        String sql = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp ASC";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, userA);
            ps.setLong(2, userB);
            ps.setLong(3, userB);
            ps.setLong(4, userA);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRowToMessage(rs));
            }
        } catch (SQLException e) {
            System.err.println("[EROARE RepoMessageDB.findConversation] " + e.getMessage());
        }
        return list;
    }

    private Message mapRowToMessage(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        long senderId = rs.getLong("sender_id");
        long receiverId = rs.getLong("receiver_id"); // un singur receiver în DB
        String content = rs.getString("content");

        Timestamp ts = rs.getTimestamp("timestamp");
        LocalDateTime ldt = ts.toLocalDateTime();

        List<Long> receivers = new ArrayList<>();
        receivers.add(receiverId);

        long replyTo = rs.getLong("reply_to");
        boolean replyIsNull = rs.wasNull();

        if (!replyIsNull) {
            return new ReplyMessage(
                    id,
                    senderId,
                    receivers,
                    content,
                    ldt,
                    replyTo
            );
        } else {
            return new Message(
                    id,
                    senderId,
                    receivers,
                    content,
                    ldt,
                    null
            );
        }
    }

}

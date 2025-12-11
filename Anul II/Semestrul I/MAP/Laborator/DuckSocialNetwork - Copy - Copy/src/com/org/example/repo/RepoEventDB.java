package com.org.example.repo;

import com.org.example.enums.TipRata;
import com.org.example.model.RaceEvent;
import com.org.example.users.User;
import com.org.example.users.Duck;

import java.sql.*;
import java.time.Instant;
import java.util.*;

public class RepoEventDB {

    private final String url;
    private final String user;
    private final String pass;

    public RepoEventDB(String url, String user, String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    // ============================================================
    // GENERATE ID
    // ============================================================
    public long generateId() {
        String sql = "SELECT nextval('events_seq')";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) return rs.getLong(1);
        } catch (SQLException e) {
            System.err.println("[RepoEventDB.generateId ERROR] " + e.getMessage());
        }
        return -1;
    }

    // ============================================================
    // SAVE RACE EVENT
    // ============================================================
    public void saveRaceEvent(RaceEvent ev) {
        String sqlEvent =
                "INSERT INTO events (id, title, description, creator_id, created_at, type) " +
                        "VALUES (?, ?, ?, ?, ?, 'RACE')";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {

            // 1) events
            try (PreparedStatement st = connection.prepareStatement(sqlEvent)) {
                st.setLong(1, ev.getId());
                st.setString(2, ev.getTitle());
                st.setString(3, ev.getDescription());
                Long x = ev.getCreator() == null ? null : ev.getCreator().getId();
                st.setLong(4, x);
                st.setTimestamp(5, Timestamp.from(Instant.now()));
                st.executeUpdate();
            }

            // 2) subscribers
            String sqlSub = "INSERT INTO event_subscribers (event_id, user_id) VALUES (?, ?)";
            try (PreparedStatement st = connection.prepareStatement(sqlSub)) {
                for (User u : ev.getSubscribers()) {
                    st.setLong(1, ev.getId());
                    st.setLong(2, u.getId());
                    st.addBatch();
                }
                st.executeBatch();
            }

            // 3) balize
            String sqlB = "INSERT INTO race_balize (event_id, distance) VALUES (?, ?)";
            try (PreparedStatement st = connection.prepareStatement(sqlB)) {
                for (Double d : ev.getBalize()) {
                    st.setLong(1, ev.getId());
                    st.setDouble(2, d);
                    st.addBatch();
                }
                st.executeBatch();
            }

            // 4) participants
            String sqlP = "INSERT INTO race_participants (event_id, duck_id) VALUES (?, ?)";
            try (PreparedStatement st = connection.prepareStatement(sqlP)) {
                for (Duck d : ev.getParticipants()) {
                    st.setLong(1, ev.getId());
                    st.setLong(2, d.getId());
                    st.addBatch();
                }
                st.executeBatch();
            }

        } catch (SQLException e) {
            System.err.println("[RepoEventDB.saveRaceEvent ERROR] " + e.getMessage());
        }
    }

    // ============================================================
    // UPDATE RACE EVENT (title/description)
    // ============================================================
    public void updateRaceEvent(RaceEvent ev) {
        String sql = "UPDATE events SET title=?, description=? WHERE id=?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, ev.getTitle());
            st.setString(2, ev.getDescription());
            st.setLong(3, ev.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[RepoEventDB.updateRaceEvent ERROR] " + e.getMessage());
        }
    }

    // ============================================================
    // FIND ONE
    // ============================================================
    public Optional<RaceEvent> findRaceEvent(long id) {
        String sqlEv = "SELECT * FROM events WHERE id = ? AND type = 'RACE'";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {

            RaceEvent event = null;

            try (PreparedStatement st = connection.prepareStatement(sqlEv)) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();

                if (!rs.next()) return Optional.empty();

                long creatorId = rs.getLong("creator_id");

                User creator = loadUser(connection, creatorId);

                event = new RaceEvent(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        creator
                );
            }

            if (event == null) return Optional.empty();

            // balize
            String sqlB = "SELECT distance FROM race_balize WHERE event_id = ?";
            try (PreparedStatement st = connection.prepareStatement(sqlB)) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) event.addBalize(rs.getDouble("distance"));
            }

            // subscribers
            String sqlS = "SELECT user_id FROM event_subscribers WHERE event_id = ?";
            try (PreparedStatement st = connection.prepareStatement(sqlS)) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    User sub = loadUser(connection, rs.getLong("user_id"));
                    if (sub != null) event.subscribe(sub);
                }
            }

            // participants
            String sqlP = "SELECT duck_id FROM race_participants WHERE event_id = ?";
            try (PreparedStatement st = connection.prepareStatement(sqlP)) {
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                    Duck d = loadDuck(connection, rs.getLong("duck_id"));
                    if (d != null) event.addParticipant(d);
                }
            }

            return Optional.of(event);

        } catch (SQLException e) {
            System.err.println("[RepoEventDB.findRaceEvent ERROR] " + e.getMessage());
            return Optional.empty();
        }
    }

    // ============================================================
    // FIND ALL RACE EVENTS
    // ============================================================
    public List<RaceEvent> findAllRaceEvents() {
        List<RaceEvent> list = new ArrayList<>();
        String sql = "SELECT id FROM events WHERE type='RACE'";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("id");
                findRaceEvent(id).ifPresent(list::add);
            }

        } catch (SQLException e) {
            System.err.println("[RepoEventDB.findAllRaceEvents ERROR] " + e.getMessage());
        }

        return list;
    }

    // ============================================================
    // DELETE
    // ============================================================
    public boolean deleteEvent(long id) {
        try (Connection connection = DriverManager.getConnection(url, user, pass)) {

            connection.prepareStatement("DELETE FROM race_participants WHERE event_id = " + id).executeUpdate();
            connection.prepareStatement("DELETE FROM race_balize WHERE event_id = " + id).executeUpdate();
            connection.prepareStatement("DELETE FROM event_subscribers WHERE event_id = " + id).executeUpdate();
            connection.prepareStatement("DELETE FROM events WHERE id = " + id).executeUpdate();

            return true;

        } catch (SQLException e) {
            System.err.println("[RepoEventDB.deleteEvent ERROR] " + e.getMessage());
            return false;
        }
    }

    // ============================================================
    // SUBSCRIBERS HELPERS
    // ============================================================
    public List<Long> findSubscribersForEvent(long eventId) {
        List<Long> list = new ArrayList<>();
        String sql = "SELECT user_id FROM event_subscribers WHERE event_id=?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, eventId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) list.add(rs.getLong("user_id"));
        } catch (SQLException e) {
            System.err.println("[RepoEventDB.findSubscribersForEvent ERROR] " + e.getMessage());
        }
        return list;
    }

    public void addSubscriber(long eventId, long userId) {
        String sql = "INSERT INTO event_subscribers (event_id, user_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, eventId);
            st.setLong(2, userId);
            st.executeUpdate();
        } catch (SQLException e) {
            // duplicate entry is ok → ignore
        }
    }

    public void removeSubscriber(long eventId, long userId) {
        String sql = "DELETE FROM event_subscribers WHERE event_id=? AND user_id=?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, eventId);
            st.setLong(2, userId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[RepoEventDB.removeSubscriber ERROR] " + e.getMessage());
        }
    }

    // ============================================================
    // PARTICIPANTS
    // ============================================================
    public List<Duck> findParticipantsForEvent(long eventId) {
        List<Duck> list = new ArrayList<>();
        String sql = "SELECT duck_id FROM race_participants WHERE event_id=?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, eventId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Duck d = loadDuck(connection, rs.getLong("duck_id"));
                if (d != null) list.add(d);
            }
        } catch (SQLException e) {
            System.err.println("[RepoEventDB.findParticipantsForEvent ERROR] " + e.getMessage());
        }
        return list;
    }

    public void addParticipant(long eventId, long duckId) {
        String sql = "INSERT INTO race_participants (event_id, duck_id) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, eventId);
            st.setLong(2, duckId);
            st.executeUpdate();
        } catch (SQLException e) {
            // duplicate entry is ok
        }
    }

    public void removeParticipant(long eventId, long duckId) {
        String sql = "DELETE FROM race_participants WHERE event_id=? AND duck_id=?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, eventId);
            st.setLong(2, duckId);
            st.executeUpdate();
        } catch (SQLException e) {
            System.err.println("[RepoEventDB.removeParticipant ERROR] " + e.getMessage());
        }
    }

    // ============================================================
    // LOAD USER
    // ============================================================
    private User loadUser(Connection connection, long id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) return null;

            return new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
            ) {
                @Override public void performPeriodicActions() {}
            };
        }
    }

    private Duck loadDuck(Connection connection, long id) throws SQLException {
        String sql = "SELECT * FROM ducks WHERE id = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (!rs.next()) return null;

            return new Duck(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    TipRata.valueOf(rs.getString("tip")),
                    rs.getDouble("viteza"),
                    rs.getDouble("rezistenta")
            );
        }
    }
}

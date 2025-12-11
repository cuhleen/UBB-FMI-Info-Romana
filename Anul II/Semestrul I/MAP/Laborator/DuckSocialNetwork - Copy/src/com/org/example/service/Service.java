package com.org.example.service;

import com.org.example.model.Event;
import com.org.example.model.Message;
import com.org.example.repo.RepoDuck;
import com.org.example.repo.RepoPerson;
import com.org.example.users.Duck;
import com.org.example.users.Person;
import com.org.example.users.User;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class Service {
    private final RepoDuck repoDuck;
    private final RepoPerson repoPerson;

    private final Map<Long, User> usersById = new HashMap<>();
    private final Map<String, User> usersByUsername = new HashMap<>();
    private final Map<Long, Event> events = new HashMap<>();
    private final List<String> activityLog = new ArrayList<>();

    public Service(RepoDuck repoDuck, RepoPerson repoPerson) {
        this.repoDuck = repoDuck;
        this.repoPerson = repoPerson;
    }

    // users Management

    public void addUser(User u) {
        usersById.put(u.getId(), u);
        usersByUsername.put(u.getUsername(), u);
        log("User added: " + u);
    }

    public void removeUser(User u) {
        usersById.remove(u.getId());
        usersByUsername.remove(u.getUsername());
        for (User friend : new ArrayList<>(u.getFriends())) {
            u.removeFriend(friend);
        }
        log("User removed: " + u);
    }

    public Optional<User> findUserById(long id) {
        return Optional.ofNullable(usersById.get(id));
    }

    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    public List<User> listAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    // search
    public List<User> searchUsersByName(String q) {
        String lower = q.toLowerCase();
        return usersById.values().stream().filter(u -> {
            if (u instanceof Person p) {
                return (p.getFullName().toLowerCase().contains(lower) || p.getUsername().toLowerCase().contains(lower));
            } else {
                return u.getUsername().toLowerCase().contains(lower);
            }
        }).collect(Collectors.toList());
    }

    // messages
    public boolean deleteMessage(User owner, long messageId) {
        List<Message> inbox = owner.getInbox();
        Optional<Message> found = inbox.stream().filter(m -> m.getId() == messageId).findFirst();
        if (found.isPresent()) {
            inbox.remove(found.get());
            log("Message " + messageId + " deleted from " + owner.getUsername());
            return true;
        }
        return false;
    }

    // events
    public void addEvent(Event e) {
        events.put(e.getId(), e);
        log("Event added: " + e);
    }

    public Optional<Event> findEventById(long id) {
        return Optional.ofNullable(events.get(id));
    }

    public List<Event> listEvents() {
        return new ArrayList<>(events.values());
    }

    // friendships
    public boolean addFriendship(String user1, String user2) {
        User u1 = usersByUsername.get(user1.trim());
        User u2 = usersByUsername.get(user2.trim());
        if (u1 == null || u2 == null) {
            log("Eroare: unul dintre utilizatori nu există -> " + user1 + ", " + user2);
            return false;
        }
        boolean added = u1.addFriend(u2);
        if (added) log("Prietenie adăugată: " + user1 + " - " + user2);
        return added;
    }

    public boolean removeFriendship(String user1, String user2) {
        User u1 = usersByUsername.get(user1);
        User u2 = usersByUsername.get(user2);
        if (u1 == null || u2 == null) {
            log("Eroare: unul dintre utilizatori nu există");
            return false;
        }
        boolean removed = u1.removeFriend(u2);
        if (removed) log("Prietenie ștearsă: " + user1 + " - " + user2);
        return removed;
    }

    // community
    public int getNumarComunitati() {
        Set<User> vizitat = new HashSet<>();
        int count = 0;
        for (User u : usersById.values()) {
            if (!vizitat.contains(u)) {
                exploreComponent(u, vizitat);
                count++;
            }
        }
        log("Număr comunități: " + count);
        return count;
    }

    private void exploreComponent(User start, Set<User> vizitat) {
        Queue<User> q = new LinkedList<>();
        q.add(start);
        vizitat.add(start);
        while (!q.isEmpty()) {
            User cur = q.poll();
            for (User friend : cur.getFriends()) {
                if (vizitat.add(friend)) {
                    q.add(friend);
                }
            }
        }
    }

    public List<User> getCeaMaiSociabilaComunitate() {
        Set<User> vizitat = new HashSet<>();
        List<User> bestComponent = new ArrayList<>();
        int bestDiametru = -1;

        for (User u : usersById.values()) {
            if (!vizitat.contains(u)) {
                List<User> component = getComponentMembers(u, vizitat);
                int diametru = calculeazaDiametru(component);
                if (diametru > bestDiametru) {
                    bestDiametru = diametru;
                    bestComponent = component;
                }
            }
        }
        log("Cea mai sociabilă comunitate are diametrul: " + bestDiametru);
        return bestComponent;
    }

    private List<User> getComponentMembers(User start, Set<User> vizitat) {
        List<User> component = new ArrayList<>();
        Queue<User> q = new LinkedList<>();
        q.add(start);
        vizitat.add(start);
        while (!q.isEmpty()) {
            User cur = q.poll();
            component.add(cur);
            for (User f : cur.getFriends()) {
                if (vizitat.add(f)) {
                    q.add(f);
                }
            }
        }
        return component;
    }

    private int calculeazaDiametru(List<User> component) {
        int maxDist = 0;
        for (User start : component) {
            Set<User> vizitat = new HashSet<>();
            int dist = dfsMaxDist(start, vizitat, 0);
            if (dist > maxDist) maxDist = dist;
        }
        return maxDist;
    }

    private int dfsMaxDist(User current, Set<User> vizitat, int depth) {
        vizitat.add(current);
        int max = depth;
        for (User friend : current.getFriends()) {
            if (!vizitat.contains(friend)) {
                int newDepth = dfsMaxDist(friend, vizitat, depth + 1);
                if (newDepth > max) max = newDepth;
            }
        }
        return max;
    }

    // logging
    private void log(String line) {
        String s = Instant.now() + " - " + line;
        activityLog.add(s);
        System.out.println("[Service Log] " + s);
    }

    public List<String> getActivityLog() {
        return Collections.unmodifiableList(activityLog);
    }
}

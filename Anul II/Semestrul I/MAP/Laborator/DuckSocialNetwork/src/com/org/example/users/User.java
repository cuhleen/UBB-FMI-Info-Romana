package com.org.example.users;

import com.org.example.id_generators.MessageIdGenerator;
import com.org.example.model.Event;
import com.org.example.model.Message;

import java.time.Instant;
import java.util.*;

public abstract class User {
    private final long id;
    private String username;
    private String email;
    private String password;

    private final Set<User> friends = new HashSet<>();
    private final List<Message> inbox = new ArrayList<>();
    private final List<Event> subscribedEvents = new ArrayList<>();
    private boolean loggedIn = false;

    public User(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    // autentificare

    public boolean login(String password) {
        if (this.password.equals(password)) {
            loggedIn = true;
            log(username + " logged in");
            return true;
        }
        return false;
    }

    public void logout() {
        loggedIn = false;
        log(username + " logged out");
    }

    // mesaje

    public void sendMessage(User receiver, String content) {
        Message m = new Message(
                MessageIdGenerator.nextId(),
                this,
                receiver,
                content,
                Instant.now()
        );
        receiver.receiveMessage(m);
        log("Message sent from " + username + " to " + receiver.username);
    }

    public void receiveMessage(Message m) {
        inbox.add(m);
        log(username + " received message from " + m.getSender().getUsername());
    }

    public List<Message> getInbox() {
        return Collections.unmodifiableList(inbox);
    }

    // prieteni

    public boolean addFriend(User other) {
        boolean changed = friends.add(other);
        other.friends.add(this);
        if (changed) log(username + " and " + other.username + " are now friends");
        return changed;
    }

    public boolean removeFriend(User other) {
        boolean changed = friends.remove(other);
        other.friends.remove(this);
        if (changed) log(username + " and " + other.username + " are no longer friends");
        return changed;
    }

    public Set<User> getFriends() {
        return Collections.unmodifiableSet(friends);
    }

    // evenimente

    public void subscribeToEvent(Event e) {
        if (!subscribedEvents.contains(e)) {
            subscribedEvents.add(e);
            e.subscribe(this);
        }
    }

    public void unsubscribeFromEvent(Event e) {
        subscribedEvents.remove(e);
        e.unsubscribe(this);
    }

    // logging

    protected void log(String msg) {
        System.out.println("[User Log] " + Instant.now() + " - " + msg);
    }

    // abstract

    public abstract void performPeriodicActions();

    @Override
    public String toString() {
        return String.format(
                "%s{id=%d, username='%s', email='%s'}",
                this.getClass().getSimpleName(), id, username, email
        );
    }
}

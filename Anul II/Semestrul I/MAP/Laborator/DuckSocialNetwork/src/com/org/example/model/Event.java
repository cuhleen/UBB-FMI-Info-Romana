package com.org.example.model;

import com.org.example.id_generators.MessageIdGenerator;
import com.org.example.users.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Event {
    protected final long id;
    protected final String title;
    protected final String description;
    protected final User creator;
    protected final Instant createdAt;
    protected final List<User> subscribers = new ArrayList<>();
    protected final List<String> eventLog = new ArrayList<>();

    public Event(long id, String title, String description, User creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.createdAt = Instant.now();
    }

    public long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public User getCreator() { return creator; }
    public List<User> getSubscribers() { return Collections.unmodifiableList(subscribers); }
    public List<String> getEventLog() { return Collections.unmodifiableList(eventLog); }

    // observer methods
    public void subscribe(User u) {
        if (!subscribers.contains(u)) {
            subscribers.add(u);
            log("User " + u.getUsername() + " subscribed");
        }
    }

    public void unsubscribe(User u) {
        if (subscribers.remove(u)) {
            log("User " + u.getUsername() + " unsubscribed");
        }
    }

    protected void notifySubscribers(String notification) {
        log("Notifying subscribers: " + notification);
        for (User u : subscribers) {
            u.receiveMessage(
                    new Message(
                            MessageIdGenerator.nextId(),
                            creator,
                            u,
                            "[Event: " + title + "] " + notification,
                            Instant.now()
                    )
            );
        }
    }

    // logging
    public void log(String line) {
        eventLog.add(Instant.now() + " - " + line);
    }

    // abstract
    public abstract void startEvent();

    @Override
    public String toString() {
        return String.format("Event{id=%d, title='%s', creator=%s}", id, title, creator.getUsername());
    }
}

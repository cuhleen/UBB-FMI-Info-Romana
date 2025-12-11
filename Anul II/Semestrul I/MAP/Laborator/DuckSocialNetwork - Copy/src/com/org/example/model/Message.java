package com.org.example.model;

import com.org.example.users.User;

import java.time.Instant;

public class Message {
    private final long id;
    private final User sender;
    private final User receiver;
    private final String content;
    private final Instant timestamp;

    public Message(long id, User sender, User receiver, String content, Instant timestamp) {
        this.id = id; this.sender = sender; this.receiver = receiver; this.content = content; this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public User getSender() { return sender; }
    public User getReceiver() { return receiver; }
    public String getContent() { return content; }
    public Instant getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        return String.format("[%s] %s -> %s: %s", timestamp, sender.getUsername(), receiver.getUsername(), content);
    }
}

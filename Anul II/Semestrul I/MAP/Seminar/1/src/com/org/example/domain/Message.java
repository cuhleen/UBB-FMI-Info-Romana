package com.org.example.domain;

import utils.Constants;

import java.time.LocalDateTime;

public class Message {

    private final String id;
    private final String subject;
    private final String message;
    private final String from;
    private final String to;
    private final LocalDateTime date;

    public Message(String id, String subject, String message, String from, String to, LocalDateTime date) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date.format(Constants.DATE_TIME_FORMATTER) +
                '}';
    }
}

package com.org.tasks.domain;

import static utils.Constants.*;
import java.time.LocalDateTime;

public class Message {
    private final String id;
    private final String subject;
    private final String message;
    private final String form;
    private final String to;
    private final LocalDateTime date;

    public Message(String id, String subject, String message, String form, String to, LocalDateTime date) {
        this.id = id;
        this.subject = subject;
        this.message = message;
        this.form = form;
        this.to = to;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", form='" + form + '\'' +
                ", to='" + to + '\'' +
                ", date='" + date.format(DATE_TIME_FORMATTER) + '\'' +
                '}';
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getForm() {
        return form;
    }

    public String getTo() {
        return to;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

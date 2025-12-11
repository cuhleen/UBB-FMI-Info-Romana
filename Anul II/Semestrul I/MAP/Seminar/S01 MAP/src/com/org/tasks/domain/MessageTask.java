package com.org.tasks.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {
    private Message message;

    public MessageTask(String taskId, String descriere) {
        super(taskId, descriere);
    }

    public MessageTask(String taskId, String descriere, Message message) {
        super(taskId, descriere);
        this.message = message;
    }

    @Override
    public void execute() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "MessageTask{" +
                super.toString() +
                ", message='" + message + '\'' +
                '}';
    }
}

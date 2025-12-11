package com.org.example.domain;

import java.time.LocalDateTime;

public class MessageTask extends Task{

    private Message message;

    public MessageTask(String taskID, String descriere, Message message) {
        super(taskID, descriere);
        this.message = message;
    }

    @Override
    public String toString() {
        return "MessageTask{" +
                super.toString() +
                "message='" + message + '\'' +
                '}';
    }

    @Override
    public void execute() {
        System.out.println(this);
    }
}

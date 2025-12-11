package com.org.example;

import com.org.example.domain.Message;
import com.org.example.domain.MessageTask;

import java.time.LocalDateTime;

class Main {

    private static MessageTask[] createMessageArray(){
        var m = new Message("1", "Feedback", "Ai obtinut 9.60", "Gigi", "Ana", LocalDateTime.now().plusYears(2));
        MessageTask task1 = new MessageTask("1", "descriere1", m);
        MessageTask task2 = new MessageTask("2", "descriere2", m);
        MessageTask task3 = new MessageTask("3", "descriere3", m);

        return new MessageTask[]{task1, task2, task3};
    }

    static void main() {
        var messages = createMessageArray();
        for (MessageTask message : messages) {
            message.execute();
        }
    }
}
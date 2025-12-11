

package com.org.tasks;

import com.org.tasks.domain.Message;
import com.org.tasks.domain.MessageTask;
import com.org.tasks.factory.Strategy;
import com.org.tasks.runner.DelayTaskRunner;
import com.org.tasks.runner.PrinterTaskRunner;
import com.org.tasks.runner.StrategyTaskRunner;
import com.org.tasks.runner.TaskRunner;

import java.time.LocalDateTime;

public class Main
{
    private static MessageTask[] getMessages() {
        MessageTask task1 = new MessageTask("123", "desc", new Message("ID0001", "Subject", "mesaj", "eu", "tu", LocalDateTime.now()));
        MessageTask task2 = new MessageTask("222", "desc", new Message("ID0002", "Subject", "mesaj", "eu", "tu", LocalDateTime.now()));
        MessageTask task3 = new MessageTask("333", "desc", new Message("ID0003", "Subject", "mesaj", "eu", "tu", LocalDateTime.now()));
        MessageTask task4 = new MessageTask("444", "desc", new Message("ID0004", "Subject", "mesaj", "eu", "tu", LocalDateTime.now()));
        MessageTask task5 = new MessageTask("555", "desc", new Message("ID0005", "Subject", "mesaj", "eu", "tu", LocalDateTime.now()));

        return new MessageTask[] {task1, task2, task3, task4, task5, task1};
    }

    public static void main(String[] args)
    {
        MessageTask[] tasks = getMessages();

        TaskRunner taskRunner = new StrategyTaskRunner(Strategy.LIFO);

        for(MessageTask task : tasks)
        {
            taskRunner.addTask(task);
        }

        //taskRunner.executeAll();

        //TaskRunner printerTaskRunner = new PrinterTaskRunner(taskRunner);
        TaskRunner printerTaskRunner = new DelayTaskRunner(taskRunner);

        printerTaskRunner.executeAll();

    }
}

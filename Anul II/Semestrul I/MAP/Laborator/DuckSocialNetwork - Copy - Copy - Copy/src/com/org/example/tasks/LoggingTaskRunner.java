package com.org.example.tasks;

import java.time.Instant;

public class LoggingTaskRunner implements TaskRunner {
    private final TaskRunner inner;

    public LoggingTaskRunner(TaskRunner inner) {
        this.inner = inner;
    }

    @Override
    public void runTask(Runnable task) {
        log("Task started");
        inner.runTask(task);
        log("Task finished");
    }

    private void log(String message) {
        System.out.println("[Task Log] " + Instant.now() + " - " + message);
    }
}

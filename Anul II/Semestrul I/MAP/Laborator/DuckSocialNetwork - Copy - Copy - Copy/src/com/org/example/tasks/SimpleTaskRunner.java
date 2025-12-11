package com.org.example.tasks;

public class SimpleTaskRunner implements TaskRunner {
    @Override
    public void runTask(Runnable task) {
        task.run();
    }
}

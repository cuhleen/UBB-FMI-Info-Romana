package com.org.tasks.runner;

import com.org.tasks.domain.Task;

import java.io.IOException;

public abstract class AbstractTaskRunner implements TaskRunner {

    private TaskRunner taskRunner;

    protected AbstractTaskRunner(TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    @Override
    public void executeOneTask() throws IOException {
        taskRunner.executeOneTask();
    }

    @Override
    public void executeAll() throws IOException {
        while (taskRunner.hasTask())
        {
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        taskRunner.addTask(t);
    }

    @Override
    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}

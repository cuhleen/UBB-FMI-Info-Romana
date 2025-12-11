package com.org.tasks.runner;

import com.org.tasks.domain.Task;

import java.io.IOException;

public interface TaskRunner {
    void executeOneTask() throws IOException;

    void executeAll() throws IOException;

    void addTask(Task t);

    boolean hasTask();
}

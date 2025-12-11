package com.org.tasks.runner;

import com.org.tasks.domain.Task;

public interface TaskRunner {
    void executeOneTask();

    void executeAll();

    void addTask(Task t);

    boolean hasTask();
}

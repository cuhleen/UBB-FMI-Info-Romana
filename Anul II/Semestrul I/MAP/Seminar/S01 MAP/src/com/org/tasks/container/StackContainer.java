package com.org.tasks.container;

import com.org.tasks.domain.Task;

public class StackContainer implements Container {
    private Task[] tasks;
    private int size;

    public StackContainer() {
        tasks = new Task[10];
        size = 0;
    }

    @Override
    public Task remove() {
        if (isEmpty()) return null;

        return tasks[--size];
    }

    @Override
    public void add(Task task) {
        tasks[size++] = task;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}

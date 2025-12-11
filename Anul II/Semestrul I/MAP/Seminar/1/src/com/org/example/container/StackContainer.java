package com.org.example.container;

import com.org.example.domain.Task;

public class StackContainer implements Container {

    private Task[] tasks;
    private int size;

    public StackContainer(){
        tasks = new Task[10];
    }

    @Override
    public Task remove() {
        return null;
    }

    @Override
    public void add(Task task) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}


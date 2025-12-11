package com.org.tasks.container;

import com.org.tasks.domain.Task;

public interface Container {
    Task remove();
    void add(Task task);
    int size();
    boolean isEmpty();
}

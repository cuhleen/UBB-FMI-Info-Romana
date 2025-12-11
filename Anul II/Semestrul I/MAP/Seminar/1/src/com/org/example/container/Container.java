package com.org.example.container;

import com.org.example.domain.Task;

public interface Container{
    Task remove();
    void add(Task task);
    int size();
    boolean isEmpty();
}
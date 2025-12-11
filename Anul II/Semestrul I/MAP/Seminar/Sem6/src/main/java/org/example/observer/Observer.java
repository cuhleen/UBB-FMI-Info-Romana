package org.example.observer;

public interface Observer<E extends Event> {
    void update(E e);
}

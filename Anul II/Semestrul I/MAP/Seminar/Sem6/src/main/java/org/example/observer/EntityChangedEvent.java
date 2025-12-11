package org.example.observer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class EntityChangedEvent<E> implements Event {
    public enum EventType {
        ADD,UPDATE,REMOVE
    }

    private final EventType eventType;
    private final E data;

}

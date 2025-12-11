package com.org.example.id_generators;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerators {
    private static final AtomicLong userId = new AtomicLong(1);
    private static final AtomicLong eventId = new AtomicLong(1001);
    public static long nextUserId() { return userId.getAndIncrement(); }
    public static long nextEventId() { return eventId.getAndIncrement(); }
}

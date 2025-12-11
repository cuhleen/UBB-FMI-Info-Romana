package core.id_generators;

import java.util.concurrent.atomic.AtomicLong;

public class MessageIdGenerator {
    private static final AtomicLong mid = new AtomicLong(1);
    public static long nextId() { return mid.getAndIncrement(); }
}

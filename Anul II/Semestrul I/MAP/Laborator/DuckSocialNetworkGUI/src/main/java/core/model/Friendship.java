package core.model;

import java.time.Instant;

public class Friendship {
    private final User a;
    private final User b;
    private final Instant timestamp;

    public Friendship(User a, User b) {
        this.a = a; this.b = b; this.timestamp = Instant.now();
    }

    public User getA() { return a; }
    public User getB() { return b; }
}
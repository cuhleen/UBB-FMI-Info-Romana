package core.model;

import java.time.LocalDateTime;

public class Notification {

    private final long id;
    private final long userId;
    private final String content;
    private final LocalDateTime createdAt;
    private boolean read;

    public Notification(long id, long userId, String content,
                        LocalDateTime createdAt, boolean read) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
        this.read = read;
    }

    public long getId() { return id; }
    public long getUserId() { return userId; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isRead() { return read; }

    public void markRead() { this.read = true; }
}

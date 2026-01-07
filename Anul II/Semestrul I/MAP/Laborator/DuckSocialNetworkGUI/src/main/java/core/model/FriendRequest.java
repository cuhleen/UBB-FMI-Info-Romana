package core.model;

import java.time.LocalDateTime;

public class FriendRequest {

    private final long id;
    private final long fromUserId;
    private final long toUserId;
    private final String status; // PENDING, ACCEPTED, REJECTED
    private final LocalDateTime createdAt;

    public FriendRequest(long id, long fromUserId, long toUserId, String status, LocalDateTime createdAt) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

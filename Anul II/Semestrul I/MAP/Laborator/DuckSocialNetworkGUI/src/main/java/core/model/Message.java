package core.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Message {

    private final long id;
    private final long senderId;
    private final List<Long> receiverIds;  // <--- lista cerută
    private final String content;
    private final LocalDateTime timestamp;
    private final Long replyToId;

    public Message(long id, long senderId, List<Long> receiverIds, String content,
                   LocalDateTime timestamp, Long replyToId) {
        this.id = id;
        this.senderId = senderId;
        this.receiverIds = receiverIds;
        this.content = content;
        this.timestamp = timestamp;
        this.replyToId = replyToId;
    }


    public Message(long id, long senderId, long receiverId, String content,
                   LocalDateTime timestamp, Long replyToId) {
        this(id, senderId, Collections.singletonList(receiverId), content, timestamp, replyToId);
    }

    public long getId() { return id; }
    public long getSenderId() { return senderId; }
    public List<Long> getReceiverIds() { return receiverIds; }

    public long getFirstReceiverId() { return receiverIds.get(0); }

    public String getContent() { return content; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Long getReplyToId() { return replyToId; }

    @Override
    public String toString() {
        return String.format("[%s] %d -> %s : %s (replyTo=%s)",
                timestamp, senderId, receiverIds, content, Objects.toString(replyToId));
    }
}

package core.model;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {
    public ReplyMessage(long id, long senderId, List<Long> receivers,
                        String content, LocalDateTime timestamp,
                        Long replyToId) {
        super(id, senderId, receivers, content, timestamp, replyToId);
    }
}

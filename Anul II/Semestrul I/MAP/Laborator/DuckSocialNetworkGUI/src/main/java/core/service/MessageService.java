package core.service;

import core.model.Message;
import core.model.ReplyMessage;
import core.repository.RepoMessageDB;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class MessageService {

    private final RepoMessageDB repo;

    public MessageService(RepoMessageDB repo) {
        this.repo = repo;
    }

    // -----------------------------
    // SEND
    // -----------------------------
    public Message sendMessage(long senderId, long receiverId, String content, Long replyToId) {

        if (content == null || content.isBlank())
            throw new IllegalArgumentException("Content gol");

        List<Long> receivers = List.of(receiverId);

        Message msg;

        if (replyToId != null) {
            msg = new ReplyMessage(
                    0,
                    senderId,
                    receivers,
                    content,
                    LocalDateTime.now(),
                    replyToId
            );
        } else {
            msg = new Message(
                    0,
                    senderId,
                    receivers,
                    content,
                    LocalDateTime.now(),
                    null
            );
        }

        return repo.save(msg);
    }

    public Message sendMessage(long senderId, long receiverId, String content) {
        return sendMessage(senderId, receiverId, content, null);
    }

    // -----------------------------
    // READ
    // -----------------------------
    public Optional<Message> getById(long id) {
        return repo.findById(id);
    }

    public List<Message> getInbox(long userId) {
        return repo.findByReceiver(userId);
    }

    public List<Message> getConversation(long userA, long userB) {
        return repo.findConversation(userA, userB);
    }
}

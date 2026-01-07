package core.service;

import core.model.Notification;
import core.repository.RepoNotificationDB;

import java.time.LocalDateTime;
import java.util.List;

public class NotificationService {

    private final RepoNotificationDB repo;

    public NotificationService(RepoNotificationDB repo) {
        this.repo = repo;
    }

    public void notifyUser(long userId, String text) {
        Notification n = new Notification(
                repo.generateId(),
                userId,
                text,
                LocalDateTime.now(),
                false
        );
        repo.save(n);
        System.out.println("[DEBUG] Notification saved for userId=" + userId + ": " + text);
    }


    public List<Notification> getForUser(long userId) {
        return repo.findByUser(userId);
    }

    public List<Notification> getUnreadForUser(long userId) {
        return repo.findByUser(userId).stream()
                .filter(n -> !n.isRead())
                .toList();
    }

    public void markRead(long notifId) {
        repo.markRead(notifId);
    }
}

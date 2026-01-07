package core.service;

import core.model.FriendRequest;
import core.repository.RepoFriendRequestDB;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class FriendRequestService {

    private final RepoFriendRequestDB repo;
    private final FriendshipService friendshipService;

    public FriendRequestService(RepoFriendRequestDB repo,
                                FriendshipService friendshipService) {
        this.repo = repo;
        this.friendshipService = friendshipService;
    }

    public void sendFriendRequest(long fromUserId, long toUserId) {

        if (fromUserId == toUserId)
            throw new IllegalArgumentException("Nu te poți adăuga pe tine");

        Optional<FriendRequest> existing =
                repo.findBetweenUsers(fromUserId, toUserId);

        if (existing.isPresent()) {
            FriendRequest fr = existing.get();

            switch (fr.getStatus()) {

                case "PENDING" -> {
                    // dacă e cerere inversă/reciprocă -> auto accept
                    if (fr.getFromUserId() == toUserId) {
                        acceptRequest(fr.getId());
                        return;
                    }
                    throw new IllegalStateException("Există deja o cerere pending");
                }

                case "ACCEPTED" ->
                        throw new IllegalStateException("Sunteți deja prieteni");

                case "REJECTED" -> {
                    Duration sinceReject =
                            Duration.between(fr.getCreatedAt(), LocalDateTime.now());

                    if (sinceReject.toMinutes() < 1) {
                        throw new IllegalStateException("Așteaptă înainte să retrimiți cererea");
                    }
                    // continuă și creează una nouă
                }
            }
        }

        repo.save(new FriendRequest(
                0,
                fromUserId,
                toUserId,
                "PENDING",
                LocalDateTime.now()
        ));
    }

    public void acceptRequest(long requestId) {

        Optional<FriendRequest> opt = repo.findById(requestId);
        if (opt.isEmpty())
            throw new IllegalArgumentException("Cerere inexistentă");

        FriendRequest fr = opt.get();

        repo.updateStatus(requestId, "ACCEPTED");

        friendshipService.addFriendship(
                fr.getFromUserId(),
                fr.getToUserId()
        );
    }

    public void rejectRequest(long requestId) {

        Optional<FriendRequest> opt = repo.findById(requestId);
        if (opt.isEmpty())
            throw new IllegalArgumentException("Cerere inexistentă");

        repo.updateStatus(requestId, "REJECTED");
    }

    public List<FriendRequest> getPendingRequests(long userId) {
        return repo.findPendingByToUser(userId);
    }

    public List<FriendRequest> getAllRequestsOf(long userId) {
        return repo.findAllByUser(userId);
    }


}
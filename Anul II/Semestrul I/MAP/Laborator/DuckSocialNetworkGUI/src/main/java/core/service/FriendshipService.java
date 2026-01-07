package core.service;

import core.repository.RepoFriendshipDB;

import java.util.ArrayList;
import java.util.List;

public class FriendshipService {

    private final RepoFriendshipDB repo;

    public FriendshipService(RepoFriendshipDB repo) {
        this.repo = repo;
    }

    // -----------------------------
    // ADD friendship
    // -----------------------------
    public void addFriendship(long userA, long userB) {
        if (userA == userB) return;
        repo.save(userA, userB);
    }

    // -----------------------------
    // REMOVE friendship
    // -----------------------------
    public boolean removeFriendship(long userA, long userB) {
        return repo.delete(userA, userB);
    }

    // -----------------------------
    // GET ALL friendships
    // -----------------------------
    public List<long[]> getAllFriendships() {
        return repo.findAll();
    }

    // -------------------------------------------
    // GET ALL friendships for one user
    // -------------------------------------------

    public List<Long> getFriendsOf(long userId) {
        List<Long> friends = new ArrayList<>();

        for (long[] pair : repo.findAll()) {
            long a = pair[0];
            long b = pair[1];

            if (a == userId) {
                friends.add(b);
            } else if (b == userId) {
                friends.add(a);
            }
        }

        return friends;
    }

    // -----------------------------
    // DELETE all for one user
    // -----------------------------
    public int removeAllForUser(long userId) {
        return repo.deleteAllForUser(userId);
    }

    public void deleteFriendshipsForUser(long userId) {
        int deleted = removeAllForUser(userId);
        System.out.println("[FriendshipService] Deleted " + deleted + " friendships for user " + userId);
    }

}

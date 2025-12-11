package com.org.example.service;

import com.org.example.repo.RepoFriendshipDB;

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

    // -----------------------------
    // DELETE all for one user
    // -----------------------------
    public int removeAllForUser(long userId) {
        return repo.deleteAllForUser(userId);
    }

    // în FriendshipService.java adaugă metoda de conveniență
    public void deleteFriendshipsForUser(long userId) {
        int deleted = removeAllForUser(userId);
        System.out.println("[FriendshipService] Deleted " + deleted + " friendships for user " + userId);
    }

}

package com.org.example.service;

import com.org.example.repo.RepoDuckDB;
import com.org.example.users.Duck;

import java.util.List;
import java.util.Optional;

public class DuckService {

    private final RepoDuckDB repo;

    public DuckService(RepoDuckDB repo) {
        this.repo = repo;
    }

    // CRUD -------------------------------------

    public void addDuck(Duck d) {
        repo.save(d);
    }

    public Optional<Duck> getDuck(long id) {
        return repo.findById(id);
    }

    public List<Duck> findAll() {
        return repo.findAll();
    }

    public boolean updateDuck(Duck d) {
        return repo.update(d);
    }

    public boolean updateDuck(long id, String username, String email, String password, com.org.example.enums.TipRata tip, double viteza, double rezistenta) {
        Optional<Duck> duckOpt = repo.findById(id);
        if (duckOpt.isPresent()) {
            Duck duck = duckOpt.get();
            duck.setUsername(username);
            duck.setEmail(email);
            duck.setPassword(password);
            duck.setTip(tip);
            duck.setViteza(viteza);
            duck.setRezistenta(rezistenta);
            return repo.update(duck);
        }
        return false;
    }

    public boolean deleteDuck(long id) {
        // First remove duck from card if it's in one
        removeDuckFromCardIfExists(id);

        // Then delete from database
        boolean success = repo.delete(id);

        if (success) {
            System.out.println("[DuckService] Duck " + id + " deleted successfully with card removal handled.");
        }

        return success;
    }

    // LOGIC specific to ducks ------------------

    public void changePerformance(long id, double newV, double newR) {
        Optional<Duck> od = repo.findById(id);
        if (od.isPresent()) {
            Duck d = od.get();
            d.setViteza(newV);
            d.setRezistenta(newR);
            repo.update(d);
        }
    }

    public void removeDuckFromCardIfExists(long duckId) {
        Optional<Duck> od = repo.findById(duckId);
        if (od.isPresent()) {
            Duck d = od.get();

            // verifică dacă duck-ul are card
            d.getCard().ifPresent(card -> {
                card.removeMember(d);   // scoate din card
            });

            // actualizează în DB
            repo.update(d);

            System.out.println("[DuckService] Duck " + duckId + " removed from card if existed.");
        }
    }

    public boolean isDuck(long id) {
        return repo.findById(id).isPresent();
    }
}

package com.org.example.service;

import com.org.example.repo.RepoCardDB;
import com.org.example.repo.RepoDuckDB;
import com.org.example.users.Card;
import com.org.example.users.Duck;

import java.util.List;
import java.util.Optional;

public class CardService {

    private final RepoCardDB repoCard;
    private final RepoDuckDB repoDuck;

    public CardService(RepoCardDB repoCard, RepoDuckDB repoDuck) {
        this.repoCard = repoCard;
        this.repoDuck = repoDuck;
    }

    // CRUD -------------------------------------

    public void addCard(Card c) {
        repoCard.save(c);
    }

    public Optional<Card> getCard(long id) {
        return repoCard.findById(id);
    }

    public List<Card> getAllCards() {
        return repoCard.findAll();
    }

    public boolean updateCard(Card c) {
        return repoCard.update(c);
    }

    public boolean deleteCard(long id) {
        return repoCard.delete(id);
    }

    // LOGIC specific: add/remove duck ------------

    public boolean addDuckToCard(long duckId, long cardId) {
        Optional<Duck> od = repoDuck.findById(duckId);
        Optional<Card> oc = repoCard.findById(cardId);

        if (od.isEmpty() || oc.isEmpty())
            return false;

        Duck d = od.get();
        Card c = oc.get();

        // actualizăm ramura din memorie
        c.addMember(d);

        // actualizăm și DB (setăm card_id)
        repoDuck.update(d);

        return true;
    }

    public boolean removeDuckFromCard(long duckId) {
        Optional<Duck> od = repoDuck.findById(duckId);
        if (od.isEmpty()) return false;

        Duck d = od.get();

        d.getCard().ifPresent(c -> {
            c.removeMember(d);
            d.setCard(null);
            repoDuck.update(d);
        });

        return true;
    }
}

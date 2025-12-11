package com.org.example.repo;

import com.org.example.enums.TipRata;
import com.org.example.id_generators.IdGenerators;
import com.org.example.users.Card;
import com.org.example.users.Duck;
import com.org.example.validation.ValidatorContext;

import java.util.*;

public class RepoDuck {
    private final Map<Long, Duck> ducks = new HashMap<>();
    private final Map<Long, Card> cards = new HashMap<>();

    private final ValidatorContext validator = new ValidatorContext();

    public Duck createDuck(String username, String email, String password, TipRata tip, double viteza, double rezistenta) {
        Duck d = new Duck(IdGenerators.nextUserId(), username, email, password, tip, viteza, rezistenta);
        validator.validate(d);
        ducks.put(d.getId(), d);
        return d;
    }

    public Optional<Duck> findById(long id) {
        return Optional.ofNullable(ducks.get(id));
    }

    public List<Duck> listAll() {
        return new ArrayList<>(ducks.values());
    }

    // Cârd CRUD
    public Card createCard(long id, String numeCard) {
        Card c = new Card(id, numeCard);
        cards.put(id, c);
        return c;
    }

    public Optional<Card> findCardById(long id) {
        return Optional.ofNullable(cards.get(id));
    }

    public List<Card> listCards() {
        return new ArrayList<>(cards.values());
    }
}

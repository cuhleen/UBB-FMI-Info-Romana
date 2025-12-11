package com.org.example.users;

import com.org.example.enums.TipRata;

import java.util.Optional;

public class Duck extends User {
    private TipRata tip;
    private double viteza;    // speed (units per second)
    private double rezistenta; // endurance (arbitrary units)
    private Card card;        // group

    public Duck(long id, String username, String email, String password, TipRata tip, double viteza, double rezistenta) {
        super(id, username, email, password);
        this.tip = tip; this.viteza = viteza; this.rezistenta = rezistenta;
    }

    public TipRata getTip() { return tip; }
    public double getViteza() { return viteza; }
    public double getRezistenta() { return rezistenta; }
    public String getPassword(){ return super.getPassword();}
    public Optional<Card> getCard() { return Optional.ofNullable(card); }

    public void setTip(TipRata tip) { this.tip = tip; }

    public void setViteza(double newV){
        this.viteza = newV;
    }

    public void setRezistenta(double newR){
        this.rezistenta = newR;
    }

    public void setCard(Card card) {
        if (this.card != null) this.card.removeMember(this);
        this.card = card;
        if (card != null && !card.getMembri().contains(this)) card.addMember(this);
    }

    // automatic message
    public void sendAutomaticQuack() {
        // nu e folosit nicăieri momentan
        String content = "Quack! Am terminat antrenamentul!";
        for (User friend : getFriends()) {
            sendMessage(friend, content);
        }
    }

    @Override
    public void performPeriodicActions() {
        // nimic momentan
    }

    @Override
    public String toString() {
        return String.format("Duck{id=%d, username='%s', email='%s', password='%s', tip=%s, viteza=%.3f, rezistenta=%.3f, card=%s}",
                getId(), getUsername(), getEmail(), getPassword(), tip, viteza, rezistenta,
                getCard().map(Card::getNumeCard).orElse("No Card"));
    }
}
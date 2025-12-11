package core.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Card {
    private final long id;
    private String numeCard;
    private final List<Duck> membri = new ArrayList<>();

    public Card(long id, String numeCard) {
        this.id = id;
        this.numeCard = numeCard;
    }

    public long getId() { return id; }
    public String getNumeCard() { return numeCard; }
    public List<Duck> getMembri() { return Collections.unmodifiableList(membri); }

    public void setNumeCard(String numeCard) { this.numeCard = numeCard; }

    // membri

    void addMemberInternal(Duck d) {
        if (!membri.contains(d)) {
            membri.add(d);
        }
    }


    public void addMember(Duck d) {
        if (!membri.contains(d)) {
            membri.add(d);
            if (d.getCard().orElse(null) != this) {
                d.setCard(this); // acest apel acum e sigur dacă Duck.setCard evită recursivitatea
            }
            log("Duck " + d.getUsername() + " adăugat în card " + numeCard);
        }
    }


    public void removeMember(Duck d) {
        if (membri.remove(d)) {
            d.setCard(null);
            log("Duck " + d.getUsername() + " eliminat din card " + numeCard);
        }
    }

    public boolean hasMember(Duck d) {
        return membri.contains(d);
    }

    // performanță

    public Performance getPerformantaMedie() {
        if (membri.isEmpty()) return new Performance(0.0, 0.0);
        double avgV = membri.stream().mapToDouble(Duck::getViteza).average().orElse(0.0);
        double avgR = membri.stream().mapToDouble(Duck::getRezistenta).average().orElse(0.0);
        return new Performance(avgV, avgR);
    }

    public static class Performance {
        public final double vitezaMedie;
        public final double rezistentaMedie;

        public Performance(double v, double r) {
            vitezaMedie = v;
            rezistentaMedie = r;
        }

        @Override
        public String toString() {
            return String.format("Viteza medie=%.3f, Rezistenta medie=%.3f", vitezaMedie, rezistentaMedie);
        }
    }

    // logging

    private void log(String msg) {
        System.out.println("[Card Log] " + msg);
    }

    // ToString

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Card{id=%d, nume='%s', membri=%d", id, numeCard, membri.size()));
        if (!membri.isEmpty()) {
            sb.append(", membri_list=[");
            for (int i = 0; i < membri.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(membri.get(i).getUsername());
            }
            sb.append("]");
        }
        sb.append("}");
        return sb.toString();
    }
}

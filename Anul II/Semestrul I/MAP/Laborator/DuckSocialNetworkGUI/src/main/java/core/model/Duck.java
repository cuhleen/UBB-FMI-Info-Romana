package core.model;

import java.util.Optional;

public class Duck extends User {
    private TipRata tip;
    private double viteza;
    private double rezistenta;
    private core.model.Card card;

    public Duck(long id, String username, String email, String password, TipRata tip, double viteza, double rezistenta) {
        super(id, username, email, password);
        this.tip = tip; this.viteza = viteza; this.rezistenta = rezistenta;
    }

    public TipRata getTip() { return tip; }
    public double getViteza() { return viteza; }
    public double getRezistenta() { return rezistenta; }
    public String getPassword(){ return super.getPassword();}
    public Optional<core.model.Card> getCard() { return Optional.ofNullable(card); }

    public void setTip(TipRata tip) { this.tip = tip; }

    public void setViteza(double newV){
        this.viteza = newV;
    }

    public void setRezistenta(double newR){
        this.rezistenta = newR;
    }

    public void setCard(Card card) {
        if (this.card == card) return;

        if (this.card != null) {
            this.card.removeMember(this);
        }

        this.card = card;

        if (card != null && !card.hasMember(this)) {
            card.addMemberInternal(this);
        }
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
                getCard().map(core.model.Card::getNumeCard).orElse("No Card"));
    }
}
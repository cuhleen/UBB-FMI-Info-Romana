package core.model;

import java.time.LocalDate;

public class Person extends User {
    private String nume;
    private String prenume;
    private LocalDate dataNasterii;
    private String ocupatie;
    private double nivelEmpatie; // 0..1

    public Person(long id, String username, String email, String password,
                  String nume, String prenume, LocalDate dataNasterii,
                  String ocupatie, double nivelEmpatie) {
        super(id, username, email, password);
        this.nume = nume;
        this.prenume = prenume;
        this.dataNasterii = dataNasterii;
        this.ocupatie = ocupatie;
        this.nivelEmpatie = nivelEmpatie;
    }

    public double getNivelEmpatie() { return nivelEmpatie; }
    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }
    public String getFullName() { return nume + " " + prenume; }
    public String getPassword() { return super.getPassword(); }
    public LocalDate getDataNasterii() { return dataNasterii; }
    public String getOcupatie() { return ocupatie; }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public void setDataNasterii(LocalDate dataNasterii) {
        this.dataNasterii = dataNasterii;
    }

    public void setOcupatie(String ocupatie) {
        this.ocupatie = ocupatie;
    }

    public void setNivelEmpatie(double nivelEmpatie) {
        this.nivelEmpatie = nivelEmpatie;
    }

    @Override
    public void performPeriodicActions() {
        // momentan nu face nimic
    }

    @Override
    public String toString() {
        return String.format("Person{id=%d, username='%s', email='%s', password='%s', nume='%s', prenume='%s', dataNasterii=%s, ocupatie='%s', nivelEmpatie=%.3f}",
                getId(), getUsername(), getEmail(), getPassword(), nume, prenume, dataNasterii, ocupatie, nivelEmpatie);
    }
}

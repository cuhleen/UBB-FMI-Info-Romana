package com.org.example.factories;

import com.org.example.enums.TipRata;
import com.org.example.users.Duck;
import com.org.example.users.Person;
import com.org.example.users.User;

import java.time.LocalDate;

class UserFactory {
    public static User createPerson(long id, String username, String email, String password,
                                    String nume, String prenume, LocalDate dataNasterii, String ocupatie, double nivelEmpatie) {
        return new Person(id, username, email, password, nume, prenume, dataNasterii, ocupatie, nivelEmpatie);
    }

    public static User createDuck(long id, String username, String email, String password,
                                  TipRata tip, double viteza, double rezistenta) {
        return new Duck(id, username, email, password, tip, viteza, rezistenta);
    }
}

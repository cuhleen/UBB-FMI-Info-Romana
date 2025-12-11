package com.org.example.repo;

import com.org.example.id_generators.IdGenerators;
import com.org.example.users.Person;
import com.org.example.validation.ValidatorContext;

import java.time.LocalDate;
import java.util.*;

public class RepoPerson {
    private final Map<Long, Person> persons = new HashMap<>();
    private final ValidatorContext validator = new ValidatorContext();

    public Person createPerson(String username, String email, String password, String nume, String prenume, LocalDate dataNasterii, String ocupatie, double nivelEmpatie) {
        Person p = new Person(IdGenerators.nextUserId(), username, email, password, nume, prenume, dataNasterii, ocupatie, nivelEmpatie);
        validator.validate(p);
        persons.put(p.getId(), p);
        return p;
    }

    public Optional<Person> findById(long id) {
        return Optional.ofNullable(persons.get(id));
    }

    public List<Person> listAll() {
        return new ArrayList<>(persons.values());
    }
}
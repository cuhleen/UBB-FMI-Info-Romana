package com.org.example.service;

import com.org.example.repo.RepoPersonDB;
import com.org.example.users.Person;

import java.util.List;
import java.util.Optional;

public class PersonService {

    private final RepoPersonDB repo;

    public PersonService(RepoPersonDB repo) {
        this.repo = repo;
    }

    // CRUD -------------------------------------

    public void addPerson(Person p) {
        repo.save(p);
    }

    public Optional<Person> getPerson(long id) {
        return repo.findById(id);
    }

    public List<Person> findAll() {
        return repo.findAll();
    }

    public boolean updatePerson(Person p) {
        return repo.update(p);
    }

    public boolean deletePerson(long id) {
        // pentru ștergerea cu prietenii, apelează deleteUser din AppService
        boolean success = repo.delete(id);

        if (success) {
            System.out.println("[PersonService] Person " + id + " deleted successfully.");
        }

        return success;
    }

    public void changeOccupation(long id, String newOcc) {
        Optional<Person> op = repo.findById(id);
        if (op.isPresent()) {
            Person p = op.get();
            // p.setOcupatie(newOcc); // ai nevoie de setter
            repo.save(p);
        }
    }
}

package core.service;

import core.repository.RepoDuckDB;
import core.model.Duck;
import core.repository.RepoUsersDB;
import core.security.PasswordUtil;
import core.validation.ValidatorContext;
import core.validation.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public class DuckService {

    private final RepoDuckDB repo;
    private final RepoUsersDB repoUsers;   // <-- nou
    private final ValidatorContext validatorContext;

    public DuckService(RepoDuckDB repo, RepoUsersDB repoUsers) {
        this.repo = repo;
        this.repoUsers = repoUsers;
        this.validatorContext = new ValidatorContext();
    }

    // CRUD -------------------------------------

    public void addDuck(Duck d) {
        validatorContext.validate(d);
        if (repo.findById(d.getId()).isPresent()) {
            throw new ValidationException("Duck cu ID " + d.getId() + " există deja!");
        }
        String hashed = PasswordUtil.sha256(d.getPassword());
        Duck duckToSave = new Duck(
                d.getId(),
                d.getUsername(),
                d.getEmail(),
                hashed,
                d.getTip(),
                d.getViteza(),
                d.getRezistenta()
        );
        repo.save(duckToSave);

    }

    public Optional<Duck> getDuck(long id) {
        return repo.findById(id);
    }

    public List<Duck> findAll() {
        return repo.findAll();
    }

    public boolean updateDuck(Duck d) {
        validatorContext.validate(d);
        return repo.update(d);
    }

    public boolean deleteDuck(long id) {
        removeDuckFromCardIfExists(id);

        boolean success = repo.delete(id);

        if (success) {
            repoUsers.delete(id);

            System.out.println("[DuckService] Duck " + id + " deleted + removed from users.");
        }

        return success;
    }

    public void removeDuckFromCardIfExists(long duckId) {
        Optional<Duck> od = repo.findById(duckId);
        if (od.isPresent()) {
            Duck d = od.get();

            d.getCard().ifPresent(card -> {
                card.removeMember(d);
            });

            repo.update(d);

            System.out.println("[DuckService] Duck " + duckId + " removed from card if existed.");
        }
    }

    public List<Duck> getPage(int pageNumber, int pageSize) {
        int offset = pageNumber * pageSize;
        return repo.findPage(pageSize, offset);
    }

    public boolean isDuck(long id) {
        return repo.findById(id).isPresent();
    }
}

package core.service;

import core.repository.RepoPersonDB;
import core.repository.RepoUsersDB;
import core.model.Person;
import core.security.PasswordUtil;
import core.validation.ValidatorPerson;
import core.validation.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public class PersonService {

    private final RepoPersonDB repo;
    private final RepoUsersDB repoUsers;
    private final FriendshipService friendshipService;

    public PersonService(RepoPersonDB repo, RepoUsersDB repoUsers, FriendshipService friendshipService) {
        this.repo = repo;
        this.repoUsers = repoUsers;
        this.friendshipService = friendshipService;
    }

    // CRUD -------------------------------------

    public void addPerson(Person p) {
        new ValidatorPerson().validate(p);

        if (repo.findById(p.getId()).isPresent()) {
            throw new ValidationException("Person cu ID " + p.getId() + " există deja!");
        }

        String hashedPass = PasswordUtil.sha256(p.getPassword());
        p.setPassword(hashedPass);

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

        friendshipService.deleteFriendshipsForUser(id);

        boolean okPersons = repo.delete(id);

        if (!okPersons) {
            System.err.println("[PersonService] Failed to delete person " + id + " from persons.");
            return false;
        }

        boolean okUsers = repoUsers.delete(id);

        if (!okUsers) {
            System.err.println("[PersonService] WARNING: person " + id + " deleted from persons but NOT from users!");
        }

        System.out.println("[PersonService] Person " + id + " deleted successfully with friendships and users cleanup.");

        return true;
    }
}

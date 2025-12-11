package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.model.User;
import org.example.observer.EntityChangedEvent;
import org.example.observer.Observable;
import org.example.observer.Observer;
import org.example.repository.UserRepository;
import org.example.repository.dto.Page;
import org.example.repository.dto.Pageable;
import org.example.validator.UserValidator;
import org.example.validator.ValidationException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService implements Observable<EntityChangedEvent<User>> {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final List<Observer<EntityChangedEvent<User>>> observers = new ArrayList<>();

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> save(User user) throws ValidationException {
        userValidator.validate(user);
        Optional<User> savedUser = userRepository.save(user);
        savedUser.ifPresent(u->notifyObservers(new EntityChangedEvent<>(EntityChangedEvent.EventType.ADD,u)));
        return savedUser;
    }

    public Optional<User> update(User user) throws ValidationException {
        userValidator.validate(user);

        Optional<User> updatedUser = userRepository.update(user);
        updatedUser.ifPresent(u->notifyObservers(new EntityChangedEvent<>(EntityChangedEvent.EventType.UPDATE,u)));
        return updatedUser ;
    }

    public Optional<User> delete(Integer id) throws ValidationException {
        if (id == null) {
            throw new ValidationException("id cannot be null");
        }

        Optional<User> user = userRepository.delete(id);
        user.ifPresent(u->notifyObservers(new EntityChangedEvent<>(EntityChangedEvent.EventType.REMOVE,u)));
        return user;
    }

    public Page<User> findAllOnPage(Pageable pageable) {
        return userRepository.findAllOnPage(pageable);
    }

    @Override
    public void addObserver(Observer<EntityChangedEvent<User>> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EntityChangedEvent<User>> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EntityChangedEvent<User> userEntityChangedEvent) {
        observers.forEach(observer -> observer.update(userEntityChangedEvent));
    }
}

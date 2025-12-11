package org.example;

import org.example.config.Config;
import org.example.model.User;
import org.example.observer.EntityChangedEvent;
import org.example.observer.Event;
import org.example.observer.Observer;
import org.example.repository.Repository;
import org.example.repository.UserDBRepository;
import org.example.repository.UserRepository;
import org.example.repository.dto.Pageable;
import org.example.service.UserService;
import org.example.validator.UserValidator;
import org.example.validator.ValidationException;

import java.time.LocalDateTime;
import java.util.Properties;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws ValidationException {
        var properties = Config.getProperties();

        UserRepository repo = new UserDBRepository(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
                );

        UserValidator validator = new UserValidator();
        UserService service = new UserService(repo, validator);
        var printObserver = new PrintObserver();
        service.addObserver(printObserver);
        User u1 = new User("user8", LocalDateTime.now(), 5);
        service.save(u1);
        service.findAllOnPage(new Pageable(1,5)).getElements().forEach(System.out::println);
        //repo.findById(1).ifPresent(System.out::println);
    }

    static class PrintObserver implements Observer<EntityChangedEvent<User>> {

        @Override
        public void update(EntityChangedEvent<User> event) {
            System.out.println(event + "----------- observer");

        }
    }

    //        var user = repo.findById(2);
//        if(user.isPresent()) {
//            user.get().setCredits(99);
//            repo.update(user.get());
//        }
    //repo.save(u1);
    //System.out.println(repo.delete(2));
    //repo.findAll().forEach(System.out::println);
    //service.save(new User(null,null,-1));
}

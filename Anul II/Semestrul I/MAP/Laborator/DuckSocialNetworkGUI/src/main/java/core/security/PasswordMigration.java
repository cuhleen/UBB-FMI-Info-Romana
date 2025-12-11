package core.security;

import core.service.DuckService;
import core.service.PersonService;

public class PasswordMigration {

    public static boolean isHashed(String password) {
        return password != null && password.matches("^[0-9a-fA-F]{64}$");
    }

    public static void migratePasswords(
            DuckService duckService,
            PersonService personService
    ) {
        // Ducks
        duckService.findAll().forEach(d -> {
            if (!isHashed(d.getPassword())) {
                d.setPassword(PasswordUtil.sha256(d.getPassword()));
                duckService.updateDuck(d);
                System.out.println("Updated duck: " + d.getId());
            }
        });

        // Persons
        personService.findAll().forEach(p -> {
            if (!isHashed(p.getPassword())) {
                p.setPassword(PasswordUtil.sha256(p.getPassword()));
                personService.updatePerson(p);
                System.out.println("Updated person: " + p.getId());
            }
        });

        System.out.println("Done migrating passwords.");
    }
}

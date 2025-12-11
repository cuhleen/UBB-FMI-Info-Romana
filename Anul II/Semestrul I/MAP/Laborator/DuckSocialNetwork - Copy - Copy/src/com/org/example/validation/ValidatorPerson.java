package com.org.example.validation;

import com.org.example.users.Person;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class ValidatorPerson implements Validator<Person> {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._-]{3,20}$");

    @Override
    public void validate(Person p) {
        if (p == null)
            throw new IllegalArgumentException("Entitatea Person este null.");

        if (p.getUsername() == null || !USERNAME_PATTERN.matcher(p.getUsername()).matches())
            throw new IllegalArgumentException("Username invalid: " + p.getUsername());

        if (p.getEmail() == null || !EMAIL_PATTERN.matcher(p.getEmail()).matches())
            throw new IllegalArgumentException("Email invalid: " + p.getEmail());

        if (p.getFullName() == null || p.getFullName().isBlank())
            throw new IllegalArgumentException("Numele nu poate fi gol.");

        if (p.getNivelEmpatie() < 0 || p.getNivelEmpatie() > 1)
            throw new IllegalArgumentException("Nivelul de empatie trebuie să fie între 0 și 1.");
    }
}
package core.validation;

import core.model.Duck;
import core.model.TipRata;
import core.validation.exceptions.ValidationException;

import java.util.regex.Pattern;

public class ValidatorDuck implements Validator<Duck> {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._-]{3,20}$");

    @Override
    public void validate(Duck d) {
        if (d == null)
            throw new ValidationException("Entitatea Duck este null.");

        if (d.getUsername() == null || !USERNAME_PATTERN.matcher(d.getUsername()).matches())
            throw new ValidationException("Username invalid: " + d.getUsername());

        if (d.getEmail() == null || !EMAIL_PATTERN.matcher(d.getEmail()).matches())
            throw new ValidationException("Email invalid: " + d.getEmail());

        TipRata tip = d.getTip();
        if (tip == null)
            throw new ValidationException("Tipul raței nu poate fi null.");

        if (d.getViteza() <= 0)
            throw new ValidationException("Viteza trebuie să fie pozitivă.");

        if (d.getRezistenta() <= 0)
            throw new ValidationException("Rezistența trebuie să fie pozitivă.");
    }
}

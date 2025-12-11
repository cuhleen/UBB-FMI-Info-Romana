package core.validation;

import core.model.Duck;
import core.model.Person;

public class ValidatorContext {

    private final Validator<Person> personValidator;
    private final Validator<Duck> duckValidator;

    public ValidatorContext() {
        this.personValidator = new ValidatorPerson();
        this.duckValidator = new ValidatorDuck();
    }

    public void validate(Object entity) {
        if (entity instanceof Person p) {
            personValidator.validate(p);
        } else if (entity instanceof Duck d) {
            duckValidator.validate(d);
        } else {
            throw new IllegalArgumentException("Tip necunoscut pentru validare: " + entity.getClass());
        }
    }
}
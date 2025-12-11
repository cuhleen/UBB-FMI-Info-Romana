package org.example.validator;

import org.example.model.User;

public class UserValidator implements Validator<User> {
    @Override
    public void validate(User user) throws ValidationException {
        StringBuilder validations = new StringBuilder();
        if ( user.getCreatedAt() == null )
            validations.append("user.createdAt is null\n");
        if ( user.getUsername() == null )
            validations.append("user.username is null\n");
        if (user.getCredits() < 0 )
            validations.append("user.credits is negative\n");

        if ( ! validations.isEmpty() )
            throw new ValidationException(validations.toString());
    }
}

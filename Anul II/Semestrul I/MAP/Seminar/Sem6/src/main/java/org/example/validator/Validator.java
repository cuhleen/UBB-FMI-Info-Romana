package org.example.validator;

public interface Validator<E> {
    void validate(E e) throws ValidationException;
}

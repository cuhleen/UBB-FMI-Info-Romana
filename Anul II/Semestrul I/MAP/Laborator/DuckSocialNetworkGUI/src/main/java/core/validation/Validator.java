package core.validation;

public interface Validator<T> {
    /**
     * Validează obiectul de tip T.
     * @param entity obiectul de validat
     * @throws IllegalArgumentException dacă entitatea nu e validă
     */
    void validate(T entity);
}
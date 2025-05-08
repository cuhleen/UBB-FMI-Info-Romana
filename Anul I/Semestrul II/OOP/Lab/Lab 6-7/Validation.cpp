#include "Validation.h"

#include <cassert>

string ValidationException::getMessage() const {
    return message;
}

void Validation::validateFilm(const Film& film) {
    if (film.getTitle().empty()) {
        throw ValidationException("Name cannot be empty");
    }
    if (film.getGenre().empty()) {
        throw ValidationException("Genre cannot be empty");
    }
    if (film.getActor().empty()) {
        throw ValidationException("Actor cannot be empty");
    }
    if (film.getYear() <= 0) {
        throw ValidationException("Year must be positive");
    }
}

void testValidation() {
    Film p1("Film1", "Genre1", "Actor1", 10.0);
    try {
        Validation::validateFilm(p1);
    }
    catch (ValidationException) {
        throw ValidationException("Validation failed for valid film");
        assert(false);
    }
    Film p2("", "Genre1", "Actor1", 10);
    try {
        Validation::validateFilm(p2);
    }
    catch (ValidationException &e) {
        assert(e.getMessage() == "Name cannot be empty");
    }
    Film p3("Film1", "", "Actor1", 10);
    try {
        Validation::validateFilm(p3);
    }
    catch (ValidationException &e) {
        assert(e.getMessage() == "Genre cannot be empty");
    }
    Film p4("Film1", "Genre1", "", 10);
    try {
        Validation::validateFilm(p4);
    }
    catch (ValidationException &e) {
        assert(e.getMessage() == "Actor cannot be empty");
    }
    Film p5("Film1", "Genre1", "Actor1", -10);
    try {
        Validation::validateFilm(p5);
    }
    catch (ValidationException &e) {
        assert(e.getMessage() == "Year must be positive");
    }
}
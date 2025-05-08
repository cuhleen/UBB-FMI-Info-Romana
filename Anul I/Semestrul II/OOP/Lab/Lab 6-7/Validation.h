#ifndef VALIDATION_H
#define VALIDATION_H
#include <string>
#include <utility>

#include "Film.h"
using std::string;
class ValidationException {
private:
    string message;
public:
    explicit ValidationException(string message) : message(std::move(message)){}
    [[nodiscard]] string getMessage() const;
};

class Validation {
public:
    static void validateFilm(const Film& film);
};

void testValidation();


#endif //VALIDATION_H

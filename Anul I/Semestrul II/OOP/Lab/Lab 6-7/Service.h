#ifndef SERVICE_H
#define SERVICE_H
#include <utility>

#include "FilmRepo.h"
#include "Validation.h"

class Service {
private:
    FilmRepo filmRepo;
    Validation validator;
public:
    Service(FilmRepo filmRepo, Validation validator) : filmRepo(std::move(filmRepo)), validator(validator) {
    }
    void addFilm(const string& name, const string& type, const string& manufacturer, int price);
    void removeFilm(const string& name);
    void updateFilm(const string& name, const Film& newProduct);
    [[nodiscard]] int searchFilm(const string& name) const;
    [[nodiscard]] VectorDinamic<Film> getAll() const;
    [[nodiscard]] VectorDinamic<Film> filterFilms(const string& choice, const string& filter) const;
    [[nodiscard]] VectorDinamic<Film> sortFilms(const string& choice, int order) const;
};

void testService();

#endif //SERVICE_H

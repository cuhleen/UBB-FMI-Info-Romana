#ifndef FILM_REPO_H
#define FILM_REPO_H

#include <utility>
#include "TemplateDynamicVector.h"
#include "Film.h"

class RepoException {
    string message;
public:
    explicit RepoException(string message) : message(std::move(message)) {}
    [[nodiscard]] string getMessage() const;
};

class FilmRepo {
private:
    VectorDinamic<Film> products;
public:
    void addFilm(const Film& p);
    void removeFilm(const string& name);
    void updateFilm(const string& name, const Film& newFilm);
    [[nodiscard]] int searchFilm(const string& name) const;
    [[nodiscard]] VectorDinamic<Film> getAll() const;
};

void testRepo();


#endif

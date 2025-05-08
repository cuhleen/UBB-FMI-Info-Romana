#include "FilmRepo.h"

#include <cassert>

string RepoException::getMessage() const {
    return message;
}

void FilmRepo::addFilm(const Film& p) {
    for (const Film& prod : products) {
        if (prod.getTitle() == p.getTitle() && prod.getGenre() == p.getGenre()) {
            throw RepoException("Film already exists");
        }
    }
    products.push_back(p);
}

void FilmRepo::removeFilm(const string& name) {
    int poz = -1, size = products.size();
    for (int i = 0; i < size; ++i) {
        if (products[i].getTitle() == name) {
            poz = i;
            break;
        }
    }
    if (poz == -1) {
        throw RepoException("Film not found");
    } else {
        for (int j = poz; j < size; ++j) {
            products[j] = products[j + 1];
        }
        products.pop_back();
    }
}

void FilmRepo::updateFilm(const string& name, const Film& newFilm) {
    for (auto & product : products) {
        if (product.getTitle() == name) {
            product = newFilm;
            return;
        }
    }
    throw RepoException("Film not found");
}

int FilmRepo::searchFilm(const string& name) const {
    for (int i = 0; i < products.size(); ++i) {
        if (products.get(i).getTitle() == name) {
            return i;
        }
    }
    return -1;
}

VectorDinamic<Film> FilmRepo::getAll() const {
    return products;
}

void testRepo() {
    FilmRepo repo;
    const Film p1("Film1", "Type1", "Manufacturer1", 10);
    repo.addFilm(p1);
    const auto products = repo.getAll();
    assert(products.size() == 1);
    try {
        repo.addFilm(p1);
    }
    catch (RepoException &e) {
        assert(e.getMessage() == "Film already exists");
    }
    const Film p2("Film2", "Type2", "Manufacturer2", 20);
    repo.addFilm(p2);
    assert(repo.getAll().size() == 2);
    assert(repo.searchFilm("Film1") == 0);
    assert(repo.searchFilm("Film2") == 1);
    try {
        repo.removeFilm("Film3");
    }
    catch (RepoException &e) {
        assert(e.getMessage() == "Film not found");
    }
    repo.removeFilm("Film1");
    assert(repo.getAll().size() == 1);
    try {
        repo.updateFilm("Film1", p2);
    }
    catch (RepoException &e) {
        assert(e.getMessage() == "Film not found");
    }
    const Film p3("Film3", "Type3", "Manufacturer3", 30);
    repo.updateFilm("Film2", p3);
    assert(repo.getAll().size() == 1);
    assert(repo.getAll()[0].getTitle() == "Film3");
    assert(repo.getAll()[0].getGenre() == "Type3");
    assert(repo.getAll()[0].getActor() == "Manufacturer3");
    assert(repo.getAll()[0].getYear() == 30);
    assert(repo.searchFilm("Film4") == -1);
}

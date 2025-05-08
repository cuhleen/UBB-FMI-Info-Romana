#pragma once
#include "Film.h"
#include "repo.h"
#include <memory>
#include <vector>

class ActiuneUndo {
public:
    virtual void doUndo() = 0;
    virtual ~ActiuneUndo() = default;
};

class UndoAdauga : public ActiuneUndo {
    Repo& repository;
    Film filmAdaugat;
public:
    UndoAdauga(Repo& repo, const Film& film) : repository{repo}, filmAdaugat{film} {}
    void doUndo() override {
        repository.stergeFilm(repository.cautaPozitieDupaTitlu(filmAdaugat.getTitluFilm()));
    }
};

class UndoSterge : public ActiuneUndo {
    Repo& repository;
    Film filmSters;
public:
    UndoSterge(Repo& repo, const Film& film) : repository{repo}, filmSters{film} {}
    void doUndo() override {
        repository.adaugaFilm(filmSters);
    }
};

class UndoModifica : public ActiuneUndo {
    Repo& repository;
    Film filmOriginal;
public:
    UndoModifica(Repo& repo, const Film& film) : repository{repo}, filmOriginal{film} {}
    void doUndo() override {
        const int pozitie = repository.cautaPozitieDupaTitlu(filmOriginal.getTitluFilm());
        repository.modificaFilm(filmOriginal, pozitie);
    }
};
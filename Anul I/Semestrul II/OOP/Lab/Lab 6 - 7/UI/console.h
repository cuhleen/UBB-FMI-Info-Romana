#pragma once
#include "../Service/FilmService.h"
#include "../Domain/Film.h"

class UI {
private:

    Service& service;

    /*
    Citeste datele de la tastatura si adauga film nou in repository
    arunca exceptie daca parametrii nu sunt valizi sau titlul filmului este deja existent
    */
    void ui_adaugaFilm();

    /*
    Sterge un film din repository
    */
    void ui_stergeFilm();

    /*
    Modifica un film din repository
    */
    void ui_modificaFilm();

    /*
    Afiseaza lista de filme din repository
    */
    static void ui_afiseazaFilme(const VectorDinamic<Film>& filme);

    /*
    Cauta un film dupa titlul acestuia
    */
    void ui_cautaFilm();

    /*
    Filtreaza filme dupa gen / anul aparitiei
    */
    void ui_filtrare();

    /*
    Sorteaza filme dupa actorul principal / gen + anul aparitiei
    */
    void ui_sortare();

    /*
    Adauga filme default in lista
    */
    void ui_default();

public:
    explicit UI(Service& service) :service{ service } {}
    //nu permitem copierea obiectelor
    UI(const UI& other) = delete;

    ~UI();

    void ruleaza();

};
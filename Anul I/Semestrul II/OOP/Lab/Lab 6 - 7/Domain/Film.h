#pragma once
#include <iostream>
#include <string>
#include <utility>
using std::string;
using std::cout;

class Film {

private:

    string titluFilm;
    string genFilm;
    int anAparitie;
    string actorPrincipal;

public:

    [[nodiscard]] int getAnAparitie() const;

    [[nodiscard]] string getTitluFilm() const;

    [[nodiscard]] string getGenFilm() const;

    [[nodiscard]] string getActorPrincipal() const;

    void setGenFilm(const string& genFilmNou);

    void setAnAparitie(int anAparitieNou);

    void setActorPrincipal(const string& actorPrincipalNou);

    Film(string  titluFilm, string  genFilm, const int anAparitie, string  actorPrincipal) : titluFilm{std::move( titluFilm )}, genFilm{std::move( genFilm )}, anAparitie{ anAparitie }, actorPrincipal{std::move( actorPrincipal )} {}

    Film(const Film& other) :titluFilm{ other.titluFilm }, genFilm{ other.genFilm }, anAparitie{ other.anAparitie }, actorPrincipal{ other.actorPrincipal } {
        cout <<"";
    }

};
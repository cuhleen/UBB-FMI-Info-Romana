#include "ShoppingCart.h"

#include "repo.h"
#include <cassert>
#include <algorithm>

void ShoppingCart::addFilm(const Film& f) {
    for(const Film &film: filmCart)
    {
        if (film.getTitluFilm() == f.getTitluFilm())
            throw ExceptieRepo("Filmul deja se afla in cos");
    }
    filmCart.push_back(f);
}

void ShoppingCart::removeFilm(const Film& f)
{
    // for (int i = 0; i < filmCart.size(); i++)
    //
    //     if (filmCart[i].getTitluFilm() == f.getTitluFilm())
    //     {
    //         filmCart.erase(filmCart.begin() + i);
    //         return;
    //     }
    // throw ExceptieRepo("Filmul nu a fost gasit in cos");

    auto it = std::find_if(filmCart.begin(), filmCart.end(), [&](const Film& film) {
        return film.getTitluFilm() == f.getTitluFilm();
    });

    if (it != filmCart.end()) {
        filmCart.erase(it);
    } else {
        throw ExceptieRepo("Filmul nu a fost gasit in cos");
    }

}

void ShoppingCart::clearCart()
{
    filmCart.clear();
}

vector<Film> ShoppingCart::getCart()
{
    return filmCart;
}

int ShoppingCart::getNumFilms() const
{
    return filmCart.size();
}


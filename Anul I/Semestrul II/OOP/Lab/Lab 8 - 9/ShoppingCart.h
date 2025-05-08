#pragma once

#include "film.h"
#include <vector>
#include <utility>

using std::vector;

class ShoppingCart {

    private:
        vector<Film> filmCart;
    public:
        void addFilm(const Film& f);
        void removeFilm(const Film& f);
        void clearCart();
        vector<Film> getCart();
        [[nodiscard]] int getNumFilms() const;

};



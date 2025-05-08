#pragma once
#include "Film.h"
#include <utility>
#include <vector>
#include <string>

using std::vector;
using std::string;
using std::ostream;

class ExceptieValidare {
    vector<string> mesajEroare;
public:
    explicit ExceptieValidare(vector<string> erori) :mesajEroare{std::move(erori)} {}
    friend ostream& operator<<(ostream& out, const ExceptieValidare& exceptie);

    vector<string> getMesaj() { return mesajEroare; }
};

ostream& operator<<(ostream& out, const ExceptieValidare& exceptie);

class Validator {
public:
    static void valideaza(const Film& film);
};
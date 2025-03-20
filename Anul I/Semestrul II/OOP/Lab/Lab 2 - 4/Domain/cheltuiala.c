#include "cheltuiala.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

Cheltuiala creeazaCheltuiala(int id, int zi, int suma, char* tip) {
    Cheltuiala cheltuiala;
    size_t numarDeCaractere = 0;
    if (tip != NULL)
        numarDeCaractere = strlen(tip) + 1;
    cheltuiala.tip = malloc(sizeof(char) * numarDeCaractere);
    if (cheltuiala.tip != NULL)
        if (tip != NULL)
            strcpy(cheltuiala.tip, tip);
    cheltuiala.id = id;
    cheltuiala.zi = zi;
    cheltuiala.suma = suma;
    return cheltuiala;
}

void distrugeCheltuiala(Cheltuiala* cheltuiala) {
    free(cheltuiala->tip);
    cheltuiala->tip = NULL;
    cheltuiala->id = -1;
    cheltuiala->zi = -1;
    cheltuiala->suma = -1;
}

int valideazaCheltuiala(Cheltuiala cheltuiala) {
    if (cheltuiala.id < 0) {
        return 1;
    }
    if (cheltuiala.zi < 0 || cheltuiala.zi > 31) {
        return 2;
    }
    if (cheltuiala.suma < 0) {
        return 3;
    }
    if ((strcmp(cheltuiala.tip, "mancare") != 0) && (strcmp(cheltuiala.tip, "transport") != 0) && (strcmp(cheltuiala.tip, "internet") != 0) && (strcmp(cheltuiala.tip, "imbracaminte") != 0) && (strcmp(cheltuiala.tip, "altele") != 0)) {
        return 4;
    }
    return 0;
}

Cheltuiala copiazaCheltuiala(Cheltuiala* cheltuiala) {
    return creeazaCheltuiala(cheltuiala->id, cheltuiala->zi, cheltuiala->suma, cheltuiala->tip);
}
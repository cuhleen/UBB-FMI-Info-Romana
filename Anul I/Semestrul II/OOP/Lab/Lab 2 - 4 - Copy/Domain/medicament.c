#include "medicament.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

Medicament* creeazaMedicament(int id, int concentratie, int cantitate, char* nume) {
    Medicament* medicament = malloc(sizeof(Medicament));
    size_t numarDeCaractere = 0;
    if (nume != NULL)
        numarDeCaractere = strlen(nume) + 1;
    medicament->nume = malloc(sizeof(char) * numarDeCaractere);
    if (medicament->nume != NULL)
        if (nume != NULL)
            strcpy(medicament->nume, nume);
    medicament->id = id;
    medicament->concentratie = concentratie;
    medicament->cantitate = cantitate;
    return medicament;
}

void distrugeMedicament(Medicament* medicament) {
    free(medicament->nume);
    medicament->cantitate = 0;
    medicament->concentratie = 0;
    free(medicament);
}

int valideazaMedicament(Medicament* medicament) {
    if (medicament->id < 0) {
        return 1;
    }
    if (medicament->concentratie < 0 || medicament->concentratie > 100) {
        return 2;
    }
    if (medicament->cantitate < 0) {
        return 3;
    }
    if (strlen(medicament->nume) == 0){
        return 4;
    }
    return 0;
}

Medicament* copiazaMedicament(Medicament* medicament) {
    return creeazaMedicament(medicament->id, medicament->concentratie, medicament->cantitate, medicament->nume);
}
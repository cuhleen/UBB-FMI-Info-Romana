#pragma once
typedef struct {
    int id;
    int zi;
    int suma;
    char* tip;
} Cheltuiala;

/**
    @brief Funcție care creează o listă goală
    @param id id-ul cheltuielii
    @param zi ziua cheltuielii
    @param suma suma cheltuielii
    @param tip tipul cheltuielii
    @return cheltuiala
*/
Cheltuiala creeazaCheltuiala(int id, int zi, int suma, char* tip);

/**
    @brief Funcție distruge o cheltuială
    @param cheltuiala cheltuiala de distrus
*/
void distrugeCheltuiala(Cheltuiala* cheltuiala);

/**
    @brief Funcție care validează o cheltuială
    @return 0 dacă este validă, 1 pentru id neconform, 2 pentru zi neconformă, 3 pentru suma neconformă, 4 pentru tip neconform (tipul poate fi doar "mancare"/"transport"/"internet"/"imbracaminte"/"altele")
*/
int valideazaCheltuiala(Cheltuiala cheltuiala);

/**
    @brief Funcție care copiază o cheltuială dată
    @param cheltuiala cheltuiala de copiat
    @return cheltuiala "nouă"
*/
Cheltuiala copiazaCheltuiala(Cheltuiala* cheltuiala);
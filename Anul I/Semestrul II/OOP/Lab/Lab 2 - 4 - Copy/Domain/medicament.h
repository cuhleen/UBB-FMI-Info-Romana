#pragma once
typedef struct {
    int id;
    int concentratie;
    int cantitate;
    char* nume;
} Medicament;

/**
    @brief Funcție care creează o listă goală
    @param id id-ul cheltuielii
    @param concentratie ziua cheltuielii
    @param cantitate cantitate cheltuielii
    @param nume tipul cheltuielii
    @return medicament
*/
Medicament* creeazaMedicament(int id, int concentratie, int cantitate, char* nume);

/**
    @brief Funcție distruge un medicament
    @param medicament medicament de distrus
*/
void distrugeMedicament(Medicament* medicament);

/**
    @brief Funcție care validează un medicament
    @return 0 dacă este validă, 1 pentru id neconform, 2 pentru concentratie neconformă, 3 pentru cantitate neconformă, 4 pentru nume neconform
*/
int valideazaMedicament(Medicament* medicament);

/**
    @brief Funcție care copiază un medicament dată
    @param medicament medicament de copiat
    @return medicament "nou"
*/
Medicament* copiazaMedicament(Medicament* medicament);
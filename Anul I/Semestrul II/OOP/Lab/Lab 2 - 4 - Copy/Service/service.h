#pragma once

#include "../Repo/lista.h"

typedef int(*FunctieComparare)(Medicament* medicament1, Medicament* medicament2);

typedef struct {
	Lista* Medicamente;
	Lista* undoStack;
} Farmacie;

/**
	@brief Funcție care creează o farmacie goală
	@return farmacie goală
*/
Farmacie creeazaFarmacie();

/**
	@brief Funcție care dealocă o farmacie
*/
void distrugeFarmacie(Farmacie* farmacie);

/**
	@brief Funcție care creează un medicament și o adaugă în farmacie
	@param farmacie farmacia în care adăugăm
	@param id id-ul medicamentului
	@param concentratie concentratia medicamentului
	@param cantitate cantitatea medicamentului
	@param nume numele medicamentului
	@return 0 dacă a reușit să șteargă, erori de validare altfel
*/
int adaugaMedicamentInFarmacie(Farmacie* farmacie, int id, int concentratie, int cantitate, char* nume);

/**
	@brief Funcție care modifică un medicament după id într-o farmacie dată
	@param farmacie farmacia în care căutăm
	@param id id-ul medicamentului
	@param concentratie concentratia medicamentului
	@param cantitate cantitatea medicamentului
	@param nume numele medicamentului
	@return 0 dacă a reușit să șteargă, 1 sau erori de validare altfel
*/
int modificaMedicamentInFarmacie(Farmacie* farmacie, int id, int concentratie, int cantitate, char* nume);

/**
	@brief Funcție care șterge un medicament după id într-un farmacie dat
	@param farmacie farmacia în care căutăm
	@param id id-ul medicamentului pe care îl căutăm
	@return 0 dacă a reușit să șteargă, 1 altfel
*/
int stergeMedicamentDinFarmacie(Farmacie* farmacie, int id);

/**
	@brief Funcție care face undo, reîntorcând datele curente la o versiune anterioară
	@param farmacie
	@return
 */
int undo(Farmacie* farmacie);

/**
	@brief Funcție care filtrează medicamentele unui farmacie dat după concentratie
	@param farmacie farmacia în care căutăm
	@param concentratie concentratieua după care se filtrează
	@return o listă cu medicamentele filtrate
*/
Lista* filtreazaDupaConcentratie(Farmacie* farmacie, int concentratie);

/**
	@brief Funcție care filtrează medicamentele unui farmacie dat după cantitate
	@param farmacie farmacia în care căutăm
	@param cantitate cantitate după care se filtrează
	@return o listă cu medicamentele filtrate
*/
Lista* filtreazaDupaCantitate(Farmacie* farmacie, int cantitate);

/**
	@brief Funcție care filtrează medicamentele unui farmacie dat după nume
	@param farmacie farmacia în care căutăm
	@param nume numele după care se filtrează
	@return o listă cu medicamentele filtrate
*/
Lista* filtreazaDupaNume(Farmacie* farmacie, char* nume);

/**
	@brief Funcție care sortează medicamentele unei liste date după o funcție
	@param listaDeSortat lista de sortat
	@param functieDeComparare funcție de comparare
*/
void sorteaza(Lista* listaDeSortat, FunctieComparare functieDeComparare);
//void ruleazaToateTestele();

/**
	Următoarele funcții funcționează astfel:

		comparareDupa[Chestie][ÎnMod]
	[Chestie] fiind unul dintre atributele medicamentului, id, concentratie, cantitate, nume
	[ÎnMod] fiind crescător sau descrescător. Adică este doar "return c1->[Chestie] > c2->[Chestie]" pentru crescător, "return c1->[Chestie] < c2->[Chestie]" pentru descrescător.
		sorteazaDupa[Chestie]
	[Chestie] este la fel, un atribut
	Funcția ia ca parametru și modul în care se compară
 */

int comparareDupaIdCrescator(Medicament* medicament1, Medicament* medicament2);

int comparareDupaIdDescrescator(Medicament* medicament1, Medicament* medicament2);

Lista* sorteazaDupaId(Farmacie* farmacie, int ordineDeComparare);


int comparareDupaConcentratieCrescator(Medicament* medicament1, Medicament* medicament2);

int comparareDupaConcentratieDescrescator(Medicament* medicament1, Medicament* medicament2);

Lista* sorteazaDupaConcentratie(Farmacie* farmacie, int ordineDeComparare);


int comparareDupaCantitateCrescator(Medicament* medicament1, Medicament* medicament2);

int comparareDupaCantitateDescrescator(Medicament* medicament1, Medicament* medicament2);

Lista* sorteazaDupaCantitate(Farmacie* farmacie, int ordineDeComparare);


int comparareDupaNumeCrescator(Medicament* medicament1, Medicament* medicament2);

int comparareDupaNumeDescrescator(Medicament* medicament1, Medicament* medicament2);

Lista* sorteazaDupaNume(Farmacie* farmacie, int ordineDeComparare);
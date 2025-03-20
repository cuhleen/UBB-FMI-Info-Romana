#pragma once

#include "../Repo/lista.h"

typedef int(*FunctieComparare)(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

typedef struct {
	Lista Cheltuieli;
} BugetFamilie;

/**
	@brief Funcție care creează un buget gol
	@return bugetul gol
*/
BugetFamilie creeazaBugetFamilie();

/**
	Funcție care dealocă un buget
*/
void distrugeBugetFamilie(BugetFamilie* buget);

/**
	@brief Funcție care creează o cheltuială și o adaugă în buget
	@param buget bugetul în care adăugăm
	@param id id-ul cheltuielii
	@param zi ziua cheltuielii
	@param suma suma cheltuielii
	@param tip tipul cheltuielii
	@return 0 dacă a reușit să șteargă, erori de validare altfel
*/
int adaugaCheltuialaInBuget(BugetFamilie* buget, int id, int zi, int suma, char* tip);

/**
	@brief Funcție care modifică o cheltuială după id într-un buget dat
	@param buget bugetul în care căutăm
	@param id id-ul cheltuielii
	@param zi ziua cheltuielii
	@param suma suma cheltuielii
	@param tip tipul cheltuielii
	@return 0 dacă a reușit să șteargă, 1 sau erori de validare altfel
*/
int modificaCheltuialaDinBuget(BugetFamilie* buget, int id, int zi, int suma, char* tip);

/**
	@brief Funcție care șterge o cheltuială după id într-un buget dat
	@param buget bugetul în care căutăm
	@param id id-ul cheltuielii pe care o căutăm
	@return 0 dacă a reușit să șteargă, 1 altfel
*/
int stergeCheltuialaDinBuget(BugetFamilie* buget, int id);

/**
	@brief Funcție care filtrează cheltuielile unui buget dat după zi
	@param buget bugetul în care căutăm
	@param zi ziua după care se filtrează
	@return o listă cu cheltuielile filtrate
*/
Lista filtreazaDupaZi(BugetFamilie* buget, int zi);

/**
	@brief Funcție care filtrează cheltuielile unui buget dat după suma
	@param buget bugetul în care căutăm
	@param suma suma după care se filtrează
	@return o listă cu cheltuielile filtrate
*/
Lista filtreazaDupaSuma(BugetFamilie* buget, int suma);

/**
	@brief Funcție care filtrează cheltuielile unui buget dat după tip
	@param buget bugetul în care căutăm
	@param tip tipul după care se filtrează
	@return o listă cu cheltuielile filtrate
*/
Lista filtreazaDupaTip(BugetFamilie* buget, char* tip);

/**
	@brief Funcție care sortează cheltuielile unei liste date după o funcție
	@param listaDeSortat lista de sortat
	@param functieDeComparare funcție de comparare
*/
void sorteaza(Lista* listaDeSortat, FunctieComparare functieDeComparare);

/**
	Următoarele funcții funcționează astfel:

		comparareDupa[Chestie][ÎnMod]
	[Chestie] fiind unul dintre atributele cheltuielii, id, zi, suma, tip
	[ÎnMod] fiind crescător sau descrescător. Adică este doar "return c1->[Chestie] > c2->[Chestie]" pentru crescător, "return c1->[Chestie] < c2->[Chestie]" pentru descrescător.
		sorteazaDupa[Chestie]
	[Chestie] este la fel, un atribut
	Funcția ia ca parametru și modul în care se compară
 */

int comparareDupaIdCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaIdDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaId(BugetFamilie* buget, int ordineDeComparare);


int comparareDupaZiCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaZiDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaZi(BugetFamilie* buget, int ordineDeComparare);


int comparareDupaSumaCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaSumaDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaSuma(BugetFamilie* buget, int ordineDeComparare);


int comparareDupaTipCrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

int comparareDupaTipDescrescator(Cheltuiala* cheltuiala1, Cheltuiala* cheltuiala2);

Lista sorteazaDupaTip(BugetFamilie* buget, int ordineDeComparare);
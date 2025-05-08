#include "Colectie.h"
#include "IteratorColectie.h"
#include <exception>
#include <iostream>

using namespace std;

// Complexitate = BC = WC = AC = θ(1)
Colectie::Colectie() {
	capacitate = 20;
	dimensiune = 0;
	dimensiuneIndividuala = 0;
	elemente = new TElem[capacitate];
	frecventa = new int[capacitate];
}

// Complexitate = BC = WC = AC = θ(n)
void Colectie::redimensionare()
{
	TElem *elementeRedim = new TElem[capacitate * 2];
	int *frecventaRedim = new int[capacitate * 2];

	for (int i = 0; i < dimensiune; i++)
	{
		elementeRedim[i] = elemente[i];
		frecventaRedim[i] = frecventa[i];
	}

	capacitate *= 2;
	delete [] elemente;
	delete [] frecventa;
	elemente = elementeRedim;
	frecventa = frecventaRedim;
}

// Complexitate = BC = θ(1), WC = θ(n), AC = O(1) amortizat
void Colectie::adauga(TElem elem) {
	if (dimensiune == capacitate)
		redimensionare();

	for (int i = 0; i < dimensiuneIndividuala; i++)
		if (elemente[i] == elem)
		{
			frecventa[i]++;
			dimensiune++;
			return;
		}
	elemente[dimensiuneIndividuala] = elem;
	frecventa[dimensiuneIndividuala] = 1;
	dimensiuneIndividuala++;
	dimensiune++;
}

// Complexitate = BC = θ(1), WC = θ(n), AC = O(1) amortizat
void Colectie::adaugaAparitiiMultiple(int nr, TElem elem) {
	if (nr < 0)
		throw std::exception();

	if (nr == 0)
		return;

	if (dimensiune == capacitate)
		redimensionare();

	for (int i = 0; i < dimensiuneIndividuala; i++)
		if (elemente[i] == elem)
		{
			frecventa[i] += nr;
			dimensiune+=nr;
			return;
		}
	elemente[dimensiuneIndividuala] = elem;
	frecventa[dimensiuneIndividuala] = nr;
	dimensiuneIndividuala++;
	dimensiune+=nr;
}

/**
 *	Subalgoritmul adaugaAparitiiMultiple(nr, elem):
 *		Daca nr < 0 atunci
 *			arunca excepție "numar invalid!"
 *		Sf. Daca
 *
 *		Dacă nr = 0 atunci
 *			ieșire din program
 *		Sf. Daca
 *
 *		Daca dimensiune = capacitate
 *			redimensionare()
 *		Sf. Daca
 *
 *		Pentru i <- 0, dimensiuneIndividuala
 *			Daca elemente[i]
 *				frecventa[i] <- frecventa[i] + nr
 *				dimensiune[i] <- dimensiune[i] + nr
 *				iesire din program
 *			Sf. Daca
 *		Sf. Pentru
 *
 *		elemente[dimensiuneIndividuala] = elem
 *		frecventa[dimensiuneIndividuala] = nr
 *		dimensiuneIndividuala++
 *		dimensiune <- dimensiune + nr
 *
 *		Sf. adaugaAparitiiMultiple
 */

// Complexitate = BC = θ(1), WC = θ(n), AC = O(n)
bool Colectie::sterge(TElem elem) {
	for (int i = 0; i < dimensiuneIndividuala; i++)
		if (elemente[i] == elem)
		{
			frecventa[i]--;
			dimensiune--;
			if (frecventa[i] == 0)
			{
				for (int j = i; j < dimensiuneIndividuala; j++)
				{
					elemente[j] = elemente[j + 1];
					frecventa[j] = frecventa[j + 1];
				}
				dimensiuneIndividuala--;
			}
			return true;
		}
	return false;
}

// Complexitate = BC = θ(1), WC = θ(n), AC = O(n)
bool Colectie::cauta(TElem elem) const {
	for (int i = 0; i < dimensiuneIndividuala; i++)
		if (elem == elemente[i])
			return true;
	return false;
}

// Complexitate = BC = θ(n), WC = θ(n), AC = θ(n)
int Colectie::nrAparitii(TElem elem) const {
	for (int i = 0; i < dimensiuneIndividuala; i++)
		if (elem == elemente[i])
			return frecventa[i];
	return 0;
}

// Complexitate = BC = WC = AC = θ(1)
int Colectie::dim() const {
	return dimensiune;
}

// Complexitate = BC = WC = AC = θ(1)
int Colectie::dimIndiv() const
{
	return dimensiuneIndividuala;
}

// Complexitate = BC = WC = AC = θ(1)
bool Colectie::vida() const {

	return dimensiune == 0;
}

// Complexitate = BC = WC = AC = θ(1)
IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}

// Complexitate = BC = WC = AC = θ(1)
Colectie::~Colectie() {
	delete [] elemente;
	delete [] frecventa;
}



#include "lista.h"

#include <stdlib.h>

Lista creeazaListaGoala() {
	Lista lista;
	lista.dimensiune = 0;
	lista.capacitate = 2;
	lista.elemente = malloc(sizeof(TipElement) * lista.capacitate);
	return lista;
}

void distrugeLista(Lista* lista) {
	for (int i = 0; i < lista->dimensiune; i++) {
		distrugeCheltuiala(&lista->elemente[i]);
	}
	free(lista->elemente);
	lista->elemente = NULL;
	lista->dimensiune = 0;
}

TipElement getElement(Lista* lista, int pozitieElementInLista) {
	return lista->elemente[pozitieElementInLista];
}

TipElement setElement(Lista* lista, int pozitieElementInLista, Cheltuiala cheltuiala) {
	TipElement cheltuialaVeche = lista->elemente[pozitieElementInLista];
	lista->elemente[pozitieElementInLista] = cheltuiala;
	return cheltuialaVeche;
}

int size(Lista* lista) {
	return lista->dimensiune;
}

void adaugaElementInLista(Lista* lista, TipElement element) {
	int esteOK = asiguraCapacitate(lista);
	if (esteOK == 0) {
		for (int i = 0; i < lista->dimensiune; i++)
		{
			if (lista->elemente[i].id == element.id)
			{

				return;
			}
		}
		lista->elemente[lista->dimensiune] = element;
		lista->dimensiune++;
	}
}

int asiguraCapacitate(Lista* lista) {
	if (lista->dimensiune < lista->capacitate) {
		return 0;
	}
	int nouaCapacitate = lista->capacitate * 2;
	TipElement* elemente = malloc(sizeof(TipElement) * nouaCapacitate);
	if (elemente == NULL) {
		return 1;
	}
	for (int i = 0; i < nouaCapacitate; i++) {
		if (i < lista->dimensiune)
			elemente[i] = lista->elemente[i];
	}
	free(lista->elemente);
	lista->elemente = elemente;
	lista->capacitate = nouaCapacitate;
	return 0;
}

int modificaElementDinLista(Lista* lista, Cheltuiala cheltuialaModificata) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Cheltuiala cheltuialaCurenta = getElement(lista, i);
		if (cheltuialaCurenta.id == cheltuialaModificata.id) {
			lista->elemente[i] = cheltuialaModificata;
			distrugeCheltuiala(&cheltuialaCurenta);
			return 0;
		}
	}
	return 1;
}

int stergeElementDinLista(Lista* lista, int id) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Cheltuiala cheltuiala = getElement(lista, i);
		if (cheltuiala.id == id) {
			distrugeCheltuiala(&cheltuiala);
			for (int j = i; j <= lista->dimensiune; j++) {
				lista->elemente[j] = lista->elemente[j + 1];
			}
			lista->dimensiune--;
			return 0;
		}
	}
	return 1;
}

int cautaDupaId(Lista* lista, int id) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Cheltuiala cheltuiala = getElement(lista, i);
		if (cheltuiala.id == id)
			return 10;
	}
	return 11;
}

Lista copiazaLista(Lista* lista) {
	Lista listaCopiata = creeazaListaGoala();
	for (int i = 0; i < size(lista); i++) {
		TipElement cheltuiala = getElement(lista, i);
		adaugaElementInLista(&listaCopiata, copiazaCheltuiala(&cheltuiala));
	}
	return listaCopiata;
}
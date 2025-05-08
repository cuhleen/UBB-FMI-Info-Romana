#include "lista.h"

#include <stdlib.h>

Lista *creeazaListaGoala(DestroyFunction destroyFct) {
	Lista *lista = (Lista*)malloc(sizeof(Lista));
	lista->dimensiune = 0;
	lista->capacitate = 2;
	lista->elemente = (TipElement*)malloc(sizeof(TipElement) * lista->capacitate);

	lista->destroyFct = destroyFct;

	return lista;
}

void distrugeLista(Lista* lista) {
	for (int i = 0; i < lista->dimensiune; i++) {
		lista->destroyFct(lista->elemente[i]);
	}
	free(lista->elemente);
	free(lista);
}

TipElement getElement(Lista* lista, int pos)
{
	return lista->elemente[pos];
}

TipElement setElement(Lista* lista, int pos, TipElement medicament)
{
	TipElement medicamentNou =lista->elemente[pos];
	lista->elemente[pos] = medicament;
	return medicamentNou;
}

int size(Lista* lista) {
	return lista->dimensiune;
}

void adaugaElementInLista(Lista* lista, TipElement element) {
	int esteOK = asiguraCapacitate(lista);
	if (esteOK == 0) {
		for (int i = 0; i < lista->dimensiune; i++)
		{
			Medicament* medExistent = lista->elemente[i];
			Medicament* medNou = element;
			if (medExistent->id == medNou->id)
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

int modificaElementDinLista(Lista* lista, TipElement medicamentModificat) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Medicament* medicamentCurent = getElement(lista, i);
		Medicament* medModif = medicamentModificat;
		if (medicamentCurent->id == medModif->id) {
			distrugeMedicament(setElement(lista, i, medicamentModificat));
			return 0;
		}
	}
	return 1;
}

int stergeElementDinLista(Lista* lista, int id) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Medicament* medicament = getElement(lista, i);
		if (medicament->id == id) {
			distrugeMedicament(medicament);
			for (int j = i; j <= lista->dimensiune; j++) {
				lista->elemente[j] = lista->elemente[j + 1];
			}
			lista->dimensiune--;
			return 0;
		}
	}
	return 1;
}

TipElement stergeUltimulElementDinLista(Lista* lista)
{
	TipElement elementDeSters =lista->elemente[lista->dimensiune - 1];
	lista->dimensiune -= 1;
	return elementDeSters;
}

int cautaDupaId(Lista* lista, int id) {
	for (int i = 0; i < lista->dimensiune; i++) {
		Medicament* medicament = getElement(lista, i);
		if (medicament->id == id)
			return 10;
	}
	return 11;
}

Lista* copiazaLista(Lista* lista, CopyFunction copyFct) {
	Lista* listaCopiata = creeazaListaGoala(lista->destroyFct);
	for (int i = 0; i < lista->dimensiune; i++) {
		TipElement medicament = getElement(lista, i);
		adaugaElementInLista(listaCopiata, copyFct(medicament));
	}
	return listaCopiata;
}
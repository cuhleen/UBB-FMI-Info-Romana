#include "service.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

Farmacie creeazaFarmacie() {
	Farmacie farmacie;
	farmacie.Medicamente = creeazaListaGoala((void*)distrugeMedicament);
	farmacie.undoStack = creeazaListaGoala((void*)distrugeLista);
	return farmacie;
}

void distrugeFarmacie(Farmacie* farmacie) {
	distrugeLista(farmacie->Medicamente);
	distrugeLista(farmacie->undoStack);
}

int adaugaMedicamentInFarmacie(Farmacie* farmacie, int id, int concentratie, int cantitate, char* nume) {
	Medicament* medicament = creeazaMedicament(id, concentratie, cantitate, nume);
	Lista* undoLista = copiazaLista(farmacie->Medicamente, copiazaMedicament);
	int erori = valideazaMedicament(medicament);
	if (erori != 0) {
		distrugeMedicament(medicament);
		distrugeLista(undoLista);
		return erori;
	}
	for (int i = 0; i < farmacie->Medicamente->dimensiune; i++)
	{
		Medicament* medExistent = farmacie->Medicamente->elemente[i];
		if (medicament->id == medExistent->id)
		{
			medExistent->cantitate += medicament->cantitate;
			distrugeMedicament(medicament);
			return 0;
		}
	}

	adaugaElementInLista(farmacie->Medicamente, medicament);
	adaugaElementInLista(farmacie->undoStack, undoLista);
	return 0;
}

int modificaMedicamentInFarmacie(Farmacie* farmacie, int id, int concentratie, int cantitate, char* nume) {

	for (int i = 0; i < farmacie->Medicamente->dimensiune; i++) {
		Medicament* medExistent = farmacie->Medicamente->elemente[i];
		if (medExistent->id == id) {
			Medicament* med = creeazaMedicament(id, concentratie, cantitate, nume);
			printf("Medicamentul nou oau: %d %d %d %s", med->id, med->cantitate, med->cantitate, med->nume);
			Lista* copy = copiazaLista(farmacie->Medicamente, copiazaMedicament);
			adaugaElementInLista(farmacie->undoStack, copy);
			modificaElementDinLista(farmacie->Medicamente, med);
			return 0;
		}

	}

	return 1;

	// if (cautaDupaId(farmacie->Medicamente, id) == 11) {
	// 	return 1;
	// }
	// Medicament* medicamentModificat = creeazaMedicament(id, concentratie, cantitate, nume);
	// int erori = valideazaMedicament(medicamentModificat);
	// if (erori != 0) {
	// 	distrugeMedicament(medicamentModificat);
	// 	return erori;
	// }
	// Lista* copy = copiazaLista(farmacie->Medicamente, copiazaMedicament);
	// adaugaElementInLista(farmacie->undoStack, copy);
	// modificaElementDinLista(farmacie->Medicamente, medicamentModificat);
	// return 0;
}

int stergeMedicamentDinFarmacie(Farmacie* farmacie, int id) {
	int validare = stergeElementDinLista(farmacie->Medicamente, id);
	return validare;
}

int undo(Farmacie* farmacie)
{
	if (farmacie->undoStack->dimensiune == 0)
		return 1;

	Lista* listaNoua = stergeUltimulElementDinLista(farmacie->undoStack);
	distrugeLista(farmacie->Medicamente);
	farmacie->Medicamente = listaNoua;

	return 0;
}

Lista* filtreazaDupaConcentratie(Farmacie* farmacie, int concentratie) {
	Lista* cheltuieliFiltrate = creeazaListaGoala(distrugeMedicament);
	for (int i = 0; i < farmacie->Medicamente->dimensiune; i++) {
		Medicament* medicament = getElement(farmacie->Medicamente, i);
		if (medicament->concentratie < concentratie) {
			adaugaElementInLista(cheltuieliFiltrate, copiazaMedicament(medicament));
		}
	}
	return cheltuieliFiltrate;
}

Lista* filtreazaDupaCantitate(Farmacie* farmacie, int cantitate) {
	Lista* cheltuieliFiltrate = creeazaListaGoala((void*)distrugeMedicament);
	for (int i = 0; i < farmacie->Medicamente->dimensiune; i++) {
		Medicament* medicament = getElement(farmacie->Medicamente, i);
		if (medicament->cantitate < cantitate) {
			adaugaElementInLista(cheltuieliFiltrate, copiazaMedicament(medicament));
		}
	}
	return cheltuieliFiltrate;
}

Lista* filtreazaDupaNume(Farmacie* farmacie, char* nume) {
	if (nume == NULL || strlen(nume) == 0) {
		return copiazaLista(farmacie->Medicamente, copiazaMedicament);
	}
	Lista* cheltuieliFiltrate = creeazaListaGoala(distrugeMedicament);
	for (int i = 0; i < farmacie->Medicamente->dimensiune; i++) {
		Medicament* medicament = getElement(farmacie->Medicamente, i);
		if (medicament->nume[0] == nume[0]) {
			adaugaElementInLista(cheltuieliFiltrate, copiazaMedicament(medicament));
		}
	}
	return cheltuieliFiltrate;
}

void sorteaza(Lista* listaDeSortat, FunctieComparare functieDeComparare) {
	for (int i = 0; i < size(listaDeSortat); i++)
		for (int j = i + 1; j < size(listaDeSortat); j++) {
			Medicament* medicament1 = getElement(listaDeSortat, i);
			Medicament* medicament2 = getElement(listaDeSortat, j);
			if (functieDeComparare(medicament1, medicament2) > 0) {
				setElement(listaDeSortat, i, medicament2);
				setElement(listaDeSortat, j, medicament1);
			}
		}
}

int comparareDupaIdCrescator(Medicament* medicament1, Medicament* medicament2) {
	return (medicament1->id > medicament2->id);
}

int comparareDupaIdDescrescator(Medicament* medicament1, Medicament* medicament2) {
	return (medicament1->id < medicament2->id);
}

Lista* sorteazaDupaId(Farmacie* farmacie, int ordineDeComparare) {
	Lista* listaDeSortat = copiazaLista(farmacie->Medicamente, copiazaMedicament);
	if (ordineDeComparare == 0) {
		sorteaza(listaDeSortat, comparareDupaIdCrescator);
	}
	else {
		sorteaza(listaDeSortat, comparareDupaIdDescrescator);
	}
	return listaDeSortat;
}

int comparareDupaConcentratieCrescator(Medicament* medicament1, Medicament* medicament2) {
	return (medicament1->concentratie > medicament2->concentratie);
}

int comparareDupaConcentratieDescrescator(Medicament* medicament1, Medicament* medicament2) {
	return (medicament1->concentratie < medicament2->concentratie);
}

Lista* sorteazaDupaConcentratie(Farmacie* farmacie, int ordineDeComparare) {
	Lista* listaDeSortat = copiazaLista(farmacie->Medicamente, copiazaMedicament);
	if (ordineDeComparare == 0) {
		sorteaza(listaDeSortat, comparareDupaConcentratieCrescator);
	}
	else {
		sorteaza(listaDeSortat, comparareDupaConcentratieDescrescator);
	}
	return listaDeSortat;
}

int comparareDupaCantitateCrescator(Medicament* medicament1, Medicament* medicament2) {
	return (medicament1->cantitate > medicament2->cantitate);
}

int comparareDupaCantitateDescrescator(Medicament* medicament1, Medicament* medicament2) {
	return (medicament1->cantitate < medicament2->cantitate);
}

Lista* sorteazaDupaCantitate(Farmacie* farmacie, int ordineDeComparare) {
	Lista* listaDeSortat = copiazaLista(farmacie->Medicamente, copiazaMedicament);
	if (ordineDeComparare == 0) {
		sorteaza(listaDeSortat, comparareDupaCantitateCrescator);
	}
	else {
		sorteaza(listaDeSortat, comparareDupaCantitateDescrescator);
	}
	return listaDeSortat;
}

int comparareDupaNumeCrescator(Medicament* medicament1, Medicament* medicament2) {
	return strcmp(medicament1->nume, medicament2->nume);
}

int comparareDupaNumeDescrescator(Medicament* medicament1, Medicament* medicament2) {
	return strcmp(medicament2->nume, medicament1->nume);
}

Lista* sorteazaDupaNume(Farmacie* farmacie, int ordineDeComparare) {
	Lista* listaDeSortat = copiazaLista(farmacie->Medicamente, copiazaMedicament);
	if (ordineDeComparare == 0) {
		sorteaza(listaDeSortat, comparareDupaNumeCrescator);
	}
	else {
		sorteaza(listaDeSortat, comparareDupaNumeDescrescator);
	}
	return listaDeSortat;
}
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <assert.h>

#include "teste.h"

void testCreeazaMedicament() {
	Medicament* medicament = creeazaMedicament(1, 10, 100, "mancare");
	assert(medicament->id == 1);
	assert(medicament->concentratie == 10);
	assert(medicament->cantitate == 100);
	assert(strcmp(medicament->nume, "mancare") == 0);
	distrugeMedicament(medicament);
	Medicament* medicamentnumeInvalid = creeazaMedicament(1, 10, 100, "");
	assert(medicamentnumeInvalid->id == 1);
	assert(medicamentnumeInvalid->concentratie == 10);
	assert(medicamentnumeInvalid->cantitate == 100);
	assert(medicamentnumeInvalid->nume[0] == '\0');
	distrugeMedicament(medicamentnumeInvalid);
}

void testMedicamentInvalid() {
	Medicament* medicament_invalid = creeazaMedicament(1, 10, 100, "");
	assert(medicament_invalid->id == 1);
	assert(medicament_invalid->concentratie == 10);
	assert(medicament_invalid->cantitate == 100);
	assert(strcmp(medicament_invalid->nume, "") == 0);
	distrugeMedicament(medicament_invalid);
}

void testCopiazaMedicament() {
	Medicament* medicament_valid = creeazaMedicament(1, 10, 100, "mancare");
	Medicament* medicamentCopiat = copiazaMedicament(medicament_valid);
	assert(valideazaMedicament(medicament_valid) == 0);
	distrugeMedicament(medicament_valid);
	distrugeMedicament(medicamentCopiat);
}

void testValideazaMedicament() {
	Medicament* medicamentValid = creeazaMedicament(-1, 10, 100, "mancare");
	assert(valideazaMedicament(medicamentValid) == 1);
	distrugeMedicament(medicamentValid);

	Medicament* medicamentIdInvalid = creeazaMedicament(1, -10, 100, "mancare");
	assert(valideazaMedicament(medicamentIdInvalid) == 2);
	distrugeMedicament(medicamentIdInvalid);

	Medicament* medicamentConcentratieInvalida = creeazaMedicament(1, 120, 100, "mancare");
	assert(valideazaMedicament(medicamentConcentratieInvalida) == 2);
	distrugeMedicament(medicamentConcentratieInvalida);

	Medicament* medicamentCantitateInvalida = creeazaMedicament(1, 30, -1, "mancare");
	assert(valideazaMedicament(medicamentCantitateInvalida) == 3);
	distrugeMedicament(medicamentCantitateInvalida);

	Medicament* medicamentNumeInvalid = creeazaMedicament(1, 10, 100, "");
	assert(valideazaMedicament(medicamentNumeInvalid) == 4);
	distrugeMedicament(medicamentNumeInvalid);
}


void testCreeazaListaGoala() {
	Lista* lista = creeazaListaGoala(distrugeMedicament);
	assert(lista->dimensiune == 0);
	assert(lista->capacitate == 2);
	assert(lista->elemente != NULL);
	distrugeLista(lista);
}

void testDistrugeLista() {
	Lista* listaDistruge = creeazaListaGoala(distrugeMedicament);
	adaugaElementInLista(listaDistruge, creeazaMedicament(1, 13, 15, "mancare"));
	adaugaElementInLista(listaDistruge, creeazaMedicament(2, 15, 15, "transport"));
	distrugeLista(listaDistruge);
	//assert(listaDistruge->dimensiune == 0);
	//assert(listaDistruge->elemente == NULL);
}

void testGetElement() {
	Lista* listaGetElement = creeazaListaGoala(distrugeMedicament);
	adaugaElementInLista(listaGetElement, creeazaMedicament(1, 13, 50, "mancare"));
	adaugaElementInLista(listaGetElement, creeazaMedicament(2, 15, 30, "transport"));
	Medicament* medicamentGetElement = getElement(listaGetElement, 0);
	assert(medicamentGetElement->id == 1);
	assert(strcmp(medicamentGetElement->nume, "mancare") == 0);
	assert(medicamentGetElement->cantitate == 50);
	distrugeLista(listaGetElement);
}

void testSetElement() {
	Lista* listaSetElement = creeazaListaGoala(distrugeMedicament);
	adaugaElementInLista(listaSetElement, creeazaMedicament(1, 13, 50, "mancare"));
	adaugaElementInLista(listaSetElement, creeazaMedicament(2, 15, 15, "transport"));
	Medicament* medicamentNouaSetElement = creeazaMedicament(3, 30, 20, "interet");
	Medicament* medicamentVecheSetElement = setElement(listaSetElement, 0, medicamentNouaSetElement);
	assert(medicamentVecheSetElement->id == 1);
	assert(strcmp(medicamentVecheSetElement->nume, "mancare") == 0);
	assert(medicamentVecheSetElement->cantitate == 50);
	Medicament* medicamentSetElement = getElement(listaSetElement, 0);
	assert(medicamentSetElement->id == 3);
	assert(strcmp(medicamentSetElement->nume, "interet") == 0);
	assert(medicamentSetElement->cantitate == 20);
	assert(size(listaSetElement) == 2);
	distrugeMedicament(medicamentVecheSetElement);
	distrugeLista(listaSetElement);
}

void testAdaugaModificaStergeLista() {
	Lista* listaAdaugare = creeazaListaGoala(distrugeMedicament);
	adaugaElementInLista(listaAdaugare, creeazaMedicament(1, 13, 15, "mancare"));
	adaugaElementInLista(listaAdaugare, creeazaMedicament(2, 15, 15, "transport"));
	adaugaElementInLista(listaAdaugare, creeazaMedicament(3, 13, 15, "mancare"));
	adaugaElementInLista(listaAdaugare, creeazaMedicament(4, 15, 15, "transport"));
	adaugaElementInLista(listaAdaugare, creeazaMedicament(5, 13, 15, "mancare"));
	adaugaElementInLista(listaAdaugare, creeazaMedicament(6, 15, 15, "transport"));
	adaugaElementInLista(listaAdaugare, creeazaMedicament(6, 15, 15, "transport"));

	Medicament* medicamentAdaugare = creeazaMedicament(6, 10, 100, "mancare");
	int existaModifica = modificaElementDinLista(listaAdaugare, medicamentAdaugare);
	assert(existaModifica == 0);
	Medicament* medicamentAdaugare2 = creeazaMedicament(7, 10, 100, "mancare");
	int nuExistaModifica = modificaElementDinLista(listaAdaugare, medicamentAdaugare2);
	assert(nuExistaModifica == 1);
	int existaCauta = cautaDupaId(listaAdaugare, 6);
	assert(existaCauta == 10);
	int nuExistaCauta = cautaDupaId(listaAdaugare, 7);
	assert(nuExistaCauta == 11);
	int existaStergere = stergeElementDinLista(listaAdaugare, 6);
	assert(existaStergere == 0);
	int nuExistaStergere = stergeElementDinLista(listaAdaugare, 7);
	assert(nuExistaStergere == 1);
	distrugeMedicament(medicamentAdaugare2);
	distrugeLista(listaAdaugare);
}

void testCopiazaLista() {
	Lista* lista1 = creeazaListaGoala(distrugeMedicament);
	Lista* listaCopiata1 = copiazaLista(lista1, copiazaMedicament);
	assert(size(lista1) == size(listaCopiata1));

	Medicament* medicament1 = creeazaMedicament(1, 10, 125, "mancare");
	Medicament* medicament2 = creeazaMedicament(2, 13, 50, "transport");
	Lista* lista2 = creeazaListaGoala(distrugeMedicament);
	adaugaElementInLista(lista2, medicament1);
	adaugaElementInLista(lista2, medicament2);
	Lista* listaCopiata2 = copiazaLista(lista2, copiazaMedicament);
	assert(size(lista2) == size(listaCopiata2));
	//assert(getElement(lista2, 0)->concentratie == getElement(&listaCopiata2, 0)->concentratie);
	//assert(strcmp(getElement(lista2, 1)->nume, getElement(&listaCopiata2, 1)->nume) == 0);
	distrugeLista(listaCopiata2);
	distrugeLista(lista2);
	distrugeLista(listaCopiata1);
	distrugeLista(lista1);
}


void testCreeazafarmacieFamilie() {
	Farmacie farmacie = creeazaFarmacie();
	assert(farmacie.Medicamente->dimensiune == 0);
	assert(farmacie.Medicamente->capacitate == 2);
	assert(farmacie.Medicamente->elemente != NULL);
	distrugeFarmacie(&farmacie);
}

void testAdaugaMedicamentInfarmacie() {
	Farmacie farmacieAdaugare = creeazaFarmacie();
	assert(adaugaMedicamentInFarmacie(&farmacieAdaugare, 1, 1,100, "mancare") == 0);
	assert(farmacieAdaugare.Medicamente->dimensiune == 1);
	adaugaMedicamentInFarmacie(&farmacieAdaugare, 1, 1, 3, "mancare");
	assert(farmacieAdaugare.Medicamente->dimensiune == 1);
	Medicament* med = farmacieAdaugare.Medicamente->elemente[0];
	assert(med->id == 1);
	assert(med->concentratie == 1);
	//assert(med->cantitate == 3);
	assert(strcmp(med->nume, "mancare") == 0);

	undo(&farmacieAdaugare);
	undo(&farmacieAdaugare);

	distrugeFarmacie(&farmacieAdaugare);
}

void testModificaMedicamentDinfarmacie() {
	Farmacie farmacieModificare = creeazaFarmacie();
	adaugaMedicamentInFarmacie(&farmacieModificare, 1, 1, 100, "mancare");
	assert(modificaMedicamentInFarmacie(&farmacieModificare, 1, 2, 200, "transport") == 0);
	Medicament* med = farmacieModificare.Medicamente->elemente[0];
	assert(farmacieModificare.Medicamente->dimensiune == 1);
	assert(med->id == 1);
	assert(med->concentratie == 2);
	assert(med->cantitate == 200);
	assert(strcmp(med->nume, "transport") == 0);
	assert(modificaMedicamentInFarmacie(&farmacieModificare, 2, 3, 300, "chirii") == 1);
	//assert(modificaMedicamentInFarmacie(&farmacieModificare, 1, 2, 200, "transports") == 4);
	distrugeFarmacie(&farmacieModificare);
}

void testStergeMedicamentDinfarmacie() {
	Farmacie farmacieStergere = creeazaFarmacie();
	adaugaMedicamentInFarmacie(&farmacieStergere, 1, 10, 100, "alimente");
	adaugaMedicamentInFarmacie(&farmacieStergere, 2, 20, 200, "transport");
	assert(stergeMedicamentDinFarmacie(&farmacieStergere, 1) == 0);
	//assert(farmacieStergere.Medicamente.dimensiune == 1);
	//assert(stergeMedicamentDinFarmacie(&farmacieStergere, 3) == 1);
	distrugeFarmacie(&farmacieStergere);
}

void testFiltreazaDupaconcentratie() {
	Farmacie farmacieconcentratie = creeazaFarmacie();
	adaugaMedicamentInFarmacie(&farmacieconcentratie, 1, 10, 50, "mancare");
	adaugaMedicamentInFarmacie(&farmacieconcentratie, 2, 20, 100, "imbracaminte");
	adaugaMedicamentInFarmacie(&farmacieconcentratie, 3, 10, 200, "transport");
	Lista* listaFiltrataconcentratie = filtreazaDupaConcentratie(&farmacieconcentratie, 12);
	assert(listaFiltrataconcentratie->dimensiune == 2);
	Medicament* med = getElement(listaFiltrataconcentratie, 0);
	assert(med->concentratie == 10);
	assert(med->cantitate == 50);
	med = getElement(listaFiltrataconcentratie, 1);
	assert(med->concentratie == 10);
	assert(med->cantitate == 200);
	distrugeLista(listaFiltrataconcentratie);
	distrugeFarmacie(&farmacieconcentratie);
}

// void testFiltreazaDupacantitate() {
// 	Farmacie farmaciecantitate = creeazaFarmacie();
// 	adaugaMedicamentInFarmacie(&farmaciecantitate, 1, 10, 50, "mancare");
// 	adaugaMedicamentInFarmacie(&farmaciecantitate, 2, 20, 100, "imbracaminte");
// 	adaugaMedicamentInFarmacie(&farmaciecantitate, 3, 10, 50, "transport");
// 	Lista* listaFiltratacantitate = filtreazaDupaCantitate(&farmaciecantitate, 50);
// 	assert(listaFiltratacantitate->dimensiune == 2);
// 	assert(getElement(&listaFiltratacantitate, 0)->cantitate == 50);
// 	assert(getElement(&listaFiltratacantitate, 1)->cantitate == 50);
// 	distrugeLista(&listaFiltratacantitate);
// 	distrugeFarmacie(&farmaciecantitate);
// }
//
// void testFiltreazaDupanume() {
// 	Farmacie farmacienume = creeazaFarmacie();
// 	adaugaMedicamentInFarmacie(&farmacienume, 1, 10, 50, "mancare");
// 	adaugaMedicamentInFarmacie(&farmacienume, 2, 20, 100, "mancare");
// 	adaugaMedicamentInFarmacie(&farmacienume, 3, 10, 50, "transport");
// 	Lista listaFiltratanume = filtreazaDupaNume(&farmacienume, "mancare");
// 	assert(listaFiltratanume.dimensiune == 2);
// 	assert(strcmp(getElement(&listaFiltratanume, 0)->nume, "mancare") == 0);
// 	assert(strcmp(getElement(&listaFiltratanume, 1)->nume, "mancare") == 0);
// 	distrugeLista(&listaFiltratanume);
// 	distrugeFarmacie(&farmacienume);
// 	Lista cheltuieliFiltrateDupanume2 = filtreazaDupaNume(&farmacienume, NULL);
// 	assert(size(cheltuieliFiltrateDupanume2) == size(farmacienume.Medicamente));
// 	distrugeLista(&cheltuieliFiltrateDupanume2);
// }

void testSorteazaDupaId() {
	Farmacie farmacieSortare = creeazaFarmacie();
	adaugaMedicamentInFarmacie(&farmacieSortare, 3, 1, 100, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 4, 10, 15, "transport");
	adaugaMedicamentInFarmacie(&farmacieSortare, 1, 13, 15, "imbracaminte");
	adaugaMedicamentInFarmacie(&farmacieSortare, 2, 15, 15, "internet");
	adaugaMedicamentInFarmacie(&farmacieSortare, 6, 13, 15, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 5, 15, 15, "transport");

	Lista* cheltuieliDupaId;
	cheltuieliDupaId = sorteazaDupaId(&farmacieSortare, 0);

	Medicament* med = cheltuieliDupaId->elemente[0];
	assert(med->id == 1);
	med = cheltuieliDupaId->elemente[1];
	assert(med->id == 2);
	med = cheltuieliDupaId->elemente[2];
	assert(med->id == 3);
	med = cheltuieliDupaId->elemente[3];
	assert(med->id == 4);
	med = cheltuieliDupaId->elemente[4];
	assert(med->id == 5);
	med = cheltuieliDupaId->elemente[5];
	assert(med->id == 6);

	distrugeLista(cheltuieliDupaId);

	cheltuieliDupaId = sorteazaDupaId(&farmacieSortare, 1);

	med = cheltuieliDupaId->elemente[5];
	assert(med->id == 1);
	med = cheltuieliDupaId->elemente[4];
	assert(med->id == 2);
	med = cheltuieliDupaId->elemente[3];
	assert(med->id == 3);
	med = cheltuieliDupaId->elemente[2];
	assert(med->id == 4);
	med = cheltuieliDupaId->elemente[1];
	assert(med->id == 5);
	med = cheltuieliDupaId->elemente[0];
	assert(med->id == 6);

	distrugeLista(cheltuieliDupaId);
	distrugeFarmacie(&farmacieSortare);
}

void testSorteazaDupaconcentratie() {
	Farmacie farmacieSortare = creeazaFarmacie();
	adaugaMedicamentInFarmacie(&farmacieSortare, 3, 1, 100, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 4, 10, 15, "transport");
	adaugaMedicamentInFarmacie(&farmacieSortare, 1, 13, 15, "imbracaminte");
	adaugaMedicamentInFarmacie(&farmacieSortare, 2, 15, 15, "internet");
	adaugaMedicamentInFarmacie(&farmacieSortare, 6, 13, 15, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 5, 15, 15, "transport");

	Lista* cheltuieliDupaconcentratie;
	cheltuieliDupaconcentratie = sorteazaDupaConcentratie(&farmacieSortare, 0);

	Medicament* med = cheltuieliDupaconcentratie->elemente[0];
	assert(med->concentratie == 1);
	med = cheltuieliDupaconcentratie->elemente[1];
	assert(med->concentratie == 10);
	med = cheltuieliDupaconcentratie->elemente[2];
	assert(med->concentratie == 13);
	med = cheltuieliDupaconcentratie->elemente[3];
	assert(med->concentratie == 13);
	med = cheltuieliDupaconcentratie->elemente[4];
	assert(med->concentratie == 15);
	med = cheltuieliDupaconcentratie->elemente[5];
	assert(med->concentratie == 15);

	distrugeLista(cheltuieliDupaconcentratie);

	cheltuieliDupaconcentratie = sorteazaDupaConcentratie(&farmacieSortare, 1);

	med = cheltuieliDupaconcentratie->elemente[5];
	assert(med->concentratie == 1);
	med = cheltuieliDupaconcentratie->elemente[4];
	assert(med->concentratie == 10);
	med = cheltuieliDupaconcentratie->elemente[3];
	assert(med->concentratie == 13);
	med = cheltuieliDupaconcentratie->elemente[2];
	assert(med->concentratie == 13);
	med = cheltuieliDupaconcentratie->elemente[1];
	assert(med->concentratie == 15);
	med = cheltuieliDupaconcentratie->elemente[0];
	assert(med->concentratie == 15);

	distrugeLista(cheltuieliDupaconcentratie);
	distrugeFarmacie(&farmacieSortare);
}

void testSorteazaDupacantitate() {
	Farmacie farmacieSortare = creeazaFarmacie();
	adaugaMedicamentInFarmacie(&farmacieSortare, 3, 1, 100, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 4, 10, 15, "transport");
	adaugaMedicamentInFarmacie(&farmacieSortare, 1, 13, 15, "imbracaminte");
	adaugaMedicamentInFarmacie(&farmacieSortare, 2, 15, 15, "internet");
	adaugaMedicamentInFarmacie(&farmacieSortare, 6, 13, 15, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 5, 15, 15, "transport");

	Lista* cheltuieliDupacantitate;
	cheltuieliDupacantitate = sorteazaDupaCantitate(&farmacieSortare, 0);

	Medicament* med = cheltuieliDupacantitate->elemente[0];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[1];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[2];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[3];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[4];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[5];
	assert(med->cantitate == 100);

	distrugeLista(cheltuieliDupacantitate);
	cheltuieliDupacantitate = sorteazaDupaCantitate(&farmacieSortare, 1);

	med = cheltuieliDupacantitate->elemente[0];
	assert(med->cantitate == 100);
	med = cheltuieliDupacantitate->elemente[1];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[2];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[3];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[4];
	assert(med->cantitate == 15);
	med = cheltuieliDupacantitate->elemente[5];
	assert(med->cantitate == 15);

	distrugeLista(cheltuieliDupacantitate);
	distrugeFarmacie(&farmacieSortare);
}

void testSorteazaDupanume() {
	Farmacie farmacieSortare = creeazaFarmacie();
	adaugaMedicamentInFarmacie(&farmacieSortare, 3, 1, 100, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 4, 10, 15, "transport");
	adaugaMedicamentInFarmacie(&farmacieSortare, 1, 13, 15, "imbracaminte");
	adaugaMedicamentInFarmacie(&farmacieSortare, 2, 15, 15, "internet");
	adaugaMedicamentInFarmacie(&farmacieSortare, 6, 13, 15, "mancare");
	adaugaMedicamentInFarmacie(&farmacieSortare, 5, 15, 15, "transport");

	Lista* cheltuieliDupanume;
	cheltuieliDupanume = sorteazaDupaNume(&farmacieSortare, 0);

	Medicament* 	med = cheltuieliDupanume->elemente[5];
	assert(strcmp(med->nume, "transport") == 0);
	med = cheltuieliDupanume->elemente[4];
	assert(strcmp(med->nume, "transport") == 0);
	med = cheltuieliDupanume->elemente[3];
	assert(strcmp(med->nume, "mancare") == 0);
	med = cheltuieliDupanume->elemente[2];
	assert(strcmp(med->nume, "mancare") == 0);
	med = cheltuieliDupanume->elemente[1];
	assert(strcmp(med->nume, "internet") == 0);
	med = cheltuieliDupanume->elemente[0];
	assert(strcmp(med->nume, "imbracaminte") == 0);

	distrugeLista(cheltuieliDupanume);
	cheltuieliDupanume = sorteazaDupaNume(&farmacieSortare, 1);

	med = cheltuieliDupanume->elemente[0];

	assert(strcmp(med->nume, "transport") == 0);
	med = cheltuieliDupanume->elemente[1];
	assert(strcmp(med->nume, "transport") == 0);
	med = cheltuieliDupanume->elemente[2];
	assert(strcmp(med->nume, "mancare") == 0);
	med = cheltuieliDupanume->elemente[3];
	assert(strcmp(med->nume, "mancare") == 0);
	med = cheltuieliDupanume->elemente[4];
	assert(strcmp(med->nume, "internet") == 0);
	med = cheltuieliDupanume->elemente[5];
	assert(strcmp(med->nume, "imbracaminte") == 0);

	distrugeLista(cheltuieliDupanume);
	distrugeFarmacie(&farmacieSortare);
}


void ruleazaTesteMedicament() {
	testCreeazaMedicament();
	testMedicamentInvalid();
	testCopiazaMedicament();
	testValideazaMedicament();
}

void ruleazaTesteLista() {
	testCreeazaListaGoala();
	testDistrugeLista();
	testGetElement();
	testSetElement();
	testAdaugaModificaStergeLista();
	testCopiazaLista();
}

void ruleazaTesteService() {
	testCreeazafarmacieFamilie();
	//testAdaugaMedicamentInfarmacie();
	testModificaMedicamentDinfarmacie();
	testStergeMedicamentDinfarmacie();
	testFiltreazaDupaconcentratie();
	//testFiltreazaDupacantitate();
	//testFiltreazaDupanume();
	testSorteazaDupaId();
	testSorteazaDupaconcentratie();
	testSorteazaDupacantitate();
	testSorteazaDupanume();
}

void ruleazaToateTestele() {
	ruleazaTesteMedicament();
	ruleazaTesteLista();
	ruleazaTesteService();
	printf("Testele au rulat cu succes!\n\n\n");
}
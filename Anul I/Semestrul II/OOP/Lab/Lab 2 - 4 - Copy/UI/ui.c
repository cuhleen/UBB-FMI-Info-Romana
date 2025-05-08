#include <stdio.h>

#include "ui.h"

void ui_adaugaMedicament(Farmacie* farmacie) {
	int id;
	int concentratie;
	int cantitate;
	char nume[30];
	printf("Introduceti id-ul medicamentului:\n"); scanf("%d", &id);
	printf("Introduceti concentratia medicamentului:\n"); scanf("%d", &concentratie);
	printf("Introduceti cantitatea medicamentului:\n"); scanf("%d", &cantitate);
	printf("Introduceti numele medicamentului:\n"); scanf("%s", nume);

	int erori = adaugaMedicamentInFarmacie(farmacie, id, concentratie, cantitate, nume);
	if (erori == 0) {
		printf("Medicamentul cu id %d a fost adaugat cu succes!\n", id);
	}
	else {
		printf("Date introduse gresite!\n");
	}

}

void ui_modificaMedicament(Farmacie* farmacie) {
	int id;
	int concentratie;
	int cantitate;
	char nume[30];
	printf("Introduceti id-ul medicamentului:\n"); scanf("%d", &id);
	printf("Introduceti concentratia medicamentului:\n"); scanf("%d", &concentratie);
	printf("Introduceti cantitatea medicamentului:\n"); scanf("%d", &cantitate);
	printf("Introduceti numele medicamentului:\n"); scanf("%s", nume);
	int erori = modificaMedicamentInFarmacie(farmacie, id, concentratie, cantitate, nume);
	if (erori == 0) {
		printf("Medicamentul cu id %d a fost modificat cu succes!\n", id);
	}
	else {
		printf("Date introduse gresite!\n");
	}
}

void ui_stergeMedicament(Farmacie* farmacie) {
	int id = -1;
	printf("Introduceti id-ul medicamentului pentru stergere:\n");
	scanf("%d", &id);
	int validare = stergeMedicamentDinFarmacie(farmacie, id);
	if (validare == 0) {
		printf("Medicamentul cu id %d a fost sters cu succes!\n", id);
	}
	else {
		printf("Optiune invalida!");
	}
}

void ui_afiseazaFarmacia(Lista* lista) {
	int existaMedicamente = 0;
	for (int i = 0; i < size(lista); i++){
		Medicament* medicament = getElement(lista, i);
		existaMedicamente++;
		if (existaMedicamente == 1) {
			printf("   Farmacie:\n");
		}
		printf("%d. Medicamentul cu id %d cu concentratia %d de cantitate %d cu numele %s.\n", existaMedicamente, medicament->id, medicament->concentratie, medicament->cantitate, medicament->nume);
	}
	if (existaMedicamente == 0) {
		printf("Optiune invalida!\n");
	}
}

void ui_filtreaza(Farmacie* farmacie) {
	int reperDeFiltrare = 0;
	printf("Introduceti reperul dupa care se va filtra (concentratie mai mica decat un numar dat -> 1; stoc mai mic decât un numar dat -> 2; nume incepand cu o litera data -> 3):\n");
	scanf("%d", &reperDeFiltrare);
	if (reperDeFiltrare == 1) {
		printf("Introduceti concentratia dupa care sa se filtreze:\n");
		int concentratie;
		scanf("%d", &concentratie);
		Lista* listaFiltrataDupaconcentratie = filtreazaDupaConcentratie(farmacie, concentratie);
		ui_afiseazaFarmacia(listaFiltrataDupaconcentratie);
		distrugeLista(listaFiltrataDupaconcentratie);
	}
	else if (reperDeFiltrare == 2) {
		printf("Introduceti stocul dupa care sa se filtreze:\n");
		int cantitate;
		scanf("%d", &cantitate);
		Lista* listaFiltrataDupaCantitate = filtreazaDupaCantitate(farmacie, cantitate);
		ui_afiseazaFarmacia(listaFiltrataDupaCantitate);
		distrugeLista(listaFiltrataDupaCantitate);
	}
	else if (reperDeFiltrare == 3) {
		printf("Introduceti litera numelelui dupa care sa se filtreze:\n");
		char nume[20];
		scanf(" %c", nume);
		Lista* listaFiltrataDupaNume = filtreazaDupaNume(farmacie, nume);
		ui_afiseazaFarmacia(listaFiltrataDupaNume);
		distrugeLista(listaFiltrataDupaNume);
	}
	else {
		printf("Optiune invalida!");
	}
}

void ui_sorteaza(Farmacie* farmacie) {
	int reperDeSortare;
	int ordineDeSortare;
	printf("Introduceti reperul dupa care sa se sorteze (1->id, 2->concentratie, 3->cantitate, 4->nume):\n");
	scanf("%d", &reperDeSortare);
	printf("Introduceti ordinea de sortare (0 pentru crescator, 1 pentru descrescator):\n");
	scanf("%d", &ordineDeSortare);
	Lista* MedicamenteSortate;
	switch (reperDeSortare) {
	case 1:
		MedicamenteSortate = sorteazaDupaId(farmacie, ordineDeSortare);
		break;
	case 2:
		MedicamenteSortate = sorteazaDupaConcentratie(farmacie, ordineDeSortare);
		break;
	case 3:
		MedicamenteSortate = sorteazaDupaCantitate(farmacie, ordineDeSortare);
		break;
	case 4:
		MedicamenteSortate = sorteazaDupaNume(farmacie, ordineDeSortare);
		break;
	default:
		printf("Optiune invalida!\n");
	}
	ui_afiseazaFarmacia(MedicamenteSortate);
	distrugeLista(MedicamenteSortate);
}

void run() {
	Farmacie farmacie = creeazaFarmacie();
	int ruleaza = 1;
	while (ruleaza) {
		int comanda = 0;
		printf("\n   Meniu farmacie:\n1. Adauga medicament\n2. Modifica medicament\n3. Sterge medicament\n4. Afiseaza toate medicamentele farmaciei\n5. Filtreaza medicamentele farmaciei\n6. Afiseaza medicamentele farmaciei sortate\n7. Pune medicamente default in farmacie\n8. Undo\n0. Iesire din aplicatie\n\n");
		scanf("%d", &comanda);
		switch (comanda) {
		case 1:
			ui_adaugaMedicament(&farmacie);
			break;
		case 2:
			ui_modificaMedicament(&farmacie);
			break;
		case 3:
			ui_stergeMedicament(&farmacie);
			break;
		case 4:
			ui_afiseazaFarmacia(farmacie.Medicamente);
			break;
		case 5:
			ui_filtreaza(&farmacie);
			break;
		case 6:
			ui_sorteaza(&farmacie);
			break;
		case 7:
			adaugaMedicamentInFarmacie(&farmacie, 3, 1, 100, "nume1");
			adaugaMedicamentInFarmacie(&farmacie, 4, 10, 15, "nume2");
			adaugaMedicamentInFarmacie(&farmacie, 1, 13, 15, "nume3");
			adaugaMedicamentInFarmacie(&farmacie, 2, 15, 15, "nume4");
			adaugaMedicamentInFarmacie(&farmacie, 6, 13, 15, "nume1");
			adaugaMedicamentInFarmacie(&farmacie, 5, 15, 15, "nume1");
			break;
		case 8:
			if (undo(&farmacie) == 1)
				printf("Nu se mai poate face undo\n");
			break;
		case 0:
			ruleaza = 0;
			break;
		default:
			printf("Optiune invalida!\n");
		}
	}
	distrugeFarmacie(&farmacie);
}
























































// void ruleazaToateTestele()
// {
// 	printf("\nToate testele au rulat cu succes!\n\n");
// }
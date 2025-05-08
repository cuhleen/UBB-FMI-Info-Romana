/*
4. Inchiriere masini

Creati o aplicatie pentru o firma de inchirieri auto. Fiecare masina are un numar de inmatriculare,
model si categorie (mini, sport, suv, etc).
Aplicatia permite:
a) adaugare de masini
b) actualizare masina existenta
c) inchiriere masina/returnare masina
d) Vizualizare masini dupa un criteru dat (categorie, model)
e) Permite sortarea masinilor dupa: model sau categorie (crescator/descrescator)
*/
#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>

#include "ui.h"
#include "list.h"
#include "teste.h"

int main()
{	
	runAllTests();
	runUI();
	return 0;
} 
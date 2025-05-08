#include "TestScurt.h"

#include <assert.h>
#include <exception>


#include "Lista.h"
#include "IteratorLP.h"



using namespace std;

void testAll(){
	Lista lista = Lista();
	assert(lista.dim() == 0);
	assert(lista.vida());

    lista.adaugaSfarsit(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());

    IteratorLP it = lista.cauta(1);
    assert(it.valid());
    assert(it.element() == 1);
    it.urmator();
    assert(!it.valid());
    it.prim();
    assert(it.valid());
    assert(it.element() == 1);

    assert(lista.sterge(it) == 1);
    assert(lista.dim() == 0);
    assert(lista.vida());

    lista.adaugaInceput(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());

	// teste elimina()

	Lista lCazParticular;
	lCazParticular.adaugaSfarsit(10);
	lCazParticular.adaugaSfarsit(20);
	lCazParticular.adaugaSfarsit(30);

	IteratorLP itEliminaCazParticular = lCazParticular.cauta(30); // elementul referit de iterator este ultimul
	assert(itEliminaCazParticular.valid());
	assert(itEliminaCazParticular.element() == 30);
	TElem stersCazParticular = itEliminaCazParticular.elimina();
	assert(stersCazParticular == 30);

	assert(lCazParticular.dim() == 2); // în listă rămân 2 elemente
	assert(!itEliminaCazParticular.valid()); // dar totuși iteratorul NU este valid

	Lista l;
	l.adaugaSfarsit(10);
	l.adaugaSfarsit(20);
	l.adaugaSfarsit(30);

	IteratorLP itElimina = l.prim();
	assert(itElimina.valid());
	assert(itElimina.element() == 10);

	// Eliminăm primul element
	TElem sters1 = itElimina.elimina();
	assert(sters1 == 10);
	assert(itElimina.valid());
	assert(itElimina.element() == 20);
	assert(l.dim() == 2);

	// Eliminăm "al doilea" element
	TElem sters2 = itElimina.elimina();
	assert(sters2 == 20);
	assert(itElimina.valid());
	assert(itElimina.element() == 30);
	assert(l.dim() == 1);

	// Eliminăm "al treilea" element
	TElem sters3 = itElimina.elimina();
	assert(sters3 == 30);
	assert(!itElimina.valid());
	assert(l.dim() == 0);
	assert(l.vida());

	// Eliminăm element inexistent / Eliminăm din iterator invalid
	try {
		itElimina.elimina();
		assert(false);
	} catch (exception&) {
		assert(true);
	}


}


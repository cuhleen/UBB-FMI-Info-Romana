#include "IteratorLP.h"
#include "Lista.h"
#include <exception>

using namespace std;

// Complexitate = BC = θ(1) WC = θ(1) AC = θ(1)
IteratorLP::IteratorLP(Lista& l) : lista(l) {
	curent = l.primNod;
}
// Complexitate = BC = θ(1) WC = θ(1) AC = θ(1)
void IteratorLP::prim() {
	curent = lista.primNod;
}

// Complexitate = BC = θ(1) WC = θ(1) AC = θ(1)
void IteratorLP::urmator() {
	if (!valid()) throw exception();
	curent = curent->urm;
}

// Complexitate = BC = θ(1) WC = θ(1) AC = θ(1)
bool IteratorLP::valid() const {
	return curent != nullptr;
}

// Complexitate = BC = θ(1) WC = θ(1) AC = θ(1)
TElem IteratorLP::element() const {
	if (!valid()) throw exception();
	return curent->e;
}

// Complexitate = BC = θ(1) WC = θ(n) AC = θ(n)
TElem IteratorLP::elimina() {
	if (!valid()) throw exception();

	return lista.sterge(*this);
}

/**
	Subalgoritm elimina(l):
		Dacă iteratorul nu este valid
			Aruncă excepție
		Sf. Dacă

		Returnează lista.sterge(l.primNod())

	Sf.elimina

*/
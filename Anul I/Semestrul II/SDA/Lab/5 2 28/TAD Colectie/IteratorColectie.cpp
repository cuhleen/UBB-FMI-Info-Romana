#include "IteratorColectie.h"
#include "Colectie.h"
#include <exception>

// Complexitate = BC = WC = AC = θ(1)
IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	curent = 0;
	frecventaCurent = 0;
}

// Complexitate = BC = WC = AC = θ(1)
void IteratorColectie::prim() {
	curent = 0;
	frecventaCurent = col.nrAparitii(curent);
}

// Complexitate = BC = WC = AC = θ(1)
void IteratorColectie::urmator() {
	if (!valid())
		throw std::exception();

	frecventaCurent++;
	if (frecventaCurent >= col.frecventa[curent]) {
		curent++;
		frecventaCurent = 0;
	}
}

// Complexitate = BC = WC = AC = θ(1)
bool IteratorColectie::valid() const {
	return curent < col.dimIndiv();
}

// Complexitate = BC = WC = AC = θ(1)
TElem IteratorColectie::element() const {
	if (!valid())
		throw std::exception();
	return col.elemente[curent];
}


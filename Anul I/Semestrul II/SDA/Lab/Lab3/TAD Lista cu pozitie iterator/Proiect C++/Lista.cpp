#include <exception>
#include "IteratorLP.h"
#include "Lista.h"

using namespace std;

// Complexitate = BC = WC = AC = θ(1)
Lista::Lista() {
	primNod = nullptr;
	dimensiune = 0;
}

// Complexitate = BC = WC = AC = θ(1)
int Lista::dim() const {
	return dimensiune;
}

// Complexitate = BC = WC = AC = θ(1)
bool Lista::vida() const {
	return primNod == nullptr;
}

// Complexitate = BC = WC = AC = θ(1)
IteratorLP Lista::prim(){
	return IteratorLP(*this);
}

// Complexitate = BC = WC = AC = θ(1)
TElem Lista::element(IteratorLP poz) const {
	if (!poz.valid()) throw exception();
	return poz.curent->e;
}

// Complexitate = BC = WC = AC = θ(1)
TElem Lista::modifica(IteratorLP poz, TElem e) {
	if (!poz.valid()) throw exception();
	TElem vechi = poz.curent->e;
	poz.curent->e = e;
	return vechi;
}

// Complexitate = BC = WC = AC = θ(1)
void Lista::adaugaInceput(TElem e) {
	Nod* nou = new Nod(e, primNod);
	primNod = nou;
	dimensiune++;
}

// Complexitate = BC = θ(1) WC = θ(n) AC = θ(n)
void Lista::adaugaSfarsit(TElem e) {
	Nod* nou = new Nod(e, nullptr);
	if (primNod == nullptr) {
		primNod = nou;
	} else {
		Nod* p = primNod;
		while (p->urm != nullptr) {
			p = p->urm;
		}
		p->urm = nou;
	}
	dimensiune++;
}

// Complexitate = BC = WC = AC = θ(1)
void Lista::adauga(IteratorLP& poz, TElem e) {
	if (!poz.valid()) throw exception();
	Nod* nou = new Nod(e, poz.curent->urm);
	poz.curent->urm = nou;
	poz.curent = nou;
	dimensiune++;
}

// Complexitate = BC = θ(1) WC = θ(n) AC = θ(n)
TElem Lista::sterge(IteratorLP& poz) {
	if (!poz.valid()) throw exception();
	Nod* deSters = poz.curent;

	if (deSters == primNod) {
		primNod = primNod->urm;
		poz.curent = primNod;
	} else {
		Nod* anterior = primNod;
		while (anterior->urm != deSters) {
			anterior = anterior->urm;
		}
		anterior->urm = deSters->urm;
		poz.curent = deSters->urm;
	}

	TElem valoare = deSters->e;
	delete deSters;
	dimensiune--;
	return valoare;
}

// Complexitate = BC = θ(1) WC = θ(n) AC = θ(n)
IteratorLP Lista::cauta(TElem e){
	Nod* p = primNod;
	while (p != nullptr) {
		if (p->e == e) {
			IteratorLP it(*this);
			it.curent = p;
			return it;
		}
		p = p->urm;
	}
	IteratorLP it(*this);
	it.curent = nullptr; // iterator invalid
	return it;
}

// Complexitate = BC = WC = AC = θ(n)
Lista::~Lista() {
	while (primNod != nullptr) {
		Nod* tmp = primNod;
		primNod = primNod->urm;
		delete tmp;
	}
}

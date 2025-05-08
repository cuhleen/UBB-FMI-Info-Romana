#pragma once
#include "Lista.h"

class IteratorLP {
	friend class Lista;
private:
	IteratorLP(Lista& lista);

	Lista& lista;
	Lista::Nod* curent;

public:
	void prim();
	void urmator();
	bool valid() const;
	TElem element() const;

	TElem elimina();
};

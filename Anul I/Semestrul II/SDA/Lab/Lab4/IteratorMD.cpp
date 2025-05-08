#include "IteratorMD.h"
#include "MD.h"

using namespace std;

/// Complexitate: θ(1)
IteratorMD::IteratorMD(const MD &_md): md(_md) {
	current = md.head;
	prev = -1;  // Inițializăm prev cu -1, deoarece nu există nod anterior la început
}

/// Complexitate: θ(1)
TElem IteratorMD::element() const {
	if (!valid()) {
		throw exception();
	}
	return md.nodes[current].element;
}

/// Complexitate: θ(1)
bool IteratorMD::valid() const {
	return current != -1;
}

/// Complexitate: θ(1)
void IteratorMD::urmator() {
	if (!valid()) {
		throw exception();
	}
	prev = current;         // Salvăm nodul curent ca fiind anterior
	current = md.nodes[current].next;
}

/// Complexitate: θ(1)
void IteratorMD::anterior() {
	if (prev == -1) {  // Dacă prev este -1, nu avem nod anterior
		throw exception();
	}
	current = prev;  // Mergem la nodul anterior
	prev = md.nodes[current].prev;  // Actualizăm prev pentru următorul pas
}

/// Complexitate: θ(1)
void IteratorMD::prim() {
	current = md.head;
	prev = -1;  // Resetăm prev
}

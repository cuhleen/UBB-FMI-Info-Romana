#include "IteratorMD.h"
#include "MD.h"

using namespace std;

/// Complexitate: Theta(1)
IteratorMD::IteratorMD(const MD &_md): md(_md) {
	current = md.head;
}

/// Complexitate: Theta(1)
TElem IteratorMD::element() const {
	if (!valid()) {
		throw exception();
	}
	return md.nodes[current].element;
}

/// Complexitate: Theta(1)
bool IteratorMD::valid() const {
	return current != -1;
}

/// Complexitate: Theta(1)
void IteratorMD::urmator() {
	if (!valid()) {
		throw exception();
	}
	current = md.nodes[current].next;
}

/// Complexitate: Theta(1)
void IteratorMD::prim() {
	current = md.head;
}

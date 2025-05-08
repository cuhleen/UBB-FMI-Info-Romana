// #pragma once
// template <typename ElementT>
// class IteratorVectorT;
//
// template <typename ElementT>
// class VectDinNewDeleteT
// {
// public:
// 	/*
// 	Constructor default
// 	Alocam loc pentru 5 elemente
// 	*/
// 	VectDinNewDeleteT();
//
// 	/*
// 	Constructor de copiere
// 	*/
// 	VectDinNewDeleteT(const VectDinNewDeleteT& ot); //rule of 3
//
// 	/*
// 	Eliberam memoria
// 	*/
// 	~VectDinNewDeleteT();//rule of 3
//
// 	/*
// 	Operator assgnment
// 	elibereaza ce era in obiectul curent (this)
// 	aloca spatiu pentru elemente
// 	copieaza elementele din ot in this
// 	*/
// 	VectDinNewDeleteT& operator=(const VectDinNewDeleteT& ot);//rule of 3
//
//
// 	/*
// 	Move constructor
// 	Apelat daca construim un nou vector dintr-un r-value (ex temporary, expresie de la return)
// 	Obiectul ot nu se mai foloseste astfel se poate refolosi interiorul lui
// 	*/
// 	VectDinNewDeleteT(VectDinNewDeleteT&& ot); //rule of 5
//
// 	/*
// 	Move assignment
// 	Similar cu move constructor
// 	Folosit la assignment
// 	*/
// 	VectDinNewDeleteT& operator=(VectDinNewDeleteT&& ot); //rule of 5
//
//
// 	void add(const ElementT& el);
//
// 	ElementT& get(int poz) const;
//
// 	void set(int poz, const ElementT& el);
//
// 	int size() const noexcept;
//
// 	friend class IteratorVectorT<ElementT>;
// 	//functii care creaza iteratori
// 	IteratorVectorT<ElementT> begin();
// 	IteratorVectorT<ElementT> end();
//
//
// private:
// 	int lg;//numar elemente
// 	int cap;//capacitate
// 	ElementT* elems;//elemente
//
// 	void ensureCapacity();
// };
//
// /*
// Constructor default
// Alocam loc pentru 5 elemente
// */
// template<typename ElementT>
// VectDinNewDeleteT<ElementT>::VectDinNewDeleteT() :elems{ new ElementT[INITIAL_CAPACITY] }, cap{ INITIAL_CAPACITY }, lg{ 0 } {}
//
// /*
// Constructor de copiere
// Obiectul current (this) acum se creaza
// aloca spatiu pentru elemente
// copieaza elementele din ot in this
// */
// template<typename ElementT>
// VectDinNewDeleteT<ElementT>::VectDinNewDeleteT(const VectDinNewDeleteT<ElementT>& ot) {
// 	elems = new ElementT[ot.cap];
// 	//copiez elementele
// 	for (int i = 0; i < ot.lg; i++) {
// 		elems[i] = ot.elems[i];  //assignment din Pet
// 	}
// 	lg = ot.lg;
// 	cap = ot.cap;
// }
//
// /*
// Operator assgnment
// elibereaza ce era in obiectul curent (this)
// aloca spatiu pentru elemente
// copieaza elementele din ot in this
// */
// template<typename ElementT>
// VectDinNewDeleteT<ElementT>& VectDinNewDeleteT<ElementT>::operator=(const VectDinNewDeleteT<ElementT>& ot) {
// 	if (this == &ot) {
// 		return *this;//s-a facut l=l;
// 	}
// 	delete[] elems;
// 	elems = new ElementT[ot.cap];
// 	//copiez elementele
// 	for (int i = 0; i < ot.lg; i++) {
// 		elems[i] = ot.elems[i];  //assignment din Pet
// 	}
// 	lg = ot.lg;
// 	cap = ot.cap;
// 	return *this;
// }
//
// /*
// Eliberam memoria
// */
// template<typename ElementT>
// VectDinNewDeleteT<ElementT>::~VectDinNewDeleteT() {
// 	delete[] elems;
// }
//
//
// /*
// Move constructor
// Apelat daca construim un nou vector dintr-un r-value (ex temporary)
// Obiectul ot nu se mai foloseste astfel se poate refolosi interiorul lui
// */
// template<typename ElementT>
// VectDinNewDeleteT<ElementT>::VectDinNewDeleteT(VectDinNewDeleteT&& ot) {
// 	// Copy the data pointer from other
// 	elems = ot.elems;
// 	lg = ot.lg;
// 	cap = ot.cap;
//
// 	// Release the data pointer from the source object so that
// 	// the destructor does not free the memory multiple times.
// 	ot.elems = nullptr;
// 	ot.lg = 0;
// 	ot.cap = 0;
// }
//
// /*
// Move assignment
// Similar cu move constructor
// Folosit la assignment
// Elibereaza ce continea obiectul curent (this)
// "fura" interiorul lui ot
// pregateste ot pentru distrugere
// */
// template<typename ElementT>
// VectDinNewDeleteT<ElementT>& VectDinNewDeleteT<ElementT>::operator=(VectDinNewDeleteT<ElementT>&& ot) {
// 	if (this == &ot) {
// 		return *this;
// 	}
// 	delete[] elems;
// 	// Copy the data pointer from other
// 	elems = ot.elems;
// 	lg = ot.lg;
// 	cap = ot.cap;
//
// 	// Release the data pointer from the source object so that
// 	// the destructor does not free the memory multiple times.
// 	ot.elems = nullptr;
// 	ot.lg = 0;
// 	ot.cap = 0;
// 	return *this;
// }
//
// template<typename ElementT>
// void VectDinNewDeleteT<ElementT>::add(const ElementT& el) {
// 	ensureCapacity();//daca e nevoie mai alocam memorie
// 	elems[lg++] = el;
// }
//
// template<typename ElementT>
// ElementT& VectDinNewDeleteT<ElementT>::get(int poz) const {
// 	return elems[poz];
// }
//
// template<typename ElementT>
// void VectDinNewDeleteT<ElementT>::set(int poz, const ElementT& el) {
// 	elems[poz] = el;
// }
//
// template<typename ElementT>
// int VectDinNewDeleteT<ElementT>::size() const noexcept {
// 	return lg;
// }
//
// template<typename ElementT>
// void VectDinNewDeleteT<ElementT>::ensureCapacity() {
// 	if (lg < cap) {
// 		return; //mai avem loc
// 	}
// 	cap *= 2;
// 	ElementT* aux = new ElementT[cap];
// 	for (int i = 0; i < lg; i++) {
// 		aux[i] = elems[i];
// 	}
// 	delete[] elems;
// 	elems = aux;
// }
//
// template<typename ElementT>
// IteratorVectorT<ElementT> VectDinNewDeleteT<ElementT>::begin()
// {
// 	return IteratorVectorT<ElementT>(*this);
// }
//
// template<typename ElementT>
// IteratorVectorT<ElementT> VectDinNewDeleteT<ElementT>::end()
// {
// 	return IteratorVectorT<ElementT>(*this, lg);
// }
//
// template<typename ElementT>
// class IteratorVectorT {
// private:
// 	const VectDinNewDeleteT<ElementT>& v;
// 	int poz = 0;
// public:
// 	IteratorVectorT(const VectDinNewDeleteT<ElementT>& v) noexcept;
// 	IteratorVectorT(const VectDinNewDeleteT<ElementT>& v, int poz)noexcept;
// 	bool valid()const;
// 	ElementT& element() const;
// 	void next();
// 	ElementT& operator*();
// 	IteratorVectorT& operator++();
// 	bool operator==(const IteratorVectorT& ot)noexcept;
// 	bool operator!=(const IteratorVectorT& ot)noexcept;
// };
//
// template<typename ElementT>
// IteratorVectorT<ElementT>::IteratorVectorT(const VectDinNewDeleteT<ElementT>& v) noexcept:v{ v } {}
//
// template<typename ElementT>
// IteratorVectorT<ElementT>::IteratorVectorT(const VectDinNewDeleteT<ElementT>& v, int poz)noexcept : v{ v }, poz{ poz } {}
//
// template<typename ElementT>
// bool IteratorVectorT<ElementT>::valid()const {
// 	return poz < v.lg;
// }
//
// template<typename ElementT>
// ElementT& IteratorVectorT<ElementT>::element() const {
// 	return v.elems[poz];
// }
//
// template<typename ElementT>
// void IteratorVectorT<ElementT>::next() {
// 	poz++;
// }
//
// template<typename ElementT>
// ElementT& IteratorVectorT<ElementT>::operator*() {
// 	return element();
// }
//
// template<typename ElementT>
// IteratorVectorT<ElementT>& IteratorVectorT<ElementT>::operator++() {
// 	next();
// 	return *this;
// }
//
// template<typename ElementT>
// bool IteratorVectorT<ElementT>::operator==(const IteratorVectorT<ElementT>& ot) noexcept {
// 	return poz == ot.poz;
// }
//
// template<typename ElementT>
// bool IteratorVectorT<ElementT>::operator!=(const IteratorVectorT<ElementT>& ot)noexcept {
// 	return !(*this == ot);
// }
//

#pragma once
template<typename ElementT>
class IteratorVectorT;

template<typename ElementT>
class VectorDinamic {
private:
	int dimensiune;
	int capacitate;
	ElementT *elemente;

	void asiguraCapacitate();

public:
	VectorDinamic();

	VectorDinamic(const VectorDinamic &other);

	~VectorDinamic();

	VectorDinamic &operator=(const VectorDinamic &other);

	VectorDinamic(VectorDinamic &&other);

	VectorDinamic &operator=(VectorDinamic &&other);

	void add(const ElementT &element);

	ElementT &get(int pozitieElement) const;

	void set(int pozitieElement, const ElementT &element);

	[[nodiscard]] int size() const;

	void push_back(const ElementT &element);

	void pop_back();

	ElementT &operator[](int index);

	friend class IteratorVectorT<ElementT>;

	IteratorVectorT<ElementT> begin() const;

	IteratorVectorT<ElementT> end() const;
};

template<typename ElementT>
class IteratorVectorT {
private:
	const VectorDinamic<ElementT> &vector;
	int pozitieElement = 0;

public:
	explicit IteratorVectorT(const VectorDinamic<ElementT> &vector) noexcept;

	IteratorVectorT(const VectorDinamic<ElementT> &vector, int pozitieElement) noexcept;

	[[nodiscard]] bool valid() const noexcept;

	ElementT &element() const noexcept;

	void next() noexcept;

	ElementT &operator*();

	IteratorVectorT &operator++();

	bool operator==(const IteratorVectorT &other) noexcept;

	bool operator!=(const IteratorVectorT &other) noexcept;
};

#include "VectorDinamicTemplate.h"

template<typename ElementT>
VectorDinamic<ElementT>::VectorDinamic(): elemente{new ElementT[5]}, capacitate{5}, dimensiune{0} {
}

template<typename ElementT>
VectorDinamic<ElementT>::VectorDinamic(const VectorDinamic<ElementT> &other) {
	elemente = new ElementT[other.capacitate];
	for (int i = 0; i < other.dimensiune; i++) {
		elemente[i] = other.elemente[i];
	}
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;
}

template<typename ElementT>
VectorDinamic<ElementT>::~VectorDinamic() {
	delete[] elemente;
}

template<typename ElementT>
VectorDinamic<ElementT> &VectorDinamic<ElementT>::operator=(const VectorDinamic<ElementT> &other) {
	if (this == &other) {
		return *this;
	}
	delete[] elemente;
	elemente = new ElementT[other.capacitate];
	for (int i = 0; i < other.dimensiune; i++) {
		elemente[i] = other.elemente[i];
	}
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;
	return *this;
}

template<typename ElementT>
VectorDinamic<ElementT>::VectorDinamic(VectorDinamic &&other) {
	elemente = other.elemente;
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;

	other.elemente = nullptr;
	other.dimensiune = 0;
	other.capacitate = 0;
}

template<typename ElementT>
VectorDinamic<ElementT> &VectorDinamic<ElementT>::operator=(VectorDinamic<ElementT> &&other) {
	if (this == &other) {
		return *this;
	}
	delete[] elemente;

	elemente = other.elemente;
	dimensiune = other.dimensiune;
	capacitate = other.capacitate;

	other.elemente = nullptr;
	other.dimensiune = 0;
	other.capacitate = 0;
	return *this;
}

template<typename ElementT>
void VectorDinamic<ElementT>::add(const ElementT &element) {
	asiguraCapacitate();
	elemente[dimensiune++] = element;
}

template<typename ElementT>
ElementT &VectorDinamic<ElementT>::get(int pozitieElement) const {
	return elemente[pozitieElement];
}

template<typename ElementT>
void VectorDinamic<ElementT>::set(int pozitieElement, const ElementT &element) {
	elemente[pozitieElement] = element;
}

template<typename ElementT>
int VectorDinamic<ElementT>::size() const {
	return dimensiune;
}

template<typename ElementT>
void VectorDinamic<ElementT>::asiguraCapacitate() {
	if (dimensiune < capacitate) {
		return;
	}
	capacitate *= 2;
	auto *elementeAuxiliar = new ElementT[capacitate];
	for (int i = 0; i < dimensiune; i++) {
		elementeAuxiliar[i] = elemente[i];
	}
	delete[] elemente;
	elemente = elementeAuxiliar;
}

template<typename ElementT>
void VectorDinamic<ElementT>::push_back(const ElementT &element) {
	if (dimensiune == capacitate) {
		asiguraCapacitate();
	}
	elemente[dimensiune] = element;
	dimensiune++;
}

template<typename ElementT>
void VectorDinamic<ElementT>::pop_back() {
	dimensiune--;
}

template<typename ElementT>
ElementT &VectorDinamic<ElementT>::operator[](int index) {
	return elemente[index];
}

template<typename ElementT>
IteratorVectorT<ElementT> VectorDinamic<ElementT>::begin() const {
	return IteratorVectorT<ElementT>(*this);
}

template<typename ElementT>
IteratorVectorT<ElementT> VectorDinamic<ElementT>::end() const {
	return IteratorVectorT<ElementT>(*this, dimensiune);
}

template<typename ElementT>
IteratorVectorT<ElementT>::IteratorVectorT(const VectorDinamic<ElementT> &vector) noexcept : vector{vector} {
}

template<typename ElementT>
IteratorVectorT<ElementT>::IteratorVectorT(const VectorDinamic<ElementT> &vector, int pozitieElement) noexcept : vector{vector}, pozitieElement{pozitieElement} {
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::valid() const noexcept {
	return pozitieElement < vector.dimensiune;
}

template<typename ElementT>
ElementT &IteratorVectorT<ElementT>::element() const noexcept {
	return vector.elemente[pozitieElement];
}

template<typename ElementT>
void IteratorVectorT<ElementT>::next() noexcept {
	pozitieElement++;
}

template<typename ElementT>
ElementT &IteratorVectorT<ElementT>::operator*() {
	return element();
}

template<typename ElementT>
IteratorVectorT<ElementT> &IteratorVectorT<ElementT>::operator++() {
	next();
	return *this;
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::operator==(const IteratorVectorT<ElementT> &other) noexcept {
	return pozitieElement == other.pozitieElement;
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::operator!=(const IteratorVectorT<ElementT> &other) noexcept {
	return !(*this == other);
}


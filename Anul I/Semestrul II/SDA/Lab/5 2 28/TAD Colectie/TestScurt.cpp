#include "TestScurt.h"
#include <assert.h>
#include <exception>

#include "Colectie.h"
#include "IteratorColectie.h"

void testAll() { //apelam fiecare functie sa vedem daca exista
	Colectie c;
	assert(c.vida() == true);
	assert(c.dim() == 0); //adaug niste elemente
	c.adauga(5);
	c.adauga(1);
	c.adauga(10);
	c.adauga(7);
	c.adauga(1);
	c.adauga(11);
	c.adauga(-3);
	assert(c.dim() == 7);
	assert(c.cauta(10) == true);
	assert(c.cauta(16) == false);
	assert(c.nrAparitii(1) == 2);
	assert(c.nrAparitii(7) == 1);
	assert(c.sterge(1) == true);
	assert(c.sterge(6) == false);
	assert(c.dim() == 6);
	assert(c.nrAparitii(1) == 1);
	IteratorColectie ic = c.iterator();
	ic.prim();
	while (ic.valid()) {
		TElem e = ic.element();
		ic.urmator();
	}

	//Teste functie noua
	c.adaugaAparitiiMultiple(20, 999); // se adauga
	assert(c.cauta(999) == true); // se afla
	assert(c.nrAparitii(999) == 20); // apare de cate ori i-am zis
	assert(c.sterge(999) == true); // se poate sterge
	assert(c.nrAparitii(999) == 19); // chiar se sterge

	c.adaugaAparitiiMultiple(0, 999); // se adauga cu nr 0

	bool exceptiePrinsa = false;

	try
	{
		c.adaugaAparitiiMultiple(-20, 1000); // se adauga cu nr negativ
	}catch (const std::exception&)
	{
		exceptiePrinsa = true;
	}
	assert(exceptiePrinsa);



}
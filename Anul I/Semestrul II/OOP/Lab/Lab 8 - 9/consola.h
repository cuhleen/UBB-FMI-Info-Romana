#pragma once
#include "service.h"
#include "Film.h"

class UI {
private:

	Service& service;
	ShoppingCart& shoppingCart;

	/*
	Citeste datele de la tastatura si adauga film nou in repository
	arunca exceptie daca parametrii nu sunt valizi sau titlul filmului este deja existent
	*/
	void ui_adaugaFilm();

	/*
	Sterge un film din repository
	*/
	void ui_stergeFilm();

	/*
	Modifica un film din repository
	*/
	void ui_modificaFilm();

	/*
	Afiseaza lista de filme din repository
	*/
	static void ui_afiseazaFilme(const vector<Film>& filme);

	/*
	Cauta un film dupa titlul acestuia
	*/
	void ui_cautaFilm();

	/*
	Filtreaza filme dupa gen / anul aparitiei
	*/
	void ui_filtrare();

	/*
	Sorteaza filme dupa actorul principal / gen + anul aparitiei
	*/
	void ui_sortare();

	void ui_adaugaInCos();
	void ui_stergeDinCos();
	void ui_afiseazaCos();
	void ui_elibereazaCos();
	void ui_genereazaCos();
	void ui_exportCos();

public:
	explicit UI(Service& service, ShoppingCart& shoppingCart) :service{ service }, shoppingCart{ shoppingCart} {}
	//nu permitem copierea obiectelor
	UI(const UI& other) = delete;

	~UI();

	void ruleaza();

};
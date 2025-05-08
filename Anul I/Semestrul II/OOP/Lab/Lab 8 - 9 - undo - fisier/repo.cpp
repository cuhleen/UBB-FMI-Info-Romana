#include "repo.h"

#include <fstream>

void Repo::adaugaFilm(const Film& film) {
	if (exista(film)) {
		throw ExceptieRepo("Filmul cu titlul " + film.getTitluFilm() + " este deja adaugat!\n");
	}
	filme.push_back(film);
}

void Repo::stergeFilm(int pozitie) {
	// if (pozitie != filme.size() - 1) {
	// 	for (pozitie; pozitie < filme.size() - 1; pozitie++) {
	// 		filme[pozitie] = filme[pozitie + 1];
	// 	}
	// 	filme.pop_back();
	// }
	// else {
	// 	filme.pop_back();
	// }

	if (pozitie < 0 || pozitie >= filme.size()) return; // optional, protecție
	if (pozitie != filme.size() - 1) {
		std::copy(filme.begin() + pozitie + 1, filme.end(), filme.begin() + pozitie);
	}
	filme.pop_back();
}

void Repo::modificaFilm(const Film filmModificat, int pozitie) {
	if (!exista(filmModificat)) {
		throw ExceptieRepo("Titlul " + filmModificat.getTitluFilm() + " nu a fost gasit!\n");
	}
	filme[pozitie].setGenFilm(filmModificat.getGenFilm());
	filme[pozitie].setAnAparitie(filmModificat.getAnAparitie());
	filme[pozitie].setActorPrincipal(filmModificat.getActorPrincipal());

}

bool Repo::exista(const Film& film) {
	try {
		cautaFilm(film.getTitluFilm());
		return true;
	}
	catch (ExceptieRepo&) {
		return false;}
}

const Film& Repo::cautaFilm(const string& titluFilm) {
	for (const auto& film : filme) {
		if (film.getTitluFilm() == titluFilm) {
			return film;
		}
	}
	throw ExceptieRepo("Nu s-a gasit filmul cu titlul " + titluFilm + "!\n");
}

int Repo::cautaPozitieDupaTitlu(const string& titluFilm) const {
	int pozitie = -1, i = -1;
	for (const auto& film : filme) {
		i++;
		if (film.getTitluFilm() == titluFilm) {
			pozitie = i;
		}
	}
	if (pozitie == -1) {
		throw ExceptieRepo("Nu exista film cu titlul " + titluFilm);
	}
	return pozitie;
}

const vector<Film>& Repo::getAll() const noexcept {
	return this->filme;
}

void Repo::incarcaDinFisier(const string& numeFisier) {
	std::ifstream in(numeFisier);
	if (!in.is_open()) {
		throw ExceptieRepo("Nu s-a putut deschide fisierul pentru citire!");
	}

	filme.clear();
	string linie;
	while (getline(in, linie)) {
		if (linie.empty()) continue;

		size_t pos = 0;
		vector<string> tokens;
		while ((pos = linie.find(','))){
			tokens.push_back(linie.substr(0, pos));
			linie.erase(0, pos + 1);
			if (pos == string::npos) break;
		}

		if (tokens.size() != 4) continue;

		string titlu = tokens[0];
		string gen = tokens[1];
		int an = stoi(tokens[2]);
		string actor = tokens[3];

		Film film{titlu, gen, an, actor};
		filme.push_back(film);
	}
	in.close();
}

void Repo::salveazaInFisier(const string& numeFisier) {
	std::ofstream out(numeFisier);
	if (!out.is_open()) {
		throw ExceptieRepo("Nu s-a putut deschide fisierul pentru scriere!");
	}

	for (const auto& film : filme) {
		out << film.getTitluFilm() << ","
			<< film.getGenFilm() << ","
			<< film.getAnAparitie() << ","
			<< film.getActorPrincipal() << "\n";
	}
	out.close();
}

ostream& operator<<(ostream& out, const ExceptieRepo& exceptie) {
	for (const auto& mesaj: exceptie.mesajEroare)
		out << mesaj;
	return out;
}

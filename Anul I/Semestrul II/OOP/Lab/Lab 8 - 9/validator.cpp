#include "validator.h"
#include <assert.h>
#include <sstream>

void Validator::valideaza(const Film& film) {
	vector<string> mesajEroare;
	if (film.getTitluFilm().empty()) mesajEroare.emplace_back("Titlu invalid!\n");
	if (film.getGenFilm().empty()) mesajEroare.emplace_back("Gen invalid!\n");
	if (film.getAnAparitie() < 1500) mesajEroare.emplace_back("An invalid!\n");
	if (film.getActorPrincipal().empty()) mesajEroare.emplace_back("Actor principal invalid!\n");
	if (!mesajEroare.empty()) {
		throw ExceptieValidare(mesajEroare);
	}
}

ostream& operator<<(ostream& out, const ExceptieValidare& exceptie) {
	for (const auto& mesaj : exceptie.mesajEroare) {
		out << mesaj << " ";
	}
	return out;
}

#include "Service.h"
#include <cassert>
#include <cstring>
#include <iostream>

void Service::addFilm(const string &name, const string &type, const string &manufacturer, int price) {
	const Film p(name, type, manufacturer, price);
	Validation::validateFilm(p);
	try {
		filmRepo.addFilm(p);
	}
	catch (RepoException re) {
		std::cout << re.getMessage();
	}
}

void Service::removeFilm(const string &name) {
	try {
		filmRepo.removeFilm(name);
	}
	catch (RepoException re) {
		std::cout << re.getMessage();
	}
}

void Service::updateFilm(const string &name, const Film &newFilm) {
	try {
		filmRepo.updateFilm(name, newFilm);
	}
	catch (RepoException re) {
		std::cout << re.getMessage();
	}

}

int Service::searchFilm(const string &name) const {
	return filmRepo.searchFilm(name);
}

VectorDinamic<Film> Service::getAll() const {
	return filmRepo.getAll();
}

VectorDinamic<Film> Service::filterFilms(const string &choice, const string &filter) const {
	VectorDinamic<Film> result;
	if (choice == "Actor") {
		for (int i = 0; i < filmRepo.getAll().size(); i++) {
			if (filmRepo.getAll()[i].getActor() == filter) {
				result.push_back(filmRepo.getAll()[i]);
			}
		}
	} else if (choice == "Year") {
		for (int i = 0; i < filmRepo.getAll().size(); i++) {
			if (filmRepo.getAll()[i].getYear() == std::stod(filter)) {
				result.push_back(filmRepo.getAll()[i]);
			}
		}
	} else if (choice == "Title") {
		for (int i = 0; i < filmRepo.getAll().size(); i++) {
			if (filmRepo.getAll()[i].getTitle() == filter) {
				result.push_back(filmRepo.getAll()[i]);
			}
		}
	}
	return result;
}

VectorDinamic<Film> Service::sortFilms(const string &choice, int order) const {
	VectorDinamic<Film> result;
	for (int i = 0; i < filmRepo.getAll().size(); i++) {
		result.push_back(filmRepo.getAll()[i]);
	}
	if (choice == "Title") {
		for (int i = 0; i < result.size() - 1; i++) {
			for (int j = i + 1; j < result.size(); j++) {
				if (strcmp(result[i].getTitle().c_str(), result[j].getTitle().c_str()) > 0 && order == 1 || strcmp(result[j].getTitle().c_str(), result[i].getTitle().c_str()) < 0 && order == 2) {
					Film temp = result[i];
					result[i] = result[j];
					result[j] = temp;
				}
			}
		}
	} else if (choice == "Year") {
		for (int i = 0; i < result.size() - 1; i++) {
			for (int j = i + 1; j < result.size(); j++) {
				if (result[i].getYear() > result[j].getYear() && order == 1 || result[j].getYear() < result[i].getYear() && order == 2) {
					Film temp = result[i];
					result[i] = result[j];
					result[j] = temp;
				}
			}
		}
	} else if (choice == "Title + Genre") {
		for (int i = 0; i < result.size() - 1; i++) {
			for (int j = i + 1; j < result.size(); j++) {
				if ((strcmp(result[i].getTitle().c_str(), result[j].getTitle().c_str()) > 0 || strcmp(result[i].getTitle().c_str(), result[j].getTitle().c_str()) == 0 && strcmp(result[i].getGenre().c_str(), result[j].getGenre().c_str()) > 0) && order == 1 || (strcmp(result[j].getTitle().c_str(), result[i].getTitle().c_str()) < 0 || strcmp(result[i].getTitle().c_str(), result[j].getTitle().c_str()) == 0 && strcmp(result[i].getGenre().c_str(), result[j].getGenre().c_str()) < 0) && order == 2) {
					Film temp = result[i];
					result[i] = result[j];
					result[j] = temp;
				}
			}
		}
	}
	return result;
}

void testService() {
	const FilmRepo repo;
	constexpr Validation validator;
	Service service(repo, validator);
	service.addFilm("Film1", "Genre1", "Actor1", 10);
	auto products = service.getAll();
	assert(products.size() == 1);
	assert(products[0].getTitle() == "Film1");
	assert(products[0].getGenre() == "Genre1");
	assert(products[0].getActor() == "Actor1");
	assert(products[0].getYear() == 10.0);
	service.removeFilm("Film1");
	products = service.getAll();
	assert(products.size() == 0);
	service.addFilm("Film1", "Genre1", "Actor1", 10);
	service.updateFilm("Film1", Film("Film2", "Genre2", "Actor2", 20));
	products = service.getAll();
	assert(products.size() == 1);
	assert(products[0].getTitle() == "Film2");
	assert(products[0].getGenre() == "Genre2");
	assert(products[0].getActor() == "Actor2");
	assert(products[0].getYear() == 20.0);
	assert(service.searchFilm("Film2") == 0);
	try {
		service.addFilm("Film2", "Genre2", "Actor2", -20);
	} catch (ValidationException &e) {
		assert(e.getMessage() == "Year must be positive");
	}
	service.removeFilm("Film2");
	service.addFilm("Film2", "Genre2", "Actor2", 20);
	service.addFilm("Film1", "Genre1", "Actor1", 10);
	service.addFilm("Film3", "Genre2", "Actor1", 10);
	VectorDinamic<Film> filtered_products = service.filterFilms("Actor", "Actor1");
	assert(filtered_products.size() == 2);
	assert(filtered_products[0].getTitle() == "Film1");
	assert(filtered_products[1].getTitle() == "Film3");
	filtered_products = service.filterFilms("Title", "Film2");
	assert(filtered_products.size() == 1);
	assert(filtered_products[0].getTitle() == "Film2");
	filtered_products = service.filterFilms("Year", "10");
	assert(filtered_products.size() == 2);
	assert(filtered_products[0].getTitle() == "Film1");
	assert(filtered_products[1].getTitle() == "Film3");
	VectorDinamic<Film> sorted_products = service.sortFilms("Title", 1);
	assert(sorted_products.size() == 3);
	assert(sorted_products[0].getTitle() == "Film1");
	assert(sorted_products[1].getTitle() == "Film2");
	assert(sorted_products[2].getTitle() == "Film3");
	service.addFilm("Film4", "Genre1", "Actor2", 5);
	sorted_products = service.sortFilms("Year", 1);
	assert(sorted_products.size() == 4);
	assert(sorted_products[0].getTitle() == "Film4");
	assert(sorted_products[1].getTitle() == "Film3");
	assert(sorted_products[2].getTitle() == "Film1");
	assert(sorted_products[3].getTitle() == "Film2");
	service.removeFilm("Film4");
	sorted_products = service.sortFilms("Title + Genre", 1);
	assert(sorted_products.size() == 3);
	assert(sorted_products[0].getTitle() == "Film1");
	assert(sorted_products[1].getTitle() == "Film2");
	assert(sorted_products[2].getTitle() == "Film3");
}

#ifndef FILM_H
#define FILM_H

#include <string>
#include <utility>
using std::string;

class Film {
private:
	string title;
	string genre;
	string actor;
	int year{};

public:
	[[nodiscard]] string getTitle() const;

	[[nodiscard]] string getGenre() const;

	[[nodiscard]] string getActor() const;

	[[nodiscard]] int getYear() const;

	Film(string title, string genre, string actor, const int year): title(std::move(title)), genre(std::move(genre)), actor(std::move(actor)), year(year) {
	}

	Film(const Film &film) = default;

	Film() = default;

	bool operator==(const Film &other) const {
		return title == other.title && genre == other.genre && actor == other.actor && year == other.year;
	}

	Film &operator=(const Film &other) = default;
};


#endif

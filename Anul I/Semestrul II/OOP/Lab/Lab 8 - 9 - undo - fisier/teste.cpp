#include "teste.h"

#include <fstream>
#include <cstdio>

void testRepoAdauga() {
	Repo repository;
	Film film{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film);
	assert(repository.getAll().size() == 1);

	try {
		repository.adaugaFilm(film);
		assert(false);
	}
	catch (const ExceptieRepo&) {
		assert(true);
	}

	try {
		repository.cautaFilm("La La Land");
		assert(false);
	}
	catch (const ExceptieRepo& eroare) {
		std::stringstream os;
		os << eroare;
		assert(os.str().find("adaugat") >= 0);
	}
}

void testRepoSterge() {
	Repo repository;
	Film film1{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film1);
	Film film2{ "The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch" };
	repository.adaugaFilm(film2);
	Film film3{ "Titanic", "Epic Romance", 1997, "Leonardo DiCaprio" };
	repository.adaugaFilm(film3);
	Film film4{ "Avatar 2", "Science-Fiction", 2022, "Sam Worthington" };
	repository.adaugaFilm(film4);
	Film film5{ "The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio" };
	repository.adaugaFilm(film5);
	assert(repository.getAll().size() == 5);
	repository.stergeFilm(2);
	assert(repository.getAll().size() == 4);
	repository.stergeFilm(3);
}

void testRepoModifica() {
	Repo repository;
	Film film1{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film1);
	Film film2{ "The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch" };
	repository.adaugaFilm(film2);
	Film film3{ "Titanic", "Epic Romance", 1997, "Leonardo DiCaprio" };
	repository.adaugaFilm(film3);
	assert(repository.getAll().size() == 3);
	Film filmModificat{ "Titanic", "Romance", 2010, "Leonardo DiCaprio" };
	repository.modificaFilm(filmModificat, 2);
	assert(repository.getAll().size() == 3);
	assert(filmModificat.getGenFilm() == "Romance");
	Film filmModificat1{ "Titanicum", "Romance", 2010, "Leonardo DiCaprio" };
	try {
		repository.modificaFilm(filmModificat1, 2);
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void testRepoCauta() {
	Repo repository;
	Film film{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film);
	auto filmCautat = repository.cautaFilm("John Wick: Chapter 4");
	assert(filmCautat.getTitluFilm() == film.getTitluFilm());
	assert(filmCautat.getGenFilm() == film.getGenFilm());
	assert(filmCautat.getAnAparitie() == film.getAnAparitie());
	assert(filmCautat.getActorPrincipal() == film.getActorPrincipal());

	try {
		repository.cautaFilm("La La Land");
		assert(false);
	}
	catch (ExceptieRepo&){
		assert(true);
	}
}

void testRepoCautaPozitieDupaTitlu() {
	Repo repository;
	Film film1{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	repository.adaugaFilm(film1);
	Film film2{ "The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch" };
	repository.adaugaFilm(film2);
	Film film3{ "Titanic", "Epic Romance", 1997, "Leonardo DiCaprio" };
	repository.adaugaFilm(film3);
	const int pozitieGasita = repository.cautaPozitieDupaTitlu("The Imitation Game");
	assert(pozitieGasita == 1);
	try {
		repository.cautaPozitieDupaTitlu("La La Land");
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void ruleazaTesteValidator() {
	Validator validator;
	Film filmValid{ "John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves" };
	Validator::valideaza(filmValid);
	Film filmInvalid{"", "", -1, ""};
	try {
		Validator::valideaza(filmInvalid);
		assert(false);
	}
	catch (const ExceptieValidare& exceptie) {
		std::stringstream sout;
		sout << exceptie;
		assert(sout.str().find("invalid") >= 0);
	}
}

void testServiceAdauga() {
	Repo repository;
	Validator validator;
	ShoppingCart cosFilme;
	Service service{ repository, validator, cosFilme };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	assert(service.getAll().size() == 1);
	
	try {
		service.adaugaFilm("", "", -1, "");
		assert(false);
	}
	catch (ExceptieValidare&) {
		assert(true);
	}

	try {
		service.adaugaFilm("John Wick: Chapter 4", "Aventura", -1, "Benedict Cumberbatch");
		assert(false);
	}
	catch (ExceptieValidare&) {
		assert(true);
	}
}

void testServiceSterge() {
	Repo repository;
	Validator validator;
	ShoppingCart cosFilme;
	Service service{ repository, validator, cosFilme };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch");
	service.adaugaFilm("Titanic", "Epic Romance", 1997, "Leonardo DiCaprio");
	service.adaugaFilm("Avatar 2", "Science-Fiction", 2022, "Sam Worthington");
	service.adaugaFilm("The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio");
	assert(service.getAll().size() == 5);
	service.stergeFilm("Titanic");
	assert(service.getAll().size() == 4);

	try {
		service.stergeFilm("La La Land");
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void testServiceModifica() {
	Repo repository;
	Validator validator;
	ShoppingCart cosFilme;
	Service service{ repository, validator, cosFilme };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch");
	service.adaugaFilm("The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio");
	assert(service.getAll().size() == 3);
	service.modificaFilm("The Imitation Game", "Drama", 2007, "Arnold Scwarzenegger");
	assert(service.getAll().size() == 3);
}

void testServiceCauta() {
	Repo repository;
	Validator validator;
	ShoppingCart cosFilme;
	Service service{ repository, validator, cosFilme };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2023, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Historic Drama", 2014, "Benedict Cumberbatch");
	service.adaugaFilm("The Revenant", "American Action Drama", 2016, "Leonardo DiCaprio");
	assert(service.getAll().size() == 3);
	Film film = service.cautaFilm("The Imitation Game");
	assert(film.getTitluFilm() == "The Imitation Game");
	assert(film.getGenFilm() == "Historic Drama");
	assert(film.getAnAparitie() == 2014);
	assert(film.getActorPrincipal() == "Benedict Cumberbatch");

	try {
		service.cautaFilm("Avatar");
		assert(false);
	}
	catch (ExceptieRepo&) {
		assert(true);
	}
}

void testServiceFiltreazaDupaGenDupaAnAparitie() {
	Repo repository;
	Validator validator;
	ShoppingCart cosFilme;
	Service service{ repository, validator, cosFilme };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2020, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Drama", 2021, "Benedict Cumberbatch");
	service.adaugaFilm("Titanic", "Romance", 2020, "Leonardo DiCaprio");
	service.adaugaFilm("Avatar 2", "Actiune", 2020, "Sam Worthington");
	service.adaugaFilm("The Revenant", "Drama", 2022, "Leonardo DiCaprio");
	assert(service.getAll().size() == 5);

	vector<Film> filmeFiltrateDupaGen = service.filtreazaDupaGen("Drama");
	assert(filmeFiltrateDupaGen.size() == 2);
	assert(filmeFiltrateDupaGen[0].getTitluFilm() == "The Imitation Game");
	assert(filmeFiltrateDupaGen[1].getTitluFilm() == "The Revenant");

	vector<Film> filmeFiltrateDupaAnAparitie = service.filtreazaDupaAnAparitie(2020);
	assert(filmeFiltrateDupaAnAparitie.size() == 3);
	assert(filmeFiltrateDupaAnAparitie[0].getTitluFilm() == "John Wick: Chapter 4");
	assert(filmeFiltrateDupaAnAparitie[1].getTitluFilm() == "Titanic");
	assert(filmeFiltrateDupaAnAparitie[2].getTitluFilm() == "Avatar 2");
}

void testServiceSortari() {
	Repo repository;
	Validator validator;
	ShoppingCart cosFilme;
	Service service{ repository, validator, cosFilme };
	service.adaugaFilm("John Wick: Chapter 4", "Actiune", 2020, "Keanu Reeves");
	service.adaugaFilm("The Imitation Game", "Drama", 2021, "Benedict Cumberbatch");
	service.adaugaFilm("Titanic", "Romance", 2020, "Leonardo DiCaprio");
	service.adaugaFilm("Avatar 2", "Actiune", 2020, "Sam Worthington");
	service.adaugaFilm("The Revenant", "Drama", 2022, "Leonardo DiCaprio");
	assert(service.getAll().size() == 5);

	vector<Film> filmeFiltrateDupaTitluCrescator = service.sorteaza(1, 1);
	assert(filmeFiltrateDupaTitluCrescator[0].getTitluFilm() == "Avatar 2");
	assert(filmeFiltrateDupaTitluCrescator[1].getTitluFilm() == "John Wick: Chapter 4");
	assert(filmeFiltrateDupaTitluCrescator[2].getTitluFilm() == "The Imitation Game");
	assert(filmeFiltrateDupaTitluCrescator[3].getTitluFilm() == "The Revenant");
	assert(filmeFiltrateDupaTitluCrescator[4].getTitluFilm() == "Titanic");

	vector<Film> filmeFiltrateDupaTitluDescrescator = service.sorteaza(1, 2);
	assert(filmeFiltrateDupaTitluDescrescator[4].getTitluFilm() == "Avatar 2");
	assert(filmeFiltrateDupaTitluDescrescator[3].getTitluFilm() == "John Wick: Chapter 4");
	assert(filmeFiltrateDupaTitluDescrescator[2].getTitluFilm() == "The Imitation Game");
	assert(filmeFiltrateDupaTitluDescrescator[1].getTitluFilm() == "The Revenant");
	assert(filmeFiltrateDupaTitluDescrescator[0].getTitluFilm() == "Titanic");

	vector<Film> filmeFiltrateDupaActorCrescator = service.sorteaza(2, 1);
	assert(filmeFiltrateDupaActorCrescator[0].getActorPrincipal() == "Benedict Cumberbatch");
	assert(filmeFiltrateDupaActorCrescator[1].getActorPrincipal() == "Keanu Reeves");
	assert(filmeFiltrateDupaActorCrescator[2].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorCrescator[3].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorCrescator[4].getActorPrincipal() == "Sam Worthington");

	vector<Film> filmeFiltrateDupaActorDescrescator = service.sorteaza(2, 2);
	assert(filmeFiltrateDupaActorDescrescator[4].getActorPrincipal() == "Benedict Cumberbatch");
	assert(filmeFiltrateDupaActorDescrescator[3].getActorPrincipal() == "Keanu Reeves");
	assert(filmeFiltrateDupaActorDescrescator[2].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorDescrescator[1].getActorPrincipal() == "Leonardo DiCaprio");
	assert(filmeFiltrateDupaActorDescrescator[0].getActorPrincipal() == "Sam Worthington");

	vector<Film> filmeFiltrateDupaAnSiGenCrescator = service.sorteaza(3, 1);
	assert(filmeFiltrateDupaAnSiGenCrescator[0].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenCrescator[1].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenCrescator[2].getGenFilm() == "Romance");
	assert(filmeFiltrateDupaAnSiGenCrescator[3].getGenFilm() == "Drama");
	assert(filmeFiltrateDupaAnSiGenCrescator[4].getGenFilm() == "Drama");

	vector<Film> filmeFiltrateDupaAnSiGenDescrescator = service.sorteaza(3, 2);
	assert(filmeFiltrateDupaAnSiGenDescrescator[4].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenDescrescator[3].getGenFilm() == "Actiune");
	assert(filmeFiltrateDupaAnSiGenDescrescator[2].getGenFilm() == "Romance");
	assert(filmeFiltrateDupaAnSiGenDescrescator[1].getGenFilm() == "Drama");
	assert(filmeFiltrateDupaAnSiGenDescrescator[0].getGenFilm() == "Drama");

	assert(filmeFiltrateDupaAnSiGenCrescator[0].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenCrescator[1].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenCrescator[2].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenCrescator[3].getAnAparitie() == 2021);
	assert(filmeFiltrateDupaAnSiGenCrescator[4].getAnAparitie() == 2022);

	assert(filmeFiltrateDupaAnSiGenDescrescator[4].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenDescrescator[3].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenDescrescator[2].getAnAparitie() == 2020);
	assert(filmeFiltrateDupaAnSiGenDescrescator[1].getAnAparitie() == 2021);
	assert(filmeFiltrateDupaAnSiGenDescrescator[0].getAnAparitie() == 2022);
}

void testServiceCart() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{ repository, validator, cosFilme };

    // Adăugăm niște filme în repo
    service.adaugaFilm("Inception", "Sci-Fi", 2010, "Leonardo DiCaprio");
    service.adaugaFilm("Interstellar", "Sci-Fi", 2014, "Matthew McConaughey");
    service.adaugaFilm("The Prestige", "Thriller", 2006, "Hugh Jackman");
    assert(service.getAll().size() == 3);

    // 1. Test adăugare în coș
    service.adaugaInCos("Inception");
    service.adaugaInCos("Interstellar");
    assert(service.getNumFilmeCos() == 2);

    // 2. Test adăugare film deja în coș -> aruncă excepție
    bool exceptieCaught = false;
    try {
        service.adaugaInCos("Inception");
    } catch (const ExceptieRepo&) {
        exceptieCaught = true;
    }
    assert(exceptieCaught);

    // 3. Test ștergere film din coș
    service.stergeDinCos("Inception");
    assert(service.getNumFilmeCos() == 1);

    // 4. Test ștergere film inexistent din coș -> aruncă excepție
    exceptieCaught = false;
    try {
        service.stergeDinCos("The Revenant"); // nu există în repo deci nici în coș
    } catch (const ExceptieRepo&) {
        exceptieCaught = true;
    }
    assert(exceptieCaught);

    // 5. Test eliberare coș
    service.elibereazaCos();
    assert(service.getNumFilmeCos() == 0);

    // 6. Test generare coș cu filme random
    service.genereazaCos(2);
    assert(service.getNumFilmeCos() == 2);

    // 7. Test generare coș cu număr mai mare decât filmele existente (nu face nimic)
    int numarFilmeInitial = service.getNumFilmeCos();
    service.genereazaCos(10); // sunt doar 3 filme în total
    assert(service.getNumFilmeCos() == numarFilmeInitial); // nu s-a schimbat

    // 8. Test dublare după generare (nu permite duplicate)
    const auto cos = service.getFilmeCos();
    for (const auto& film : cos) {
        try {
            service.adaugaInCos(film.getTitluFilm());
            assert(false); // n-ar trebui să ajungă aici
        } catch (const ExceptieRepo&) {
            assert(true); // corect
        }
    }
}

void testUndoAdauga() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{repository, validator, cosFilme};

    service.adaugaFilm("Inception", "SF", 2010, "Leonardo DiCaprio");
    assert(service.getAll().size() == 1);

    service.undo();
    assert(service.getAll().size() == 0);

    try {
        service.undo();
        assert(false); // Nu ar trebui să ajungă aici
    } catch (ExceptieRepo&) {
        assert(true);
    }
}

void testUndoSterge() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{repository, validator, cosFilme};

    service.adaugaFilm("The Dark Knight", "Action", 2008, "Christian Bale");
    Film film = service.cautaFilm("The Dark Knight");

    service.stergeFilm("The Dark Knight");
    assert(service.getAll().size() == 0);

    service.undo();
    assert(service.getAll().size() == 1);
    Film filmDupaUndo = service.cautaFilm("The Dark Knight");
    assert(film.getTitluFilm() == filmDupaUndo.getTitluFilm());
}

void testUndoModifica() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{repository, validator, cosFilme};

    service.adaugaFilm("Interstellar", "SF", 2014, "Matthew McConaughey");
    service.modificaFilm("Interstellar", "Sci-Fi", 2014, "Matthew McConaughey");

    Film filmModificat = service.cautaFilm("Interstellar");
    assert(filmModificat.getGenFilm() == "Sci-Fi");

    service.undo();
    Film filmOriginal = service.cautaFilm("Interstellar");
    assert(filmOriginal.getGenFilm() == "SF");
}

void testUndoMultiple() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{repository, validator, cosFilme};

    // Adaugă 3 filme
    service.adaugaFilm("Film1", "Gen1", 2001, "Actor1");
    service.adaugaFilm("Film2", "Gen2", 2002, "Actor2");
    service.adaugaFilm("Film3", "Gen3", 2003, "Actor3");
    assert(service.getAll().size() == 3);

    // Undo pentru ultimele 2 operații
    service.undo();
    service.undo();
    assert(service.getAll().size() == 1);

    // Verifică că a rămas doar primul film
    Film film = service.cautaFilm("Film1");
    assert(film.getTitluFilm() == "Film1");
}

void testeShoppingCart()
{
	ShoppingCart shoppingCart;
	Film f1("titlu1", "gen1", 1999, "actor1");
	Film f2("titlu2", "gen2", 2000, "actor2");
	Film f3("titlu3", "gen3", 2010, "actor3");

	// 1. Test adăugare filme
	shoppingCart.addFilm(f1);
	shoppingCart.addFilm(f2);
	assert(shoppingCart.getNumFilms() == 2);

	// 2. Test dublare
	bool exceptieCaught = false;
	try {
		shoppingCart.addFilm(f1);
	} catch (const ExceptieRepo&) {
		exceptieCaught = true;
	}
	assert(exceptieCaught);

	// 3. Test getCart conține filmele corecte
	std::vector<Film> cos = shoppingCart.getCart();
	assert(cos.size() == 2);
	assert(cos[0].getTitluFilm() == "titlu1");
	assert(cos[1].getTitluFilm() == "titlu2");

	// 4. Test ștergere film existent
	shoppingCart.removeFilm(f1);
	assert(shoppingCart.getNumFilms() == 1);

	// 5. Test ștergere film inexistent
	exceptieCaught = false;
	try {
		shoppingCart.removeFilm(f3); // f3 nu există în cos
	} catch (const ExceptieRepo&) {
		exceptieCaught = true;
	}
	assert(exceptieCaught);

	// 6. Test ordinea se păstrează
	shoppingCart.addFilm(f3);
	cos = shoppingCart.getCart();
	assert(cos[0].getTitluFilm() == "titlu2");
	assert(cos[1].getTitluFilm() == "titlu3");

	// 7. Test clearCart
	shoppingCart.clearCart();
	assert(shoppingCart.getNumFilms() == 0);
	assert(shoppingCart.getCart().empty());

	// 8. Test clearCart pe coș deja gol
	shoppingCart.clearCart();
	assert(shoppingCart.getNumFilms() == 0);

	// 9. Test adăugare + ștergere repetată
	shoppingCart.addFilm(f1);
	shoppingCart.removeFilm(f1);
	assert(shoppingCart.getNumFilms() == 0);
}

void testSalvareIncarcareFisier() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{repository, validator, cosFilme};

    const string testFile = "test_filme.txt";

    // Adaugă câteva filme
    service.adaugaFilm("Film1", "Gen1", 2001, "Actor1");
    service.adaugaFilm("Film2", "Gen2", 2002, "Actor2");

    // Salvează în fișier
    service.salveazaDateInFisier(testFile);

    // Verifică că fișierul a fost creat
    std::ifstream in(testFile);
    assert(in.is_open());
    in.close();

    // Crează un nou service și încarcă din fișier
    Repo repo2;
    Validator validator2;
    ShoppingCart cos2;
    Service service2{repo2, validator2, cos2};
    service2.incarcaDateDinFisier(testFile);

    // Verifică că datele s-au încărcat corect
    assert(service2.getAll().size() == 2);
    Film film = service2.cautaFilm("Film1");
    assert(film.getGenFilm() == "Gen1");
    assert(film.getAnAparitie() == 2001);

    // Șterge fișierul de test
    std::remove(testFile.c_str());
}

void testFisierInexistent() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{repository, validator, cosFilme};

    try {
        service.incarcaDateDinFisier("fisier_inexistent.txt");
        assert(false); // Nu ar trebui să ajungă aici
    } catch (ExceptieRepo&) {
        assert(true);
    }
}

void testFisierInvalid() {
    Repo repository;
    Validator validator;
    ShoppingCart cosFilme;
    Service service{repository, validator, cosFilme};

    const string testFile = "test_invalid.txt";

    // Creează un fișier cu format invalid
    std::ofstream out(testFile);
    out << "Titlu1,Gen1\n"; // Linie incompletă
    out << "Titlu2,Gen2,abc,Actor2\n"; // An invalid
    out.close();

    try {
        service.incarcaDateDinFisier(testFile);
        assert(false); // Nu ar trebui să ajungă aici
    } catch (ExceptieRepo&) {
        assert(true);
    }

    // Șterge fișierul de test
    std::remove(testFile.c_str());
}

void ruleazaTesteRepo() {
	testRepoAdauga();
	testRepoSterge();
	testRepoModifica();
	testRepoCauta();
	testRepoCautaPozitieDupaTitlu();
}

void ruleazaTesteService() {
	testServiceAdauga();
	testServiceSterge();
	testServiceModifica();
	testServiceCauta();
	testServiceFiltreazaDupaGenDupaAnAparitie();
	testServiceSortari();
	testUndoAdauga();
	testUndoSterge();
	testUndoModifica();
	testUndoSterge();
	testSalvareIncarcareFisier();
	testFisierInexistent();
	//testFisierInvalid();
}

void ruleazaToateTestele() {
	ruleazaTesteRepo();
	ruleazaTesteValidator();
	ruleazaTesteService();
	testServiceCart();
	testeShoppingCart();

	cout<<"\n\nToate testele au rulat cu succes!\n\n\n\n";
}
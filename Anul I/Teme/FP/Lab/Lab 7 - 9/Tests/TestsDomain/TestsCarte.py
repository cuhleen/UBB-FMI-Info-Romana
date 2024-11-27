from Domains.carte import *
from Domains.validation import validatorCarte



def testCarte():
    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    assert(carte1.getId() == 1)
    assert(carte1.getTitlu() == "Titlu 1")
    assert(carte1.getDescriere() == "Descriere 1")
    assert(carte1.getAutor() == "Autor 1")

    carte1.setId(2)
    assert(carte1.getId() == 2)

    carte1.setTitlu("Titlu 2")
    assert(carte1.getTitlu() == "Titlu 2")

    carte1.setDescriere("Descriere 2")
    assert(carte1.getDescriere() == "Descriere 2")

    carte1.setAutor("Autor 2")
    assert(carte1.getAutor() == "Autor 2")



def testValidareCarte():
    validator = validatorCarte()

    # ID-ul trebuie să fie un număr natural pozitiv
    carte1 = Carte(-1, "Titlu 1", "Descriere 1", "Autor 1")
    try:
        validator.validate(carte1)
        assert False
    except ValueError:
        assert True

    # Titlul trebuie să aibă cel puțin un caracter
    carte2 = Carte(2, "", "Descriere 2", "Autor 2")
    try:
        validator.validate(carte2)
        assert False
    except ValueError:
        assert True

    # Descrierea trebuie să aibă cel puțin un caracter
    carte3 = Carte(3, "Titlu 3", "", "Autor 3")
    try:
        validator.validate(carte3)
        assert False
    except ValueError:
        assert True

    # Autorul trebuie să aibă cel puțin un caracter
    carte4 = Carte(4, "Titlu 4", "Descriere 4", "")
    try:
        validator.validate(carte4)
        assert False
    except ValueError:
        assert True

    # Carte invalidă din mai multe puncte de vedere
    carte5 = Carte(-5, "Titlu 5", "", "")
    try:
        validator.validate(carte5)
        assert False
    except ValueError:
        assert True



def testEgalCarte():
    carte1 = Carte(12, "Titlu 12", "Descriere 12", "Autor 12")
    carte2 = Carte(12, "Titlu 12", "Descriere 12", "Autor 12")

    assert (carte1 == carte2)

    carte3 = Carte(3, "Titlu 3", "Descriere 3", "Autor 3")

    assert (carte1 != carte3)
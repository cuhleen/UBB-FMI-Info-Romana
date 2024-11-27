from Domains.carte import Carte
from Repository.repository import RepositoryCarti



def testStoreRepoCarte():
    testRepo = RepositoryCarti()
    assert(testRepo.getSize() == 0)

    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    testRepo.store(carte1)
    assert(testRepo.getSize() == 1)

    carte2 = Carte(1, "Titlu 2", "Descriere 2", "Autor 2")

    """try:
        testRepo.store(carte2)
        assert False
    except ValueError:
        assert True"""

    assert (testRepo.getSize() == 1)

    testRepo.store(Carte(2, "Titlu 3", "Descriere 3", "Autor 3"))
    assert(testRepo.getSize() == 2)



def testUpdateRepoCarte():
    testRepo = RepositoryCarti()
    assert(testRepo.getSize() == 0)

    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    """try:
        testRepo.update(carte1)
        assert False
    except ValueError:
        assert True"""

    testRepo.store(carte1)
    assert(testRepo.getSize() == 1)

    carte2 = Carte(1, "Titlu 2", "Descriere 2", "Autor 2")

    testRepo.update(carte2)

    assert(testRepo.getSize() == 1)
    carteModif = testRepo.find(1)
    assert(carteModif.getTitlu() == "Titlu 2")
    assert(carteModif.getDescriere() == "Descriere 2")
    assert(carteModif.getAutor() == "Autor 2")

    carteNoua = Carte(4, "Titlu 4", "Descriere 4", "Autor 4")
    """try:
        testRepo.update(carteNoua)
        assert False
    except ValueError:
        assert True"""
    assert(testRepo.getSize() == 1)



def testFindRepoCarte():
    testRepo = RepositoryCarti()
    assert(testRepo.getSize() == 0)
    carteGasita = testRepo.find(1)
    assert(carteGasita is None)

    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    carte2 = Carte(2, "Titlu 2", "Descriere 2", "Autor 2")
    carte3 = Carte(3, "Titlu 3", "Descriere 3", "Autor 3")

    testRepo.store(carte1)
    testRepo.store(carte2)
    testRepo.store(carte3)

    assert(testRepo.getSize() == 3)

    carte1Gasita = testRepo.find(1)
    assert(carte1Gasita.getTitlu() == "Titlu 1")
    assert (carte1Gasita.getDescriere() == "Descriere 1")
    assert (carte1Gasita.getAutor() == "Autor 1")

    carte2Gasita = testRepo.find(2)
    assert(carte2Gasita.getTitlu() == "Titlu 2")
    assert (carte2Gasita.getDescriere() == "Descriere 2")
    assert (carte2Gasita.getAutor() == "Autor 2")

    carte3Gasita = testRepo.find(3)
    assert(carte3Gasita.getTitlu() == "Titlu 3")
    assert (carte3Gasita.getDescriere() == "Descriere 3")
    assert (carte3Gasita.getAutor() == "Autor 3")

    carte4Gasita = testRepo.find(4)
    assert(carte4Gasita is None)
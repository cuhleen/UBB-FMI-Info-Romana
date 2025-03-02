import unittest

from Repository.repository import RepositoryCarti
from Domains.validation import validatorCarte
from Service.controller import ControllerCarti



class TestServiceCarte(unittest.TestCase):
    def setUp(self):
        testRepo = RepositoryCarti()
        validator = validatorCarte()
        self.testService = ControllerCarti(testRepo, validator)

    def testAdaugaCarte(self):
        self.assertEqual(len(self.testService.getAll()), 0)
        self.testService.adaugaCarte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.assertEqual(len(self.testService.getAll()), 1)
        self.testService.adaugaCarte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.assertEqual(len(self.testService.getAll()), 1)
        self.testService.adaugaCarte(1, "Titlu 2", "Descriere 2", "Autor 2")
        self.assertEqual(len(self.testService.getAll()), 1)

    def testFiltru(self):
        self.assertEqual(len(self.testService.getAll()), 0)

        self.testService.adaugaCarte(1, "Titlu 1", "Descriere 1", "Autor 1")
        self.testService.adaugaCarte(2, "Titlu 2", "Descriere 2", "Autor 1")
        self.testService.adaugaCarte(3, "Titlu 3", "Descriere 3", "Autor 2")
        self.testService.adaugaCarte(4, "Titlu 4", "Descriere 4", "Autor 1")
        self.testService.adaugaCarte(5, "Titlu 5", "Descriere 5", "Autor 3")
        self.testService.adaugaCarte(6, "Titlu 6", "Descriere 6", "Autor 1")
        self.testService.adaugaCarte(7, "Titlu 7", "Descriere 7", "Autor 1")
        self.testService.adaugaCarte(8, "Titlu 8", "Descriere 8", "Autor 4")
        self.testService.adaugaCarte(9, "Titlu 9", "Descriere 9", "Autor 4")

        self.assertEqual(len(self.testService.getAll()), 9)

        listaFiltrata = self.testService.filtreazaDupaAutor("Autor 1")
        assert (len(listaFiltrata) == 5)

        listaFiltrata = self.testService.filtreazaDupaAutor("Autor 4")
        assert (len(listaFiltrata) == 2)

        listaFiltrata = self.testService.filtreazaDupaAutor("Autor 10")
        assert (len(listaFiltrata) == 0)

    def tearDown(self):
        pass



"""
def testAddServiceCarte():
    testService = ControllerCarti(RepositoryCarti(),validatorCarte())
    assert(len(testService.getAll()) == 0)

    testService.adaugaCarte(1, "Titlu 1", "Descriere 1", "Autor 1")
    assert(len(testService.getAll()) == 1)

    try:
        testService.adaugaCarte(1, "Titlu 2", "Descriere 2", "Autor 2")
        assert False
    except ValueError:
        assert True

    try:
        testService.adaugaCarte(3, "", "", "Autor 3")
        assert False
    except ValueError:
        assert True

    try:
        testService.adaugaCarte(4, "Titlu 4", "Descriere 4", "")
        assert False
    except ValueError:
        assert True

def testActualizeazaServiceCarte():
    testService = ControllerCarti(RepositoryCarti(), validatorCarte())
    assert(len(testService.getAll()) == 0)

    testService.adaugaCarte(1, "Titlu 1", "Descriere 1", "Autor 1")
    testService.adaugaCarte(2, "Titlu 2", "Descriere 2", "Autor 2")
    testService.adaugaCarte(3, "Titlu 3", "Descriere 3", "Autor 3")

    assert(len(testService.getAll()) == 3)

    testService.actualizeazaCarte(1, "Titlu 1", "Descriere 1", "Autor 111")
    carteNoua = testService.cautaCarte(1)
    assert(carteNoua.getAutor() == "Autor 111")

    try:
        testService.actualizeazaCarte(3, "", "", "Autor 333")
        assert False
    except ValueError:
        assert True

    try:
        testService.actualizeazaCarte(999, "Titlu 999", "Descriere 999", "Autor 999")
        assert False
    except ValueError:
        assert True

def testFiltreServiceCarte():
    testService = ControllerCarti(RepositoryCarti(), validatorCarte())
    assert(len(testService.getAll()) == 0)

    testService.adaugaCarte(1, "Titlu 1", "Descriere 1", "Autor 1")
    testService.adaugaCarte(2, "Titlu 2", "Descriere 2", "Autor 1")
    testService.adaugaCarte(3, "Titlu 3", "Descriere 3", "Autor 2")
    testService.adaugaCarte(4, "Titlu 4", "Descriere 4", "Autor 1")
    testService.adaugaCarte(5, "Titlu 5", "Descriere 5", "Autor 3")
    testService.adaugaCarte(6, "Titlu 6", "Descriere 6", "Autor 1")
    testService.adaugaCarte(7, "Titlu 7", "Descriere 7", "Autor 1")
    testService.adaugaCarte(8, "Titlu 8", "Descriere 8", "Autor 4")
    testService.adaugaCarte(9, "Titlu 9", "Descriere 9", "Autor 4")

    assert(len(testService.getAll()) == 9)

    listaFiltrata = testService.filtreazaDupaAutor("Autor 1")
    assert(len(listaFiltrata) == 5)

    listaFiltrata = testService.filtreazaDupaAutor("Autor 4")
    assert (len(listaFiltrata) == 2)

    listaFiltrata = testService.filtreazaDupaAutor("Autor 10")
    assert (len(listaFiltrata) == 0)
"""
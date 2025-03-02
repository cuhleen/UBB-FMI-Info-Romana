import unittest

from Repository.repository import RepositoryClienti
from Domains.validation import validatorClient
from Service.controller import ControllerClienti



class TestServiceClient(unittest.TestCase):
    def setUp(self):
        testRepo = RepositoryClienti()
        validator = validatorClient()
        self.testService = ControllerClienti(testRepo, validator)

    def testAdaugaClient(self):
        self.assertEqual(len(self.testService.getAll()), 0)
        self.testService.adaugaClient(1, "Nume Prenume 1", 1111111111111)
        self.assertEqual(len(self.testService.getAll()), 1)
        self.testService.adaugaClient(1, "Nume Prenume 2", 1111111111111)
        self.assertEqual(len(self.testService.getAll()), 1)
        self.testService.adaugaClient(1, "Nume Prenume 2", 2222222222222)
        self.assertEqual(len(self.testService.getAll()), 2)

    def testFiltru(self):
        self.assertEqual(len(self.testService.getAll()), 0)

        self.testService.adaugaClient(1, "Nume Prenume 1", 5000119111111)
        self.testService.adaugaClient(2, "Nume Prenume 2", 5000207111112)
        self.testService.adaugaClient(3, "Nume Prenume 3", 5020510111113)
        self.testService.adaugaClient(4, "Nume Prenume 4", 5030619111114)
        self.testService.adaugaClient(5, "Nume Prenume 5", 5050312111115)
        self.testService.adaugaClient(6, "Nume Prenume 6", 5060316111116)
        self.testService.adaugaClient(7, "Nume Prenume 7", 5070119111117)
        self.testService.adaugaClient(8, "Nume Prenume 8", 5070424111118)
        self.testService.adaugaClient(9, "Nume Prenume 9", 5071029111119)

        """
        2000/01/19
        2000/02/07
        2002/05/10
        2003/06/19
        2005/03/12
        2006/03/16
        2007/01/19
        2007/04/24
        2007/10/29
        """

        assert (len(self.testService.getAll()) == 9)

        listaFiltrata = self.testService.filtreazaDupaDataNasterii("2000/01/01", "2001/01/01")
        assert (len(listaFiltrata) == 2)

        listaFiltrata = self.testService.filtreazaDupaDataNasterii("2010/01/01", "2012/01/01")
        assert (len(listaFiltrata) == 0)



"""
def testAddServiceClient():
    testService = ControllerClienti(RepositoryClienti(), validatorClient())

    assert(len(testService.getAll()) == 0)

    testService.adaugaClient(1, "Nume Prenume 1", 1111111111111)
    assert(len(testService.getAll()) == 1)

    try:
        testService.adaugaClient(1, "Nume Prenume 2", 1111111111112)
        assert False
    except ValueError:
        assert True

    try:
        testService.adaugaClient(3, "", "")
        assert False
    except ValueError:
        assert True

def testActualizeazaServiceClient():
    testService = ControllerClienti(RepositoryClienti(), validatorClient())
    assert(len(testService.getAll()) == 0)

    testService.adaugaClient(1, "Nume Prenume 1", 1111111111111)
    testService.adaugaClient(2, "Nume Prenume 2", 1111111111112)
    testService.adaugaClient(3, "Nume Prenume 3", 1111111111113)

    assert(len(testService.getAll()) == 3)

    testService.actualizeazaClient(1, "Nume Prenume 111", 1111111111111)
    ClientNou = testService.cautaClient(1)
    assert(ClientNou.getNume() == "Nume Prenume 111")

    try:
        testService.actualizeazaClient(3, "", "")
        assert False
    except ValueError:
        assert True

    try:
        testService.actualizeazaClient(999, "Nume Prenume 999", 1111111111999)
        assert False
    except ValueError:
        assert True

def testFiltreServiceClient():
    testService = ControllerClienti(RepositoryClienti(), validatorClient())
    assert(len(testService.getAll()) == 0)

    testService.adaugaClient(1, "Nume Prenume 1", 5000119111111)
    testService.adaugaClient(2, "Nume Prenume 2", 5000207111112)
    testService.adaugaClient(3, "Nume Prenume 3", 5020510111113)
    testService.adaugaClient(4, "Nume Prenume 4", 5030619111114)
    testService.adaugaClient(5, "Nume Prenume 5", 5050312111115)
    testService.adaugaClient(6, "Nume Prenume 6", 5060316111116)
    testService.adaugaClient(7, "Nume Prenume 7", 5070119111117)
    testService.adaugaClient(8, "Nume Prenume 8", 5070424111118)
    testService.adaugaClient(9, "Nume Prenume 9", 5071029111119)
    
    2000/01/19
    2000/02/07
    2002/05/10
    2003/06/19
    2005/03/12
    2006/03/16
    2007/01/19
    2007/04/24
    2007/10/29
    

    assert(len(testService.getAll()) == 9)

    listaFiltrata = testService.filtreazaDupaDataNasterii("2000/01/01", "2001/01/01")
    assert(len(listaFiltrata) == 2)

    listaFiltrata = testService.filtreazaDupaDataNasterii("2010/01/01", "2012/01/01")
    assert (len(listaFiltrata) == 0)
"""
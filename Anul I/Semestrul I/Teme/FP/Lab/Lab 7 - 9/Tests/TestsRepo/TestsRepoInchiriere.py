import unittest

from Domains.inchiriere import Inchiriere
from Domains.client import Client
from Domains.carte import Carte
from Repository.repository import RepositoryInchiriere

class TestRepoInchiriere(unittest.TestCase):
    def setUp(self):
        print("setUp called")
        self.testRepo = RepositoryInchiriere()

    def testStore(self):
        self.assertEqual(len(self.testRepo.getAll()), 0)
        client1 = Client(1, "Nume Prenume 1", "1111111111111")
        carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        inchiriere1 = Inchiriere(client1, carte1)
        self.testRepo.store(inchiriere1)
        self.assertEqual(len(self.testRepo.getAll()), 1)

        client2 = Client(2, "Nume Prenume 2", "2222222222222")
        carte2 = Carte(2, "Titlu 2", "Descriere 2", "Autor 2")
        inchiriere2 = Inchiriere(client2, carte2)
        self.testRepo.store(inchiriere2)
        self.assertEqual(len(self.testRepo.getAll()), 2)
"""
def testStoreRepoInchiriere():
    testRepo = RepositoryInchiriere()
    assert(testRepo.getSize() == 0)

    client1 = Client(1, "Nume Prenume 1", "1111111111111")
    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    inchiriere1 = Inchiriere(client1, carte1)
    testRepo.store(inchiriere1)
    assert(testRepo.getSize() == 1)

    try:
        client2 = Client(1, "Nume Prenume 1", "1111111111111")
        carte2 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        inchiriere2 = Inchiriere(client2, carte2)
        testRepo.store(inchiriere2)
        assert False
    except ValueError:
        assert True


    client3 = Client(3, "Nume Prenume 3", "3333333333333")
    carte3 = Carte(3, "Titlu 3", "Descriere 3", "Autor 3")
    inchiriere3 = Inchiriere(client3, carte3)
    testRepo.store(inchiriere3)
    assert(testRepo.getSize() == 2)
"""
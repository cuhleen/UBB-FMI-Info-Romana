import unittest

from Domains.client import Client
from Repository.repository import RepositoryClienti
from Repository.repository import RepositoryClientiFile

class TestRepoClient(unittest.TestCase):
    def setUp(self)->None:
        print("setUp called")
        self.test_repo = RepositoryClienti()

    def testStore(self):
        print("testStore")
        self.assertEqual(len(self.test_repo.getAll()), 0)
        c = Client(1, "Nume Prenume 1", 1111111111111)
        self.test_repo.store(c)
        self.assertEqual(len(self.test_repo.getAll()), 1)

    def testFind(self):
        self.assertEqual(len(self.test_repo.getAll()), 0)
        c = self.test_repo.find(1)
        self.assertEqual(c, None)
        c = Client(1, "Nume Prenume 1", 1111111111111)
        self.test_repo.store(c)
        self.assertEqual(len(self.test_repo.getAll()), 1)
        cGasit = Client(1, "Nume Prenume 1", 1111111111111)
        self.assertEqual(self.test_repo.find(1), cGasit)

    def testGetAll(self):
        print("testGetAll")
        self.assertEqual(len(self.test_repo.getAll()), 0)
        c1 = Client(1, "Nume Prenume 1", 1111111111111)
        self.test_repo.store(c1)
        self.assertEqual(len(self.test_repo.getAll()), 1)
        c2 = Client(2, "Nume Prenume 1", 2222222222222)
        self.test_repo.store(c2)
        self.assertEqual(len(self.test_repo.getAll()), 2)

    def tearDown(self):
        print("tearDown called")



class testRepoClientFile(unittest.TestCase):
    def setUp(self):
        self.testRepo = RepositoryClientiFile("ClientiDefaultTest.txt")

    def testStore(self):
        self.assertEqual(len(self.testRepo.getAll()), 5)
        c = Client(1, "Nume Prenume 1", 1111111111111)
        self.testRepo.store(c)
        self.assertEqual(len(self.testRepo.getAll()), 6)

    def tearDown(self):
        with open("ClientiDefaultTest.txt", "w") as f:
            f.write('111,"Augustin Banciu",1960923345678\n112,"Catalina Enache",2930501123456\n113,"Andrei Morariu",5030712234567\n114,"Denisa Dinu",6061109345678\n115,"Mazon Tudor",1791225456789')



class TestRepoClientBlackBox(unittest.TestCase):
    def setUp(self) -> None:
        self.test_repo = RepositoryClienti()

    def testStoreFindDelete(self):
        lungimeInitiala = len(self.test_repo.getAll())

        c = Client(1234, "Nume Prenume 1", 1111111111111)
        self.test_repo.store(c)
        self.assertEqual(len(self.test_repo.getAll()), lungimeInitiala + 1)

        clientGasit = self.test_repo.find(1234)
        self.assertIsNotNone(clientGasit)
        self.assertEqual(clientGasit.getNume(), c.getNume())
        self.assertEqual(clientGasit.getCnp(), c.getCnp())

        self.test_repo.sterge(1234)
        self.assertEqual(len(self.test_repo.getAll()), lungimeInitiala)
        self.assertIsNone(self.test_repo.find(1234))

    def testFindNonExistentClient(self):
        self.assertIsNone(self.test_repo.find(1234))

    def testGetALl(self):
        c1 = Client(1, "Nume Prenume 1", 1111111111111)
        c2 = Client(2, "Nume Prenume 2", 2222222222222)

        self.test_repo.store(c1)
        self.test_repo.store(c2)

        allClients = self.test_repo.getAll()
        self.assertIn(c1, allClients)
        self.assertIn(c2, allClients)

    def tearDown(self):
        pass



"""
def testStoreRepoClient():
    testRepo = RepositoryClienti()
    assert(testRepo.getSize() == 0)

    client1 = Client(1, "Nume Prenume 1", "1111111111111")
    testRepo.store(client1)
    assert(testRepo.getSize() == 1)

    client2 = Client(1, "Nume Prenume 2", "2222222222222")

    try:
        testRepo.store(client2)
        assert False
    except ValueError:
        assert True

    assert (testRepo.getSize() == 1)

    testRepo.store(Client(2, "Nume Prenume 3", "3333333333333"))
    assert(testRepo.getSize() == 2)



def testUpdateRepoClient():
    testRepo = RepositoryClienti()
    assert(testRepo.getSize() == 0)

    client1 = Client(1, "Nume Prenume 1", "1111111111111")
    try:
        testRepo.update(client1)
        assert False
    except ValueError:
        assert True

    testRepo.store(client1)
    assert(testRepo.getSize() == 1)

    client2 = Client(1, "Nume Prenume Actualizat", "1111111111111")
    testRepo.update(client2)

    assert(testRepo.getSize() == 1)
    clientModif = testRepo.find(1)
    assert(clientModif.getNume() == "Nume Prenume Actualizat")
    assert(clientModif.getCnp() == "1111111111111")

    clientNou = Client(4, "Nume Prenume Nou", "4444444444444")
    try:
        testRepo.update(clientNou)
        assert False
    except ValueError:
        assert True
    assert(testRepo.getSize() == 1)



def testFindRepoClient():
    testRepo = RepositoryClienti()
    assert(testRepo.getSize() == 0)
    clientGasit = testRepo.find(1)
    assert(clientGasit is None)

    client1 = Client(1, "Nume Prenume 1", "1111111111111")
    client2 = Client(2, "Nume Prenume 2", "2222222222222")
    client3 = Client(3, "Nume Prenume 3", "3333333333333")

    testRepo.store(client1)
    testRepo.store(client2)
    testRepo.store(client3)

    assert(testRepo.getSize() == 3)

    client1Gasit = testRepo.find(1)
    assert(client1Gasit.getNume() == "Nume Prenume 1")
    assert(client1Gasit.getCnp() == "1111111111111")

    client2Gasit = testRepo.find(2)
    assert(client2Gasit.getNume() == "Nume Prenume 2")
    assert(client2Gasit.getCnp() == "2222222222222")

    client3Gasit = testRepo.find(3)
    assert(client3Gasit.getNume() == "Nume Prenume 3")
    assert(client3Gasit.getCnp() == "3333333333333")

    client4Gasit = testRepo.find(4)
    assert(client4Gasit is None)
"""
from Domains.client import Client
from Repository.repository import RepositoryClienti



def testStoreRepoClient():
    testRepo = RepositoryClienti()
    assert(testRepo.getSize() == 0)

    client1 = Client(1, "Nume Prenume 1", "1111111111111")
    testRepo.store(client1)
    assert(testRepo.getSize() == 1)

    client2 = Client(1, "Nume Prenume 2", "2222222222222")

    """try:
        testRepo.store(client2)
        assert False
    except ValueError:
        assert True"""

    assert (testRepo.getSize() == 1)

    testRepo.store(Client(2, "Nume Prenume 3", "3333333333333"))
    assert(testRepo.getSize() == 2)



def testUpdateRepoClient():
    testRepo = RepositoryClienti()
    assert(testRepo.getSize() == 0)

    client1 = Client(1, "Nume Prenume 1", "1111111111111")
    """try:
        testRepo.update(client1)
        assert False
    except ValueError:
        assert True"""

    testRepo.store(client1)
    assert(testRepo.getSize() == 1)

    client2 = Client(1, "Nume Prenume Actualizat", "1111111111111")
    testRepo.update(client2)

    assert(testRepo.getSize() == 1)
    clientModif = testRepo.find(1)
    assert(clientModif.getNume() == "Nume Prenume Actualizat")
    assert(clientModif.getCnp() == "1111111111111")

    clientNou = Client(4, "Nume Prenume Nou", "4444444444444")
    """try:
        testRepo.update(clientNou)
        assert False
    except ValueError:
        assert True"""
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

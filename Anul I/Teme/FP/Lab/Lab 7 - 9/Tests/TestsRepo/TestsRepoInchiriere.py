from Domains.inchiriere import Inchiriere
from Domains.client import Client
from Domains.carte import Carte
from Repository.repository import RepositoryInchiriere



def testStoreRepoInchiriere():
    testRepo = RepositoryInchiriere()
    assert(testRepo.getSize() == 0)

    client1 = Client(1, "Nume Prenume 1", "1111111111111")
    carte1 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
    inchiriere1 = Inchiriere(client1, carte1)
    testRepo.store(inchiriere1)
    assert(testRepo.getSize() == 1)

    """try:
        client2 = Client(1, "Nume Prenume 1", "1111111111111")
        carte2 = Carte(1, "Titlu 1", "Descriere 1", "Autor 1")
        inchiriere2 = Inchiriere(client2, carte2)
        testRepo.store(inchiriere2)
        assert False
    except ValueError:
        assert True"""


    client3 = Client(3, "Nume Prenume 3", "3333333333333")
    carte3 = Carte(3, "Titlu 3", "Descriere 3", "Autor 3")
    inchiriere3 = Inchiriere(client3, carte3)
    testRepo.store(inchiriere3)
    assert(testRepo.getSize() == 2)
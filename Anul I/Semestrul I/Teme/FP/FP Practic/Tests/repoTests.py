from Domain.melodie import Melodie
from Repo.repoMelodie import RepoMelodie

def toateTesteleRepo():
    testStoreDelete()
    testModificare()
    print("Au trecut cu succes toate testele repo!\n")

def testStoreDelete():
    testRepo = RepoMelodie("repoFisierTEST.txt")
    assert testRepo.getSize() == 10
    melodie = Melodie("testTitlu", "testArtist", "testGen", "01.01.2025")
    testRepo.adaugaMelodie(melodie)
    assert testRepo.getSize() == 11

    testRepo.stergeUltimaMelodie()

def testModificare():
    testRepo = RepoMelodie("repoFisierTEST.txt")
    melodie = Melodie("melodie10", "artist7", "genEditat", "02.02.2022")
    testRepo.modificaMelodie(melodie)

    melodii = testRepo.getAll()
    melodiaEditata = melodii[9]
    assert melodiaEditata.getGen() == "genEditat"
    assert melodiaEditata.getData() == "02.02.2022"

    melodie = Melodie("melodie10", "artist7", "Rock", "03.02.2025")
    testRepo.modificaMelodie(melodie)
from Domain.validation import validation
from Repo.repoMelodie import RepoMelodie
from Service.controllerMelodie import ControllerMelodie

def toateTesteleService():
    testStoreDelete()
    testModificaMelodie()
    testStatistici()
    print("Au trecut cu succes toate testele service!\n")

def testStoreDelete():
    testRepo = RepoMelodie("repoFisierTEST.txt")
    validator = validation()
    testService = ControllerMelodie(testRepo, validator)

    assert len(testService.getAll()) == 10
    testService.adaugaMelodie("melodieTEST", "artistTEST", "Jazz", "01.01.2025")
    assert len(testService.getAll()) == 11
    testService.stergeUltimaMelodie()


def testModificaMelodie():
    testRepo = RepoMelodie("repoFisierTEST.txt")
    validator = validation()
    testService = ControllerMelodie(testRepo, validator)

    melodii = testRepo.getAll()
    melodieEditata = melodii[9]
    assert melodieEditata.getData() == "03.02.2025"
    assert melodieEditata.getGen() == "Rock"

    testService.modificaMelodie("melodie10","artist7","Jazz","03.02.2099")

    assert melodieEditata.getData() == "03.02.2099"
    assert melodieEditata.getGen() == "Jazz"

    testService.modificaMelodie("melodie10","artist7","Rock","03.02.2025")

def testStatistici():
    testRepo = RepoMelodie("repoFisierTEST.txt")
    validator = validation()
    testService = ControllerMelodie(testRepo, validator)

    ceaMaiNouaTEST = testService.ceaMaiNoua()
    ceaMaiVecheTEST = testService.ceaMaiVeche()

    assert ceaMaiNouaTEST.getData() == "03.02.2027"
    assert ceaMaiVecheTEST.getData() == "03.02.2023"

    nrRock, nrPop, nrJazz = testService.numarMelodiiPerGen()
    assert nrRock == 5
    assert nrPop == 3
    assert nrJazz == 2
from Tests.TestsDomain.TestsClient import *
from Tests.TestsDomain.TestsCarte import *
from Tests.TestsRepo.TestsRepoClient import *
from Tests.TestsRepo.TestsRepoCarte import *
from Tests.TestsRepo.TestsRepoInchiriere import *
from Tests.TestsService.TestsServiceClient import *
from Tests.TestsService.TestsServiceCarte import *

def runTestsDomain():
    testClient()
    testValidareClient()
    testEgalClient()

    testCarte()
    testValidareCarte()
    testEgalCarte()

    print("\nTestele Domain rulate cu succes!")

def runTestsRepo():
    testStoreRepoClient()
    testUpdateRepoClient()
    testFindRepoClient()

    testStoreRepoCarte()
    testUpdateRepoCarte()
    testFindRepoCarte()

    testStoreRepoInchiriere()

    print("Testele Repo rulate cu succes!")

def runTestsService():
    testAddServiceClient()
    testActualizeazaServiceClient()
    testFiltreServiceClient()

    testAddServiceCarte()
    testActualizeazaServiceCarte()
    testFiltreServiceCarte()

    print("Testele Service rulate cu succes!")

def runAlltests():
    runTestsDomain()
    runTestsRepo()
    runTestsService()
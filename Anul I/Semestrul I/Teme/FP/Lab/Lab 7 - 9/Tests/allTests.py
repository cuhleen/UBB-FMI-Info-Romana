from Tests.TestsDomain.TestsClient import *
from Tests.TestsDomain.TestsCarte import *
from Tests.TestsRepo.TestsRepoClient import *
from Tests.TestsRepo.TestsRepoCarte import *
from Tests.TestsRepo.TestsRepoInchiriere import *
from Tests.TestsService.TestsServiceClient import *
from Tests.TestsService.TestsServiceCarte import *

def runTestsDomain():
    TestClient()
    TestCarte()

    print("\nTestele Domain rulate cu succes!")

def runTestsRepo():
    TestRepoClient()
    TestRepoCarte()
    TestRepoInchiriere()

    print("Testele Repo rulate cu succes!")

def runTestsService():
    TestServiceClient()
    TestServiceCarte()

    print("Testele Service rulate cu succes!")

def runAlltests():
    runTestsDomain()
    runTestsRepo()
    runTestsService()
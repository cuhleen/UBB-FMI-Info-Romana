from Repo.repoCamera import RepoCamera
from Service.controllerCamera import ServiceCamera

class testServiceCamera:
    def testCameraDupaNumarPersoaneSiVedere(self):
        testRepo = RepoCamera("TestRepo.txt")
        testServ = ServiceCamera(testRepo)

        camere = testServ.cameraDupaNumarPersoaneSiVedere(1, '"mare"')
        assert(len(camere) == 2)

        camere = testServ.cameraDupaNumarPersoaneSiVedere(1, '"curte"')
        assert (len(camere) == 1)

        camere = testServ.cameraDupaNumarPersoaneSiVedere(999, '"munte"')
        assert (len(camere) == 0)

    def testInchiriazaCameraPret(self):
        testRepo = RepoCamera("TestRepo.txt")
        testServ = ServiceCamera(testRepo)

        assert(testServ.inchiriazaCameraPret(5, 10) == 300)

        assert (testServ.inchiriazaCameraPret(999, 10) is None)

    def testInchiriazaCameraCamera(self):
        testRepo = RepoCamera("TestRepo.txt")
        testServ = ServiceCamera(testRepo)

        assert (len(testServ.inchiriazaCameraCamera(999)) == 0)
        assert (len(testServ.inchiriazaCameraCamera(5)) == 1)
from Repo.repoCamera import RepoCamera

class testRepoCamera():
    def testFind(self):
        testRepo = RepoCamera("TestRepo.txt")

        cameraGasita = testRepo.find(1)

        assert (cameraGasita.getLocuri() == 1)
        assert (cameraGasita.getVedere() == '"mare"')
        assert (cameraGasita.getPretNoapte() == 10)

        cameraNuGasita = testRepo.find(999)
        assert (cameraNuGasita is None)

    def testGetAll(self):
        testRepo = RepoCamera("TestRepo.txt")
        assert (len(testRepo.getAll()) == 5)
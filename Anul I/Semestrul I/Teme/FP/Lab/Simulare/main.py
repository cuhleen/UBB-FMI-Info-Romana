from UI.console import Console
from Service.controllerCamera import ServiceCamera
from Repo.repoCamera import RepoCamera
from Tests.RepoTests import testRepoCamera
from Tests.ServiceTests import testServiceCamera

testRepoCamera = testRepoCamera()
testRepoCamera.testFind()
testRepoCamera.testGetAll()

testServiceCamera = testServiceCamera()
testServiceCamera.testCameraDupaNumarPersoaneSiVedere()
testServiceCamera.testInchiriazaCameraPret()
testServiceCamera.testInchiriazaCameraCamera()

print("\n\n\n\n\n\n\n\nTestele au rulat cu succes!")

repoCamera = RepoCamera("repoCamereFile.txt")
servCamera = ServiceCamera(repoCamera)
console = Console(servCamera)

console.run()
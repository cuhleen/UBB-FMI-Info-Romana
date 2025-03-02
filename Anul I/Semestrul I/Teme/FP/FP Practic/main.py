from Tests.repoTests import toateTesteleRepo
from Tests.serviceTests import toateTesteleService
from UI.console import Console
from Service.controllerMelodie import ControllerMelodie
from Repo.repoMelodie import RepoMelodie
from Domain.validation import validation

print('\n')
toateTesteleRepo()
toateTesteleService()

repoMelodie = RepoMelodie("repoFisier.txt")
validator = validation()
controllerMelodie = ControllerMelodie(repoMelodie, validator)
console = Console(controllerMelodie)

console.run()
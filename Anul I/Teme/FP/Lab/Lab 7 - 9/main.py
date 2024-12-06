from Domains.validation import validatorCarte
from Domains.validation import validatorClient
from Repository.repository import RepositoryCarti
from Repository.repository import RepositoryClienti
from Repository.repository import RepositoryInchiriere
from Repository.repository import RepositoryCartiFile
from Repository.repository import RepositoryClientiFile
from Repository.repository import RepositoryInchiriereFile
from Service.controller import ControllerCarti
from Service.controller import ControllerClienti
from Service.controller import ControllerInchirieri
from UI.console import Console
from Tests.allTests import runAlltests
p1
runAlltests()

validatorCarte1 = validatorCarte()
validatorClient1 = validatorClient()

repoCarte = RepositoryCarti()
repoClient = RepositoryClienti()
repoInchirieri = RepositoryInchiriere()

repoCarteFile = RepositoryCartiFile("Text Repos/CartiDefault.txt")
repoClientFile = RepositoryClientiFile("Text Repos/ClientiDefault.txt")
repoInchiriereFile = RepositoryInchiriereFile("Text Repos/InchirieriDefault.txt", repoClientFile, repoCarteFile)

cartiService = ControllerCarti(repoCarte, validatorCarte1)
clientiService = ControllerClienti(repoClient, validatorClient1)
inchirieriService = ControllerInchirieri(repoClient, repoCarte, repoInchirieri)

console = Console(cartiService, clientiService, inchirieriService)

console.run()
from Domains.validation import validatorCarte
from Domains.validation import validatorClient
from Repository.repository import RepositoryCarti
from Repository.repository import RepositoryClienti
from Repository.repository import RepositoryInchiriere
from Service.controller import ControllerCarti
from Service.controller import ControllerClienti
from Service.controller import ControllerInchirieri
from UI.console import Console
from Tests.allTests import runAlltests

runAlltests()

validatorCarte1 = validatorCarte()
validatorClient1 = validatorClient()

repoCarte = RepositoryCarti()
repoClient = RepositoryClienti()
repoInchirieri = RepositoryInchiriere()

cartiService = ControllerCarti(repoCarte, validatorCarte1)
clientiService = ControllerClienti(repoClient, validatorClient1)
inchirieriService = ControllerInchirieri(repoClient, repoCarte, repoInchirieri)

console = Console(cartiService, clientiService, inchirieriService)

console.run()

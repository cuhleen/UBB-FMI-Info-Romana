import datetime

from Domains.carte import Carte
from Domains.client import Client
from Domains.inchiriere import Inchiriere
from Domains.validation import validatorCarte
from Domains.validation import validatorClient
from Repository.repository import RepositoryCarti
from Repository.repository import RepositoryClienti
from Repository.repository import RepositoryInchiriere



class ControllerCarti:
    def __init__(self, repo: RepositoryCarti, validator: validatorCarte):
        self.__repo = repo
        self.__validator = validator

    def adaugaCarte(self, id, titlu, descriere, autor):
        """
        Adaugă o carte
        :param id: ID-ul cărții de adaugat
        :param titlu: titlul cărții pe care vrem să o adăugam
        :param descriere: descrierea cărții pe care vrem să o adăugam
        :param autor: autorul cărții pe care vrem să o adăugam
        :return: -; lista dată se modifică prin adăugarea cărții cu informațiile date
        """
        carte = Carte(id, titlu, descriere, autor)
        self.__validator.validate(carte)
        self.__repo.store(carte)

    def actualizeazaCarte(self, id: int, titluNou: str, descriereNoua: str, autorNou: str):
        """
        Actualizează cartea cu ID-ul dat cu informațiile date
        :param id: ID-ul cărții de actualizat
        :param titluNou: titlul nou al cărții
        :param descriereNoua: descrierea nouă a cărții
        :param autorNou: autorul nou al cărții
        :return: -; lista dată se modifică prin actualizarea cărții cu ID-ul dat cu informațiile date, dacă o carte cu acest ID există
        """

        carteNoua = Carte(id, titluNou, descriereNoua, autorNou)
        self.__validator.validate(carteNoua)
        self.__repo.update(carteNoua)

    def cautaCarte(self, id: int):
        """
        Caută carte cu ID-ul dat
        :param id: ID-ul după care se caută
        :return: cartea cu id-ul dat, dacă aceasta există, None altfel
        """
        return self.__repo.find(id)

    def stergeCarte(self, id:int):
        """
        Șterge cartea cu ID-ul dat
        :param id: ID-ul după care se caută
        :return: -
        """
        self.__repo.sterge(id)

    def filtreazaDupaAutor(self, autor:str) -> list:
        """
        Returnează lista de cărți care au autorul dat ca parametru
        :param autor: Autorul după care se filtrează
        :return: lista de cărți care au autorul dat
        """

        listaFiltrata = []
        for elem in self.__repo.getAll():
            if elem.getAutor() == autor:
                listaFiltrata.append(elem)
        return listaFiltrata

    def addDefault(self):
        self.adaugaCarte(101, "No Longer Human", "It tells the story of a troubled man incapable of revealing his true self to others, and who, instead, maintains a façade of hollow jocularity, later turning to a life of alcoholism and drug abuse before his final disappearance.", "Osamu Dazai")
        self.adaugaCarte(102, "The Trial", "It tells the story of Josef K., a man arrested and prosecuted by a remote, inaccessible authority, with the nature of his crime revealed neither to him nor to the reader.", "Franz Kafka")
        self.adaugaCarte(103, "Down And Out In Paris And London", "A semi-autobiographical account of George Orwell's time spent in poverty in Paris and London, exploring the harsh realities faced by the impoverished and the marginalized.", "George Orwell")
        self.adaugaCarte(104, "The Illiad", "An epic poem attributed to Homer, detailing the events of the Trojan War and the fates of heroes such as Achilles, Hector, and others.", "Homer")
        self.adaugaCarte(105, "Dune", "A science fiction epic set in a desert world, focusing on the struggles for control of valuable spice, and young Paul Atreides' journey to fulfill his destiny.", "Frank Herbert")
        self.adaugaCarte(106, "And Then There Were None", "A mystery novel where ten strangers are invited to a secluded island, only to be killed off one by one as they seek to uncover the murderer among them.", "Agatha Christie")
        self.adaugaCarte(107, "The Flowers of Evil", "A collection of poems that explores beauty, decadence, eroticism, and the darker sides of human nature, challenging the moral norms of the time.", "Charles Baudelaire")
        self.adaugaCarte(108, "The Stranger", "A novel about an emotionally detached man, Meursault, whose life spirals after he commits an impulsive murder, highlighting themes of existentialism and the absurd.", "Albert Camus")
        self.adaugaCarte(109, "Pet Sematary", "A horror novel about a family that discovers a burial ground with the power to resurrect the dead, leading to terrifying and tragic consequences.", "Stephen King")
        self.adaugaCarte(110, "No Country For Old Men", "The story occurs in the vicinity of the Mexico–United States border in 1980 and concerns an illegal drug deal gone awry in the Texas desert back country.", "Cormac McCarthy")

    def getAll(self) -> list:
        """
        Returneaza colecția de cărți
        :return:
        """
        return self.__repo.getAll()
    


class ControllerClienti:
    def __init__(self, repo: RepositoryClienti, validator: validatorClient):
        self.__repo = repo
        self.__validator = validator

    def adaugaClient(self, id, nume, cnp):
        """
        Adaugă un client
        :param id: ID-ul clientului de adaugat
        :param nume: numele clientului pe care vrem să îl adăugam
        :param cnp: cnp-ul clientului pe care vrem să îl adăugam
        :return: -; lista dată se modifică prin adăugarea clientului cu informațiile date
        """
        client = Client(id, nume, cnp)
        self.__validator.validate(client)
        self.__repo.store(client)

    def actualizeazaClient(self, id: int, numeNou: str, cnpNou: int):
        """
        Actualizeaza clientul cu ID-ul dat cu informatiile date
        :param id: ID-ul cărții de actualizat
        :param numeNou: numele nou al clientului
        :param cnpNou: CNP-ul nouă al clientului
        :return: -; lista dată se modifică prin actualizarea cărții cu ID-ul dat cu informațiile date, dacă o carte cu acest ID există
        """

        clientNou = Client(id, numeNou, cnpNou)
        self.__validator.validate(clientNou)
        self.__repo.update(clientNou)

    def cautaClient(self, id: int):
        """
        Caută clientul cu ID-ul dat
        :param id: ID-ul după care se caută
        :return: clientul cu id-ul dat, dacă acesta există, None altfel
        """
        return self.__repo.find(id)

    def stergeClient(self, id):
        """
        Șterge clientul cu ID-ul dat
        :param id: ID-ul după care se caută
        :return: -
        """
        self.__repo.sterge(id)

    def filtreazaDupaDataNasterii(self, dataInceput:str, dataSfarsit:str) -> list:
        """
        Returnează lista de clienți care au fost născuți dat ca parametru
        :param dataInceput: data de inceput dupa care se filtreaza clienti
        :param dataSfarsit: data de sfarsit dupa care se filtreaza clienti
        :return: listă de clienți născuți între cele duă date
        """
        an, luna, zi = map(int, dataInceput.split('/'))
        dataInceput = datetime.date(an, luna, zi)
        an, luna, zi = map(int, dataSfarsit.split('/'))
        dataSfarsit = datetime.date(an, luna, zi)

        listaFiltrata = []
        for elem in self.__repo.getAll():
            cnpStr = str(elem.getCnp())
            if int(cnpStr[1]) <= 2 and int(cnpStr[2]) <= 4:
                anNastereClient = 2000 + int(cnpStr[1]) * 10 + int(cnpStr[2])
            else:
                anNastereClient = 1900 + int(cnpStr[1]) * 10 + int(cnpStr[2])
            lunaNastereClient = int(cnpStr[3]) * 10 + int(cnpStr[4])
            ziNastereClient = int(cnpStr[5]) * 10 + int(cnpStr[6])

            dataNastereClient = datetime.date(anNastereClient, lunaNastereClient, ziNastereClient)

            if dataInceput <= dataNastereClient <= dataSfarsit:
                listaFiltrata.append(elem)

        return listaFiltrata


    def addDefault(self):
        self.adaugaClient(101, "Gabriel Tomescu", 1960111223456)
        self.adaugaClient(102, "Lucia Slăboiu", 2730801245678)
        self.adaugaClient(103, "Cosmin Mihăilescu", 5040116345678)
        self.adaugaClient(104, "Carla Lămboiu", 6101203345678)
        self.adaugaClient(105, "Otilia Șonda", 2750911245678)
        self.adaugaClient(106, "Irina Olteanu", 6200307345678)
        self.adaugaClient(107, "Viorela Pop", 2700602245678)
        self.adaugaClient(108, "Horia Almășan", 1670508245678)
        self.adaugaClient(109, "Radu Butu", 1890709245678)
        self.adaugaClient(110, "Parhon Radu", 5010405345678)

    def getAll(self) -> list:
        """
        Returneaza colecția de clienți
        :return:
        """
        return self.__repo.getAll()



class ControllerInchirieri:
    def __init__(self, repoClienti: RepositoryClienti, repoCarti: RepositoryCarti, repoInchirieri: RepositoryInchiriere):
        self.__repoClienti = repoClienti
        self.__repoCarti = repoCarti
        self.__repoInchirieri = repoInchirieri

    def cautaInchiriere(self, client, carte):
        """
        Caută îchirierea după clientul și cartea dată
        :param client: clientul asociat închirierii
        :param carte: cartea asociată închirierii
        :return: clientul cu id-ul dat, dacă acesta există, None altfel
        """
        inchiriere = Inchiriere(client, carte)
        return self.__repoInchirieri.find(inchiriere)

    def adaugaInchiriere(self, client:Client, carte:Carte):
        """
        Adaugă o închiriere
        :param client: clientul asociat închirierii
        :param carte: cartea asociată închirierii
        :return: -; lista dată se modifică prin adăugarea clientului cu informațiile date
        """
        Client = self.__repoClienti.find(client.getId())
        Carte = self.__repoCarti.find(carte.getId())
        if Client is not None and Carte is not None:
            inchiriere = Inchiriere(Client, Carte)
            self.__repoInchirieri.store(inchiriere)
        else:
            print("Nu există cartea sau clientul dat")
            return

    def stergeInchiriere(self, client, carte):
        """
        Șterge închirierea cu clientul și cartea dată
        :param client: clientul după care se caută
        :param carte: cartea după care se caută
        :return: -
        """
        self.__repoInchirieri.sterge(client, carte)

    def getAll(self) -> list:
        """
        Returneaza colecția de închirieri
        :return:
        """
        return self.__repoInchirieri.getAll()

    def getSize(self):
        """
        Returnează mărimea colecției de închirieri
        :return:
        """
        return self.__repoInchirieri.getSize()

    def verifInchirieri(self):
        """
        Verifică dacă clienții și cărțile asociate închirierilor existente încă există
        Se apelează atunci când se șterge sau schimbă o carte sau un client
        :return:
        """
        for inchiriere in self.__repoInchirieri.getAll():
            client = inchiriere.getClient()
            carte = inchiriere.getCarte()
            idClient = client.getId()
            idCarte = carte.getId()
            if self.__repoClienti.find(idClient) == None or self.__repoCarti.find(idCarte) == None:
                self.__repoInchirieri.sterge(client, carte)
            else:
                inchiriere.setClient(self.__repoClienti.find(idClient))
                inchiriere.setCarte(self.__repoCarti.find(idCarte))


    def celeMaiInchiriate(self):
        """
        Returnează lista celor mai închiriate cărți, ordonate descrescător după numărul de închirieri.
        :return: o listă de tupluri (carte, număr închirieri)
        """
        cartiInchirieri = {}
        for inchiriere in self.__repoInchirieri.getAll():
            carte = inchiriere.getCarte()
            if carte.getId() not in cartiInchirieri:
                cartiInchirieri[carte.getId()] = 0
            cartiInchirieri[carte.getId()] += 1

        # Sortare descrescătoare după numărul de închirieri
        cartiOrdonate = sorted(
            cartiInchirieri.items(),
            key=lambda x: x[1],
            reverse=True
        )

        return [(self.__repoCarti.find(carteId).getTitlu(), numar) for carteId, numar in cartiOrdonate]

    def clientiCuCartiInchiriate(self):
        """
        Returnează lista de clienți cu cărți închiriate, ordonată după nume și numărul de închirieri.
        :return: o listă de tupluri (client, număr închirieri)
        """
        clientiInchirieri = {}
        for inchiriere in self.__repoInchirieri.getAll():
            client = inchiriere.getClient()
            if client.getId() not in clientiInchirieri:
                clientiInchirieri[client.getId()] = 0
            clientiInchirieri[client.getId()] += 1

        # Sortare după nume și număr de închirieri
        clientiOrdonati = sorted(
            clientiInchirieri.items(),
            key=lambda x: (self.__repoClienti.find(x[0]).getNume(), -x[1])
        )

        return [(self.__repoClienti.find(clientId).getNume(), numar) for clientId, numar in clientiOrdonati]

    def top20LaSutaClienti(self):
        """
        Returnează primii 20% cei mai activi clienți, ordonați după numărul de închirieri.
        :return: o listă de tupluri (client, număr închirieri)
        """
        clientiInchirieri = {}
        for inchiriere in self.__repoInchirieri.getAll():
            client = inchiriere.getClient()
            if client.getId() not in clientiInchirieri:
                clientiInchirieri[client.getId()] = 0
            clientiInchirieri[client.getId()] += 1

        # Sortare descrescătoare după numărul de închirieri
        clientiOrdonati = sorted(
            clientiInchirieri.items(),
            key=lambda x: -x[1]
        )

        top_20 = int(0.2 * len(clientiOrdonati))
        if top_20 < 1:
            top_20 = 1

        return [(self.__repoClienti.find(clientId).getNume(), numar) for clientId, numar in clientiOrdonati[:top_20]]

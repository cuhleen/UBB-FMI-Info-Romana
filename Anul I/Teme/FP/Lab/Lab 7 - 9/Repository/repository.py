from Domains.carte import Carte
from Domains.client import Client
from Domains.inchiriere import Inchiriere


class RepositoryCarti:
    def __init__(self):
        self.__elements = []
    
    def find(self, id:int)->Carte:
        """
        Caută cartea cu ID-ul dat
        :param id: ID-ul de căutat
        :return: obiect de tip Carte dacă există carte cu ID-ul dat, None altfel
        """
        for carte in self.__elements:
            if carte.getId() == id:
                return carte
        return None
    
    def store(self, carte:Carte):
        """
        Adaugă o carte la colecția de cărți
        :param carte: cartea de adăugat
        :return: Colecția de cărți se modifică prin adăugarea cărții date 
        :raises: ValueError dacă se încearcă adăugarea unei cărți cu ID care există deja
        """
        if self.find(carte.getId()) is not None:
            print("Există deja carte cu acest ID")
            return
        self.__elements.append(carte)

    def __cautaCarte(self, id:int):
        """
        Găsește poziția în listă a cărții cu ID-ul dat
        :param id: ID-ul de căutat în listă
        :return: poziția în listă a cărții cu id dat, poziție returnată între 0 și len(self.__elements) dacă cartea există, -1 dacă nu există carte cu ID-ul dat
        """
        pozitie = -1
        for index, carte in enumerate(self.__elements):
            if carte.getId() == id:
                pozitie = index
                break
        return pozitie

    def update(self, carteActualizata:Carte):
        """
        Actualizează cartea din listă cu ID egal cu ID-ul cărții date ca parametru
        :param carteActualizata: cartea actualizată
        :return:
        """
        pozitie = self.__cautaCarte(carteActualizata.getId())
        if pozitie == -1:
            print("Nu există carte cu ID-ul dat")
            return
        self.__elements[pozitie] = carteActualizata

    def sterge(self, id:int):
        """
        Șterge cartea din listă cu ID egal cu ID-ul dat ca parametru
        :param id: ID-ul cărții de șters
        :return:
        """
        if self.__cautaCarte(id) == -1:
            print("Nu există carte cu ID-ul dat")
            return
        self.__elements.pop(self.__cautaCarte(id))

    def getAll(self) -> list:
        """
        Returnează colecția de cărți
        :return: colecția de cărți
        """
        return self.__elements

    def getSize(self)->int:
        """
        Returnează lungimea colecției de cărți
        :return: lungimea colecției de cărți
        """
        return len(self.__elements)



class RepositoryClienti:
    def __init__(self):
        self.__elements = []

    def find(self, id:int)->Client:
        """
        Caută clientul cu ID-ul dat
        :param id: ID-ul de căutat
        :return: obiect de tip Client dacă există client cu ID-ul dat, None altfel
        """
        for client in self.__elements:
            if client.getId() == id:
                return client
        return None

    def store(self, client:Client):
        """
        Adaugă un client la colecția de clienți
        :param client: client de adăugat
        :return: Colecția de clienți se modifică prin adăugarea clientului dat
        :raises: ValueError dacă se încearcă adăugarea unui client cu ID care există deja
        """
        
        if self.find(client.getId()) is not None:
            print("Există deja client cu acest ID")
            return
        
        self.__elements.append(client)

    def __cautaClient(self, id:int):
        """
        Găsește poziția în listă a clientului cu ID-ul dat
        :param id: ID-ul de căutat în listă
        :return: poziția în listă a clientului cu id dat, poziție returnată între 0 și len(self.__elements) dacă clientul există, -1 dacă nu există client cu ID-ul dat
        """
        pozitie = -1
        for index, client in enumerate(self.__elements):
            if client.getId() == id:
                pozitie = index
                break
        return pozitie

    def update(self, clientActualizat:Client):
        """
        Actualizează clientul din listă cu ID egal cu ID-ul clientului dat ca parametru
        :param clientActualizat: clientlu actualizat
        :return:
        """
        pozitie = self.__cautaClient(clientActualizat.getId())
        if pozitie == -1:
            print("Nu există client cu ID-ul dat")
            return
        self.__elements[pozitie] = clientActualizat

    def sterge(self, id:int):
        """
        Șterge clientul din listă cu ID egal cu ID-ul dat ca parametru
        :param id: ID-ul clientului de șters
        :return:
        """
        if self.__cautaClient(id) == -1:
            print("Nu există client cu ID-ul dat")
            return
        self.__elements.pop(self.__cautaClient(id))

    def getAll(self) -> list:
        """
        Returnează colecția de clienți
        :return: colecția de clienți
        """
        return self.__elements

    def getSize(self)->int:
        """
        Returnează lungimea colecției de clienți
        :return: lungimea colecției de clienți
        """
        return len(self.__elements)



class RepositoryInchiriere:
    def __init__(self):
        self.__elements = []

    def find(self, inchiriere):
        """
        Caută clientul cu ID-ul dat
        :param inchiriere: închirierea de găsit
        :return: obiect de tip Inchiriere dacă există închiriere a cărții date de către clientul dat, None altfel
        """
        for inchiriereExistenta in self.__elements:
            if inchiriere.getClient() == inchiriereExistenta.getClient() and inchiriere.getCarte() == inchiriereExistenta.getCarte():
                return inchiriere

        return None

    def store(self, inchiriere:Inchiriere):
        """
        Adaugă o închiriere la colecția de închirieri
        :param inchiriere: închirierea de stocat
        :return: Colecția de închirieri se modifică prin adăugarea închirierii date
        :raises: ValueError dacă se încearcă adăugarea unei închirieri care există deja
        """
        if self.find(inchiriere) is not None:
            print("Există deja închirierea aceasta")
            return
        self.__elements.append(inchiriere)

    def getAll(self):
        """
        Returnează toate închirierile
        :return: colecția de închirieri
        """
        return self.__elements

    def __cautaInchiriere(self, client:Client, carte:Carte):
        """
        Găsește poziția în listă a închirierii cu clientul și cartea dată
        :param client: Clientul asociat închirierii de căutat în listă
        :param carte: Cartea asociată închirierii de căutat în listă
        :return: poziția în listă a clientului cu id dat, poziție returnată între 0 și len(self.__elements) dacă clientul există, -1 dacă nu există client cu ID-ul dat
        """
        pozitie = -1
        for index, inchiriere in enumerate(self.__elements):
            if inchiriere.getClient() == client and inchiriere.getCarte() == carte:
                pozitie = index
                break
        return pozitie

    def sterge(self, client:Client, carte:Carte):
        """
        Șterge închirierea din listă cu clientul și cartea dată
        :param client: clientul închirierii de șters
        :param carte: cartea închirierii de șters
        :return:
        """
        if self.__cautaInchiriere(client, carte) == -1:
            print("Nu există închiriere a cărții date de către clientul dat")
            return
        self.__elements.pop(self.__cautaInchiriere(client, carte))

    def getSize(self)->int:
        """
        Returnează lungimea colecției de închirieri
        :return: lungimea colecției de închirieri
        """
        return len(self.__elements)

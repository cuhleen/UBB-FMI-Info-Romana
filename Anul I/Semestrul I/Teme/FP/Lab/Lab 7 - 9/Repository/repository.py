import csv

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



class RepositoryCartiFile(RepositoryCarti):
    def __init__(self, fileName:str):
        super().__init__()
        self.__fileName = fileName
        self.__readFromFile()

    def __readFromFile(self):
        """
        Citește datele din fișier și le adaugă la colecția existentă
        :return:
        """
        with open(self.__fileName, mode="r", encoding="utf-8") as f:
            reader = csv.reader(f)
            for line in reader:
                if line:
                    id, titlu, descriere, autor = line
                    id = int(id)
                    carte = Carte(id, titlu, descriere, autor)
                    # Verifică dacă cartea există deja în colecție
                    if self.find(id) is None:
                        super().store(carte)

    def store(self, carte: Carte):
        """
        Adaugă o carte în memorie și salvează în fișier
        :param carte: cartea de adăugat
        """
        super().store(carte)
        self.__saveToFile()

    def __saveToFile(self):
        """
        Salvează datele despre cărți în fișier
        """
        with open(self.__fileName, mode="w", encoding="utf-8") as f:
            for carte in self.getAll():
                carte_str = str(carte.getId()) + "," + carte.getTitlu() + "," + carte.getAutor() + "\n"
                f.write(carte_str)

    def sterge(self, id: int):
        """
        Șterge cartea cu ID-ul dat și actualizează fișierul.
        :param id: ID-ul cărții de șters.
        """
        pozitie = self._RepositoryCarti__cautaCarte(id)
        if pozitie == -1:
            print("Nu există carte cu ID-ul dat")
            return
        self.getAll().pop(pozitie)
        self.__saveToFile()

    def update(self, carteActualizata: Carte):
        """
        Actualizează cartea cu ID-ul dat și sincronizează modificările în fișier.
        :param carteActualizata: cartea actualizată.
        """
        pozitie = self._RepositoryCarti__cautaClient(carteActualizata.getId())
        if pozitie == -1:
            print("Nu există carte cu ID-ul dat")
            return
        self.getAll()[pozitie] = carteActualizata
        self.__saveToFile()



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



class RepositoryClientiFile(RepositoryClienti):
    def __init__(self, fileName):
        super().__init__()
        self.__fileName = fileName
        self.__readFromFile()

    def __readFromFile(self):
        """
        Citește datele din fișier și le adaugă la colecția existentă
        :return:
        """
        with open(self.__fileName, mode="r", encoding="utf-8") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    id, nume, cnp = line.split(",")
                    id = int(id)
                    cnp = int(cnp)
                    client = Client(id, nume, cnp)
                    # Verifică dacă clientul există deja în colecție
                    if self.find(id) is None:
                        super().store(client)

    def store(self, client: Client):
        """
        Adaugă un client în memorie și salvează în fișier
        :param client: clientul de adăugat
        """
        super().store(client)
        self.__saveToFile()

    def __saveToFile(self):
        """
        Salvează datele despre clienți în fișier
        """
        with open(self.__fileName, mode="w", encoding="utf-8") as f:
            for client in self.getAll():
                client_str = str(client.getId()) + "," + client.getNume() + "," + str(client.getCnp()) + "\n"
                f.write(client_str)

    def sterge(self, id: int):
        """
        Șterge clientul cu ID-ul dat și actualizează fișierul.
        :param id: ID-ul clientului de șters.
        """
        pozitie = self._RepositoryClienti__cautaClient(id)
        if pozitie == -1:
            print("Nu există client cu ID-ul dat")
            return
        self.getAll().pop(pozitie)
        self.__saveToFile()

    def update(self, clientActualizat: Client):
        """
        Actualizează clientul cu ID-ul dat și sincronizează modificările în fișier.
        :param clientActualizat: clientul actualizat.
        """
        pozitie = self._RepositoryClienti__cautaClient(clientActualizat.getId())
        if pozitie == -1:
            print("Nu există client cu ID-ul dat")
            return
        self.getAll()[pozitie] = clientActualizat
        self.__saveToFile()



class RepositoryInchiriere:
    def __init__(self):
        self.__elements = []

    def find(self, inchiriere):
        """
        Caută închirierea cu același client și aceeași carte
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



class RepositoryInchiriereFile(RepositoryInchiriere, ):
    def __init__(self, fileName, repoClientiFile, repoCartiFile):
        super().__init__()
        self.__fileName = fileName
        self.__repoClienti = repoClientiFile
        self.__repoCarti = repoCartiFile
        self.__readFromFile()

    def __readFromFile(self):
        """
        Citește datele din fișier
        :return:
        """
        with open(self.__fileName, mode="r", encoding="utf-8") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    idClient, idCarte = line.split(",")
                    idClient = int(idClient)
                    idCarte = int(idCarte)
                    client = self.__repoClienti.find(idClient)
                    carte = self.__repoCarti.find(idCarte)
                    inc = Inchiriere(client, carte)
                    super().store(inc)

    def store(self, inchiriere:Inchiriere):
        super().store(inchiriere)
        self.__saveToFile()

    def __saveToFile(self):
        """
        Salvează datele despre un client în fișier
        :return:
        """
        with open(self.__fileName, mode="w", encoding="utf-8") as f:
            for inchiriere in self.getAll():
                client = inchiriere.getClient()
                carte = inchiriere.getCarte()
                inchiriere_str = str(client.getId()) + "," + str(carte.getId()) + "\n"
                f.write(inchiriere_str)

    def sterge(self, client: Client, carte: Carte):
        """
        Șterge închirierea din memorie și fișier cu clientul și cartea dată
        :param client: clientul închirierii de șters
        :param carte: cartea închirierii de șters
        """
        if self._RepositoryInchiriere__cautaInchiriere(client, carte) == -1:
            print("Nu există închiriere a cărții date de către clientul dat")
            return
        self._RepositoryInchiriere__elements.pop(self._RepositoryInchiriere__cautaInchiriere(client, carte))
        self.__saveToFile()

    def update(self, inchiriereActualizata: Inchiriere):
        """
        Actualizează închirierea din memorie și fișier
        :param inchiriereActualizata: închirierea actualizată
        """
        pozitie = self.__cautaInchiriere(inchiriereActualizata.getClient(), inchiriereActualizata.getCarte())
        if pozitie == -1:
            print("Nu există închiriere cu clientul și cartea date")
            return
        self.__elements[pozitie] = inchiriereActualizata
        self.__saveToFile()
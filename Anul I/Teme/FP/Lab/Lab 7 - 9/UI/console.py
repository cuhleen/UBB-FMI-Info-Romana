class Console:
    def __init__(self, cartiService, clientiService, inchirieriService):
        self.__servCarti = cartiService
        self.__servClienti = clientiService
        self.__servInchirieri = inchirieriService

    @staticmethod
    def afiseazaMeniu():
        print("\n11. Adaugă carte la listă")
        print("111. Actualizează o carte din listă")
        print("12. Adaugă client la listă")
        print("121. Actualizează un client din listă")
        print("21. Caută carte după autor")
        print("22. Caută client după data nașterii")
        print("31. Șterge carte după ID")
        print("32. Șterge client după ID")
        print("41. Filtrează cărțile după autor")
        print("42. Filtrează clienții născuți între două date")
        print("51. Adaugă închiriere")
        print("52. Șterge închiriere")
        print("61. Afișează cele mai închiriate cărți")
        print("62. Afișează toți clienții cu cărți închiriate")
        print("63. Afișează top 20% cei mai activi clienți")
        print("D1. Adaugă cărți default")
        print("D2. Adaugă clienți default")
        print("P1. Afișează lista de cărți")
        print("P2. Afișează lista de clienți")
        print("P2. Afișează lista de închirieri")
        print("E. Ieșire din aplicație")

    def citesteInfoCarte(self) -> tuple:
        id = int(input("Introduceți ID-ul cărții:"))
        titlu = input("Introduceți titlul cărții:")
        descriere = input("Introduceți descrierea cărții:")
        autor = input("Introduceți autorul cărții:")

        return id, titlu, descriere, autor

    def afiseazaCarti(self, listaCarti):
        for carte in listaCarti:
            print(carte)

    def filtreazaDupaAutorUI(self):
        autorDorit = input("Autorul de căutat: ")

        listaFiltrata = self.__servCarti.filtreazaDupaAutor(autorDorit)
        if len(listaFiltrata) > 0:
            print("Melodiile care au autorul " + autorDorit + " sunt:")
            self.afiseazaCarti(listaFiltrata)
        else:
            print("Nu există cărți cu autorul " + autorDorit + ".")

    def adaugaCarteUI(self):
        id, titlu, descriere, autor = self.citesteInfoCarte()

        try:
            self.__servCarti.adaugaCarte(id, titlu, descriere, autor)
            print("Adăugare realizată cu succes.")

        except ValueError as ve:
            print(ve)

    def modificaCarteUI(self):
        id, titluNou, descriereNoua, autorNou = self.citesteInfoCarte()

        try:
            self.__servCarti.actualizeazaCarte(id, titluNou, descriereNoua, autorNou)
            print("Actualizare realizată cu succes.")
        except ValueError as ve:
            print(ve)

        self.__servInchirieri.verifInchirieri()

    def stergeCarteUI(self):
        self.afiseazaCarti(self.__servCarti.getAll())
        id = int(input("Introduce ID-ul cărții de șters:"))
        self.__servCarti.stergeCarte(id)
        self.__servInchirieri.verifInchirieri()



    def citesteInfoClient(self) -> tuple:
        id = int(input("Introduceți ID-ul clientului:"))
        nume = input("Introduceți numele clientului:")
        cnp = int(input("Introduceți CNP-ul clientului:"))

        return id, nume, cnp

    def afiseazaClienti(self, listaCarti):
        for carte in listaCarti:
            print(carte)

    def filtreazaDupaDataNasteriiUI(self):
        dataInceput = input("Data de început (format YYYY/MM/DD): ")
        dataSfarsit = input("Data de sfârșit (format YYYY/MM/DD): ")

        listaFiltrata = self.__servClienti.filtreazaDupaDataNasterii(dataInceput, dataSfarsit)
        if len(listaFiltrata) > 0:
            print("Clienții care sunt născuți între " + dataInceput + " și " + dataSfarsit + " sunt:")
            self.afiseazaCarti(listaFiltrata)
        else:
            print("Nu există clienți născuți între " + dataInceput + " și " + dataSfarsit + ".")

    def adaugaClientUI(self):
        id, nume, cnp = self.citesteInfoClient()

        try:
            self.__servClienti.adaugaClient(id, nume, cnp)
            print("Adăugare realizată cu succes.")

        except ValueError as ve:
            print(ve)

    def modificaClientUI(self):
        id, numeNou, cnpNou = self.citesteInfoClient()

        try:
            self.__servClienti.actualizeazaClient(id, numeNou, cnpNou)
            print("Actualizare realizată cu succes.")
        except ValueError as ve:
            print(ve)

        self.__servInchirieri.verifInchirieri()

    def stergeClientUI(self):
        self.afiseazaClienti(self.__servClienti.getAll())
        id = int(input("Introduce ID-ul clientului de șters:"))
        self.__servClienti.stergeClient(id)
        self.__servInchirieri.verifInchirieri()


    def afiseazaInchirieri(self, listaInchirieri):
        for inchiriere in listaInchirieri:
            print(inchiriere)

    def adaugaInchiriereUI(self):
        self.afiseazaClienti(self.__servClienti.getAll())
        idClient = int(input("Introduce ID-ul clientului care închiriază:"))
        client = self.__servClienti.cautaClient(idClient)
        if client is None:
            print("Nu există client cu ID-ul dat")
            return

        self.afiseazaCarti(self.__servCarti.getAll())
        idCarte = int(input("Introduce ID-ul cărții închiriate:"))
        carte = self.__servCarti.cautaCarte(idCarte)
        if carte is None:
            print("Nu există carte cu ID-ul dat")
            return

        self.__servInchirieri.adaugaInchiriere(client, carte)

    def stergeInchiriereUI(self):
        self.afiseazaInchirieri(self.__servInchirieri.getAll())

        idClient = int(input("Introduce ID-ul clientului care închiriază:"))
        client = self.__servClienti.cautaClient(idClient)
        if client is None:
            print("Nu există client cu ID-ul dat")
            return
        idCarte = int(input("Introduce ID-ul cărții închiriate:"))
        carte = self.__servCarti.cautaCarte(idCarte)
        if carte is None:
            print("Nu există carte cu ID-ul dat")
            return

        self.__servInchirieri.stergeInchiriere(client, carte)

    def inchirieriDefault1(self):
        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(101)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(102)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(104)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(101)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(102)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(109)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(108)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(107)
        self.__servInchirieri.adaugaInchiriere(client, carte)
        client = self.__servClienti.cautaClient(108)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)

    def inchirieriDefault2(self):
        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(101)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(102)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(103)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(104)
        carte = self.__servCarti.cautaCarte(104)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(105)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(106)
        carte = self.__servCarti.cautaCarte(106)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(107)
        carte = self.__servCarti.cautaCarte(107)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(108)
        carte = self.__servCarti.cautaCarte(108)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(109)
        carte = self.__servCarti.cautaCarte(109)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(110)
        carte = self.__servCarti.cautaCarte(110)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(102)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(103)
        carte = self.__servCarti.cautaCarte(104)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(106)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(107)
        carte = self.__servCarti.cautaCarte(108)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(109)
        carte = self.__servCarti.cautaCarte(110)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(101)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(104)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(106)
        carte = self.__servCarti.cautaCarte(105)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(108)
        carte = self.__servCarti.cautaCarte(107)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(110)
        carte = self.__servCarti.cautaCarte(109)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(107)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(109)
        carte = self.__servCarti.cautaCarte(101)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(104)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(106)
        carte = self.__servCarti.cautaCarte(108)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(110)
        carte = self.__servCarti.cautaCarte(102)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(103)
        carte = self.__servCarti.cautaCarte(105)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(107)
        carte = self.__servCarti.cautaCarte(109)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(110)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(104)
        carte = self.__servCarti.cautaCarte(106)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(108)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(107)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(109)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(109)
        carte = self.__servCarti.cautaCarte(104)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(103)
        carte = self.__servCarti.cautaCarte(108)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(107)
        carte = self.__servCarti.cautaCarte(102)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(110)
        carte = self.__servCarti.cautaCarte(105)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(101)
        carte = self.__servCarti.cautaCarte(107)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(106)
        carte = self.__servCarti.cautaCarte(110)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(102)
        carte = self.__servCarti.cautaCarte(106)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(104)
        carte = self.__servCarti.cautaCarte(109)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(108)
        carte = self.__servCarti.cautaCarte(103)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(105)
        carte = self.__servCarti.cautaCarte(101)
        self.__servInchirieri.adaugaInchiriere(client, carte)

        client = self.__servClienti.cautaClient(110)
        carte = self.__servCarti.cautaCarte(104)
        self.__servInchirieri.adaugaInchiriere(client, carte)


    def celeMaiInchiriateUI(self):
        print("Cele mai închiriate cărți:\n")
        for nume, numar in self.__servInchirieri.celeMaiInchiriate():
            if numar == 1:
                print('"' + nume + '" - ' + "o închiriere")
            else:
                print('"' + nume + '" - ' + str(numar) + " închirieri")

    def clientiCuCartiInchiriateUI(self):
        print("Clienții cu cărți închiriate:\n")
        for nume, numar in self.__servInchirieri.clientiCuCartiInchiriate():
            if numar == 1:
                print(nume + ": o carte închiriată")
            else:
                print(nume + ": " + str(numar) + " cărți închiriate")

    def top20LaSutaClientiUI(self):
        nrTotal = self.__servInchirieri.getSize()
        i = 0
        print("Top 20% cei mai activi clienți:\n")
        for nume, numar in self.__servInchirieri.top20LaSutaClienti():
            i += 1
            if numar == 1:
                print(str(i) + ". " + nume + ": o carte închiriată")
            else:
                print(str(i) + ". " + nume + ": " + str(numar) + " cărți închiriate")



    def run(self):
        isRunning = True
        while isRunning:
            self.afiseazaMeniu()
            optiune = input(">>>").upper().strip()
            match optiune:
                case '11':
                    self.adaugaCarteUI()
                case '111':
                    self.modificaCarteUI()
                case '12':
                    self.adaugaClientUI()
                case '121':
                    self.modificaClientUI()
                case '21':
                    self.filtreazaDupaAutorUI()
                case '22':
                    self.filtreazaDupaDataNasteriiUI()
                case '31':
                    self.stergeCarteUI()
                case '32':
                    self.stergeClientUI()
                case '41':
                    self.filtreazaDupaAutorUI()
                case '42':
                    self.filtreazaDupaDataNasteriiUI()
                case '51':
                    self.adaugaInchiriereUI()
                case '52':
                    self.stergeInchiriereUI()
                case '61':
                    self.celeMaiInchiriateUI()
                case '62':
                    self.clientiCuCartiInchiriateUI()
                case '63':
                    self.top20LaSutaClientiUI()
                case 'P1':
                    self.afiseazaCarti(self.__servCarti.getAll())
                case 'P2':
                    self.afiseazaClienti(self.__servClienti.getAll())
                case 'P3':
                    self.afiseazaInchirieri(self.__servInchirieri.getAll())
                case 'D1':
                    self.__servCarti.addDefault()
                    print("S-au adăugat cărțile default.")
                case 'D2':
                    self.__servClienti.addDefault()
                    print("S-au adăugat clienții default.")
                case 'D31':
                    self.inchirieriDefault1()
                    print("S-au adăugat închirierile default 1.")
                case 'D32':
                    self.inchirieriDefault2()
                    print("S-au adăugat închirierile default 2.")
                case 'E':
                    isRunning = False

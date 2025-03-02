from Domain.melodie import Melodie

class RepoMelodie():
    def __init__(self, numeFisier:str):
        self.__elements = []
        self.numeFisier = numeFisier
        self.citesteDinFisier()

    def adaugaMelodie(self, melodie:Melodie):
        """
        Adaugă o melodie la colecția de melodii, dar numai dacă nu mai există o altă melodie cu același titlu și artist
        :param melodie: melodia de adăugat
        :return:
        """
        for melodieExistenta in self.__elements:
            if melodie.getTitlu() == melodieExistenta.getTitlu() and melodie.getArtist() == melodieExistenta.getArtist():
                return
        self.__elements.append(melodie)
        self.scrieInFisier()

    def stergeUltimaMelodie(self):
        """
        Utilizat pentru testare, teardown
        :return:
        """
        self.__elements.pop()
        self.scrieInFisier()

    def citesteDinFisier(self):
        """
        Citește toate melodiile din fișier, fișierul fiind colecția principală de melodii
        :return:
        """

        with open(self.numeFisier, "r", encoding="utf-8") as f:
            lines = f.readlines()
            for line in lines:
                line = line.strip()
                if line != "":
                    titlu, artist, gen, data = line.split(",")
                    melodie = Melodie(titlu, artist, gen, data)
                    self.adaugaMelodie(melodie)

    def scrieInFisier(self):
        """
        Utilizat la finalul tuturor funcțiilor ce modifică colecția în orice formă. Actualizează fișierul cu datele noi
        """
        with open(self.numeFisier, "w", encoding="utf-8") as f:
            for melodie in self.__elements:
                melodieStr = melodie.getTitlu() + "," + melodie.getArtist() + "," + melodie.getGen() + "," + melodie.getData() + "\n"
                f.write(melodieStr)


    def modificaMelodie(self, melodieEditata:Melodie):
        """
        Caută în listă melodia și o modifică cu datele noi
        :param melodieEditata: Melodia cu genul și data modificată
        :return:
        """
        for melodie in self.__elements:
            if melodieEditata.getTitlu() == melodie.getTitlu() and melodieEditata.getArtist() == melodie.getArtist():
                melodie.setGen(melodieEditata.getGen())
                melodie.setData(melodieEditata.getData())
                self.scrieInFisier()
                return
        print("Nu a fost găsită melodia.")

    def getAll(self):
        return self.__elements

    def getSize(self):
        return len(self.__elements)
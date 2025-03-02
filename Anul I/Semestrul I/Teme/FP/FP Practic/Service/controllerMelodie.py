import random

from Repo.repoMelodie import RepoMelodie
from Domain.melodie import Melodie
from Domain.validation import validation

class ControllerMelodie():
    def __init__(self, repoMelodie:RepoMelodie, validator:validation):
        self.__repoMelodie = repoMelodie
        self.__validator = validator

    def getAll(self):
        """
        Returnează întreaga colecție de melodii
        :return:
        """
        return self.__repoMelodie.getAll()

    def adaugaMelodie(self, titlu:str, artist:str, gen:str, data:str):
        """
        Validează melodia și o adaugă dacă este corectă
        :param titlu: Titlul melodiei
        :param artist: Artistul melodiei
        :param gen: Genul melodiei
        :param data: Data de lansare a melodiei
        :return:
        """
        melodie = Melodie(titlu, artist, gen, data)
        if self.__validator.validate(melodie) == -1:
            self.__repoMelodie.adaugaMelodie(melodie)
        else:
            print(self.__validator.validate(melodie))

    def stergeUltimaMelodie(self):
        """
        Utilizat pentru testare, teardown
        :return:
        """
        self.__repoMelodie.stergeUltimaMelodie()

    def modificaMelodie(self, titlu:str, artist:str, gen:str, data:str):
        """
        Modifică genul și data unei melodii existente
        :param titlu: Titlul melodiei, neschimbat
        :param artist: Artistul melodiei, neschimbat
        :param gen: Genul melodiei, de schimbat
        :param data: Data de lansare a melodiei, de schimbat
        :return:
        """
        melodieEditata = Melodie(titlu, artist, gen, data)
        self.__validator.validate(melodieEditata)
        self.__repoMelodie.modificaMelodie(melodieEditata)

    def adaugaRandom(self, nr:int, titluri:str, artisti:str):
        """

        :param nr: Numărul de melodii care trebuiesc generate
        :param titluri: Lista de titluri din care preluăm aleatoriu
        :param artisti: Lista de artiști din care preluăm aleatoriu
        :return:
        """
        arrGenuri = ["Rock", "Pop", "Jazz"]
        arrDate = ["01.01.2021", "02.02.2022", "03.03.2023", "04.04.2024", "10.01.2011", "10.02.2012", "10.03.2013", "10.04.2014"]
        arrTitluri = titluri.split(",")
        arrArtisti = artisti.split(",")

        for i in range(0, nr):
            self.adaugaMelodie(random.choice(arrTitluri), random.choice(arrArtisti), random.choice(arrGenuri), random.choice(arrDate))

    def ceaMaiNoua(self):
        """
        Returnează cea mai nouă melodie, după dată
        :return:
        """
        ceaMaiNouaZi = 1
        ceaMaiNouaLuna = 1
        celMaiNouAn = 1
        ceaMaiNouaMelodie = None

        for melodie in self.__repoMelodie.getAll():
            zi, luna, an = melodie.getData().split(".")
            zi = int(zi)
            luna = int(luna)
            an = int(an)
            if an > celMaiNouAn:
                ceaMaiNouaMelodie = melodie

                celMaiNouAn = an
                ceaMaiNouaLuna = luna
                ceaMaiNouaZi = zi
            elif an == celMaiNouAn:
                if luna > ceaMaiNouaLuna:
                    ceaMaiNouaMelodie = melodie

                    ceaMaiNouaLuna = luna
                    ceaMaiNouaZi = zi
                elif luna == ceaMaiNouaLuna:
                    if zi > ceaMaiNouaZi:
                        ceaMaiNouaMelodie = melodie

                        ceaMaiNouaZi = zi

        return ceaMaiNouaMelodie

    def ceaMaiVeche(self):
        """
        Returnează cea mai veche melodie, după dată
        :return:
        """

        ceaMaiVecheZi = 31
        ceaMaiVecheLuna = 12
        celMaiVechiAn = 2999
        ceaMaiVecheMelodie = None

        for melodie in self.__repoMelodie.getAll():
            zi, luna, an = melodie.getData().split(".")
            zi = int(zi)
            luna = int(luna)
            an = int(an)

            if an < celMaiVechiAn:
                ceaMaiVecheMelodie = melodie

                celMaiVechiAn = an
                ceaMaiVecheLuna = luna
                ceaMaiVecheZi = zi
            elif an == celMaiVechiAn:
                if luna < ceaMaiVecheLuna:
                    ceaMaiVecheMelodie = melodie
                    ceaMaiVecheLuna = luna
                    ceaMaiVecheZi = zi
                elif luna == ceaMaiVecheLuna:
                    if zi < ceaMaiVecheZi:
                        ceaMaiVecheMelodie = melodie
                        ceaMaiVecheZi = zi

        return ceaMaiVecheMelodie

    def numarMelodiiPerGen(self):
        """
        Returnează câte melodii sunt rock, pop, și jazz
        :return:
        """
        nrPop = nrJazz = nrRock = 0

        for melodie in self.__repoMelodie.getAll():
            if melodie.getGen() == "Pop":
                nrPop += 1
            elif melodie.getGen() == "Jazz":
                nrJazz += 1
            else:
                nrRock += 1

        return nrRock, nrPop, nrJazz
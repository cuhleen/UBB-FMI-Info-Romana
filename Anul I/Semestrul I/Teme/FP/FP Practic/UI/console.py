from Repo.repoMelodie import RepoMelodie
from Service.controllerMelodie import ControllerMelodie

class Console:
    def __init__(self, controllerMelodie):
        self.__controller = controllerMelodie

    def afiseazaMeniu(self):
        print("\n1. Modifică melodie după titlu și artist (se modifică genul și data)")
        print("2. Creează aleator melodii după o listă de titluri și artiști")
        print("31. Statistică: Cea mai nouă și cea mai veche melodie")
        print("32. Statistică: Numărul de melodii per gen")
        print("P. Afișează toate melodiile din colecție")
        print("E. Ieșire din aplicație")

    def afiseazaToateMelodiile(self):
        for melodie in self.__controller.getAll():
            print(melodie.getTitlu() + "," + melodie.getArtist() + "," + melodie.getGen() + "," + melodie.getData())

    def modificaMelodieUI(self):
        self.afiseazaToateMelodiile()
        print("Introduceți datele noi ale melodiei, în același format în care sunt afișate:")
        stringMelodie = input(">>>")
        titlu, artist, gen, data = stringMelodie.split(",")
        self.__controller.modificaMelodie(titlu, artist, gen, data)

    def creeazaRandomUI(self):
        print("Introduceți câte melodii aleatorii doriți generate")
        nr = int(input(">>>"))
        print("Introduceți un o listă de titluri sub forma 'titlu1,titlu2,titlu3'")
        strTitluri = input(">>>")
        print("Introduceți un o listă de artiști sub forma 'artist1,artist2,artist3'")
        strArtisti = input(">>>")

        self.__controller.adaugaRandom(nr, strTitluri, strArtisti)

    def ceaMaiNouaSiVecheUI(self):
        ceaMaiNouaMelodie = self.__controller.ceaMaiNoua()
        ceaMaiVecheMelodie = self.__controller.ceaMaiVeche()
        print("Cea mai nouă melodie: " + ceaMaiNouaMelodie.getTitlu() + ", " + ceaMaiNouaMelodie.getArtist() + ", " + ceaMaiNouaMelodie.getGen() + ", " + ceaMaiNouaMelodie.getData())
        print("Cea mai veche melodie: " + ceaMaiVecheMelodie.getTitlu() + ", " + ceaMaiVecheMelodie.getArtist() + ", " + ceaMaiVecheMelodie.getGen() + ", " + ceaMaiVecheMelodie.getData())

    def nrPerGenUI(self):
        nrRock, nrPop, nrJazz = self.__controller.numarMelodiiPerGen()
        print("Rock: " + str(nrRock) + "\nPop: " + str(nrPop) + "\nJazz: " + str(nrJazz))

    def run(self):
        isRunning = True
        while isRunning:
            self.afiseazaMeniu()
            optiune = input(">>>").upper().strip()
            match optiune:
                case '1':
                    self.modificaMelodieUI()
                case '2':
                    self.creeazaRandomUI()
                case '31':
                    self.ceaMaiNouaSiVecheUI()
                case '32':
                    self.nrPerGenUI()
                case 'E':
                    isRunning = False
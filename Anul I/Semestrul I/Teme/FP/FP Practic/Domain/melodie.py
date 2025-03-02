class Melodie():
    def __init__(self, titlu:str, artist:str, gen:str, data:str):
        self.__titlu = titlu
        self.__artist = artist
        self.__gen = gen
        self.__data = data

    def setTitlu(self, titlu:str):
        self.__titlu = titlu

    def getTitlu(self):
        return self.__titlu

    def setArtist(self, artist:str):
        self.__artist = artist

    def getArtist(self):
        return self.__artist

    def setGen(self, gen:str):
        self.__gen = gen

    def getGen(self):
        return self.__gen

    def setData(self, data:str):
        self.__data = data

    def getData(self):
        return self.__data

    def __equ__(self, other):
        if type(other) != type(self):
            return 0
        if self.getTitlu() != other.getTitlu() or self.getArtist() != other.getArtist() or self.getGen() != other.getGen() or self.getData() != other.getData():
            return 0
        return 1


class Camera:
    def __init__(self, nr:int, locuri:int, vedere:str, pret_noapte:int):
        self.__nr = nr
        self.__locuri = locuri
        self.__vedere = vedere
        self.__pret_noapte = pret_noapte

    def getNr(self):
        return self.__nr

    def getLocuri(self):
        return self.__locuri

    def getVedere(self):
        return self.__vedere

    def getPretNoapte(self):
        return self.__pret_noapte

    """

    def setNr(self, val:int):
        self.__nr = val

    def setLocuri(self, val:int):
        self.__locuri = val

    def setVedere(self, val:str):
        self.__vedere = val

    def setPretNoapte(self, val:int):
        self.__pret_noapte = val
    """

    """
    def __eq__(self, other):
        if type(self) != type(other):
            return False
        return self.getNr() == other.getNr()
    """

    def __str__(self):
        return str("Camera numărul " + str(self.getNr()) + ", cu " + str(self.getLocuri()) + " locuri, vedere spre " + self.getVedere() + ", preț pe noapte " + str(self.getPretNoapte()))
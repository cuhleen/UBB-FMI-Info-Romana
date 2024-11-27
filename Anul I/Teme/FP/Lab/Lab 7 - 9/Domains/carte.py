class Carte:
    def __init__(self, id:int, titlu:str, descriere:str, autor:str):
        self.__id = id
        self.__titlu = titlu
        self.__descriere = descriere
        self.__autor = autor
    
    def getId(self):
        return self.__id
    def getTitlu(self):
        return self.__titlu
    def getDescriere(self):
        return self.__descriere
    def getAutor(self):
        return self.__autor
    
    def setId(self, valoare):
        self.__id = valoare
    def setTitlu(self, valoare):
        self.__titlu = valoare
    def setDescriere(self, valoare):
        self.__descriere = valoare
    def setAutor(self, valoare):
        self.__autor = valoare

    def __eq__(self, other):
        if type(self) != type(other):
            return False
        return self.__id == other.__id
    
    def __str__(self):
        return "[" + str(self.__id) + "] Carte: Titlu = " + self.__titlu + "; Descriere = " + self.__descriere + "; Autor = " + self.__autor
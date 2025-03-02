class Client:
    def __init__(self, id:int, nume:str, cnp:int):
        self.__id = id
        self.__nume = nume
        self.__cnp = cnp

    def getId(self):
        return self.__id
    def getNume(self):
        return self.__nume
    def getCnp(self):
        return self.__cnp
    
    def setId(self, value):
        self.__id = value
    def setNume(self, value):
        self.__nume = value
    def setCnp(self, value):
        self.__cnp = value

    def __eq__(self, other):
        if type(self) != type(other):
            return False
        return self.__id == other.__id
    
    def __str__(self):
        return "[" + str(self.__id) + "] Client: Nume = " + self.__nume + "; CNP = " + str(self.__cnp)
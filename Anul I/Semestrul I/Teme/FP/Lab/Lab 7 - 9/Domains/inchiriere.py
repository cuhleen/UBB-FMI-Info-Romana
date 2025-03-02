class Inchiriere:
    def __init__(self, client, carte):
        self.__client = client
        self.__carte = carte

    def getClient(self):
        return self.__client

    def getCarte(self):
        return self.__carte

    def setClient(self, client):
        self.__client = client

    def setCarte(self, carte):
        self.__carte = carte

    def __eq__(self, other):
        if type(self) != type(other):
            return False
        return self.__client.getId() == other.client.getId() and self.__carte.getId() == other.carte.getId()

    def __str__(self):
        return "[Inchiriere]: " + str(self.__client.getNume()) + " [" + str(self.__client.getId()) + "]" + " | " + str(self.__carte.getTitlu()+ " [" + str(self.__carte.getId()) + "]")
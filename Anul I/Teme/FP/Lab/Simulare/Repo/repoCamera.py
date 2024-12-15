from Domain.camera import Camera

class RepoCamera(Camera):
    def __init__(self, fileName:str):
        self.__fileName = fileName
        self.__elements = []
        self.__readFromFile()

    def __readFromFile(self):
        """
        Citește informațiile camerelor din fișier, le pune în lista de elemente internă pentru a putea fi prelucrate
        :return:
        """
        with open(self.__fileName,'r', encoding="utf-8") as f:
            lines = f.readlines()
            for line in lines:
                if line != "":
                    line.strip()
                    nr, locuri, vedere, pret_noapte = line.split(",")
                    nr = int(nr)
                    locuri = int(locuri)
                    pret_noapte = int(pret_noapte)
                    camera = Camera(nr, locuri, vedere, pret_noapte)
                    if self.find(camera.getNr()) is None:
                        self.__elements.append(camera)
                        self.__saveToFile()

    def __saveToFile(self):
        """
        Scrie lista de elemente în fișierul Repo al camerelor
        :return:
        """
        with open(self.__fileName,'w', encoding="utf-8") as f:
            for camera in self.__elements:
                    cameraString = str(camera.getNr()) + "," + str(camera.getLocuri()) + "," + camera.getVedere() + "," + str(camera.getPretNoapte()) + '\n'
                    f.write(cameraString)

    def find(self, nr:int):
        """
        Găsește camera după ID, dacă există
        :param nr: Numărul unic al camerei de căutat
        :return: Dacă există o cameră cu numărul specificat, o returnează. Altfel, returnează None
        """
        for camera in self.__elements:
            if camera.getNr() == nr:
                return camera
        return None

    def getAll(self):
        """
        Returnează toată lista camerelor curente
        :return:
        """
        return self.__elements
from Service.controllerCamera import ServiceCamera


class Console:
    def __init__(self, servCamera:ServiceCamera):
        self.__servCamera = servCamera

    def afiseazaMenu(self):
        """
        Afișează meniul cu care interacționează utilizatorul
        :return:
        """
        print("\n1. Caută cameră după număr de persoane și vedere")
        print("2. Închiriază cameră")
        print("E. Ieșire din aplicație")

    def cautaCameraDupaNumarPersoaneSiVedere(self):
        """
        Afișează camerele disponibile după număr de persoane dat și vedere dată
        :return:
        """
        input1 = input("Introduceți numărul de persoane și vederea, separate prin virgulă:")
        nrPers, vedere = input1.split(",")
        nrPers = int(nrPers)
        camereDeAfisat = self.__servCamera.cameraDupaNumarPersoaneSiVedere(nrPers, vedere)
        self.afiseazaCamere(camereDeAfisat)

    def inchiriazaCamere(self):
        """
        Afișează o cameră de închiriat după numărul său și după un număr de nopți. Afișează de asemenea prețul
        :return:
        """
        nrCamera = int(input("Introduceți numărul camerei:"))
        nrNopti = int(input("Introduceți numărul de nopți petrecute:"))
        pretTotal = self.__servCamera.inchiriazaCameraPret(nrCamera, nrNopti)
        cameraDeAfisat = self.__servCamera.inchiriazaCameraCamera(nrCamera)
        if cameraDeAfisat is not None and pretTotal is not None:
            for camera in cameraDeAfisat:
                print(str("Camera numărul " + str(camera.getNr()) + ", cu vedere spre " + camera.getVedere() + ", cu numărul de locuri " + str(camera.getLocuri())))
            print("Preț total: " + str(pretTotal))

    def afiseazaCamere(self, listaCamere):
        """
        Afișează camerele din lista primită
        :param listaCamere: Lista de camere de afișat
        :return: Afișează camerele din lista primită
        """
        for camera in listaCamere:
            print(camera)

    def run(self):
        """
        Afișează constant meniul
        :return:
        """
        isRunning = True
        while isRunning:
            self.afiseazaMenu()
            optiune = input("Introduceți din meniu:").upper()
            match optiune:
                case "1":
                    self.cautaCameraDupaNumarPersoaneSiVedere()
                case "2":
                    self.inchiriazaCamere()
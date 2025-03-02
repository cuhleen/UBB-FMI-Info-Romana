from Repo.repoCamera import RepoCamera

class ServiceCamera:
    def __init__(self, repoCamera:RepoCamera):
        self.__repoCamera = repoCamera

    def cameraDupaNumarPersoaneSiVedere(self, nrPers:int, vedere:str):
        """
        Găsește toate camerele ce au numărul de persoane dorit și vederea dorită
        :param nrPers: Numărul de persoane după care se caută camere
        :param vedere: Vederea după care se caută camere
        :return: O listă de camere ce au numărul de persoane dorit și vederea dorită
        """
        camereCurente = self.__repoCamera.getAll()
        camereBune = []
        for camera in camereCurente:
            if camera.getLocuri() >= nrPers and camera.getVedere() == vedere:
                camereBune.append(camera)
        return camereBune

    def inchiriazaCameraPret(self, nrCamera:int, nrNopti:int):
        """
        Calculează prețul închirierii după numărul camerei și numărul de nopți
        :param nrCamera: Numărul camerei după care se caută camera
        :param nrNopti: Numărul de nopți după care se fac calculele prețului
        :return: Prețul total al închirierii după numărul camerei și numărul de nopți
        """
        cameraGasita = self.__repoCamera.find(nrCamera)

        if cameraGasita is None:
            print("Nu s-a găsit camera cu numărul dat.")
            return
        return cameraGasita.getPretNoapte() * nrNopti

    def inchiriazaCameraCamera(self, nrCamera:int):
        """
        Găsește camera de închiriat după numărul camerei
        :param nrCamera: Numărul camerei după care se caută camera
        :return: O listă ce conține un singur element, camera de închiriat
        """
        cameraGasita = self.__repoCamera.find(nrCamera)
        listaCamere = []
        if cameraGasita is not None:
            listaCamere.append(cameraGasita)
        return listaCamere
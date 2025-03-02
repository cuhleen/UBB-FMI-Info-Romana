import datetime



def TEST_crearePachet():
    testPachet = crearePachet('2024/01/01', '2024/02/01', 'Cluj-Napoca', 100)
    assert (testPachet["dataInceput"] == "2024/01/01")
    assert (testPachet["dataSfarsit"] == "2024/02/01")
    assert (testPachet["destinatie"] == "Cluj-Napoca")
    assert (testPachet["pret"] == 100)



def crearePachet(dataInceput: str, dataSfarsit: str, destinatia: str, pret: int) -> dict:
    """
    Creeaza un pachet de călătorie pe baza informațiilor date
    :param dataInceput: data de început a călătoriei
    :param dataSfarsit: data de sfârșit a călatoriei
    :param destinația: destinația călătoriei
    :param pret: prețul călătoriei
    :return: un dictionar care reprezinta pachetul de călătorie
    """
    return {'dataInceput': dataInceput, 'dataSfarsit': dataSfarsit, 'destinatia': destinatia, 'pret': pret}
    #return [dataInceput, dataSfarsit, destinatia, pret]



def TEST_adaugareInLista():
    testList = []
    assert (len(testList) == 0)
    adaugareInLista(testList, 1)
    assert (len(testList) == 1)

    testList.clear()

    adaugareInLista(testList, {'key1': 2, 'key2': 3})
    assert (len(testList) == 1)

    adaugareInLista(testList, {'key1': 2, 'key2': 5})
    assert (len(testList) == 2)

    testList.clear()

    adaugareInLista(testList, [{'key1': 2, 'key2': 3}])
    assert (len(testList) == 1)

    adaugareInLista(testList, [{'key1': 2, 'key2': 3}, {'key1': 2, 'key2': 5}])
    assert (len(testList) == 2)
    adaugareInLista(testList, [{'key1': 2, 'key2': 3}, {'key1': 2, 'key2': 5}, {'key1': 4, 'key2': 20}])
    assert (len(testList) == 3)



def adaugareInLista(listaPachete: list, pachet: dict, listaUndo: list) -> None:
    """
    Adauga pachetul dat la lista de pachete
    :param listaPachete: lista de pachete
    :param pachet: pachetul care se adaugă
    :param listaUndo: lista pentru Undo, în care punem lista de pachete înainte de a fi modificată
    :return: -; lista dată se modifică prin adăugarea pachetului la finalul listei
    """
    listaPacheteVeche = [pachet for pachet in listaPachete]
    listaUndo.append(listaPacheteVeche)
    listaPachete.append(pachet)



def modificareInfoPachet(listaPachete: list, numarPachet: int, listaUndo: list):
    """
    Modificăm pachetul dat din lista de pachete
    :param listaPachete: lista de pachete
    :param numarPachet: numărul pachetului de modificat
    :param listaUndo: lista pentru Undo, în care punem lista de pachete înainte de a fi modificată
    :return: -; pachetul dat din listă se modifică
    """

    listaPacheteVeche = [pachet.copy() for pachet in listaPachete]
    listaUndo.append(listaPacheteVeche)

    if numarPachet < 0 or numarPachet >= len(listaPachete):
        print("Numărul pachetului este invalid.")
        return

    print("Pachetul ", numarPachet, ": \n")
    pachet = listaPachete[numarPachet]
    pachetInfo = ""
    for key, value in listaPachete[numarPachet].items():
        pachetInfo += key.capitalize() + ": " + str(value) + " | "

    print(pachetInfo, "\n")

    print("Ce doriți să modificați la pachet?")
    print("1. Data început")
    print("2. Data sfârșit")
    print("3. Destinația")
    print("4. Prețul")
    print("5. M-am răzgândit. Nu modifica pachetul")

    optiune = input(">>> ")

    match optiune:
        case '1':
            print(f"Data inițială: {pachet['dataInceput']}")
            while True:
                dataNoua = input("Introduceți noua dată de început (YYYY/MM/DD): \n>>> ")
                try:
                    dataNoua = datetime.datetime.strptime(dataNoua, '%Y/%m/%d').date()
                    if dataNoua < datetime.datetime.strptime(pachet['dataSfarsit'], '%Y/%m/%d').date():
                        pachet['dataInceput'] = dataNoua.strftime('%Y/%m/%d')
                        break
                    else:
                        print("Data de început trebuie să fie înaintea datei de sfârșit.")
                except ValueError:
                    print("Formatul datei este invalid. Vă rugăm să folosiți formatul YYYY/MM/DD.")
        
        case '2':
            print("Data inițială: ", pachet['dataSfarsit'])
            while True:
                dataNoua = input("Introduceți noua dată de sfârșit (YYYY/MM/DD): \n>>> ")
                try:
                    dataNoua = datetime.datetime.strptime(dataNoua, '%Y/%m/%d').date()
                    if dataNoua > datetime.datetime.strptime(pachet['dataInceput'], '%Y/%m/%d').date():
                        pachet['dataSfarsit'] = dataNoua.strftime('%Y/%m/%d')
                        break
                    else:
                        print("Data de sfârșit trebuie să fie după data de început.")
                except ValueError:
                    print("Formatul datei este invalid. Vă rugăm să folosiți formatul YYYY/MM/DD.")
        
        case '3':
            print("Destinația inițială:", pachet['destinatia'])
            destinatiaNoua = input("Introduceți noua destinație: \n>>> ").strip()
            if destinatiaNoua:
                pachet['destinatia'] = destinatiaNoua
            else:
                print("Destinația nu poate fi goală.")
        
        case '4':
            print(f"Prețul inițial: {pachet['pret']}")
            while True:
                try:
                    pretNou = int(input("Introduceți noul preț: \n>>> "))
                    if pretNou > 0:
                        pachet['pret'] = pretNou
                        break
                    else:
                        print("Prețul trebuie să fie un număr întreg pozitiv.")
                except ValueError:
                    print("Prețul trebuie să fie un număr întreg valid.")
        
        case '5':
            print("Modificarea pachetului a fost anulată.")
            return
        
        case _:
            print("Opțiune invalidă. Vă rugăm să alegeți o opțiune validă.")
            return

    print("\nPachetul a fost actualizat cu succes.\n")



def afiseazaPachete(listaPachete: list):
    """
    Afișează pachetele de călătorie din lista dată
    :param listaPachete: lista de pachete care se va afișa
    :return: lista dată se afișează
    """
    for i, pachet in enumerate(listaPachete):
        print("Pachet #" + str(i) + ": ", end="")
        pachetInfo = ""
        for key, value in pachet.items():
            pachetInfo += key.capitalize() + ": " + str(value) + " | "
        print(pachetInfo)



def TEST_stergereDupaDestinatie():
    testListaPachete = []
    assert(len(testListaPachete) == 0)
    stergeDupaDestinatie(testListaPachete, "Cluj-Napoca")
    assert(len(testListaPachete) == 0)

    adaugarePacheteDefault(testListaPachete)
    stergeDupaDestinatie(testListaPachete, "Cluj-Napoca")
    assert(len(testListaPachete) == 19)
    stergeDupaDestinatie(testListaPachete, "Brasov")
    assert(len(testListaPachete) == 14)
    stergeDupaDestinatie(testListaPachete, "Destinatie Falsa")
    assert(len(testListaPachete) == 14)


def stergeDupaDestinatie(listaPachete: list, destinatieDeSters: str, listaUndo: list):
    """
    Șterge pachete de călătorie din listă ce au destinația dată
    :param listaPachete: lista de pachete
    :param destinatieDeSters: destinația ce se dorește eliminată
    :param listaUndo: lista pentru Undo, în care punem lista de pachete înainte de a fi modificată
    :return: o listă fără pachetele cu destinația dată
    """
    listaPacheteVeche = [pachet.copy() for pachet in listaPachete]
    listaUndo.append(listaPacheteVeche)
    i = 0
    while i < len(listaPachete):
        if listaPachete[i]["destinatia"] == destinatieDeSters:
            listaPachete.pop(i)
        else:
            i += 1



def TEST_stergeDupaLungime():
    testListaPachete = []
    assert(len(testListaPachete) == 0)
    stergeDupaLungime(testListaPachete, 3)
    assert(len(testListaPachete) == 0)

    adaugarePacheteDefault(testListaPachete)
    stergeDupaLungime(testListaPachete, 11) # elimină doar călătoriile spre Brașov
    assert(len(testListaPachete) == 15)
    stergeDupaLungime(testListaPachete, 10000)
    assert(len(testListaPachete) == 0)



def stergeDupaLungime(listaPachete: list, lungimeDeSters: int, listaUndo: list):
    """
    Șterge pachete de călătorie din listă ce durează mai puțin decât un număr de zile dat
    :param listaPachete: lista de pachete
    :param lungimeDeSters: durata ce se dorește eliminată
    :param listaUndo: lista pentru Undo, în care punem lista de pachete înainte de a fi modificată
    :return: o listă fără pachetele unde durata este mai scurtă decât cea dată
    """
    listaPacheteVeche = [pachet.copy() for pachet in listaPachete]
    listaUndo.append(listaPacheteVeche)

    i = 0
    while i < len(listaPachete):
        dataSfarsit = listaPachete[i]["dataSfarsit"]
        an, luna, zi = map(int, dataSfarsit.split('/'))
        dataSfarsit = datetime.date(an, luna, zi)

        dataInceput = listaPachete[i]["dataInceput"]
        an, luna, zi = map(int, dataInceput.split('/'))
        dataInceput = datetime.date(an, luna, zi)

        diferentaSfarsitInceput = dataSfarsit - dataInceput
        diferentaSfarsitInceput = diferentaSfarsitInceput.days

        if diferentaSfarsitInceput < lungimeDeSters:
            listaPachete.pop(i)
        else:
            i += 1



def TEST_stergeDupaPret():
    testListaPachete = []
    assert(len(testListaPachete) == 0)
    stergeDupaPret(testListaPachete, 100)
    assert(len(testListaPachete) == 0)

    adaugarePacheteDefault(testListaPachete)
    stergeDupaPret(testListaPachete, 201)
    assert(len(testListaPachete) == 12)
    stergeDupaPret(testListaPachete, 10000)
    assert(len(testListaPachete) == 0)



def stergeDupaPret(listaPachete: list, pretDeSters: int, listaUndo):
    """
    Șterge pachete de călătorie din listă ce costă mai puțin strict decât un preț dat
    :param listaPachete: lista de pachete
    :param pretDeSters: prețul maxim ce se dorește eliminat
    :param listaUndo: lista pentru Undo, în care punem lista de pachete înainte de a fi modificată
    :return: o listă fără pachetele ce costă mai puțin strict decât prețul dat
    """
    listaPacheteVeche = [pachet.copy() for pachet in listaPachete]
    listaUndo.append(listaPacheteVeche)

    i = 0
    while i < len(listaPachete):
        if listaPachete[i]["pret"] < pretDeSters:
            listaPachete.pop(i)
        else:
            i += 1



def cautareDupaDate(listaPachete: list, dataInceputDeCautat: datetime, dataSfarsitDeCautat: datetime):
    """
    Afișează pachetele de călătorii ce se desfășoara între două date
    :param listaPachete: lista de pachete
    :param dataInceputDeCautat: data de început a călătoriilor dorite
    :param dataSfarsitDeCautat: data de sfârșit a călătoriilor dorite
    :return: o listă ce conține doar pachetele ce se desfășoara între două date
    """
    listaNoua = []
    for pachet in listaPachete:
        dataSfarsit = pachet["dataSfarsit"]
        an, luna, zi = map(int, dataSfarsit.split('/'))
        dataSfarsit = datetime.date(an, luna, zi)
        dataInceput = pachet["dataInceput"]
        an, luna, zi = map(int, dataInceput.split('/'))
        dataInceput = datetime.date(an, luna, zi)

        if dataInceputDeCautat <= dataInceput and dataSfarsit <= dataSfarsitDeCautat:
            listaNoua.append(pachet)

    return listaNoua

    #afiseazaPachete(listaNoua)



def cautareDupaDestinatieSiPret(listaPachete: list, destinatieCitita: str, pretCitit: int):
    """
    Afișează pachetele de călătorii ce au o anumită destinație și un anumit preț maxim
    :param listaPachete: lista de pachete
    :param destinatieCitita: destinația călătoriilor dorite
    :param pretCitit: prețul maxim al călătoriilor dorite
    :return: o listă ce conține doar pachetele ce au o anumită destinație și un anumit preț maxim
    """

    listaNoua = []
    for pachet in listaPachete:
        if pachet["destinatia"] == destinatieCitita and pachet["pret"] <= pretCitit:
            listaNoua.append(pachet)

    return listaNoua



def cautareDupaDataSfarsit(listaPachete: list, dataDeFinal: str):
    """
    Afișează pachetele de călătorii ce au o anumită dată de sfârșit
    :param listaPachete: lista de pachete
    :param dataDeFinal: data de sfârșit dorită
    :return: o listă ce conține doar pachetele ce au o anumită dată de sfârșit
    """

    listaNoua = []
    for pachet in listaPachete:
        if pachet["dataSfarsit"] == dataDeFinal:
            listaNoua.append(pachet)

    return listaNoua



def numarOferteDupaDestinatie(listaPachete: list, destinatieCitita: str):
    """
    Afișează numărul pachetelor de călătorii cu o destinație specifică
    :param listaPachete: lista de pachete
    :param destinatieCitita: destinația dorită
    :return: numărul de pachete de călătorii cu destinația dorită
    """
    n = 0
    for pachet in listaPachete:
        if pachet["destinatia"] == destinatieCitita:
            n += 1
        
    return n



def afisareSortataDupaDate(listaPachete: list, dataInceputDeCautat: str, dataSfarsitDeCautat: str):
    """
    Afișează pachetele de călătorii ce se desfășoara între două date, sortate după preț
    :param listaPachete: lista de pachete
    :param dataInceputDeCautat: data de început a călătoriilor dorite
    :param dataSfarsitDeCautat: data de sfârșit a călătoriilor dorite
    :return: o listă ce conține doar pachetele ce se desfășoara între două date, sortate după preț
    """
    listaNoua = cautareDupaDate(listaPachete, dataInceputDeCautat, dataSfarsitDeCautat)

    for i in range(0, len(listaNoua) - 1):
        for j in range (i + 1, len(listaNoua)):
            if int(listaNoua[i]["pret"]) > int(listaNoua[j]["pret"]):
                listaNoua[i], listaNoua[j] = listaNoua[j], listaNoua[i]

    return listaNoua



def afisareMediePretPentruDestinatie(listaPachete: list, destinatieCitita: str):
    """
    Afișează media prețurilor pachetelor pentru destinația dată
    :param listaPachete: lista de pachete
    :param destinatieCitita: destinația dorită
    :return: media prețurilor pachetelor pentru destinația dată
    """
    s = 0
    n = 0
    for pachet in listaPachete:
        if pachet["destinatia"] == destinatieCitita:
            n += 1
            s += pachet["pret"]

    return n, s



def filtrarePretDestinatie(listaPachete: list, pretCitit: int, destinatieCitita: str):
    """
    Afișează lista pachetelor cu prețul mai mic decât cel citit și destinația diferită decât cea citită
    :param listaPachete: lista de pachete
    :param pretCitit: prețul minim de eliminat
    :param destinatieCitita: destinația nedorită
    :return: o listă cu pachete cu prețul mai mic decât cel citit și destinația diferită decât cea citită
    """

    listaNoua = listaPachete.copy()

    i = 0
    while i < len(listaNoua):
        if listaNoua[i]["destinatia"] != destinatieCitita and listaNoua[i]["pret"] > pretCitit:
            listaNoua.pop(i)
        else:
            i += 1

    return listaNoua



def filtrareFaraLuna(listaPachete: str, lunaCitita: int):
    """
    Afișează lista pachetelor unde datele nu trec prin o anumita lună a anului
    :param listaPachete: lista de pachete
    :param lunaCitita: luna de evitat
    :return: o listă cu pachete unde datele nu trec prin o anumita lună a anului
    """
    listaNoua = listaPachete.copy()

    i = 0

    while(i < len(listaNoua)):
        dataSfarsit = listaNoua[i]["dataSfarsit"]
        an, luna, zi = map(int, dataSfarsit.split('/'))
        dataSfarsit = datetime.date(an, luna, zi)
        dataInceput = listaNoua[i]["dataInceput"]
        an, luna, zi = map(int, dataInceput.split('/'))
        dataInceput = datetime.date(an, luna, zi)

        if dataInceput.year == dataSfarsit.year:
            if dataInceput.month <= lunaCitita <= dataSfarsit.month:
                listaNoua.pop(i)
            else:
                i += 1
        elif dataSfarsit.year - dataInceput.year == 1:
            iData = dataInceput
            sfData = dataSfarsit
            while iData <= sfData:
                if iData.month == lunaCitita:
                    listaNoua.pop(i)
                    break
                urmatoareaLuna = iData.month % 12 + 1
                urmatorulAn = iData.year + (iData.month // 12)
                iData = iData.replace(year=urmatorulAn, month=urmatoareaLuna)
            i += 1

    return listaNoua



def adaugarePacheteDefault(listaPachete):
    adaugareInLista(listaPachete, crearePachet("2024/03/10", "2024/06/12", "Craiova", 100))
    adaugareInLista(listaPachete, crearePachet("2025/07/05", "2025/12/28", "Germania", 500))
    adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 50))
    adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 100))
    adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 150))
    adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 151))
    adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 200))
    adaugareInLista(listaPachete, crearePachet("2026/01/01", "2026/04/01", "Mamaia", 600))
    adaugareInLista(listaPachete, crearePachet("2024/02/01", "2024/03/15", "Paris", 350))
    adaugareInLista(listaPachete, crearePachet("2023/10/20", "2023/11/05", "Roma", 200))
    adaugareInLista(listaPachete, crearePachet("2024/06/10", "2024/06/25", "Lisabona", 400))
    adaugareInLista(listaPachete, crearePachet("2025/12/01", "2026/01/10", "Budapesta", 450))
    adaugareInLista(listaPachete, crearePachet("2024/07/12", "2024/08/01", "Tokyo", 1200))
    adaugareInLista(listaPachete, crearePachet("2024/10/05", "2024/10/18", "Cluj-Napoca", 150))
    adaugareInLista(listaPachete, crearePachet("2025/05/01", "2025/06/01", "Berlin", 550))
    adaugareInLista(listaPachete, crearePachet("2023/11/10", "2023/12/20", "Dubai", 1100))
    adaugareInLista(listaPachete, crearePachet("2026/07/01", "2026/07/15", "New York", 1300))
    adaugareInLista(listaPachete, crearePachet("2025/03/08", "2025/03/20", "Sinaia", 90))
    adaugareInLista(listaPachete, crearePachet("2024/09/10", "2024/09/25", "Vienna", 320))
    adaugareInLista(listaPachete, crearePachet("2025/04/10", "2025/04/25", "Amsterdam", 480))
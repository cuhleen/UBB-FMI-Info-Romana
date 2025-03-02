import datetime

from view import afiseazaMenu
from model import crearePachet, adaugareInLista, afiseazaPachete, modificareInfoPachet, stergeDupaDestinatie, stergeDupaLungime, stergeDupaPret, cautareDupaDate, cautareDupaDestinatieSiPret, cautareDupaDataSfarsit, numarOferteDupaDestinatie, afisareSortataDupaDate, afisareMediePretPentruDestinatie, filtrarePretDestinatie, filtrareFaraLuna
from errors import validarePachet


def citesteInfoPachet() -> tuple:
    while True:
        try:
            dataInceput = input('Introduceți data de început în formatul YYYY/MM/DD \n >>> ')
            dataSfarsit = input('Introduceți data de sfârșit în formatul YYYY/MM/DD \n >>> ')
            destinatia = input("Introduceți destinația: \n >>> ")
            pret = input("Introduceți prețul călătoriei: \n >>> ")
            
            validarePachet(dataInceput, dataSfarsit, destinatia, pret)

            return dataInceput, dataSfarsit, destinatia, pret
        except ValueError as e:
            print("\n################## ERORI ##################\n", str(e), "\n################## ERORI ##################\n")



def run():
    listaPachete = []
    listaUndo = []
    isRunning = True
    while isRunning:
        afiseazaMenu()
        optiune = input(">>> ").strip()

        match optiune:

            case '1':
                print("   Menu 1:")
                print("1. Adăugare călătorie nouă")
                print("2. Modificare călătorie existentă")
                optiuneSecundara = input(">>> ").strip()

                match optiuneSecundara:

                    case '1':
                        dataInceput, dataSfarsit, destinatia, pret = citesteInfoPachet()
                        pachet = crearePachet(dataInceput, dataSfarsit, destinatia, pret)
                        adaugareInLista(listaPachete, pachet, listaUndo)
                    case '2':
                        if len(listaPachete) == 0:
                            print("Nu există pachete.")
                        else:
                            print("Ce pachet doriți să modificați? Introduceți doar numărul său \n")
                            afiseazaPachete(listaPachete)
                            numarPachet = int(input("\n>>>"))
                            modificareInfoPachet(listaPachete, numarPachet, listaUndo)

            case '2':
                print("   Menu 2:")
                print("1. Ștergerea tututor pachetelor cu destinația dată")
                print("2. Ștergerea tuturor pachetelor mai scurte decât un anumit număr de zile")
                print("3. Ștergerea tuturor pachetelor cu un preț mai mare decât un număr dat")
                optiuneSecundara = input(">>> ").strip()

                match optiuneSecundara:

                    case '1':
                        print("Ce destinație ați dori să eliminați din listă? \n")
                        afiseazaPachete(listaPachete)
                        destinatieDeSters = input("\n>>>")
                        stergeDupaDestinatie(listaPachete, destinatieDeSters, listaUndo)

                    case '2':
                        print("Care este durata maximă (în zile) a pachetelor pe care le doriți eliminate din listă? (Se vor șterge toate pachetele ce durează strict mai puțin decât numărul de zile dat) \n")
                        afiseazaPachete(listaPachete)
                        while True:
                            lungimeDeSters = int(input("\n>>>"))
                            if lungimeDeSters < 1:
                                print("Durata unei excursii nu poate fi mai scurtăde o zi")
                            else:
                                break
                        stergeDupaLungime(listaPachete, lungimeDeSters, listaUndo)

                    case '3':
                        print("Care este prețul minim al pachetelor pe care le doriți din listă? (Se vor șterge toate pachetele ce costă strict mai puțin decât numărul dat) \n")
                        afiseazaPachete(listaPachete)
                        pretDeSters = int(input("\n>>>"))
                        stergeDupaPret(listaPachete, pretDeSters, listaUndo)

            case '3':

                print("   Menu 3:")
                print("1. Afișare călătorii între două date specifice")
                print("2. Afișare călătorii cu o destinație specifică și preț maxim")
                print("3. Afișare călătorii cu o dată de sfârșit specifică")
                optiuneSecundara = input(">>> ").strip()

                match optiuneSecundara:

                    case '1':
                        while True:
                            try:
                                dataCitita = input('Introduceți data de început în formatul YYYY/MM/DD \n >>>')
                                an, luna, zi = map(int, dataCitita.split('/'))
                                dataInceputDeCautat = datetime.date(an, luna, zi)

                                dataCitita = input('Introduceți data de sfârșit în formatul YYYY/MM/DD \n >>>')
                                an, luna, zi = map(int, dataCitita.split('/'))
                                dataSfarsitDeCautat = datetime.date(an, luna, zi)

                                if dataInceputDeCautat >= dataSfarsitDeCautat:
                                    print("Data de început trebuie să fie înaintea datei de sfârșit")
                                else:
                                    break
                            except ValueError:
                                print("Datele nu sunt valide.")
                        
                        afiseazaPachete(cautareDupaDate(listaPachete, dataInceputDeCautat, dataSfarsitDeCautat))
                    
                    case '2':
                        try:
                            destinatia = input("Introduceți destinația: \n >>> ")
                            if not destinatia:
                                print("Eroare: Destinația nu poate fi goală.")
                                continue

                            pret = input("Introduceți prețul călătoriei: \n >>> ")
                            if not pret.isdigit() or int(pret) <= 0:
                                print("Eroare: Prețul trebuie să fie un număr întreg pozitiv.")
                                continue
                            pret = int(pret)
                        except ValueError:
                            print("Datele introduse nu sunt valide")

                        afiseazaPachete(cautareDupaDestinatieSiPret(listaPachete, destinatia, pret))
                    
                    case '3':
                        try:
                            dataFinal = input('Introduceți data de sfârșit în formatul YYYY/MM/DD \n >>>').strip()
                            an, luna, zi = map(int, dataFinal.split('/'))
                            validareDataFinal = datetime.date(an, luna, zi)
                        except ValueError:
                            print("Data nu este valide.")
                        
                        afiseazaPachete(cautareDupaDataSfarsit(listaPachete, dataFinal))
            
            case '4':
                print("   Menu 4:")
                print("1. Afișarea numărului de oferte pentru o destinație dată")
                print("2. Afișare călătorii între două date specifice, sortate crescător după preț")
                print("3. Afișarea mediei de preț pentru o destinație dată")
                optiuneSecundara = input(">>> ").strip()
                
                match optiuneSecundara:
                    
                    case '1':
                        try:
                            destinatia = input("Introduceți destinația dorită: \n >>> ")
                            if not destinatia:
                                print("Eroare: Destinația nu poate fi goală.")
                                continue
                        except ValueError:
                            print("Date invalide.")

                        print("Numărul de pachete de călătorie la ", destinatia, " este ", numarOferteDupaDestinatie(listaPachete, destinatia))
                    
                    case '2':
                        while True:
                            try:
                                dataCitita = input('Introduceți data de început în formatul YYYY/MM/DD \n >>>')
                                an, luna, zi = map(int, dataCitita.split('/'))
                                dataInceputDeCautat = datetime.date(an, luna, zi)

                                dataCitita = input('Introduceți data de sfârșit în formatul YYYY/MM/DD \n >>>')
                                an, luna, zi = map(int, dataCitita.split('/'))
                                dataSfarsitDeCautat = datetime.date(an, luna, zi)

                                if dataInceputDeCautat >= dataSfarsitDeCautat:
                                    print("Data de început trebuie să fie înaintea datei de sfârșit")
                                else:
                                    break
                            except ValueError:
                                print("Datele nu sunt valide.")
                        
                        afiseazaPachete(afisareSortataDupaDate(listaPachete, dataInceputDeCautat, dataSfarsitDeCautat))
                    
                    case '3':
                        try:
                            destinatia = input("Introduceți destinația dorită: \n >>> ")
                            if not destinatia:
                                print("Eroare: Destinația nu poate fi goală.")
                                continue
                        except ValueError:
                            print("Date invalide.")
                        n, s = afisareMediePretPentruDestinatie(listaPachete, destinatia)
                        print("Media prețului pentru ", destinatia, " este ", s/n, ", fiind un raport de ", s, "supra ", n)
            
            case '5':
                print("   Menu 5:")
                print("1. Eliminarea ofertelor care au un preț mai mare decât cel citit și destinația diferită decât cea citită")
                print("2. Eliminarea ofertelor în care perioada călătoriei presupune zile dintr-o anumită lună")
                optiuneSecundara = input(">>> ").strip()
                
                match optiuneSecundara:
                    
                    case '1':
                        try:
                            pret = input("Introduceți prețul maxim dorit (se vor elimina ofertele cu preț mai mare strict) \n >>>")
                            if not pret.isdigit() or int(pret) <= 0:
                                print("Prețul trebuie să fie un număr întreg pozitiv.")
                                continue
                            pret = int(pret)
                            destinatia = input("Introduceți destinația: \n >>> ")
                            if not destinatia:
                                print("Destinația nu poate fi goală.")
                                continue
                        except ValueError:
                            print("Destinația nu poate fi goală, prețul trebuie săfie un număr întreg")
                        afiseazaPachete(filtrarePretDestinatie(listaPachete, pret, destinatia))
                    
                    case '2':
                        lunaCitita = int(input("Introduceți luna care să nu fie inclusă în căutare (un număr de la 1 la 12) \n >>>"))
                        afiseazaPachete(filtrareFaraLuna(listaPachete, lunaCitita))
            
            case '6':
                if len(listaUndo) == 0:
                    print("Nu se poate face Undo")
                else:
                    listaPachete = [pachet for pachet in listaUndo[len(listaUndo ) - 1]]
                    listaUndo.pop(len(listaUndo) - 1)

            case '7':
                isRunning = False
            
            case '8':
                afiseazaPachete(listaPachete)
            
            case '9':
                adaugareInLista(listaPachete, crearePachet("2024/03/10", "2024/06/12", "Craiova", 100), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/07/05", "2025/12/28", "Germania", 500), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 50), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 100), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 150), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 151), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/05/15", "2025/05/25", "Brasov", 200), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2026/01/01", "2026/04/01", "Mamaia", 600), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2024/02/01", "2024/03/15", "Paris", 350), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2023/10/20", "2023/11/05", "Roma", 200), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2024/06/10", "2024/06/25", "Lisabona", 400), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/12/01", "2026/01/10", "Budapesta", 450), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2024/07/12", "2024/08/01", "Tokyo", 1200), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2024/10/05", "2024/10/18", "Cluj-Napoca", 150), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/05/01", "2025/06/01", "Berlin", 550), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2023/11/10", "2023/12/20", "Dubai", 1100), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2026/07/01", "2026/07/15", "New York", 1300), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/03/08", "2025/03/20", "Sinaia", 90), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2024/09/10", "2024/09/25", "Vienna", 320), listaUndo)
                adaugareInLista(listaPachete, crearePachet("2025/04/10", "2025/04/25", "Amsterdam", 480), listaUndo)
            
            case _:
                print("Vă rugăm introduceți o opțiune din meniu")
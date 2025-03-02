# Lab 3 12
# Se citește o listă de elemente
# Să se verifice dacă oricare două elemente consecutive au semnul diferit

# Lab 3 16
# Se citește o listă de elemente
# Să se verifice dacă toate elementele folosesc aceleași cifre

# Aplicație care citește o listă și afișează secvența de lungime maximă ce urmează una dintre cele două reguli de mai sus



# ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ #



n = 0 # lungimea listei
arr = [] # lista



def citireArr():
    global n
    n = int(input("Câte elemente are lista? \n"))

    while n < 0:
        print("Lista nu poate avea elemente negative")
        n = int(input("Câte elemente are lista? \n"))

    if n == 0:
        print("Lista are 0 elemente, deci nu este nimic de citit")
    else:
        print("Introduceți elementele listei, fiecare element pe o linie separată")

        arr.clear()
        for i in range(0, n):
            x = int(input())
            arr.append(x)

    # print(arr)

    print("\n")
    citireMenu()



def semnDif():

    global n
    lc = 0 # lungime curentă
    lmax = 0 # lungime maximă
    poz = 0 # poziția secvenței maxime
    pozinit = 0 # poziția secvenței curente

    if n == 0:
        print("Lista nu are elemente")
        citireMenu()
    else:
        for i in range(0, n - 1):
            if arr[i] >= 0 and arr[i + 1] < 0 or arr[i] < 0 and arr[i + 1] >= 0:
                if lc == 0:
                    lc = 2
                    pozinit = i
                else:
                    lc += 1
            else:
                if lc > lmax:
                    lmax = lc
                    poz = pozinit
                lc = 0

        # În caz că și ultimul element respectă regula
        if lc > lmax:
            lmax = lc
            poz = pozinit

        
        if n == 1:
            print("Lista are doar un element: ", arr[0])
            citireMenu()

        if lmax == 0:
            print("În lista dată, nu există două cifre consecutive cu semnul diferit. Orice element individual este o secventă de lungime maximă, lungimea 1")
        else:
            print("În lista dată, secvența de lungime maximă unde oricare două cifre consecutive au semnul diferit este:")
            for i in range(poz, poz + lmax):
                print(arr[i], end=" ")

    print("\n")

    citireMenu()



def arrEgal(arr1, arr2): #funcție care verifică dacă două liste sunt egale, folosită pentru vectorii de frecvență
    for i in range(0, 10):
        if arr1[i] != arr2[i]:
            return 0
    
    return 1

def aceleasiCif():
    global n

    if n == 0:
        print("Lista nu are elemente")
        citireMenu()
    elif n == 1:
        print("Lista are doar un element: ", arr[0])
        citireMenu()

    lc = 0  # lungime curentă
    lmax = 0  # lungime maximă
    poz = 0  # poziția secvenței maxime
    pozinit = 0  # poziția secvenței curente

    frecv_ant = [0] * 10  # vectorul de frecvență al elementului anterior

    aux = abs(arr[0])

    while aux:
        frecv_ant[aux % 10] = 1
        aux //= 10

    for i in range(1, n):
        aux = abs(arr[i])
        frecv_curent = [0] * 10

        while aux:
            frecv_curent[aux % 10] = 1
            aux //= 10

        if arrEgal(frecv_ant, frecv_curent) == 1:
            if lc == 0:
                lc = 2
                pozinit = i - 1
            else:
                lc += 1
        else:
            if lc > lmax:
                lmax = lc
                poz = pozinit
            lc = 0

        frecv_ant = frecv_curent.copy()

    # În caz că ultimul element face parte din secvența cea mai lungă
    if lc > lmax:
        lmax = lc
        poz = pozinit

    if lmax == 0:
        print("Nu există două numere consecutive formate din aceleași cifre.")
    else:
        print("În lista dată, secvența de lungime maximă unde numerele sunt formate din aceleași cifre este:")
        for i in range(poz, poz + lmax):
            print(arr[i], end=" ")
    
    print("\n")
    citireMenu()



def citireMenu():
    print("    Introduceți cifra din meniu:")
    print("1. Citire listă")
    print("2. Afișarea secvenței de lungime maximă cu proprietatea ca oricare două numere consecutive să aibă semnul diferit")
    print("3. Afișarea secvenței de lungime maximă cu proprietatea ca toate elementele să fie formate din aceleași cifre")
    print("4. Ieșire din aplicație")
    print("\n")

    optiune = int(input("Opțiunea: "))
    if optiune == 1:
        citireArr()
    elif optiune == 2:
        semnDif()
    elif optiune == 3:
        aceleasiCif()
    elif optiune == 4:
        exit()
    else:
        print("Vă rugăm introduceți o opțiune validă din meniu. \n")
        citireMenu()



def main():
    citireMenu()

if __name__ == "__main__":
    main()
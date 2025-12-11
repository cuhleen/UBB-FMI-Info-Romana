from random import sample, randint


def MontyHall_sim(schimba_usa=False, nr_sim=0, afisare=False):
    count = 0
    for _ in range(nr_sim):
        asezare = ['c','c','c']
        masina, capra1, capra2 = sample([0,1,2], 3)
        asezare[masina] = 'm'
        prima_alegere = randint(0,2)
        prima_alegere_arhiva = prima_alegere
        if prima_alegere == masina:
            alegere_prezentator = sample([capra1, capra2], 1).pop()
        if prima_alegere == capra1:
            alegere_prezentator = capra2
        else:
            alegere_prezentator = capra1

        if schimba_usa:
            alegere_finala = ({0, 1, 2} - {prima_alegere, alegere_prezentator}).pop()
        else:
            alegere_finala = prima_alegere

        rezultat = masina == alegere_finala
        count += rezultat
        if afisare:
            print("Asezarea:", end = '        ')
            asezare = ['a', 'a', 'a']
            asezare[capra1] = '🐐'
            asezare[capra2] = '🐐'
            asezare[masina] = '🚗'
            print(asezare)
            print("Prima alegere:", end = '   ')
            afis = ['🚪', '🚪', '🚪']
            afis[prima_alegere_arhiva] = '☝️'
            print(afis)
            print('Capra dezvaluita', end = ' ')
            afis[alegere_prezentator] = '👀'
            print(afis)
            afis[alegere_prezentator] = '🚪'
            print('A doua alegere', end = '   ')
            afis[prima_alegere_arhiva] = '🚪'
            afis[alegere_finala] = '☝️'
            print(afis)
            print("Rezultat:", end = ' ')
            if(rezultat):
                print("SUCCES!\n")
            else:
                print("ESEC!\n")

    return count/nr_sim

# MontyHall_sim(False, 1, True)
print("\nRata succes daca schimbam usa: " + str(MontyHall_sim(True, 100000, False)))
print("Rata succes daca NU schibam usa: " + str(MontyHall_sim(False, 100000, False)))

print("\nExemple cu schimb de usa: \n")
MontyHall_sim(True, 2, True)
print("----------------------------------------------\n\nExemple fara schimb de usa: \n")
MontyHall_sim(False, 2, True)
from pprint import pprint

import numpy as np
from tabulate import tabulate


def print_table(table):
    #functie de printare a tabelului
    #(doar pentru a se vedea mai clar elementele)
    item_indices = np.arange(0, len(table)).reshape(-1, 1)
    table = np.hstack((item_indices, table))
    headers = ['']+[f"W={i}" for i in range(len(table[0]))]
    print(tabulate(table, headers=headers, tablefmt="grid"))

def solve(N, G, weights, profits):
    # creeaza o matrice de dimensiune (N+1)x(G+1)
    # umpluta cu valori de 0
    M = np.zeros((N + 1, G + 1), dtype=int)
    for current_object in range(1, N + 1):
        for weight in range(1, G + 1):
            # current_object-1 pentru ca listele de weights, profits sunt indexate de la 0
            # aici pe linia 0 si coloana 0 avem valoarea 0
            # linia 0: nu avem niciun obiect la dispozitie -> profit 0, oricare ar fi greutatea
            # coloana 0: greutate 0
            if weights[current_object - 1] > weight:
                M[current_object][weight] = M[current_object - 1][weight]
            else:
                M[current_object][weight] = max(
                    profits[current_object - 1] + M[current_object - 1][weight - weights[current_object - 1]],
                    M[current_object - 1][weight])
    print_table(M)
    return M[N, G]


weights1 = [2, 3, 6, 4]
profits1 = [20, 40, 50, 45]
max_profit = solve(4, 10, weights1, profits1)
print("Max profit:", max_profit)

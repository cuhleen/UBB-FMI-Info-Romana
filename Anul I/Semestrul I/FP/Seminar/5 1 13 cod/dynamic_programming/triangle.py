from pprint import pprint


def print_triangle(triangle):
    for lst in triangle:
        print(' '.join([str(elem) for elem in lst]))


def create_empty_triangle(n):
    t = []
    for i in range(n):
        t.append([0] * (i + 1))
    # print_triangle(t)
    return t


def solve(triangle):
    N = len(triangle)

    # matrice (triunghi) suma maxima
    S = create_empty_triangle(N)
    # matrice (triunghi) directie de deplasare
    P = create_empty_triangle(N)

    for i in range(N):
        # pornim de la ultima linie,
        # de la elementele existente in matricea/triunghiul initial
        S[-1][i] = triangle[-1][i]

    for line in range(N - 2, -1, -1):
        for column in range(line + 1):
            S[line][column] = triangle[line][column]

            # putem alege sa luam valoarea de pe linia urmatoare, coloana curenta (if)
            # in acest caz in matricea de pozitii, pentru a putea reconstitui sirul
            # punem valoarea 1
            # sau putem alege valoarea de pe linia urmatoare, coloana din dreapta (else)
            # in acest caz in matricea de pozitii, pentru a putea reconstitui
            # punem valoarea 2

            if S[line + 1][column] > S[line + 1][column + 1]:
                S[line][column] += S[line + 1][column]
                P[line][column] = 1
            else:
                S[line][column] += S[line + 1][column + 1]
                P[line][column] = 2

    max_sum = S[0][0]
    line = 0
    col = 0
    elements_of_max_sum = [triangle[0][0]]
    while line < N - 1:
        direction = P[line][col]
        if direction == 1:
            elements_of_max_sum.append(triangle[line + 1][col])
        else:
            elements_of_max_sum.append(triangle[line + 1][col + 1])
            col += 1
        line += 1

    return max_sum, elements_of_max_sum


initial_triangle = []
initial_triangle.append([5])
initial_triangle.append([4, 2])
initial_triangle.append([5, 4, 3])
initial_triangle.append([4, 6, 2, 5])
print_triangle(initial_triangle)
S, elements = solve(initial_triangle)
print(S)
print(elements)

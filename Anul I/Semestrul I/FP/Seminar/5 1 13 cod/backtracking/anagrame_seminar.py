"""
Cuvant: rac
acr
aar -> {a,r} = 2  != 3
arc
car
cra
rac
rca
"""


def consistent(x):
    return len(x) == len(list(set(x)))


def solution(x, n):
    return len(x) == n


def back_rec(x):
    x.append('')
    for i in range(len(litere_cuvant)):
        x[-1] = litere_cuvant[i]
        if consistent(x):
            if solution(x, len(litere_cuvant)):
                print(x)
            back_rec(x)

    x.pop()


def back_iter(litere_cuv):
    x = ['']
    litere_cuvant = [''] + litere_cuv
    while len(x):
        # print(x)
        value_found_for_spot = 0
        while not value_found_for_spot and litere_cuvant.index(x[-1]) < len(litere_cuvant) - 1:
            x[-1] = litere_cuvant[litere_cuvant.index(x[-1]) + 1]
            value_found_for_spot = consistent(x)

        if value_found_for_spot:
            if solution(x, len(litere_cuvant) - 1):
                print(x)
            x.append('')

        else:
            x = x[:-1]


cuvant = input("Cuvantul este:")
litere_cuvant = list(cuvant)
litere_cuvant.sort()
back_rec([])
print('------')
back_iter(litere_cuvant)

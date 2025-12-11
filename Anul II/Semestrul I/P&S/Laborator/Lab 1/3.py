from random import sample
from math import perm, comb
from itertools import permutations, combinations

print("")
print("Aranjamente")
print("")

def aranjamente(cuv:str, luateCate:int, numar_total=False, aleator=False):
    if numar_total:
        print(perm(len(cuv), luateCate))
        return
    if aleator:
        print(sample(list(permutations(cuv, luateCate)), 1))
        return
    for aranjament in permutations(cuv, luateCate):
        print(aranjament)

aranjamente("word", 2)
aranjamente("word", 2, numar_total=True)
aranjamente("word", 2, aleator=True)

print("")
print("Combinari")
print("")

def combinari(cuv:str, luateCate:int, numar_total=False, aleator=False):
    if numar_total:
        print(comb(len(cuv), luateCate))
        return
    if aleator:
        print(sample(list(combinations(cuv, luateCate)), 1))
        return
    for combinare in combinations(cuv, luateCate):
        print(combinare)

combinari("word", 2)
combinari("word", 2, numar_total=True)
combinari("word", 2, aleator=True)
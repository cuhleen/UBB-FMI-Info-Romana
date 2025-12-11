from random import sample
from math import factorial
from itertools import permutations

def permutari(cuv:str):
    for perm in permutations(cuv):
        print(perm)

def nrTotalPermutari(cuv:str):
    print(factorial(len(cuv)))

def permRand(cuv:str):
    print(sample(list(permutations(cuv)), 1))

permutari("abc")
nrTotalPermutari("abc")
permRand("abc")
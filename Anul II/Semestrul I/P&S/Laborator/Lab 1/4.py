from itertools import combinations_with_replacement

def combCuRep(cuv:str, luateCate:int):
    for combinare in combinations_with_replacement(cuv,luateCate):
        print(combinare)

combCuRep("ABCDE", 4)

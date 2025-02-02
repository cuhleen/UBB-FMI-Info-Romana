def suma_pare(lst):
    if len(lst) == 0:
        return 0
    if len(lst) == 1:
        if lst[0] % 2 == 0:
            return lst[0]
        else:
            return 0
    middle = len(lst) // 2
    return suma_pare(lst[:middle]) + suma_pare(lst[middle:])


def test_sum_array_even():
    l1 = []
    l2 = [1]
    l3 = [1, 1, 1]
    l4 = [2]
    l5 = [2, 2, 2, 2, 2]
    l6 = [1, 2, 3, 4, 5, 6]
    l7 = [1, 2, 3, 4, 5]
    l8 = [1, 3, 5, -2, -2]

    assert (suma_pare(l1) == 0)
    assert (suma_pare(l2) == 0)
    assert (suma_pare(l3) == 0)
    assert (suma_pare(l4) == 2)
    assert (suma_pare(l5) == 10)
    assert (suma_pare(l6) == 12)
    assert (suma_pare(l7) == 6)
    #De vazut daca se precizeaza numere naturale, numere intregi
    #sau punem noi preconditie
    assert (suma_pare(l8) == -4)

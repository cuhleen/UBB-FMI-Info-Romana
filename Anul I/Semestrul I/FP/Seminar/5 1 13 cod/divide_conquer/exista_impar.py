def exista_impar(lst):
    if len(lst) == 0:
        return False
    if len(lst) == 1:
        return lst[0] % 2
    middle = len(lst) // 2
    return exista_impar(lst[:middle]) or exista_impar(lst[middle:])


def test_exists_odd():
    l1 = []
    l2 = [2]
    l3 = [1]
    l4 = [1, 2, 3]
    l5 = [2, 1]
    l6 = [2, 2, 2, 2]
    l7 = [1, 1, 1, 1]

    assert (exista_impar(l1) == False)
    assert (exista_impar(l2) == False)
    assert (exista_impar(l3) == True)
    assert (exista_impar(l4) == True)
    assert (exista_impar(l5) == True)
    assert (exista_impar(l6) == False)
    assert (exista_impar(l7) == True)

import math


def cmmdc_lista(lst):
    if len(lst) == 0:
        return None
    if len(lst) == 1:
        return lst[0]
    return math.gcd(lst[0], cmmdc_lista(lst[1:]))


def cmmdc_lista_jumatati(lst):
    if len(lst) == 0:
        return None
    if len(lst) == 1:
        return lst[0]
    middle = len(lst) // 2
    return math.gcd(cmmdc_lista_jumatati(lst[:middle]), cmmdc_lista(lst[middle:]))


def test_cmmdc_lista_v1():
    l1 = []
    l2 = [1, 2, 3]
    l3 = [2, 4, 6, 8]
    l4 = [36, 24, 48]
    l5 = [24, 48]
    l6 = [0, 100]
    l7 = [100, 250, 300]

    assert (cmmdc_lista_jumatati(l1) is None)
    assert (cmmdc_lista_jumatati(l2) == 1)
    assert (cmmdc_lista_jumatati(l3) == 2)
    assert (cmmdc_lista_jumatati(l4) == 12)
    assert (cmmdc_lista_jumatati(l5) == 24)
    assert (cmmdc_lista_jumatati(l6) == 100)
    assert (cmmdc_lista_jumatati(l7) == 50)

def test_cmmdc_lista_v2():
    l1 = []
    l2 = [1, 2, 3]
    l3 = [2, 4, 6, 8]
    l4 = [36, 24, 48]
    l5 = [24, 48]
    l6 = [0, 100]
    l7 = [100, 250, 300]

    assert (cmmdc_lista(l1) is None)
    assert (cmmdc_lista(l2) == 1)
    assert (cmmdc_lista(l3) == 2)
    assert (cmmdc_lista(l4) == 12)
    assert (cmmdc_lista(l5) == 24)
    assert (cmmdc_lista(l6) == 100)
    assert (cmmdc_lista(l7) == 50)
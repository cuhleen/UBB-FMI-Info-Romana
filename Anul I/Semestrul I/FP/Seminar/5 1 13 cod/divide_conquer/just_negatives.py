def just_negatives(lst):
    if len(lst) == 0:
        return None
    if len(lst) == 1:
        return lst[0] < 0

    middle = len(lst) // 2
    return just_negatives(lst[:middle]) and just_negatives(lst[middle:])


def test_just_negatives():
    l1 = []
    l3 = [-1]
    l6 = [2]
    l7 = [0]
    l2 = [2, -3]
    l4 = [-1, -2, -3]
    l5 = [-18, -1, 29, 0]


    assert (just_negatives(l1) is None)
    assert (just_negatives(l2) == False)
    assert (just_negatives(l3) == True)
    assert (just_negatives(l4) == True)
    assert (just_negatives(l5) == False)
    assert (just_negatives(l6) == False)
    assert (just_negatives(l7) == False)

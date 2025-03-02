import math


def is_prime(x):
    if x < 2:
        return False
    if x == 2:
        return True
    if x % 2 == 0:
        return False
    for d in range(3, x // 2):
        if x % d == 0:
            return False
    return True


def max_prime(lst):
    if len(lst) == 0:
        return 0
    if len(lst) == 1:
        if is_prime(lst[0]):
            return lst[0]
        else:
            return 0
    middle = len(lst) // 2
    return max(max_prime(lst[:middle]), max_prime(lst[middle:]))


def min_prime(lst):
    if len(lst) == 0:
        return -1
    if len(lst) == 1:
        if is_prime(lst[0]):
            return lst[0]
        else:
            return math.inf

    if is_prime(lst[0]):
        return min(lst[0], min_prime(lst[1:]))
    else:
        return min_prime(lst[1:])


def test_max_prime():
    l1 = []
    l2 = [1]
    l3 = [1, 2]
    l4 = [3, 5, 7]
    l5 = [1, 1, 2, 3, 4, 5, 6, 100]
    l6 = [100, 102, 104]
    l7 = [17]

    assert (max_prime(l1) == 0)
    assert (max_prime(l2) == 0)
    assert (max_prime(l3) == 2)
    assert (max_prime(l4) == 7)
    assert (max_prime(l5) == 5)
    assert (max_prime(l6) == 0)
    assert (max_prime(l7) == 17)
    

def test_min_prime():
    l1 = []
    l2 = [1]
    l3 = [1, 2]
    l4 = [3, 5, 7]
    l5 = [1, 1, 2, 3, 4, 5, 6, 100]
    l6 = [100, 102, 104]
    l7 = [17]

    assert (min_prime(l1) == -1)
    assert (min_prime(l2) == math.inf)
    assert (min_prime(l3) == 2)
    assert (min_prime(l4) == 3)
    assert (min_prime(l5) == 2)
    assert (min_prime(l6) == math.inf)
    assert (min_prime(l7) == 17)
def number_of_occurrences(lst, x):
    if len(lst) == 1:
        if lst[0] == x:
            return 1
        else:
            return 0

    middle = len(lst) // 2
    return number_of_occurrences(lst[:middle], x) + number_of_occurrences(lst[middle:], x)


def test_number_of_occurrences():
    a1 = [1, 2, 3, 4, 1, 1, 1, 1, 4, 5, 8, 9, 8, 7]
    assert (number_of_occurrences(a1, 1) == 5)
    assert (number_of_occurrences(a1, 2) == 1)
    assert (number_of_occurrences(a1, 4) == 2)
    assert (number_of_occurrences(a1, 5) == 1)
    assert (number_of_occurrences(a1, 8) == 2)
    assert (number_of_occurrences(a1, 7) == 1)

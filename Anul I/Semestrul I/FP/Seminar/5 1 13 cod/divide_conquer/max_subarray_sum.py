import math


def max_subarray_sum_brute_force1(arr: list):
    steps = 0
    if len(arr) == 0:
        return 0, []
    max_sum = arr[0]
    max_subarray = [arr[0]]
    # n = number of values in array
    for i in range(len(arr)):  # 0..n-1
        for j in range(i + 1, len(arr) + 1):  # i+1...n

            crt_subarray = arr[i:j]
            # !! Python functions also incur complexity
            # sum-> len(crt_subarray) steps
            crt_sum = sum(crt_subarray)
            steps += len(crt_subarray)

            if crt_sum > max_sum:
                max_sum = crt_sum
                max_subarray = crt_subarray

    n = len(arr)
    #These instructions added to check result obtained
    #by calculating complexity on paper (with sums)
    sum_steps = int((n * (n + 1) * (n + 2)) / 6)
    assert (sum_steps == steps)
    print(len(arr), sum_steps, steps)
    return max_sum, max_subarray


def max_subarray_sum_brute_force2(arr: list):
    if len(arr) == 0:
        return 0
    max_sum = arr[0]
    for i in range(len(arr)):
        crt_sum = 0
        for j in range(i, len(arr)):
            crt_sum += arr[j]
            if crt_sum > max_sum:
                max_sum = crt_sum

    return max_sum


class EmptyListException(Exception):
    pass


def max_subarray_sum_dc(arr: list, left, right):
    if right == -1:
        raise EmptyListException()
    if left == right:
        return arr[left]

    middle = (left + right) // 2

    left_sum = max_subarray_sum_dc(arr, left, middle)
    right_sum = max_subarray_sum_dc(arr, middle + 1, right)

    max_left_crossing_sum = -math.inf
    left_crossing_sum = 0
    for i in range(middle, left - 1, -1):
        left_crossing_sum += arr[i]
        if left_crossing_sum > max_left_crossing_sum:
            max_left_crossing_sum = left_crossing_sum

    max_right_crossing_sum = -math.inf
    right_crossing_sum = 0
    for i in range(middle + 1, right + 1):
        right_crossing_sum += arr[i]
        if right_crossing_sum > max_right_crossing_sum:
            max_right_crossing_sum = right_crossing_sum

    max_crossing_sum = max_left_crossing_sum + max_right_crossing_sum

    return max(left_sum, right_sum, max_crossing_sum)


def test_max_subarray_sum():
    test_cases = [
        ([-2, 1, -3, 4, -1, 2, 1, -5, 4], 6),
        ([-1, -2, -3, -4], -1),
        ([1, 2, 3, 4], 10),
        ([5], 5),
        ([8, -1, -2, 4, -6, 12], 15),
        ([-2, -5, 3, -8, -1, 21, 20, -25, 4, -2, 6, -1, 2, 5, -3], 41),
    ]

    for arr, expected_output in test_cases:
        print(arr)
        result = max_subarray_sum_dc(arr, 0, len(arr) - 1)
        assert result == expected_output

    try:
        arr = []
        result = max_subarray_sum_dc(arr, 0, len(arr) - 1)
        assert False
    except EmptyListException:
        assert True

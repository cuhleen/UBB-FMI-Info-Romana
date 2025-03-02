import copy
import random


def partition(lst, l, r):
    pivot = lst[r]
    i = l - 1

    for j in range(l, r):
        if lst[j] < pivot:
            i += 1
            lst[i], lst[j] = lst[j], lst[i]

    lst[i + 1], lst[r] = lst[r], lst[i + 1]
    return i + 1


def kth_smallest_element(lst, left, right, k):
    if left == right:
        return lst[left]

    pos = partition(lst, left, right)
    i = pos - left + 1
    if i == k:
        return lst[pos]
    elif i > k:
        return kth_smallest_element(lst, left, pos - 1, k)
    else:
        return kth_smallest_element(lst, pos + 1, right, k - i)


def test_kth_smallest_elem():
    for i in range(3, 100):
        my_list = [random.randint(1, 100) for _ in range(i)]
        #print("Current list:", my_list)
        sorted_list = sorted(my_list)
        for j in range(10):
            random.shuffle(my_list)
            k = random.randint(1, len(my_list))
            # print(i,k)
            smallest = kth_smallest_element(my_list, 0, len(my_list) - 1, k)
            assert (smallest == sorted_list[k - 1])

def print_max_sublist(initial_list, P, max_sublist_start_index):
    i = max_sublist_start_index
    while i != -1:
        print(initial_list[i], end=" ")
        i = P[i]


def solve(initial_list):
    # curs, material seminar: semnificatie/continut L, P
    L = [1] * len(initial_list)
    P = [-1] * len(initial_list)

    n = len(initial_list)
    for i in range(n - 2, -1, -1):
        for j in range(i + 1, n):
            if initial_list[i] <= initial_list[j] and L[j] + 1 > L[i]:
                L[i] = L[j] + 1
                P[i] = j

    max_length = -1
    max_sublist_start_index = -1
    for i in range(n):
        if L[i] > max_length:
            max_length = L[i]
            max_sublist_start_index = i

    return max_length, max_sublist_start_index, P


a = [1, -2, 3, 2, 4, 4]
max_length, start_index, positions = solve(a)
print("Lista initiala", a)
print("Cea mai lunga sublista crescatoare are lungimea = " + str(max_length) + " si este:")
print_max_sublist(a, positions, start_index)

b = [1, 2, 3, 1, 5, 3, 4, 1, 4]
max_length, start_index, positions = solve(b)
print("Lista initiala", b)
print("Cea mai lunga sublista crescatoare are lungimea = " + str(max_length) + " si este:")
print_max_sublist(b, positions, start_index)


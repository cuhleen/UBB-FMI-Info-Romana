def solve(prices):
    N = len(prices)
    profits = [0] * N
    profits[1] = prices[1]
    for i in range(2, N):
        for j in range(i + 1):
            print("i =", i, "| j =", j, "| i - j =", i - j)
            if prices[j] + profits[i - j] > profits[i]:
                profits[i] = prices[j] + profits[i - j]
        print("=="*14)

    print(profits)
    return profits[N - 1]


# prices[i] = pretul bucatii de lungime i
prices1 = [0, 1, 5, 8, 9, 10, 17, 17, 20]
max_profit1 = solve(prices1)

prices2 = [0, 3, 5, 8, 9, 10, 17, 17, 20]
max_profit2 = solve(prices2)

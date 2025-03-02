class Item:
    def __init__(self, w, p):
        self.__weight = w
        self.__profit = p

    @property
    def weight(self):
        return self.__weight

    @property
    def profit(self):
        return self.__profit

    @property
    def profit_per_unit(self):
        return self.__profit / self.__weight

    def __str__(self):
        return "Item: w = " + str(self.__weight) + " and p = " + str(self.__profit)


def is_solution(b, w):
    tw = 0
    for item, fraction in b:
        tw += item.weight * fraction
    return tw == w


def select_most_promising(candidates):
    return candidates[-1]


def greedy(candidates, total_available_weight):
    b = []
    profit = 0
    remaining_weight = total_available_weight
    while not is_solution(b, total_available_weight) and len(candidates) > 0:
        candidate = select_most_promising(candidates)
        candidates.pop()
        if candidate.weight <= remaining_weight:
            b.append((candidate, 1))
            remaining_weight -= candidate.weight
            profit += candidate.profit
        else:
            fraction = remaining_weight / candidate.weight
            b.append((candidate, fraction))
            # profit += candidate.profit_per_unit * remaining_weight
            profit += fraction * candidate.profit
    return b, profit


W1 = 20
W2 = 50
W3 = 10
items1 = [Item(5, 10), Item(4, 20), Item(4, 10), Item(8, 10), Item(10, 22)]
# items2 = [Item(10,60), Item(20,100), Item(30,120)]
# items3 = [Item(30, 500)]
sorted_items = sorted(items1, key=lambda x: x.profit_per_unit)
b, max_profit = greedy(sorted_items, W1)
print(max_profit)
for item, fraction in b:
    print(item, fraction)

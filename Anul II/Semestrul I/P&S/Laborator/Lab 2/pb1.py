from random import randint
from matplotlib.pyplot import plot, grid, title, show

# a

def sameBirthdaySimulations(n, nsims):
    count = 0

    for _ in range(nsims):
        birthdays = []
        for i in range(1, n):
            birthdays.append(randint(1, 365))

        if len(birthdays) != len(set(birthdays)):
            count += 1

    return count/nsims

# b

# Considerăm evenimentul contrar
# pt. pers. 1 avem 365 posibilități
# pt. pers. 2 avem 364
# ...
# pt. pers. n avem 365 - n + 1 sau 0 dacă n e mai mare ca 365

def b(n):
    probContrara = 1

    if n < 365:
        for i in range(n):
            probContrara *= ((365 - i + 1) / 365)

        return 1 - probContrara
    else:
        return 1

print(b(10))

# c

n = range(2, 51)
yn = [sameBirthdaySimulations(x, 10000) for x in n]
plot(n, yn, 'r*')
grid()
show()

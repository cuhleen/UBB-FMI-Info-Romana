from random import random
from math import dist, pi

from matplotlib.pyplot import plot, grid, title, show, axis

def punctePatrat(n, cerinta):
    axis('square')
    axis((0,1,0,1))
    A, B, C, D, E = [0,0], [1, 0], [1, 1], [0, 1], [0.5, 0.5]
    count = 0

    match(cerinta):
        case 1:
            for _ in range(n):
                F = [random(), random()]
                if dist(E, F) < 0.5:
                    count += 1
                    plot(F[0], F[1], 'r*')
                else:
                    plot(F[0], F[1], 'bo')
            print("Probabilitate obtinuta: " + str(count/n))
            print("Probabilitate asteptata: " + str(pi/4))
        case 2:
            for _ in range(n):
                F = [random(), random()]
                if dist(E, F) < min(dist(F, A), dist(F, B), dist(F, C), dist(F, D)):
                    count += 1
                    plot(F[0], F[1], 'r*')
                else:
                    plot(F[0], F[1], 'bo')
            print("Probabilitate obtinuta: " + str(count / n))
            print("Probabilitate asteptata: " + str(1/2))
        case 3:
            for _ in range(n):
                F = [random(), random()]
                DF = dist(D, F)
                AF = dist(A, F)
                FB = dist(B, F)
                CF = dist(C, F)
                triObtuze = (DF*DF + AF*AF < 1) + (AF*AF + FB*FB < 1) + (FB*FB + CF*CF < 1) + (CF*CF + DF*DF < 1)
                if triObtuze == 2:
                    count += 1
                    plot(F[0], F[1], 'r*')
            print("Probabilitate obtinuta: " + str(count / n))
            print("Probabilitate asteptata: " + str(pi / 2 - 1))


punctePatrat(10000, 2)
show()
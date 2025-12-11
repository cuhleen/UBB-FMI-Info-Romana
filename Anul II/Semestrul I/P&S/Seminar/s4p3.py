# Ce valoare teoretica estimeaza programul urmator? Calculati valoarea teoretica corespunzatoare

import numpy as np
N=2000
S = np.concatenate((np.zeros(50),np.ones(70),2*np.ones(80)))
X=[]
for _ in range(N):
    k=0
    i= np.random.randint(len(S))
    while S[i] != 0:
        i= np.random.randint(len(S))
        k=k+1
    X.append(k)
print("Programul estimeaza media numarului de alegeri aleatorii care NU sunt 0 pana cand este selectat primul 0:", np.mean(X))

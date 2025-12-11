from scipy.stats import bernoulli, binom, geom, hypergeom
from matplotlib.pyplot import bar, show, hist, grid, legend, xticks
from math import comb

# 1

def pb1(nrSimulari=1000, nrPasi=10):
    rez = []
    for _ in range(nrSimulari):
        deplasari = bernoulli.rvs(0.5, size=nrPasi)
        pasi = [0]
        for pas in deplasari:
            step = int(2 * pas - 1)
            pasi.append(step + pasi[-1])
        rez.append(pasi[-1])

    # Histograma simularilor
    bin_edges = [k + 0.5 for k in range(-nrPasi, nrPasi + 1)]
    hist(rez, bins=bin_edges, density=True, rwidth=0.9,
             color='royalblue', alpha=0.8, label='Simulări')

    # Probabilitati teoretice
    p_teoretic = [comb(nrPasi, (nrPasi + x) // 2) * (0.5) ** nrPasi if (nrPasi + x) % 2 == 0 else 0
                  for x in range(-nrPasi, nrPasi + 1)]

    bar(range(-nrPasi, nrPasi + 1), p_teoretic,
            color='limegreen', alpha=0.5, width=0.4, label='Probabilități teoretice')
    legend()
    show()

pb1()

def pb1Cerc(nrSimulari=10000, nrPasi=10, n=5):
    rez = []
    for _ in range(nrSimulari):
        deplasari = bernoulli.rvs(0.5, size=nrPasi)
        poz = 0
        for pas in deplasari:
            step = int(2 * pas - 1)
            poz = (poz + step) % n
        rez.append(poz)

    # Histograma
    bin_edges = [k + 0.5 for k in range(-1, n)]
    hist(rez, bins=bin_edges, density=True, rwidth=0.9,
             color='royalblue', alpha=0.8, label='Simulări')

    # Probabilități teoretice
    # Formula de plimbare mod n: probabilitatea de a ajunge în poziția r = (nrPasi + 2k - nrPasi) % n
    p_teoretic = [0] * n
    for x in range(-nrPasi, nrPasi + 1, 2):
        if (nrPasi + x) % 2 == 0:
            p = comb(nrPasi, (nrPasi + x)//2) * (0.5)**nrPasi
            p_teoretic[x % n] += p  # adunăm probabilitățile pentru toate pozițiile echivalente mod n

    bar(range(n), p_teoretic, color='limegreen', alpha=0.5, width=0.9, label='Probabilități teoretice')

    legend()
    show()

pb1Cerc()

# 2

def ex2():
    probabilitate = 0
    for k in range(3, 7):
        probabilitate += hypergeom.pmf(k, 49, 6, 6)
    list = geom.rvs(probabilitate, size=1000)
    print(list)

    # cel putin 10 bilete succesive sunt necastigatoare
    # pana cand jucatorul nimereste un bilet castigator
    valoriAdecvate = 0
    for value in list:
        if value >= 10:
            valoriAdecvate += 1
    print("Probabilitate din simulari: ", valoriAdecvate / 1000)

    theoreticalProbability = 1 - geom.cdf(9, probabilitate)
    print("Probabilitate teoretica: ", theoreticalProbability)

ex2()





















import numpy as np
from scipy.stats import uniform, expon
import matplotlib.pyplot as plt

def ex2(noOfSimulations):
    # Parametrii distribuției exponențiale
    alpha = 1 / 12

    # Generare numere U ~ Unif(0,1)
    U = uniform.rvs(size=noOfSimulations)

    # Formula F^{-1}(u) = -(1/alpha) * ln(1 - u)
    data = -1 / alpha * np.log(1 - U)

    # Histogramă
    plt.hist(data, bins=12, density=True, range=(0, 60), alpha=0.6, label="Histogramă simulată")

    # Densitatea teoretică pentru Exp(alpha)
    # Formula f(x) = alpha * e^{-alpha x},  x >= 0
    x = np.linspace(0, 60, 300)
    plt.plot(x, expon.pdf(x, loc=0, scale=1 / alpha), 'r--', label="Densitate teoretică")

    plt.xticks(range(0, 61, 5))
    plt.xlabel("Timp (secunde)")
    plt.ylabel("Densitate")
    plt.title("Simulare Exp(1/12) cu metoda inversării")
    plt.grid(True, linestyle="--", alpha=0.5)
    plt.legend()
    plt.show()

    # Estimare probabilitate P(T >= 5)
    k = 5

    # Probabilitate teoretică
    theoreticalProb = 1 - expon.cdf(k, loc=0, scale=1 / alpha)

    # Probabilitate simulată
    simulatedProb = np.mean(data >= k)

    print(f"Probabilitatea teoretică: P(T >= 5) = {theoreticalProb:.4f}")
    print(f"Probabilitatea simulată: P(T >= 5) = {simulatedProb:.4f}")


if __name__ == '__main__':
    ex2(noOfSimulations = 10000)

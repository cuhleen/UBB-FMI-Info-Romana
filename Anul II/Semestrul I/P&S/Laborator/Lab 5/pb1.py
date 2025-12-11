import numpy as np
import matplotlib.pyplot as plt

def ex1(values, probabilities, no_of_simulations):
    values = np.array(values)
    probabilities = np.array(probabilities, dtype=float)

    # Generare numere
    rng = np.random.default_rng()
    u = rng.random(no_of_simulations)

    # Probabilități cumulative
    cumulative = np.cumsum(probabilities)

    # Variabile discrete cu metoda inversării
    indices = np.searchsorted(cumulative, u, side='right')
    simulated = values[indices]

    # Calculăm frecvențele observate
    observed_counts = np.array([(simulated == v).sum() for v in values])
    observed_freq = observed_counts / no_of_simulations

    # Afișare
    print("Valori simulate (primele 20):", simulated[:20])
    print("\nComparație frecvențe:")
    for i, v in enumerate(values):
        print(f"Valoarea {v}: Teoretică = {probabilities[i]:.3f}, Observată = {observed_freq[i]:.3f}")

    plt.bar(values, observed_freq, label="Observată", alpha=0.7)
    plt.bar(values, probabilities, label="Teoretică", alpha=0.7)

    plt.xlabel('Valoare')
    plt.ylabel('Frecvență / Probabilitate')
    plt.legend()
    plt.grid(True, linestyle='--', linewidth=0.5)
    plt.title(f'Simulare: N={no_of_simulations}')

    plt.show()


if __name__ == '__main__':
    ex1([0, 1, 2, 3], [0.46, 0.40, 0.10, 0.04], no_of_simulations=1000)
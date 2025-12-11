from scipy.stats import binom
from matplotlib.pyplot import bar, hist, grid, show, legend

n = 5
p = 0.6
X = binom.rvs(n, p, size=1000)

# subpunct A

print(X)

# subpunct B

bin_edges = [k+0.5 for k in range(0, 6)]
hist(X, bin_edges, density = True, rwidth = 0.9, color = 'green', edgecolor = 'black',
alpha = 0.5, label = 'frecvente relative')

distribution = [binom.pmf(0, n, p), binom.pmf(1, n, p), binom.pmf(2, n, p), binom.pmf(3, n, p), binom.pmf(4, n, p), binom.pmf(5, n, p)]
bar([0,1,2,3,4,5], distribution, width = 0.85, color = 'red', edgecolor = 'black',
alpha= 0.6, label = 'probabilitati')

legend(loc = 'lower left')
grid()
show()

# subpunct C

probTeoretica = binom.pmf(3, n, p) + binom.pmf(4, n, p) + binom.pmf(5, n, p)
print("Probabilitatea teoretica: " + str(probTeoretica))

cazFavorabil = 0
for i in range(1000):
    if 2 < X[i] <= 5:
        cazFavorabil += 1
probEstimata = cazFavorabil/1000

print("Probabilitatea esitmata: " + str(probEstimata))
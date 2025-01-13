"""
Se dau două numere naturale m şi n. Generaţi liste formate din numere de la 1 la n cu
proprietatea că diferenţa (în valoare absolută) între două numere consecutive din listă este
m. Tipăriţi un mesaj dacă problema nu are soluţie.
"""


class Console:

    def recursiv(self, m, n):
        def backtrack(s, ultimulNr):
            if ultimulNr + m > n:
                if "," in s:
                    results.append(s)
                return

            backtrack(s + ", " + str(ultimulNr + m), ultimulNr + m)

        results = []
        for i in range(1, n):
            backtrack(str(i), i)
        return results

    def iterativ(self, m, n):
        results = []
        for i in range(1, n):
            lista = []
            lista.append(i)
            for j in range(i, n + 1):
                if j - lista[len(lista) - 1] == m:
                    lista.append(j)
            if len(lista) > 1:
                results.append(lista)

        return results

    def run(self):
        while True:
            m = int(input("Introduceți m: "))
            n = int(input("Introduceți n: "))

            if m > n - 1:
                print("Nu are soluție")
            else:
                print("\nRezolvare recursivă:")
                solRec = self.recursiv(m, n)
                for sol in solRec:
                    print(sol)

                print("\nRezolvare iterativă:")
                solIt = self.iterativ(m, n)
                for sol in solIt:
                    print(sol)
                print("\n")


console = Console()
console.run()

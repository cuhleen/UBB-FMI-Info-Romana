"""
Pentru un n dat generați toate secvențele de paranteze care se închid corect.
Exemplu: n = 4, două soluții: (()) și ()()
"""

class Console:

    def recursiv(self, n):
        def backtrack(s, nrDeschise, nrInchise):
            if len(s) == n:
                results.append(s)
                return

            if nrDeschise < n // 2:
                backtrack(s + "(", nrDeschise + 1, nrInchise)

            if nrInchise < nrDeschise:
                backtrack(s + ")", nrDeschise, nrInchise + 1)

        results = []
        backtrack("", 0, 0)
        return results



    def iterativ(self, n):
        stack = [("", 0, 0)]
        # Ghilimelele sunt parantezele. Primul număr este numărul de paranteze deschise. Al doilea număr este numărul de paranteze închise
        results = []

        while stack:
            s, nrDeschise, nrInchise = stack.pop()
            if len(s) == n:
                results.append(s)
                
            if nrDeschise < n // 2:
                stack.append((s + "(", nrDeschise + 1, nrInchise))
            if nrInchise < nrDeschise:
                stack.append((s + ")", nrDeschise, nrInchise + 1))

        return results



    def run(self):
        while True:
            n = 1
            while n % 2 == 1:
                n = int(input("Introduceți numărul de paranteze (trebuie să fie un număr par): "))

            print("\nRezolvare recursivă:")
            solRec = self.recursiv(n)
            for sol in solRec:
                print(sol)
            print("\n")

            print("\nRezolvare iterativă:")
            solIt = self.iterativ(n)
            for sol in solIt:
                print(sol)
            print("\n")



console = Console()
console.run()

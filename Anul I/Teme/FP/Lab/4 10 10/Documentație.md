## Lab 3 12
Se citește o listă de elemente
Să se verifice dacă oricare două elemente consecutive au semnul diferit
## Lab 3 16
Se citește o listă de elemente
Să se verifice dacă toate elementele folosesc aceleași cifre

## Aplicație care citește o listă și afișează secvența de lungime maximă ce urmează una dintre cele două reguli de mai sus

<hr>

# Scenarii de rulare

|        | **Utilizator**                      | **Program**                       | **Descriere**                                                                                                  |
| ------ | ----------------------------------- | --------------------------------- | -------------------------------------------------------------------------------------------------------------- |
| **1**  |                                     | `citireMenu()`                    | Afișează meniul și așteaptă input de la utilizator                                                             |
| **2**  | 1                                   |                                   | Citire listă                                                                                                   |
| **3**  |                                     | `citireArr()`                     | Lasă utilizatorul să introducă elementele unei liste, pe care se vor face ulterior cerințele                   |
| **4**  | 6                                   | n = 10                            | Lista are 10 elemente                                                                                          |
| **5**  | 1<br>-23<br>-323<br>2233<br>-2<br>3 | arr = [1, -23, -323, 2233, -2, 3] | Elementele listei sunt 1, -23, -323, 2233, -2, 3                                                               |
| **6**  |                                     | `citireMenu()`                    | Afișează meniul și așteaptă input de la utilizator                                                             |
| **7**  | 2                                   |                                   | Afișarea secvenței de lungime maximă cu proprietatea ca oricare două numere consecutive să aibă semnul diferit |
| **8**  |                                     | `semnDif()`                       | Apelează funcția corespunzătoare                                                                               |
| **9**  |                                     | -323 2233 -2 3                    | Afișează pe ecran secvența de lungime maximă pentru cerința dorită                                             |
| **10** |                                     | `citireMenu()`                    | Afișează meniul și așteaptă input de la utilizator                                                             |
| **11** | 3                                   |                                   | Afișarea secvenței de lungime maximă cu proprietatea ca toate elementele să fie formate din aceleași cifre     |
| **12** |                                     | `aceleasiCif()`                   | Apelează funcția corespunzătoare                                                                               |
| **13** |                                     | -23 -323 2233                     | Afișează pe ecran secvența de lungime maximă pentru cerința dorită                                             |
| **14** |                                     | `citireMenu()`                    | Afișează meniul și așteaptă input de la utilizator                                                             |
| **15** | 4                                   |                                   | Ieșire din aplicație                                                                                           |
| **16** |                                     | `exit()`                          | Aplicatia se închide                                                                                           |

# Cazuri de testare

| **n** | **arr**                                                  | **Rezultate 12**                  | **Rezultate 16**                              |
| ----- | -------------------------------------------------------- | --------------------------------- | --------------------------------------------- |
| -3    |                                                          | Recitire a lui n, date inadecvate | Recitire a lui n, date inadecvate             |
| 0     | []                                                       | Mesaj coresupnzător               | Mesaj coresupnzător                           |
| 1     | [1]                                                      | 1                                 | 1                                             |
| 2     | [1, -1]                                                  | 1 -1                              | 1 -1                                          |
| 6     | [1, 2, -3, 4, -5, -6]                                    | 2 -3 4 -5                         | Nu există                                     |
| 6     | [1, 123, 23, 32, 3322, 3]                                | Nu există                         | 23 32 3322                                    |
| 10    | [1, -2, 3, -4, 5, -6, 7, -8, 9, -10]                     | 1 -2 3 -4 5 -6 7 -8 9 -10         | Nu există                                     |
| 10    | [123, 132, 213, 231, 312, 321, 1233, 1223, 1123, 112233] | Nu există                         | 123 132 213 231 312 321 1233 1223 1123 112233 |

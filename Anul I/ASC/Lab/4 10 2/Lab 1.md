https://www.cs.ubbcluj.ro/~vancea/asc/ro-lab1-teorie.php
___

# Din baza 16 in baza 2, rapid

ex: 347<sub>(10)</sub> = 15B<sub>(16)</sub> = 000101011011<sub>(2)</sub> 
Luăm fiecare "cifră" din reprezentarea în B16, o convertim cu 4 cifre în baza 2

1<sub>(16)</sub> -> 0001<sub>(2)</sub>
5<sub>(16)</sub> -> 0101<sub>(2)</sub>
B<sub>(16)</sub> -> 11<sub>(10)</sub> ->1011<sub>(2)</sub>

# Complementul

**Ordonate de la cea mai rapidă metodă la cea mai lentă**

## *(B10)* 2<sup>n</sup> minus număr unde n sunt nr. de biți
Dacă nu ne interesează valoarea în baza 2
**Definiție formală:** Suma valorilor absolute a celor 2 valori complementare este cardinalul mulțimii de valori reprezentabile pe acea dimensiune
*A.K.A* [asta](<Lab 1#*(B2/B16)* Se scade conținutul locației din 100...00, unde numărul zerourilor este numărul "cifrelor" din reprezentarea în baza 16/2>) *dar în baza 10*

## *(B2)* Inversarea biților de după primul bit de 1 din șir
### Exemplu
18<sub>(10)</sub> = 0001 0010<sub>(2)</sub>
==De la dreapta la stânga copiem biții până la primul 1 **inclusiv**==
10<sub>(2)</sub>
==Restul biților îi scriem inversați **(1 scriem 0, 0 scriem 1)**==
1110 11*10*<sub>(2)</sub>
(Secțiunea în *italic* reprezintă biții neschimbați din numărul original)

## *(B2/B16)* Se scade conținutul locației din 100...00, unde numărul zerourilor este numărul "cifrelor" din reprezentarea în baza 16/2

### Exemplu
18<sub>(10)</sub> = 12<sub>(16)</sub>
==12 este format din 2 "cifre", așa că îl scădem dintr-un număr care este format din 2 zerouri și un unu, în baza 16==

100<sub>(16)</sub>- 12<sub>(16)</sub> = EE<sub>(16)</sub>

## *(B2)* Inversare și adunare cu 1
### Exemplu
18<sub>(10)</sub> = 0001 0010<sub>(2)</sub>
==Toate numerele de 1 le scriem ca și 0 și vice versa==
0001 0010<sub>(2)</sub> -> 1110 1101<sub>(2)</sub>
==Adăugăm 1 la invers==
 1110 1101<sub>(2)</sub> + 0000 0001<sub>(2)</sub> = 1110 1110<sub>(2)</sub>

___

# [Exerciții](https://www.cs.ubbcluj.ro/~vancea/asc/ro-lab1-exercitii.php)
1. **Convertiți din baza 10 în 2 și apoi în 16 următoarele numere:**

4<sub>(10)</sub> -> 100<sub>(2)</sub> -> 4<sub>(16)</sub>
10<sub>(10)</sub> -> 1010<sub>(2)</sub> -> A<sub>(16)</sub>
15<sub>(10)</sub> -> 1111<sub>(2)</sub> -> F<sub>(16)</sub>
32<sub>(10)</sub> -> 100000<sub>(2)</sub> -> 20<sub>(16)</sub>

2. **Convertiți din baza 10 în 16 și apoi în 2 următoarele numere:**

3<sub>(10)</sub> -> 3<sub>(16)</sub> -> 11<sub>(2)</sub>
11<sub>(10)</sub> -> B<sub>(16)</sub> -> 1011<sub>(2)</sub>
16<sub>(10)</sub> -> 10<sub>(16)</sub> -> 10000<sub>(2)</sub>
17<sub>(10)</sub> -> 11<sub>(16)</sub> -> 10001<sub>(2)</sub>

3. **Convertiți din baza 2 în baza 16 următoarele numere:**

1010<sub>(2)</sub> -> A<sub>(16)</sub>
0111<sub>(2)</sub> -> 7<sub>(16)</sub>
1111<sub>(2)</sub> -> F<sub>(16)</sub>
10001010<sub>(2)</sub> -> 8A<sub>(16)</sub>
110101111<sub>(2)</sub> -> 2B<sub>(16)</sub>

4. **Convertiți din baza 16 în baza 2 următoarele numere:**

3<sub>(16)</sub> -> 11<sub>(2)</sub>
A<sub>(16)</sub> -> 1010<sub>(2)</sub>
F<sub>(16)</sub> -> 1111<sub>(2)</sub>
2B<sub>(16)</sub> -> 00101011<sub>(2)</sub>
2F8<sub>(16)</sub> -> 001011111000<sub>(2)</sub>

5. **Efectuați următoarele operații în ==baza 2== (fără a converti în baza 10):**

1 + 1 = 10
10 + 10 = 100
111 + 1 = 1000
1010 - 1 = 1001
1000 - 10 = 110

6. **Efectuați următoarele operații în ==baza 16== (fără a converti în baza 10):**

9 + 1 = A
B + 2 = D
F + 1 = 10
10 + A = 1A
10 - 2 = E
B - 3 = 9

7. **Verificați, folosind cel puțin două dintre regulile de complementaritate, dacă:**

- *Într-o locaţie de 2 octeţi numerele 9A7D<sub>(16)</sub> și 7583<sub>(16)</sub> sunt complementare.*
##### Met. 1
9A7D<sub>(16)</sub> = 1001 ==1010 0111== 1101<sub>(2)</sub>
7583<sub>(16)</sub> = 0111 ==0101 1000== 0011<sub>(2)</sub>

DA

##### Met. 2



- *Într-o locație de 4 octeți numerele 000F095D<sub>(16)</sub> și FFF0F6A3<sub>(16)</sub> sunt complementare*
##### Met. 1
==000F==095D
==FFF0==F6A3

DA

- *Într-o locație de 2 octeți numerele 4BA1<sub>(16)</sub>  și 5C93<sub>(16)</sub>  sunt complementare*
##### Met. 1
4BA1<sub>(16)</sub> = 0100 1011 1010 0001<sub>(2)</sub>
5C93<sub>(16)</sub> = 0101 1100 1001 0011<sub>(2)</sub>

NU

##### Met. 2



- *Într-o locație de 1 octet numerele 7F<sub>(16)</sub> și 81<sub>(16)</sub> sunt complementare*
##### Met. 1
7F<sub>(16)</sub> = ==0111== 1111<sub>(2)</sub>
81<sub>(16)</sub> = ==1000== 0001<sub>(2)</sub>

DA

##### Met. 2



- *Într-o locatie de 2 octeți numerele 732A<sub>(16)</sub>  și 4E58<sub>(16)</sub>  sunt complementare*
##### Met. 1
732A<sub>(16)</sub> = 0111 0011 0010 1010<sub>(2)</sub>
4E58<sub>(16)</sub> = 0100 1110 0101 1000<sub>(2)</sub>

NU

##### Met. 2


8. **Să se scrie reprezentarea fără semn pe 8 biți a următoarelor numere:**

8<sub>(10)</sub> -> 0000 1000<sub>(2)</sub>
8
2<sup>3</sup>

67<sub>(10)</sub> -> 0100 0011<sub>(2)</sub>
64 + 2 + 1
2<sup>6</sup> + 2<sup>1</sup> + 2<sup>0</sup>

230<sub>(10)</sub> -> 1110 0110<sub>(2)</sub>
128 + 64 + 32 + 4 + 2
2<sup>7</sup> + 2<sup>6</sup> + 2<sup>5</sup> + 2<sup>2</sup> + 2<sup>1</sup>

9. **Să se scrie reprezentarea cu semn pe 16 biți a următoarelor numere:**

#### -6<sub>(10)</sub> -> 1111 1010<sub>(2)</sub>
6<sub>(10)</sub> -> 0000 0110<sub>(2)</sub>

#### -121<sub>(10)</sub> -> 1111 1000 0111<sub>(2)</sub>
121<sub>(10)</sub> -> 0000 0111 1001<sub>(2)</sub>
64 + 32 + 16 + 8 + 1
2<sup>6</sup> + 2<sup>5</sup> + 2<sup>4</sup> + 2<sup>0</sup>

#### 70<sub>(10)</sub> -> 0100 0111<sub>(2)</sub>
64 + 4  + 2
2<sup>6</sup> + 2<sup>2</sup> + 2<sup>1</sup>


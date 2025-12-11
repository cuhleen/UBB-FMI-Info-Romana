
# **1. Principiul fundamental de numărare**
Numărul de perechi de obiecte în care primul obiect poate fi ales în *m* moduri și al doilea în *n* moduri este `m * n` (unde *m* și *n* sunt numere naturale)
#### *Exemplu*
În câte moduri poate o persoană să îmbrace 2 cămăși distincte și 3 blugi?
#### *Răspuns*
`2 * 3 = 6`

# **2. Aranjamente de n luate câte k**
(cu *n* și *k* numere naturale, *n* > 0, *k* <= *n*)

În câte moduri putem alege *k* obiecte distincte și ordonate din *n* obiecte date?
A<sup>k</sup><sub>n</sub> = `n!/(n - k)!`
#### *Exemplu*
Câte coduri cu 3 cifre distincte se pot forma folosind cifrele 0, 1, 2, 3, 4?
#### *Răspuns*
A<sup>3</sup><sub>5</sub> = `5!/2!` = 60

# **3. Permutări de n**
(*n* este un număr natural)
Aranjamente de n luate câte n
P<sub>n</sub> = n!
#### *Exemplu*
În câte moduri de pot aranja 4 persoane pe o bancă?
#### *Răspuns*
4! = 24

# **4. Combinări de n luate câte k**
(cu *n* și *k* numere naturale, *n* > 0, *k* <= *n*)

În câte moduri pot alege *k* obiecte distincte și neordonate din *n* obiecte distincte date
(A.K.A. nr de submulțimi cu *k* elemente ale unei mulțimi cu *n* elemente)
C<sup>k</sup><sub>n</sub> = `n!/((n-k)!*k!)`
C<sup>k</sup><sub>n</sub> = C<sup>n-k</sup><sub>n</sub>
#### *Exemplu*
Câte echipe de handbal se pot forma dintr-un grup de 9 persoane?
#### *Răspuns*
C<sup>7</sup><sub>9</sub> = `9!/(2!*7!)` = 36

# **5. Numărul de funcții f: A -> B**
card(A) = *k*
card(B) = *n*
|A| = *k*
|B| = *n*
*k* și *n* sunt numere naturale nenule

n<sup>k</sup>

#### *Observație*
Funcțiile pot fi primite ca niște aranjamente cu repetiții, adică k alegeride obiecte nu neapărat distincte (un obiect poate fi ales de mai multe ori), dar ordonate din n obiecte date

*k* = 2, *n* = 4
f{1, 2} -> {a, b, c, d}
f(1) = a
f(2) = a

f(1) = a
f(2) = b
*ESTE DIFERIT DE*
f(1) = b
f(2) = a

# **6. Permutări cu repetiții**
Considerăm *n* obiecte care pot fi împărțite în *k* grupuri (*n* și *k* sunt numere naturale nenule, *k* <= *n*) astfel:
Primul grup conține n<sub>1</sub> obiecte identice, al doilea grup n<sub>2</sub> obiecte identice, ..., al k-lea grup conține n<sub>k</sub> obiecte identice (n<sub>1</sub>, ..., n<sub>k</sub> aparțin numerelor naturale, n<sub>1</sub> + ... + n<sub>k</sub> = n)
Două obiecte alese arbitrar sunt diferite <=> ele provin din grupuri diferite
Nr de permutări ale acestor obiecte este: `n!/(n1! * n2! * ... * nk!)`

#### *Exemplu*
1) Într-o urnă sunt 3 bile albe și 4 bile roșii. În câte moduri se pot aranja aceste bile într-un rând?
`7!/(3! * 4!)` = 35
2) Câte anagrame ale cuvântului "Mississippi" există?
1 m, 4 i, 4 s, 2 p
`11!/(1! * 4! * 4! * 2!)`

# **7. Combinări cu repetiții de n luate câte k**
(*n* aparține numerelor naturale nenule, *k* aparține numerelor naturale)

Alege de *k* obiecte, nu neapărat distincte (un obiect poate fi ales de mai multe ori) neordonate din *n* obiecte distincte

C<sup>k</sup><sub>n+k-1</sub> = `(n+k-1)! / ((n-1)! * k!)`

#### *Exemplu*
1) În câte moduri se pot împărți 5 bile la 3 copii?
*n* = 3
*k* = 5
#### *Răspuns*
C<sup>5</sup><sub>7</sub>

| c1  | c2    | c3  |
| --- | ----- | --- |
| oo  | o     | oo  |
| ooo | oo    |     |
|     | ooooo |     |
| ... | ...   | ... |

Scris altfel, unde 0 sunt bile iar 1 sunt separatori
`0010100`
`0001001`
`1000001`
Refrazând întrebarea, în câte moduri pot distribui 5 cifre de 0 pe 7 poziții ( = C<sup>5</sup><sub>7</sub>)

2) O cofetărie are 7 sortimente de înghețată. În câte moduri poate un client să își aleagă sortimentele pentru 3 cornete, fiecare având câte un glob
*n* = 7
*k* = 3
C<sup>3</sup><sub>9</sub>

# **Probleme**
![[Pasted image 20250930101217.png]]

a)
(m1, m2, m3, m4, m5) (i1, i2, i3) (r1, r2, r3, r4)

*5! * 3! * 4! * 3!*

b)
(m1, ..., m5, i1, ..., i3, R) (r1, r2, r3, r4)

*9! * 4!*

c) 
(M, I, r1, ..., r4) (m1, ..., m5) (i1, i2, i3)
*6! * 5! * 3!*

![[Pasted image 20250930102248.png]]

În linie `9! / (3! * 2! * 3! * 1!)` permutări cu repetiție
Pot fi formate ^ cercuri, însă multe se repetă. Ce vrem sunt cercuri distincte, iar cel mai simplu mod e să luăm cercuri care încep cu același caracter. Adică
`(9! / (3! * 2! * 3! * 1!)) / 9`

![[Pasted image 20250930103518.png]]
Avem 7 scaune mereu libere
s1 s2 s3 s4 s5 s6 s7
Pentru a avea minim un scaun liber între fiecare 2 persoane, singurele poziții pe care putem pune câte o persoană este între acestea
_ s1 _ s2 _ s3 _ s4 _ s5 _ s6 _ s7 _
8 poziții posibile unde pot să stea 5 oameni

A<sup>5</sup><sub>8</sub>

![[Pasted image 20250930104237.png]]

Luăm un caz
n = 5
k = 3

x1 = 1
x2 = 2
x3 = 2

`|+| |+| |`
Ne interesează în câte moduri putem distribui două plusuri în 4 spații libere
În caz general ar fi în câte moduri se pot distribui k-1 plusuri în n-1 spații libere

C<sup>k-1</sup><sub>n-1</sub>

![[Pasted image 20250930104841.png]]

x1 + x2 + ... + xk = n
x1, ..., xk pot fi 0
(x1 + 1) + (x2 + 2) + ... + (xk + 1) = n + k
= y1         = y2            = yk
y1, ..., yk != 0
Problema se reduce la cea de la a) deci avem
C<sup>k-1</sup><sub>n+k-1</sub>

![[Pasted image 20250930105054.png]]

Presupunem că alegem k biți = "1"
10 - k vor fi "0"

_ 0 _ 0 _ 0 _ ... _ 0 _

10 - k + 1 spații în care pot pune cei k biți de "1"

C<sup>k</sup><sub>10 - k + 1</sub>

C<sup>0</sup><sub>11</sub> + C<sup>1</sup><sub>10</sub> + C<sup>2</sup><sub>9</sub> + C<sup>3</sup><sub>8</sub> + C<sup>4</sup><sub>7</sub> + C<sup>5</sup><sub>6</sub>

# 7, 8 temă
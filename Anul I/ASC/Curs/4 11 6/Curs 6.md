# **Operanzi de tip adresare la memorie**
## Aritmetica de pointeri și L-value R-value
uwu
*Scop*: plimbare prin memorie, pentru a dereferenția apoi
- *adunare - adunarea a doi pointeri (operație **interzisă** în informatică)*
- adunare - adunarea unui pointer cu **o constantă** (exemplu: localizarea unui element din memorie pornind de la o adresă de bază)
- scădere - scăderea a doi pointeri (exemplu: determinarea lungimii unui șir) => constantă numerică, tip de dată scalar
- scădere - scăderea unei constante dintr-un pointer (exemplu: localizarea unui element din memorie pornind de la o adresă de bază)

<hr>
==!!==
În Assembly:
`[]` - dereferencing operator
==!!==
<hr>

### Consecințe ale comutativității adunării
În C:
`i = a[7]` = `i = *(a + 7)` = `i = *(7 + a)` = `i = 7[a]`
La fel, în Assembly
`mov AX, a[7]` = `mov AX, 7[a]` = `mov AX, [a + 7]`

<hr>
==!!==
*i* = **i** + 1
Scris mai corect
*i* <- **i** + 1
*i* este adresa de memorie (a.k.a. Left Hand Side of Assignment (a.k.a. LHS (a.k.a. L-value)))
**i** este conținutul locației de memorie (a.k.a. Right Hand Side of Assignment (a.k.a. RHS (a.k.a. R-value)))

Doar LHS este atribuibilă
==!!==
<hr>
Cea mai generala instrucțiune de atribuire NU este
`<identificator> = <expresie>`
Ci este
`<expresie_calcul_de_adresă> = <valoare_expresie_aritmetică>`

### Exemplu d.p.d.v. sintactic:
Corect
```asm
a dd ...
mov [ebx + edx*4 + v - 7], a + 2
```

Dacă am fi pus paranteze drepte la "a + 2" ar fi syntax error ptc BIU acceptă doar **o** locație de memorie în operație

Corect
```c
(a + 2?b:c) = x + y + z
```

Syntax Error
```c
(a + 2?1:c) = x + y + z
```

<hr>

### C++ reference variables
Trei utilizări:
1. D.p.d.v. sintactic, se utilizează `&`
```c++
int &j = i; //j devine ALIAS pt. i 
```

2. Transmitere de variabile prin referință la apelul de sub program
```c++
float f(int &x, int y){
	//...
}
```

3. Returnarea de L-valori prin intermediul funcțiilor
```c++
int v[200];

int &f(int x, int i){
	//...
	return v[i];
}
//q = f(a, 79)
//q = v[79]
F(a, 79) = 14193; /// v[79] = 14193

```

```
o     o
 \   /
 _\_/_  /
 \owo/_/
 /   |
/|   |
 \---/
_/   \_
```

<hr>

#### Două constante:
Constante de tip pointer, offset-ul oricărei variabile este o constantă determinabilă la momentul asamblării
Constante tip adresare directă ("17", "10", "2")

mov eax, ebx
mov eax, [ebx]
mov eax, 23
mov eax, [23]
mov edx, [ecx + 2 * esp + 7] - NU MERGE, ESP nu poate fi la index
mov eax, [ebx * 2]
mov eax, [ebx * 3] - transformată în aia de jos
mov eax, [ebx + eax * 2] - la fel cu aia de sus
mov [eax * 9 + 12] - la fel cu [eax = eax * 8 + 12]
mov eax, [ebp * 7] - NU MERGE, nu poate face 7 în scală (care e 1, 2, 4, 8)
mov eax, [esp * 5] - NU MERGE, ESP nu poate fi la index
mov eax, [ebx - edx] - syntax error
mov eax, [ebx + 2]
mov eax, ebx + 2 - syntax error

## Operații făcute de ALU
1. Operații aritmetice
2. Bit shifting operations
3. Logic operations
4. *Task-ul de a seta în mod corespunzător flag-urile după execuția unei instrucțiuni*
## 8 regiștrii generali ai unității executive

(E-ul din față vine de la "Extended")

- EAX - Acumulator
	- Cel mai des folosit
- EBX - Base(d) register (general)
- ECX - Counter register
- EDX - Data register

<hr>

Tipuri de date <sub>(în assembly)</sub> = dimensiunea de reprezentare (byte (8), word (16), doubleword (32), quadword (64))

Pp. op1, op2 sunt numere în baza 10
```
op1 - m cifre
op2 - n cifre
op1 + op2 -> max(m, n) + 1 cifre
op1 * op2 -> m + n cifre
```

Consecințele sunt:
```
b ± b -> b
w ± w -> w
dw ± dw -> dw
b * b -> w
w * w -> dw
dw * dw -> qw (are o jumatate superioară și o jumătate inferioară, 32 biți EBX 32 biți EAX)
```

# ***Întrebare de examen***
### Care sunt subdiviziunile regiștrilor EAX EBX ECX EDX?
![[Pasted image 20241009185123.png|200]]
### Răspuns:
#### Partea inferioară: AX, BX, CX, DX
#### Partea superioară: nu avem denumiri și nu pot fi accesate

<hr>

ESP - Stack pointer
EBP - Base pointer

Responsabili de lucru cu stiva (structură de dată)

Stiva diferă de coadă prin disciplina de acces a elementelor
Stivă = Last in First out
Coadă = First in First out

<hr>
# ***Întrebare de examen***
### ==De ce calculatorului îi pasă de LIFO și îl <sub>(quote)</sub> doare în cot <sub>(end quote)</sub> de FIFO?==
### Răspuns: Ptc cel mai important lucru s-a întâmplat acum, nu acum 1 minut
idfk

Programul executabil este "chestia vie" din calculator
Programul executabil conține activări și deactivări ale unor funcții și proceduri
Programele executabile urmează disciplina de stivă
	Runtime Stack = Stivă de execuție

<hr>

ESP este adresa ultimului element din stivă

`x = x + 1;`
`x`-ul din stânga este adresa lui `x`, `x`-ul din dreapta este valoarea lui `x`
Dereferencing an address

EBP este adresa bazei stivei

SS spune unde este segmentul de stivă

EDI - Destination index
ESI - Source index

	Orice este adus din memorie îi trebuie făcut un calcul de adresa (un calcul în 2 pași)

<hr>

# ***În examen***
### De pus string în quadword (? cred)

<hr>

# [[ASC/Curs/4 10 16/Curs 3#Flag-uri 🏳️‍🌈|EFLAGS]]


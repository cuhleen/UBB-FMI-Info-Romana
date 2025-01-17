
Mini recapitulare [[Curs 12]] sau ceva??
# **Reprezentarea codului mașină**
Ce bytes generează asamblorul : [prefix] + cod + [ModRM] + [SIB] + [deplasament] + [imediat]

*ModRM = mod register / memory*
*SIB = Scale Index Base (formula de 2 noapte)*

## ModRM

| 7   |     | 6   | 5   |            | 3   | 2   |     | 0   |
| --- | --- | --- | --- | ---------- | --- | --- | --- | --- |
|     | Mod |     |     | Reg/Opcode |     |     | R/M |     |

## SIB

| 7   |       | 6   | 5   |       | 3   | 2   |       | 0   |
| --- | ----- | --- | --- | ----- | --- | --- | ----- | --- |
|     | Scale |     |     | Index |     |     | Scale |     |

sufăr o7

<hr>

## Valori din tabelul de instrucțiuni
### *50*
`push EAX`

### *52*
`push EDX`

### *B8*
`mov eax, Iv`
Iv - Immediate value

### *89*
`mov Ev, Gv`
Ev - reg. / mem.
Gv - reg.

## Valori din tabelul de extensii

### *83*
idk

### Întrebare: Cum știi dacă trebuie să te uiți în tabelul de instrucțiuni sau de extensii?
### ==Răspuns: NU ȘTII! Gamble away==
Din fericire nu se cere la examen, asta e mai mult doar să știm unde să căutăm dacă vrem

# Exemple
## `mov [edx], ebp`
### *89***2A**

*89* - `mov Ev, Gv`
**2A** - 2Ah - 0010 1010 - *00* **101** *010* Mod R/M
> *00* - Mod
> **101** - Reg/Opcode - EBP
> *010* - [EDX]

## `mov ecx, [esp + ebx * 4]`
### *8B***0C***9C*
*8B* - `mov Gv, Ev`
**0C** - 0Ch - 0000 1100 - *00* **001** *100* Mod R/M
> *100* - identifică linia [EBX] din secțiunea Mod = 00 (din tabel, `[--][--]` înseamnă că după Mod R/M byte urmează SIB byte)

*9C* - 9Ch - 1001 1100 - SIB byte

==Vezi mai multe exemple pe pdf sau ceva lil bro==



<hr>



# EXAMEN ==!! ȚINE MINTE DOUĂ STRUCTURI DIN FIECARE CELE 4 CATEGORII DE PREFIXE (8 TOTAL) !!==

<hr>

# ==TOT ÎNTREBARE DE EXAMEN==
Cum faci
```asm
add ebx, v
sub ebx, 6
mov eax, ebx
```
într-o singură instrucțiune?
Răspuns:
`lea eax, [ebx + v - 6]`

<hr>

În concluzie a cursului de ASC alături de doctorul Alexandru "Assembly" Vancea, NU STA PE ONLINE!!!!!!!!!!!!!!!!!!!!!!
houmaigad

Bar > Familie btw
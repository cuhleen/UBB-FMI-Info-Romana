# **Analiza conceptului de overflow**
```
Reprezentare binară
  1001 0011 +
  1011 0011
-----------
1 0100 0110

~~~

Interpretare fără semn
147 +
179
---
326

~~~

Reprezentare hexa
  93h +
  B3h
-----
1 46h

~~~

Interpretare cu semn
-109 +
 -77
----
-186

~~~

Asta ne-am aștepta să obținem ca rezultat și în program, 326 și -186
```

### Exemplu 1
```
  1001 0011 +
  1011 0011
-----------
1 0100 0110

147 +
179
---
70

!! CF = 1 !!

~~~

-109 + 
-77
----
+70

!! OF = 1 !!
```

## Exemplu 2
```
 83 +
115
---
198

~~~

Fără semn:

0101 0011 + 
0111 0011
---------
1100 0110

!! CF = 0 !!

~~~

Cu semn:

 83 +
115
---
-58

!! OF = 1 !!
```

## *Din adunare sau scădere, OF se poate face 1 doar în cazurile:*
### Adunare
```
0... + 
0...
----
1...

SAU

1... + 
1...
----
0...
```

## Scădere
```
1... -
0...
----
0...

SAU

0... - 
1...
----
1...
```

### Exemplu OF = 1 din scădere
```
  98 - 
 200
----
-102

~~~

 98 -
-56
---
154
```

<hr>

Adunarea și scăderea pot afecta flag-urile.
Înmulțirea nu poate avea depășire, a fost gândită în jurul mărimilor.
==Împărțirea nu afectează niciun flag==. Dacă câtul nu încape în registru => *runtime error*

<hr>

# ==Întrebare de examen mayb==
***Enumerați cazurile în care CF și OF se setează diferit în cadrul înmulțirii și împărțirii***

<hr>

# Instrucțiuni ale limbajului de asamblare
## Instrucțiuni de transfer de uz general
MOV *d*, *s* - *d* <- *s*
PUSH *s* - ESP = ESP - 4, depune *s* pe stivă (*s* = dublucuvânt)
POP *d* - extrahge elementul curent de pe stivă și îl depunde în *d*
XCHG *d*, *s* - schimbă valorile *s*ursă și *d*estinație între ele, *trebuie să fie ambele L values*
[reg_segment] XLAT - AL <- DS:[EBX + AL] sau AL <- <reg_segment:[EBX + AL]>
PUSHA - depune pe stivă EAX, ECX, EDX, EBX, ESP, EBP, ESI, EDI
POPA - extrage de pe stivă EAX, ECX, EDX, EBX, ESP, EBP, ESI, EDI
PUSHF - depune EFlags pe stivă
POPF - extrage EFlags de pe stivă


"She will never be able to change flags manually!"
😎:
### Manipulare a flag-urilor cu instrucțiunile POPF și PUSHF
```asm
pushf
pop eax
; ...
push eax
popf
```

```asm
mov ds, ebx
```

### ==!! PUSH - ESP scade | POP - ESP crește !!==

<hr>

# ==Întrebare de examen type beat==
Ce face `push ESP` ?
Pune pe stivă valoarea de dinainte de instrucțiune
În vârful stivei se află o valoare cu 4 mai mică

<hr>

a) ESP = 0019FF74
b) ESP = 0019FF70 - se actualizează ESP
c) [ESP] = 0019FF74

a) valoare top of stack: 7741FA29
b) push ESP | ESP <- ESP + 4 = 0019FF78
c) se pune pe stivă 7741FA29

<hr>

# Alte instrucțiuni
## XLAT - Translate

<hr>

Scrieți o funcție carfe tipărește în b16

<hr>

# LEA - Load Effective Address
Lea registru general, *conținutul* unui operand din memorie

```asm
; 16 biți
mov bx, offset v; adresa
mov bx, v; conținut
mov ax, offset[bx + SI - 7]; 
mov ax, [bx + SI - 7]; 

;32 biți
mov ebx, v; transferă adresă (offset)
mov ebx, [v]; transferă conținut
mov ebx, ebx + esi - 7; syntax error
mov ebx, [ebx + esi - 7]; ok!
```

```asm
mov lea edx, [ebx + esi - 7]; ia ADRESA, valoarea în sine "ebx + esi - 7"
```


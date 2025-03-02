# Instrucțiuni de conversie
### Doi operanzi, unul explicit, altul implicit
`CBW`
`CWD`
`CWDE`
Toate sunt signed


### Doi operanzi expliciți
`MOVZX d, s` - încarcă în d (un registru), de o dimensiune mai mare decât s (registru sau operand din memorie), conținutul lui s *fără semn (zero extension)*
`MOVSX d, s` - încarcă în d (un registru), de o dimensiune mai mare decât s (registru sau operand din memorie), conținutul lui s *cu semn (sign extension)*

### Exemple
```asm
mov ah, 0C8h
movzx edx, ah; edx = 000000C8h
movsx ebx, ah; ebx = FFFFFFC8h

movsx ax, [v]; movsx ax, byte prt DS:[offset:v]
                                      00401002
movzx eax, [v]; syntax error, operand size not specified
movzx eax, byte [v]; ok!
movzx eax, word [v]; ok!
movzx eax, dword [v]; syntax error
movzx eax, [ebx]; syntax error, precizează byte sau word dummy
movsx dword [ebx], ah; syntax error, nu de la mărimi, ci pentru că destinația trebuie să fie registru

```

## ==Atenție! NU există instrucțiuni de conversii de la o mărime mai mare spre una mai mică==

# Rotiri
```asm
SHL x, 1; x = bcdefgh0 | CF = a
SHR x, 1; x = 0abcdefg | CF = h
SAL x, 1; identic cu SHL
SAR x, 1; x = aabcdefg | CF = h
ROL x, 1; x = bcdefgha | CF = a
ROR x, 1; x = habcdefg | CF = h
RCL x, 1; x = bcdefghk | CF = a
RCR x, 1; x = kabcdefg | CF = h
```

# ==!! Întrebare de examen !!==
Se dă următoarea secvență de 2 instrucțiuni
```asm
xor edx, edx
mov dl, 0Fh
```
*Scrieți o secvență de instrucțiuni care înmulțește cu 4 valoarea pe 64 de biți reprezentată de combinația de regiștrii EDX:EAX*

Din `xor edx, edx`, EDX se umple cu `0`
`mov dl, 0Fh` pune `1` în ultimele 4 căsuțe ale lui EDX
EDX = 00...01111
**O shift-are** *la stânga a lui EDX este* **echivalent** *cu o înmulțire cu 2 a lui EDX / EDX:EAX*
Din punct de vedere *semantic*, vrem să facem asta:
`shl edx:eax, 2`

În punctul ăsta, presupunem `EDX = 00...01111` și `EAX = A B 0 0...0 0 0 0`
Vrem să ajungem cu `EDX = 00...01111AB` și `EAX = 00...0000`

```asm
shl eax, 1; Bit-ul cel mai din stânga a lui EAX se duce în Carry Flag
; Pentru asta există Rotate with Carry!
rcl edx, 1; rotate și pune bit-ul din carry în dreapta
; Repetăm pasul
shl eax, 1; Următorul bit în Carry Flag
rcl edx, 1; rotate și pune bit-ul din carry în dreapta
```

<hr>

Ce se întâmplă dacă aici renunțăm la parantezele drepte?
```asm
segment data...

	salt DD dest; salt = offset de dest
	
segment code...

	jmp [salt]

	dest:
;...
```

Sau dacă punem paranteze drepte aici?
```asm
mov eax, eticheta
jmp eax

eticheta:
;...
```

<hr>

# Instrucțiuni de salt condiționat
## Comparații între operanzi
#### `cmp d, s`
"Compară" direct d cu s
`d ? s`
Unde `?` este `>`, `<`, sau `=`

În realitate *nu compară nimic*. Este mai degrabă o *scădere*. Scopul este doar de a seta flag-urile, și rezultatul este interpretarea finală
Singurele instrucțiuni care chiar compară sunt cele cu ==equal==, ==lesser==, ==greater==, ==above==, ==below==

#### `test d, s`
Compară `d - s` cu `0`
`d - s ? 0`
Unde `?` este `>`, `<`, sau `=`

```asm
mov al, 200; AL = 11001000b = 0C8h = 200 (unsigned) = -56 (signed)
mov bl, -1; BL = 11111111b = 0FFh = 255 (unsigned) = -1 (signed)

; atunci ce interpretare folosește următoarea instrucțiune?
cmp al, bl
; niciuna, doar face o scădere fictivă, cu singurul scop de a seta corespunzător flag-urile
; singurul mod de a le compara este folosirea instrucțiunilor de comparare

jl et1; Saltul se efectuează. JL este signed, -56 este mai mic decăt -1
ja et2; Saltul NU se efectuează. 200 nu e mai mare decăt 255
jb et3; Saltul se efectuează. 200 este mai mic decăt 255
jg et4; Saltul NU se efectuează. -56 nu e mai mare decăt -1

```

# LOOP
Decrementează ECX și nu mai intră în loop dacă ECX este 0
Este jump **scurt** (distanța dintre loop și etichetă este de maxim 127 de biți)

<hr>

# Paranteză despre constante de tip string
Constantele de tip string se păstrează în tabela de constante a asamblorului, cu little endian (în ordine inversă)

## Întrebare extra: ce reprezintă "abcd" în C?
Valoarea asociată = adresa sa de început din tabela de constante
"abcd"[0] = "a"
"abcd"[1] = "b"
"abcd"[2] = "c"
"abcd"[3] = "d"
==!! Valabil în C dar nu și în Assembly !!==
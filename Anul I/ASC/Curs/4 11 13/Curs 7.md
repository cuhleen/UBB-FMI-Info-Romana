==!! Slide-uri !!==

# **Elementele De Bază Ale Limbajului De Asamblare**

De a genera în mod corect octeții în mod corespunzător directivelor și instrucțiunilor găsite în program

## 4 elemente de bază ale limbajului de asamblare:
1. *etichete* - nume scrise de utilizator, cu ajutorul cărora se pot referi date sau zone de memorie
2. *instrucțiuni* - scrie sub forma unor mnemonice care sugerează acțiunea. Asamblorul generează octeți care codifică instrucțiunea respectivă
==Întrebare de examen poate:== Cine generează octeți? Răspuns: Asamblorul
Instrucțiunile se adresează procesorului
Directivele se adresează asamblorului
3. *directive* - indicații date asamblorului în scopul generării corecte a octeților
4. *contor de locații* - un număr întreg, gestionat de asamblor. La fiecare moment al execuției, valoarea contorului de locatii coresupunde cu numărul de octeți generați corespunzător instrucțiunilor și directivelor deja înlănțuite în cadrul segmentului respectiv

#### Dolar
`$` - Location Counter, pointer
##### În NASM
`$$` - Începutul secțiunii în care suntem, pointer

`$ - $$` - distanța de la punctul respectiv până la începutul secțiunii ("de unde sunt până la începutul secțiunii" explicație Vancea Style)

*sofishticated* 🐟

## Formatul unei linii sursă
`[etichetă[:]] [prefixe] [mnemonică] [operand] [;comentariu]`
`[gânditul]` - opțional
##### Exemplu:
`aici: jmp acolo ; sărim acolo`
`etichetă + mnemonică + operand + comentariu`


```asm
start: ; început

a dw 19872, 42h ; declarare
```

```asm
etichetă + comentariu
linie goală
etichetă + mnemonică + 2 operanzi + comentariu
```

<hr>

**Valoarea unei etichete în limbaj de asamblare este un număr întreg reprezentând adresa instrucțiunii, directivei, sau datelor ce urmează eticheta**

NASM face diferența dintre adresă și conținut (`[]`). Alte asambloare nu

<hr>

## Exemple ale contorului de locații
```asm
segment data ...
	a db 17, -2, affh, 'xyz'
	lga dw $ - a; sau "lga equ $ - a", dar asta NU permite "mov [lga], ..."
	lga db $ - $$; deobicei nu dă bine, dă toți octeții generați de la începutul secțiunii curente (a.k.a. începutul segmentului). Dă lungimea corecta lui a numai dacă a este primul element definit în segment
```

```asm
segment data ...
	a db 17, -2, affh, 'xyz'
	c dd 12345678h
	lga_this db c - a
```

```asm
segment data ...
	a db 17, -2, affh, 'xyz'
	lga dw lga - a
```

<hr>

<span style="color:#B6945F"><b>Valoarea operanzilor este calculată <u>în momentul asamblării</u> pt operanzii imediați, <u>în momentul încărcării</u> programului pentru adresarea directă (adresa FAR), și <u>în momentul execuției</u> pentru operanzii registru și cei adresați indirect</b></span>

Deplasamentul unui operand cu adresare directă este calculat <u>în momentul asamblării</u> (assembly time). Adresa fiecărui operand raportată la structura programului executabil (mai precis stabilirea segmentelor la care se raportează deplasamentele calculate) este calculată <u>în momentul editării de legături</u> (linking time). Adresa fizică efectivă este calculată <u>în momentul încărcării programului pentru execuție</u> (loading time - acest proces final de ajustare a adreselor numindu-se RELOCAREA ADRESELOR = Address Relocation)

I ain't typin allat 💀 I lied
mucho texto

teo wuz here :]

## Utilizarea operanzilor din memorie
**CS** - pentru etichete de cod destinație ale unor salturi
**SS** - în adresări SIB ce folosesc *ebp* sau *esp* drept bază
**DS** - pentru restul accesărilor de date
### Exemple
```asm
mov eax, [v]; mov eax dword ptr [DS:405000]
mov eax, [ebx]; DS
mov eax, [evp]; SS
mov eax, [ebp * 2]; mov eax dword ptr [SS:ebp + ebp]
mov eax, [ebp * 3]; SS
mov eax, [ebp * 4]; DS

mov eax, [ebx + esp]; SS
mov eax, [esp + ebx]; SS
; în ambele cazuri esp este registru de bază ptc esp nu poate fi registru de index

mov eax, [ebx + esp * 2]; syntax error
mov eax, [ebx + ebp * 2]; DS

mov eax, [ebx + ebp]; DS
mov eax, [ebp + ebx]; SS
; singurul caz în care se iau în ordinea scrierii

mov eax, [ebx * 2 + ebp]; SS
mov eax, [ebx * 1 + ebp]; SS
mov eax, [ebp * 1 + ebx]; DS

mov eax, [ebp * 1 + ebx * 1]; DS
mov eax, [ebx * 1 + ebp * 1]; SS
; se încadrează în singurul caz în care se iau în ordinea scrierii

mov eax, [ebp * 1 + ebx * 2]; SS

```

# ==!! Prezentați în scris cu explicații 15 moduri de a inițializa cu 0 un registru !!==
Sau 5 moduri cu 1
Sau 5 moduri cu -1

"!" = not
vezi exemple pe ceva document ig


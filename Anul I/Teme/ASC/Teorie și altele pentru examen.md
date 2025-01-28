
<div style="background-color: #E77A59; height: 10px; width: 100%;"></div>
# ***Cerința I***
<div style="background-color: #E77A59; height: 10px; width: 100%;"></div>



## **Flag-uri**
Un flag este un identificator reprezentat pe un bit. O configurație a registrului de flag-uri indică un rezumat sintetic al execuției fiecărei instrucțiuni.
Pentru x86, registrul EFLAGS are 32 de biți, dintre care sunt folosiți uzual numai 9: CF<sub>(Carry Flag)</sub>, OF<sub>(Overflow Flag)</sub>, SF<sub>(Sign Flag)</sub>, ZF<sub>(Zero Flag)</sub>, DF<sub>(Direction Flag)</sub>, IF<sub>(Interrupt Flag)</sub>, TF<sub>(Trap Flag)</sub>, AF<sub>(Auxiliary Flag)</sub>, PF<sub>(Parity Flag)</sub>.
Flag-urile se împart în două categorii: cele care raportează efectul generat de ultima operație efectuată (*CF*, OF, PF, AF, ZF, SF), și cele cu efect ulterior setării de către programator (*CF*, TF, IF, DF).

### Carry Flag
Carry Flag-ul este un flag de transport, semnalează depășirea în cazul interpretării fără semn. Are valoarea 1 în cazul în care efectul în cadrul ultimei operații efectuate s-a produs un transport în afara domeniului de reprezentare al rezultatului și valoarea 0 în caz contrar.

Exemple:
1.
```asm
mov al, 100
mov ah, 200
add al, ah

; 100 + 200 = 100 + 200 = 300 > 255
; CF = 1
```
2.
```asm
mov al, 100
mov ah, -1
add al, ah

; 100 + (-1) = 100 + (256 - 1) = 355 > 255
; CF = 1
```

Ca și regulă generală pe un octet, un număr < 0 se transformă în număr fără semn, scăzând acel număr din cardinalul intervalului de reprezentare, 256 ([0, 255] interval octet).

Exemple:
1.
```asm
mov al, 100
mov ah, 101
sub al, ah

; 100 - 101 = -1 < 0
; CF = 1
```
2.
```asm
mov al, 100
mov ah, -1
sub al, ah

; 100 - (-1) = 100 - (256 - 1) = -155 < 0
; CF = 1
```

### Overflow Flag
Overflow Flag este tot un flag de transport, dar acesta semnalează depăsire în cazul interpretării cu semn. Are valoarea 1 în cazul în care în cadrul ultimei operații efectuate s-a produs un transport în afara domeniului de reprezentare al rezultatului și valoarea 0 în caz contrar.
Ca și regulă generală pe octet, dacă x∈[128, 255] atunci îl putem aducem în intervalul de reprezentare scăzând 256 din el.

==! Adunarea sau scăderea cu 256 nu schimbă CF și OF pe octet !==

Exemple:
##### add
1.
```asm
mov al, 100
mov ah, 100
add al, ah

; 100 + 100 = 200 >= 128
; OF = 1
```
2.
```asm
mov al, -100
mov ah, 156
add al, ah

; -100 + 156 = -100 + (156 - 256) = -100 + (-100) = -200 <= -127
; OF = 1
```
##### sub
1.
```asm
mov al, 100
mov ah, -100
sub al, ah

; 100 - (-100) = 100 + 100 = 200 >= 128
; OF = 1
```
2.
```
mov al, 100
mov ah, 156
sub al, ah

; 100 - 156 = 100 - (156 - 256) = 100 - (-100) = 200 >= 128
; OF = 1
```

Pentru operația de **înmulțire** CF = OF = 1 dacă rezultatul obținut nu încape în același interval cu cei 2 operanzi (`byte * byte = word`, `word * word = doubleword`, `doubleword * doubleword = quadword`).

Exemple:
##### fără semn
```asm
mov al, -1
mov ah, 2

; -1 * 2 = (256 - 1) * 2 = 255 * 2 > 255
; OF = CF = 1
```
##### cu semn
```asm
mov al, -1
mov ah, 2
imul al

; -1 * 2 = -2 ∈ [-128, 127]
; CF = OF = 0
```

La operația de **împărțire**, dacă rezultatul nu încape în spațiul de reprezentare alocat, se produce *run-time error*, programul se oprește, deci e irelevantă valoarea din flag-uri.

### Zero Flag
Zero Flag indică dacă rezultatul ultimei operații este sau nu 0. ===Totuși acest flag nu se setează la înmulțire și împărțire, indiferent de rezultat.== Astfel, pentru adunare și scădere, ZF = 1 dacă rezultatul ultimei operații efectuate este 0, altfel ZF = 0.

Exemple:
1.
```asm
mov al, 15
mov ah, 241
add al, ah

; 15 + 241 = 256 = 256 - 256 = 0
; 256 nu încape pe byte, deci se salvează doar ultimii 8 biți
ZF = 1
```
2.
```mov al, -1
mov ah, 255
sub al, ah

; -1 - 255 = (256 - 1) - 255 = 255 - 255 = 0
; ZF = 1
```

### Sign Flag
Sign Flag indică bitul de semn al rezulattului ultimei operații efectuate. Rezultatul va fi adus la intervalul de reprezentare admisibil (ex.: pe octet la [-128, 127]).
Exemple:
1.
```asm
mov al, 10
mov ah, -1
add al, ah

; 10 + (-1) = 9 ∈ [-128, 127] & > 0
; SF = 0
```
2.
```asm
mov al, -1
mov ah, -1
add al, ah

; -1 + (-1) = -2 ∈ [-128, 127] & < 0
; SF = 1
```

Pentru imul SF = 1 numai dacă înmulțim un număr pozitiv cu un număr negativ altfel SF = 0
Pentru mul va fi valoarea bitului de semn din AL/AX/DX

### Interrupt Flag
Interrupt Flag este folosit pentru secțiuni critice. Atunci când IF = 1 se oprește rularea oricărui alt proces. Nu poate fi setat sub 32 de biți.

### Trap Flag
Trap Flag oprește mașina după fiecare instrcțiune dacă este setat cu 1. Nu poate fi schimbat.

### Auxiliary Flag
Auxiliary Flag reține valoarea transportului de la bitul 3 la bitul 4.

### Direction Flag
Direction Flag este folosit la lucrul cu șiruri. Dacă DF = 0 parcurgerea se face de la stânga la drepata, altfel parcurgerea e de la drepata la stânga.

### Parity Flag
Parity Flag ia valoarea 1 dacă ultimul octet al ultimei operații efectuate este 0 (adică dacă este par rezultatul), și invers.

### Instrucțiuni pentru a încarca/scoate tot registrul de flag-uri
`PUSHF`
`POPF`

### Instrucțiuni de setare a flag-urilor
##### Carry Flag
`CLC` - Clear Carry Flag => CF = 0
`STC` - Set Carry Flag => CF = 1
`CMC` - Complement Carry Flag => dacă CF = 0, CF se face 1, și invers

##### Direction Flag
`CLD` - Clear Direction Flag => DF = 0
`STD` - Set Direction Flag => DF = 1

##### Interrupt Flag
==Poate fi setat doar sub 16 biți, sub 32 de biți programatorului luându-i-se această posibilitate==
`CLI` - Clear Interrupt Flag => IF = 0
`STI` - Set Interrupt Flag => IF = 1

<hr>

## **Conceptul de depășire**
Conceptul de Overflow este folosit pentru a semnala faptul că rezultatul unei anumite operații nu a încăput în spațiul destinat acestuia. În funcție de ce operație este vorba, setarea CF și OF se va face după anumite reguli, rezultqnd diferite concluzii legate de ultima operație efectuată.

### *Adunare*
##### Fără semn
Depășire dacă rezultatul nu e în intervalul alocat (ex.: [0, 255] pe byte), iar *CF* va fi setat la 1, altfel 0. 

Exemple:
1.
```asm
mov al, 100
mov ah, 200
add al, ah

; 100 + 200 = 300 > 255
; CF = 1
```
2.
```asm
mov al, 100
mov ah, -1
add al, ah

; 100 + (-1) = 100 + (256 - 1) = 355 > 255
; CF = 1
```

##### Cu semn
Depășire dacă rezultatul nu e în intervalul alocat (ex.: [-128, 127] pe byte), iar *OF* va fi setat la 1, altfel 0.

Exemple:
1.
```asm
mov al, 100
mov ah, 100
add al, ah

; 100 + 100 = 200 > 127
; OF = 1
```
2.
```asm
mov al, -100
mov ah, 156
add al, ah

; 156 se transformă în număr cu semn => 156 - 256 = -100
; -100 + (-100) = -200 < -128
; OF = 1
```

### *Scădere*
##### Fără semn
*CF* va fi setat la 1 dacă există un împrumut de la o poziție care nu există, adică dacă rezultatul nu aparține intervalului de reprezentare (ex.: [0, 255] pe byte). Altfel CF = 1.

Exemple:
1.
```asm
mov al, 100
mov ah, 101

; 100 - 101 = -1 < 0
; CF = 1
```
2.
```asm
mov al, 100
mov ah, -1
sub al, ah

; -1 se transfomă în număr fără semn => -1 + 256 = 255
; 100 - 255 = -155 < 0
; CF = 1
```

##### Cu semn
*OF* va fi setat la 1 în următoarele două cazuri:
[nr. poz.] - [nr. neg.] = [nr. neg.]
[nr. neg.] - [nr. poz.] = [nr. poz.]
Altfel OF = 0.

Exemple:
1.
```asm
mov al, 100
mov ah, -100
sub al, ah

; 100 - (-100) = 200
; 200 în interpretarea cu semn = 200 - 256 = -56 < 0
; poz. - neg. = neg.
; OF = 1
```
2.
```asm
mov al, 100
mov ah, 156
sub al, ah

; 156 se transformă în 156 - 256 = -100
; 100 - (-100) = 200
; 200 se transformă în 200 - 2256 = -56
; poz. - neg. = neg.
; OF = 1
```

### **Înmulțire**
La înmulțire, OF și CF semnalează depășire în mod diferit. S-a hotărât că depășirea înseamnă faptul că rezultatul nu a încăput în același interval de reprezentare cu operanzii (ex.: `byte * byte` chiar a generat un rezultat care încare doar pe word).

Exemple:
1.
```asm
mov al, 20
mov ah, 20
mul ah

; 20 * 20 = 400 care NU încape pe byte
; CF = OF = 1
```
2.
```asm
mov al, 3
mov ah, 6
imul ah

; 3 * 6 = 18 care încape pe byte, nu are nevoie de spațiu adițional
; CF = OF = 0
```

### **Împărțire**
Depășirea la împărțire constituie un caz special, deoarece dacă se produce, programul se oprește (division overflow -> *run-time error*). În acest caz, valorile din CF și OF sunt irelevante. Depășirea înseamnă că rezultatul nu a încăput în spațiul destinat pentru acesta.

Exemple:
1.
```asm
mov ax, 4096
mov bl, 10
div bl

; 4096 / 10 = 409 rest 6
; câtul se pune în AL
; restul se pune în AH
; 409 nu încape în AL
; programul se oprește, division overflow
```

Există mai multe moduri prin care se poate ține cont de depășiri. Asamblorul ne oferă două instrucțuni specifice pentru adunare și scădere: `ADC` (Add with Carry) și `SBB` (Sub with Borrow) în care ține cont de transportul existent din flag-uri.
Deobicei nu se ține cont de carry, dar atunci când avem un număr salvat, de exemplu în DX:AX și altul în CX:BX, dacă vrem să le adunăm, vom proceda astfel:
```asm
add ax, bx
adc dx, cx
```

<hr>
## **Multimodul**
### Codul de apel
Codul de apel este codul scris înainte de apelarea unei funcții.

- salvare resurse volatile (presupunem că toți regiștrii sunt resurse volatile -> se folosesc în funcție) - `PUSHAD` (îi salvăm pe stivă)
- transmitem parametrii: regiștrii se transmit automat, presupunem că mai dăm un parametru (o valoare din memorie, `a`) - `push dword [a]`
- efectuare apel cu salvare adresă de revernire - `call functie`

### Codul de intrare
Codul de intrare este codul scris la începutul unei funcții apelate.

- se crează un stackframe nou - `push EBP`, `mov EBP, ESP`
- alocăm spațiu pentru variabilele locale (variabile pentru modulul separat) - `sub ESP, nr_octeti`, spre exemplu pentru EAX for fi 4 octeți
- salvare resurse nevolatile (salvâm regiștrii care nu țin de apel) - exemplu: EAX nu ține de apel, dar vrem să-l folosim => `mov [EBP - 4], EAX`

### Codul de ieșire
Coduld e ieșire este codul scris la finalul unei funcții apelate.

- restaurare resurse nevolatile - `mov EAX, [EBP - 4]`
- eliberare spațiu de variabile locale - `add ESP, nr_octeti`
- eliberare cadru stivă - `mov ESP, EBP`, `pop EBP`
- revenire din funcție și scoaterea pe stivă a parametrilor - `ret`, `add ESP, 4 * 1` (pentru cazul prezentat)
- în programul principal restaurăm resursele volatile - `POPAD`

<hr>

## **Stiva**
Stiva este compusă din două părți: baza (EBP) și vârful (ESP).
Când scoatem un element  de pe stivă, ESP crește cu 4 octeți (`pop`), se salvează vârful stivei în variabila dată ca parametru (`push parametru`).

|           | <- EBP |
| --------- | ------ |
| ‎         | ‎      |
| ‎         | ‎      |
| ‎         | ‎      |
| ‎         | ‎      |
| ///////// | <- ESP |
| ‎         | ‎      |
| ‎         | ‎      |

Rolul stivei este de a crea spațiu de variabile locale și de a transmite parametrii care nu sunt regiștrii.

##### Responsabilități
Codul de apel este responsabilitatea celui care apelează.
Codul de intrare și ieșire este responsabilitatea celui care este apelat.

##### Convenții
Convenția CDECL în ASM este să transmită parametrii pe stivă și să salveze rezultatul în EAX.

<hr>

## **Memorie**
### Adresă de memorie
Adresa de memorie este un identificator al poziției unei locații de memorie pe care procesorul o poate accesa pentru citire sau scriere.

Exemplu: pentru memoria flat primul element din memorie va avea adresa 32 de 0 (pe 32 de biți)

### Segment de memorie
Un segment de memorie estge o diviziune logică a memoriei, o succesiune de locatii de moemorie menite să servească scopuri similare.

Exemplu: code segment conține instrucțiuni mașină (de la 1 la 15 octeți)

### Offset
Offset-ul estge numărul de octeți adăugați la o adresă de bază, numărul de octeți de la adresa de început de segment până la locația căreia îi vrem offset-ul.

Exemplu: în data segment
```asm
a db 1; a are offset 0 (raportat la $$)
b db 2; b are offset 1 (raportat la $$)
```

### Specificare de adresă
Formată din segment și offset (adresă FAR).

Exemplu: `mov eax, [DS:a]` în EAX pune adresa FAR a lui a

### Mecanism de segmentare
Procesul de împărțire a memoriei în diviziuni logice, menite să deservească același scop.

Exemplu: data segment, code segment, stack segment, extra segment

### Adresa liniară
Adresă de segmentare formată din bază și offset.
a<sub>7</sub>a<sub>6</sub>a<sub>5</sub>a<sub>4</sub>a<sub>3</sub>a<sub>2</sub>a<sub>1</sub>a<sub>0</sub> = b<sub>7</sub>b<sub>6</sub>b<sub>5</sub>b<sub>4</sub>b<sub>3</sub>b<sub>2</sub>b<sub>1</sub>b<sub>0</sub> + o<sub>7</sub>o<sub>6</sub>o<sub>5</sub>o<sub>4</sub>o<sub>3</sub>o<sub>2</sub>o<sub>1</sub>o<sub>0</sub>

Exemplu: avem baza = 2000h și offset = 1000h => adresa liniară = 3000h

### Model de memorie flat
Toate segmentele încep de la 0 (bloc continuu).

Exemplu: modul protejat x86 folosește modelul de memorie flat

### Adresă fizică efectivă
Rezultatul final al segmentării (+ eventual paginării) în memoria fizică (hardware).

### Adresare la memorie
Offset calculat cu următoarea formulă
adresă_offset = [bază] + [index * scală] + [constantă]

Registru de bază poate fi: EAX, EBX, ECX, EDX, EBP, ESI, EDI, ESP
Registru de index poate fi: EAX, EBX ECX, EDX, EBP, ESI, EDI
- ESP *nu* are voie să fie registru de index

### Adresare directă
Implică doar operanzi direcți și imediați. Adresare la memorie unde apare doar [constantă].

Exemplu: `mov eax, [a + 4]`

### Adresare bazată
Intervin regiștrii de bază. Adresare la memorie unde apare doar [bază].

Exemplu: `mov eax, [ebx]`

### Adresare indexată
Intervin regiștrii de index (și implicit scală). Adresare la memorie unde apare doar [index * scală].

Exemplu: `mov eax, [2 * eax]`

### Adresare indirectă
Care nu e directă (🤯).

Exemplu: `mov ax, [ebx + v + 4]`

### Adresă NEAR
Formată doar din offset, segmentul se adaugă implicit în loading time.

Exemplu: `mov eax, [v]`

### Reguli implicite de asociere între offset și registru segment
##### CS
Pentru `jmp`, `call`, `ret`.
Exemplu: `jmp acolo`
##### SS
Dacă avem ESP sau EBP ca bază
Exemplu: `mov ax, [ESP]`
##### DS
Restul.
Exemplu: `mov ax, [EBP + ECX + 4]`

==Mai multe exemple [[ASC/Curs/4 11 13/Curs 7#Utilizarea operanzilor din memorie|aici]].==



<div style="background-color: #E77A59; height: 10px; width: 100%;"></div>
# ***Cerința II***
<div style="background-color: #E77A59; height: 10px; width: 100%;"></div>



## 1.1. Prezentați și justificați structura din memorie a următorului segment. Dacă identificati date sau linii sursăpe care le considerați incorecte sintactic, justificati motivul și ignorați apoi acele valori sau linii în construirea modulului de reprezentare în memorie a segmentului de date.
```asm
segment data use32 class=data
	x dw -256, 256h
	y dw 256|-256, 256h & 256
	z db $ - z, y - x
	  db 'y' - 'x', 'y - x'
	a db 512>>2, -512<<2
	b dw z - a, !(z - a)
	c dd ($ - b) + (d - $), $ - 2 * y + 3
	d db -128, 128^(~128)
	e times 2 resw 6
	times 2 dd 1234h, 5678h
```

##### `x dw -256, 256h`
-256 se transformă în baza 2, apoi în baza 16
|-256| = 256 = 2<sup>8</sup> = 1 0000 0000
Complementul față de 2 => 1111 1111 0000 0000 = FF00
În memorie va fi 00|FF

256h = 0256h
În memorie va fi 56|02

##### `y dw 256|-256, 256h & 256`
`x|y` = `or x, y`
256|-256 se transormă în baza 2, apoi în baza 16
256 = 2<sup>8</sup> = 1 0000 0000 = 0000 0001 0000 0000 (pe word)
|-256| = 256 = 2<sup>8</sup> = 1 0000 0000
Complementul față de 2 => 1111 1111 0000 0000
256|-256 = 1111 1111 0000 0000 = FF00
În memorie va fi 00|FF

`x&y` = `and x, y`
256 se transformă în baza 2 apoi în baza 16
256 = 2<sup>8</sup> = 1 0000 0000 = 0000 0001 0000 0000 (pe word)
256h = 0256h = 0000 0010 0101 0110

`0000 0010 0101 0110` = 256h
`0000 0001 0000 0000` = 256
`0000 0000 0000 0000` = 256h & 256
În memorie va fi 00|00

##### `z db $-z, y - x`
##### `  db 'y' - 'x', 'y - x'`
`$ - z` = 0 => în memorie va fi 00
`y - x` = 4 => în memorie va fi 04

În memorie va fi 00|04

`'y' - 'x'`
Le scade codurile ASCII, 'y' - 'x' = 1 => în memorie va fi 01
`'y - x'` va pune separat codurile fiecăruia => în memorie va fi 'y'|'-'|'x'

##### `a db 512>>2, -512<<2`
`512>>2`
Shift la dreapta cu 2 biți a lui 512

512 / 2<sup>2</sup> = 128 = 2<sup>7</sup> = 1000 0000
În memorie va fi 80

`-512<<2`
Shift la stânga cu 2 biți a lui -512
|-512| = 512 = 2<sup>9</sup> = 10 0000 0000
Complement față de 2 => 1111 1110 0000 0000
-512<<2
1111 1000 0000 0000 = F8|00
Luăm doar un byte => în memorie va fi 00

==?????????????????????????????????????????????????==

##### `b dw z - a, !(z - a)`
`z - a`
z - a = -6
|-6| = 6 = 0110
Complement față de 2 => 1111 1111 1111 1010 = FFFA
În memorie va fi FA|FF

`!(z - a)` = !(-6)
În memorie va fi 00|00

==?????????????????????????????????????????????????==

##### `c dd ($ - b) + (d - $), $ - 2 * y + 3`
`$ - b` = 4
`d - $` = 4 (doar un elemenet de acolo e valid)
=> 4 + 4 = 8
În memorie va fi 08|00|00|00

`2 * y`
Înmulțirea de pointeri nu e validă! *Syntax error*

##### `d db -128, 128^(~128)`
-128
-128 + 256 = 128 (Adunarea sau scăderea cu 256 nu schimbă numărul pe octet) = 2<sup>7</sup> = 1000 0000 = 80h 
În memorie va fi 80

`128^(~128)`
`~` - complementul
`^` - xor
În memorie va fi FF

##### `e times 2 resw 6`
##### `  times 2 dd 1234h, 5678h`
`times 2 resw 6`
Un word în memorie = 00|00
În memorie va fi:
00|00|00|00|00|00|00|00|00|00|00|00
(Adică 6 * 2 de |00|)

`times 2 dd 1234h, 5678h`
34|12|00|00|78|56|00|00|34|12|00|00|78|56|00|00|

## 1.2. Scrieți o singură instrucțiune ASM care să aibă același efect (excepție unele flag-uri) ca și secvența dată și explicați/justificați de ce se obține același efect. Detaliați efectul fiecărei linii din secvența dată

```asm
mov bh, 7Fh
cmp BH, AL
rcr ah, 1
sar ah, 7
```

`mov bh, 7Fh`
BH = 0111 1111
`cmp BH, AL`
Scădere fictivă `BH - AL`, dacă bitul de semn AL este 1 atunci CF este 1, altfel CF este 0
`rcr ah, 1`
==! Shift-ările nu se transmit de la AH la AL !==
Pune pe prima poziție din CF (Rotate Carry Right = completează cu CF)
`sar ah, 7`
Shift Arithmetic Right (completează cu bitul de semn)
În AH va fi bitul de semn al lui AL

=> Echivalent cu `movsx ax, al` sau `cbw`

## 2.1. Prezentați și justificați structura din memorie a următorului segment. Dacă identificati date sau linii sursăpe care le considerați incorecte sintactic, justificati motivul și ignorați apoi acele valori sau linii în construirea modulului de reprezentare în memorie a segmentului de date.

```asm
a1 db '256'
a2 dw 256, 256h
a3 dw $ + a2
a4 equ -256/4
a5 db 256>>1, 256<<1
a6 dw a5 - a2, !(a5 - a2)
a7 dw [a2], ~a2
a8 dd 256h^256, 256256h
a9 dd $ - a9
a10 db 256, -255
a11 dw 256h - 256
a12 dw 256 - 256h
a13 dw -256
a14 dw -256h
a15 db 2, 5, 6, 25, 6, 2, 56
```

##### `a1 db '256'`
Le consideră caractere, pe fiecare dintre ele separat
În memorie va fi '2'|'5'|'6'

##### `a2 dw 256, 256h`
256 = 2<sup>8</sup> = 1 0000 0000 = 0100h
În memorie va fi 00|01

256h = 0256h
În memorie va fi 56|02

##### `a3 dw $ + a2`
==! Adunarea de pointeri nu e validă !==

##### `a4 equ -256/4`
==! Nu ocupă memorie !==

##### `a5 db 256>>1, 256<<1`
256>>1 = 2<sup>8</sup> / 2<sup>1</sup> = 2<sup>7</sup> = 1000 0000 = 80h
În memorie va fi 80

256<<1 = 2<sup>8</sup> * 2<sup>1</sup> = 2<sup>9</sup> = 0010 0000 0000 = 0200h
Suntem pe byte, se salvează doar ultimul octet
În memorie va fi 00

==?????????????????????????????????????????????????==

##### `a6 dw a5 - a2, !(a5 - a2)`
`a5 - a2` = 4
În memorie va fi 04|00

`!(a5 - a2)` = !4 = 0
În memorie va fi 00|00

==?????????????????????????????????????????????????==

##### `a7 dw [a2], ~a2`
==! Valoarea din a2 nu e determinabilă la momentul asamblării !==
==! Nu se pot face operații pe biți decât cu valori scalare !==

##### `a8 dd 256h^256, 256256h`
`256h^256`
`0000 0010 0101 0110` = 256h
`0000 0001 0000 0000` = 256
`0000 0011 0101 0110` = 0356h pe word = 00 00 03 56 pe dword
În memorie va fi 56|03|00|00

`256256h` în memorie va fi 56|62|25|00

##### `a9 dd $ - a9`
Prima dată atribuie valoare, apoi crește contorul => va fi 0
În memorie va fi 00|00|00|00

##### `a10 db 256, -255`
256 = 2<sup>8</sup> = 1 0000 0000
Suntem pe byte deci se salvează numai ultimul octet
În memorie va fi 00

-255
-255 + 256 = 1 (pe byte scăderea sau adunarea cu 256 nu schimbă ultimul octet)
În memorie va fi 01

##### `a11 dw 256h - 256`
`0000 0010 0101 0110 -`
`0000 0001 0000 0000`
`-------------------`
`0000 0001 0101 0110` = 0156h
În memorie va fi 56|01

##### `a12 dw 256-256h`
`0000 0001 0000 0000 -`
`0000 0010 0101 0110`
`-------------------`
`1111 1110 1010 1010` = FE AA
În memorie va fi AA|FE

##### `a13 dw -256`
Aici nu merge regula cu +256, deoarece suntem pe word
|-256| = 256 == 1 0000 0000
Complementul față de 2 => 1111 1111 0000 0000 = FF 00
În memorie va fi 00|FF

##### `a14 dw -256h`
|-256h| = 256h = 0000 0010 0101 0110
Complementul față de 2 => 1111 1101 1010 1010 = FD AA
În memorie va fi AA|FD

##### `a15 db 2, 5, 6, 25, 6, 2, 56`
Numerele se transformă în hexa
În memorie va fi 02|05|06|19|06|02|38

## 3.1. Care este numărul MINIM de biți necesari pentru reprezentarea numerelor de mai jos? Justificați și explicați răspunsul prin detalierea mecanismului de reprezentare a valorilor (exemplu: numărul minim de biți necesari pentru reprezentarea numărului 5 este 3: 101b; pentru 16 e 5: 10000b). Pentru fiecare număr scrieți reprezentarea în baza 2 și baza 16.
##### 61
![[Pasted image 20250127184207.png]]
##### -62
![[Pasted image 20250127184230.png]]
##### 130
![[Pasted image 20250127184256.png]]
##### -129
![[Pasted image 20250127184310.png]]

## 3.2. Scrieți o singură instrucțiune ASM care să aibă același efect (excepție unele flag-uri) ca și secvența dată și explicați/justificați de ce se obține același efect. Detaliați efectul fiecărei linii din secvența dată

```asm
xor ah, ah
cwde
add ebx, eax
mov al, [ebx]
```

`xor ah, ah`
Pune 0 în AH
`cwde`
Pune în partea high din EAX 0 (EAX va fi 00|00|00|AL)
`add ebx, eax`
EBX va fi EBX + AL
`mov al, [ebx]`
AL va fi [EBX + AL] => echivalent cu `xlat`

## 4.1. Prezentați și justificați structura din memorie a următorului segment. Dacă identificati date sau linii sursăpe care le considerați incorecte sintactic, justificati motivul și ignorați apoi acele valori sau linii în construirea modulului de reprezentare în memorie a segmentului de date.

```asm
a1 db '256,-256'
a2 dw 256, 256h
a3 dw $ - a2
a4 equ -256/4
a5 db 128>>1, -128<<1
a6 dw a2 - a5, ~(a2 - a5)
a7 dd [a2], !a2
a8 dd 256h ^ 256, 256256h
a9 dd ($ - a8) + (a10 - $)
a10 dw -255, 256
a11 resb 6
a12 times 4 dw 256
a13 dw times 4 -128
times 2 resw 2
times 2 dd 12345678h
```

##### `a1 db '256,-256'`
Le consideră caractere pe fiecare, un caracter ocupă un byte
În memorie va fi '2'|'5'|'6'|','|'-'|'2'|'5'|'6'

##### `a2 dw 256, 256h`
256 = 2<sup>8</sup> = 0000 0001 0000 0000 = 0100h
În memorie va fi 00|01

256h = 0256h
În memorie va fi 56|02

##### `a3 dw $ - a2`
`$ - a2` = 4 
Au fost declarate 2 word-uri = 4 bytes între locația curentă și a2

*Dar dacă ar fi fost* `a3 dw 20h, $ - a2` *?*
*Ar fi fost la fel,* `$` *e începutul liniei*

##### `a4 equ -256/4`
==! Nu ocupă memorie !==

##### `a5 db 128>>1, -128<<1`
128>>1 = 2<sup>7</sup> / 2<sup>1</sup> = 2<sup>6</sup> = 64 = 0100 0000 = 40h
În memorie va fi 40

-128<<1
|-128| = -128 + 256 = 128
128 * 2 = 256 = 2<sup>8</sup> = 1 0000 0000
Suntem pe byte, luăm doar ultimii 2 octeți
În memorie va fi 00

***Alternativ***

-128 * 2 = -256
Nu încape pe byte
-256 + 256 = 0
În memorie va fi 00

##### `a6 dw a2 - a5, ~(a2 - a5)`
a2 - a5 = -6 (între a2 și a5 sunt 6 bytes)
|-6| = 6 = 0000 0110
Complementul față de 2 = 1111 1010 = FA
Pe word se face FF FA
În memorie va fi FA|FF

~(a2 - a5) = ~1111 1111 1111 1010 = 0000 0000 0000 0101 = 0005h
În memorie va fi 05|00

##### `a7 dd [a2], !a2`
==! Valoarea din a2 nu e determinabilă la momentul asamblării !==
==! Nu se pot face operații pe biți decât cu valori scalare !==

##### `a8 dd 256h ^ 256, 256256h`
`0000 0010 0101 0110` = 256h
`0000 0001 0000 0000` = 256
`0000 0011 0101 0110` = 0356h
În memorie va fi 56|03|00|00

256256h
În memorie va fi 56|62|25|00

##### `a9 dd ($ - a8) + (a10 - $)`
`$ - a8` = 8 (8 bytes (2 dword-uri) între locația curentă și a8)
`a10 - $` = 4 (4 bytes (1 dword) între locația curentă și a10)

##### `a10 dw -255, 256`
-255
|-255| = 255 = 0000 0000 1111 1111
Complementul față de 2 = 1111 1111 0000 0001 = FF01h
În memorie va fi 01|FF

256 = 2<sup>8</sup> = 0000 0001 0000 0000 = 0100h
În memorie va fi 00|01

##### `a11 resb 6`
Rezervă 6 bytes
Un byte este |00|
În memorie va fi 00|00|00|00|00|00

##### `a12 times 4 dw 256`
256 = 2<sup>8</sup> = 0000 0001 0000 0000 = 0100h
Pune de 4 ori în memorie asta
În memorie va fi 00|01|00|01|00|01|00|01

##### `a13 dw times 4 -128`
*Syntax error*
Trebuia să fie `a13 times 4 dw -128`

##### `times 2 resw 2`
Rezervă de 2 ori 2 word-uri
Un word este 00|00
Două word-uri 00|00|00|00
De două ori două word-uri 00|00|00|00|00|00|00|00
În memorie va fi 00|00|00|00|00|00|00|00

##### `times 2 dd 12345678h`
Un dword este 00|00|00|00
Numărul 12345678h încape pe dword
Pune în memorie de două ori 78|56|34|12
În memorie va fi 78|56|34|12|78|56|34|12



<div style="background-color: #E77A59; height: 10px; width: 100%;"></div>
# ***Cerința III***
<div style="background-color: #E77A59; height: 10px; width: 100%;"></div>



skibidi
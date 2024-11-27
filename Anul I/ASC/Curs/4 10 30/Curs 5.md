# **Regiștrii de adresă și calculul de adresă**

BIU = Bus Interface Unit

BUS - folosit când avem un operand din memorie

## Regiștrii Segment pe 16 biți
CS - Code Segment, 16 biți
DS - Data Segment, 16 biți
SS - Stack Segment, 16 biți
ES - Extra Segment, 16 biți
FS - Extra Registru Segment adăugat la programarea pe 32 de biți
GS - Extra Registru Segment adăugat la programarea pe 32 de biți

EIP - Extended Instruction Pointer, 32 biți

<hr>

O succesiune continuăde locații de memorie, menite să deservească scopuri similare în timpul execuției unui program, formează *un segment*.

**!! Segment logic !!**

### Segment Fizic
"A block of memory of discrete size, called a *physical segment*. The number of bytes in a physical memory segment is:
1 MB for 16 bits
4 GB for 32 bits
"

### Segment Logic
"A variable-sized block of memory, called a *logical segment* occupied by a program's code or data"

<hr>

Programatorii nu lucrează *niciodată* cu adrese fizice efective. Ei lucrează doar cu așa-numitele <u>"specificări de adresă"</u>

*Specificare de adresă*/*Segment logică* = o pereche formată dintr-un **selector de segment** și un **offset**

adresă_segment (16 biți) : offset (32 biți)
^
|
Selector de segment, furnizat de Sistemul de Operare

### Tabel de selectori

| index | Baza / Adresa fizică de început de segment | Size of |
| ----- | ------------------------------------------ | ------- |
| 17    | ...                                        | ...     |
| 32    | ...                                        | ...     |
| 1614  | ...                                        | ...     |
| 2377  | ...                                        | ...     |
| ...   | ...                                        | ...     |
Noi nu vedem ce se află pe a doua coloană

Un segment logic este caracterizat de:
- adresa de bază/început
- limita/dimensiune/"size of"
- tipul lui (cod/date/stivă/extra)

Determinarea adresei de segmentare din specificarea de adresă se face printr-un *calcul de adresă*, cu formula:
<span style="background-color:#8a6624">a<sub>7</sub>a<sub>6</sub>a<sub>5</sub>a<sub>4</sub>a<sub>3</sub>a<sub>2</sub>a<sub>1</sub>a<sub>0</sub> := b<sub>7</sub>b<sub>6</sub>b<sub>5</sub>b<sub>4</sub>b<sub>3</sub>b<sub>2</sub>b<sub>1</sub>b<sub>0</sub> + o<sub>7</sub>o<sub>6</sub>o<sub>5</sub>o<sub>4</sub>o<sub>3</sub>o<sub>2</sub>o<sub>1</sub>o<sub>0</sub></span>

<span style="background-color:#8a6624">a<sub>7</sub>a<sub>6</sub>a<sub>5</sub>a<sub>4</sub>a<sub>3</sub>a<sub>2</sub>a<sub>1</sub>a<sub>0</sub></span> este adresa calculată (scrisă în hexazecimal) numită și *adresă liniară* (sau *adresă de segmentare*)

<hr>

!! Întotdeauna combinația de regiștrii *CS EIP* va reprezenta adresa instrucțiunii curente de executat !!

<hr>

## ==Întrebare de examen==
Ce conțin regiștrii segment?
16 biți - adresele de început ale segmentelor al instrucțiunii curente
32 biți - selectorii de segment al instrucțiunii active

<hr>

## Rezumat
aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa

| Noțiune                                          | Reprezentare                                | Descriere                                                                                                                                                          |
| ------------------------------------------------ | ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Specificare de adresă, adresă logică, adresă FAR | Selector<sub>16</sub> : Offset<sub>32</sub> | Definește complet atât segmentul cât și deplasamentul în cardul acestuia                                                                                           |
| Selector de segment                              | 16 biți                                     | Identifică unul dintre segmentele disponibile. Ca valoare numerică acesta codifică poziția descriptorului de segment selectat în cadrul unei tabele de descriptori |
| Offset, adresă FAR                               | 32 biți                                     | Definește doar componenta de offset (considerând că segmentul cunoscut ori folosirea modeluluide memorie flat)                                                     |
| Adresă liniară (adresă de segment)               | 32 biți                                     | Început segment + offset, reprezintă rezultatul calculului de adresă                                                                                               |
| Adresă fizică efectivă                           | Cel puțin 32 biți                           | Rezultatul final al segmentării plus, eventual, paginării. Adresa finală obținută de către BIU, indicând memoria fizică (hardware)                                 |

## Adrese FAR și NEAR
O adresă FAR este o specificare completă de adresă și ea se poate explica în nivelul unui program în trei moduri:
- s<sub>3</sub>s<sub>2</sub>s<sub>1</sub>s<sub>0</sub> : specificare_offset | unde s<sub>3</sub>s<sub>2</sub>s<sub>1</sub>s<sub>0</sub> este o constantă
- registru_segment : specificare_offset | registru segment fiind CS/DS/SS/ES/FS/GS
- `FAR [variabilă]` | unde variabilă este de tip quadword și conține cei 6 octeți constituind adresa FAR (ceea ce numim variabila pointer în limbajele de nivel înalt)

Reprezentarea adreselor respectă principiul *reprezentării little endian*

### Trei moduri de specificare a unui operand explicit:
<ol style="list-style-type: lower-roman">
<li>modul <u>registru</u> - mov <u>eax</u>, 17</li>
<li>modul <u>imediat</u> - mov eax, <u>17</u></li>
<li>modul <u>adresare la memorie</u> - în acest caz, offset-ul său se calculează după următoarea formulă: <br><span style="background-color:#8a6624">adresă_offset = [bază] + [index * scală] + [constantă] <sup>*</sup></span><br> &emsp; parantezele drepte reprezintă <i>opționalitatea</i></li>
</ol>
Registru de bază: EAX, EBX, ECX, EDX, EBP, ESI, EDI, ESP
Registru de index: EAX, EBX ECX, EDX, EBP, ESI, EDI
- ESP *nu* are voie sa fie registru de index

<sup>*</sup>*Formula de 1 noaptea*

### Moduri de adresare la memorie:
- **directă**, atunci când apare numai <span>[constantă]</span>
- **bazată**, dacă apare unul dintre regiștrii de bază
- **scalar-indexată**, dacă apare unul dintre regiștrii de index

Cele trei moduri de adresare a memoriei pot fi combinate. De exemplu poate să apară adresare directă bazată, adresare bazată și scalar-indexată, etc.

<hr>

```asm
mov ebx, 17152

mov eax, ebx; eax = ebx = 17152

mov eax, [ebx]
;eax preia valoarea de la adresa 17152
;
; .....|.....|.....| F0h | 56h | 34h | 79h | ... | ... | ... 
; 17149 17150 17151 17152 17153 17154 17155 17156 17157 17158
;
; eax = 793456F0h
```

Conținut = variabilă
Adresa = constantă
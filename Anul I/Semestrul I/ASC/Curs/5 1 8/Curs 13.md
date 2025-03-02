# ~
## Apărătoruldebiți. Scoală-te.
### Programare multi-modul.
<sub>Marius Vanța</sub>
<sub>mvanta@bitdefender.com</sub>

<hr>

# **Implementarea apelului de subprograme**

```


		______    o        ______
        |    |    .        |    |           ________________/|
		|____|  0          |____|          /______\----\_____/
        |    | oO          |    |                  \____\
	    |__./.0.oO         |____|
        |   \o.0o          |    |
		|____|  O          |____|
        |    |             |    |
		|____|             |____|
        |    |             |    |
	    |____|             |____|
        |    |             |    |
________|____|_____________|____|_______________________________________
```

## Convenții de apel
- ###### Cum transmitem parametrii către subrutine?
	- Ce tipuri de parametrii se pot transmite?
	- În ce ordine?
	- Câți parametri? Oricâți?
- ###### Ce resurse sunt volatile? (poate să le altereze funcția apelată)
- ###### Unde se regăsește rezultatul?
- ###### Ce acțiuni de curățare (cleanup) sunt necesare post-apel?
	- Cine este responsabil sa le efectueze?
- ###### Convenții
	- Uzuale: *CDECL*, *STDCALL*, FASTCALL
	- Rar folosite sau învechite: PASCAL, FORTRAN, SYSCALL, etc.
	- Utilizator: în asamblare toae aspectele documentate de către o convenție sunt accesibile programatorului

<hr>

### Paranteză: *Resurse Volatile*
EAX, ECX, EDX, EFLAGS

<hr>

## *CDECL*
- Specifică limbajului C
- Cum transmitem parametrii către subrutine? **Prin împingerea lor pe stivă**
	- Ce tipuri de parametrii se pot transmite? Orice, dar necesită extins la minim *dword*
	- În ce ordine? Dreapta către stânga, adică invers ordinii de declarație
	- ...
==!! Vezi pe pdf asta + STDCALL !!==

## Interfațarea cu limbajele de nivel înalt
### *Apelul subrutinelor*
Etape:
1. **Cod de apel**: pregătirea și efectuarea apelului
2. **Cod de intrare**: intrarea în procedură și pregătirea execuției
3. **Cod de ieșire**: revenire și eliberarea resurselor ce au expirat

Acțiunile depind în funcție de convenția de apel a subrutinei apelate - dar etapele rămân aceleași
Etapele sunt tratate/implementate *automat* în codul generat de către compilatoarele limbajelor de nivel înalt
<sub>În asamblare <u>rămâne totul sarcina noastră</u></sub>

#### **Cod de apel**
Sarcini:
1. Salvare resurse volatile în uz: `push registru`
2. Asigurare respecatre constrângeri (ESP aliniat, DF = 0, ...)
3. Pregătire argumente (stivă, conform convenției): `push`
4. Efectuare apel: `call`
	- `call subrutină` când subrutina este linkeditată static
	- `call [subrutină]` când este dinamică (la linkeditare)
	- `call registru` sau `call [variabilă]` pentru dinamică la runtime

Subrutinele asm folosite *doar* din asm pot evita (din simplitate și/sau eficiență) aceste sarcini

##### TL;DR Vancea Style
*Cod Apel*
a) Salvare resurse volatile **(punctul 1)**
b) Transmitere parametrii **(punctul 3)**
c) Efectuare apel cu salvarea adresei de revenire **(punctul 4)**

#### **Cod de intrare**
Sarcini:
1. Configurarea unui <u>cadru de stivă</u> (stack frame): reper ebp sau esp?
2. Pregătire variabile locale ale funcției: `sub esp, număr_octeți`
3. Salvarea unei copii a resurselor nevolatile modificate: `push registru`
	- orice regiștrii cu excepția celor volatili

##### TL;DR Vancea Style
*Cod Apel*
a) Configurarea cadrului de stivă
b) Rezervarea spațiului pentru variabile locale
c) Salvarea resurselor nevolatile

**Cadru de stivă**
UPC - Unitate de Program Curentă (ce era înainte)
UPC este delimitat întotdeauna de regiștrii *EBP* și *ESP*
Stack frame-ul se formează în jurul lui f1
![[20250108_190739.jpg|500]]

#### *Cod de ieșire*
##### TL;DR Vancea Style
a) Restaurare resurse nevolatile
b) Eliberare variabile locale
c) Dezafectarea cadrului de stivă

<hr>

| **Apelant** | **Apelat** | **Cine generează cod apel**         | **Cine generează cod intrare**                            | **Cine generează cod ieșire**    |
| ----------- | ---------- | ----------------------------------- | --------------------------------------------------------- | -------------------------------- |
| C           | C          | Compilatorul C                      | Compilatorul C                                            | Compilatorul C                   |
| C           | ASM        | Compilatorul C                      | Programatorul ASM                                         | Programatorul ASM                |
| ASM         | C          | Programatorul ASM                   | Compilatorul C                                            | Compilatorul C                   |
| ASM         | ASM        | call (salvarea adresei de revenire) | Nimic obligatoriu. Totul rămâne la voința programatorului | ret (recuperare adresă revenire) |

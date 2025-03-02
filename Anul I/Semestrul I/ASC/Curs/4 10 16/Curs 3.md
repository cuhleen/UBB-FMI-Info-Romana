## Computer pe 'n' biți
Viziunea hardware și software

## [[Curs 2#Operații făcute de ALU|ALU Reminder]]

## Flag-uri 🏳️‍🌈

`  1001 0011+`
`  0111 0011`
`___________`
`1 0000 0110`
^
*CF = Carry Flag* - are rol doar pentru adunare și scădere, pentru UOE<sup>*</sup>. Nu este implicat deloc în operația de împărțire, și este implicat doar parțial în operația de înmulțire

*PF = Parity Flag* - valoarea lui se stabileşte a.î. împreună cu numărul de biţi 1 din octetul cel mai puțin semnificativ al reprezentarii rezultatului UOE să rezulte un număr impar de cifre 1
- În exemplul de mai sus, în octetul cel mai puțin semnificativ (0110) se află 2 cifre de 1, deci după definiție, PF se face 1

*AF = Auxiliary Flag* - indică valoarea transportului de la bitul 3 la bitul 4 al rezultatului UOE

*ZF = Zero Flag* - primeşte valoarea 1 dacă rezultatul UOE este egal cu zero şi valoarea 0 la rezultat diferit de zero
- În exemplul de mai sus, ZF este 0

*SF = Sign Flag* - primeşte valoarea 1 dacă rezultatul UOE este un număr strict negativ şi valoarea 0 în caz contrar
- În exemplul de mai sus, SF este 0

*TF = Trap Flag* - dacă TF = 1, procesorul se oprește după executia fiecărei instrucțiuni. Flag de debugging/depănare

*IF = Interrupt Flag* - definește secțiuni critice în program. Dacă este 1, sunt permise întreruperi. Dacă este 0, până va deveni 1 înapoi, secțiunea de cod este un "time critical section"

*DF = Direction Flag* - dacă este 0, mergem ascendent, în mod normal, de la cap la coadă. Dacă este 1, mergem descendent, de la coadă la cap

*OF = Overflow Flag* - utilizat pentru depășire. Dacă OF este 1 rezultatul nu a încăput, dacă este 0 a încăput

Două categorii
1. Flag-uri care sunt setate ca efect al UOE
	1. **CF**
	2. PF
	3. AF
	4. ZF
	5. SF
	6. OF
2. Flag-uri pe care le setăm noi
	1. **CF** (`CLC` îl face 0, `STC` îl face 1, `CMC` (Coplement Carry Flag) îl face 0 dacă era 1 sau invers)
	2. DF (`CLD` îl face 0, `STD` îl face 1)
	3. TF (Nu exista instrucțiuni de acces direct la Trap Flag)
	4. IF (`ClI` îl face 0, `STI` îl face 1)

<sup>*</sup><sub>UOE - Ultima Operație Efectuată</sub>

<hr>

### Dacă nu putem utiliza direct Carry Flag-ul, atunci ce facem cu el?
#### **ADC** op1, op2
op1 = op1 + op2 + CF

*Evident* într-un octet superior

Exemplul de mai sus *NU* poate fi reprezentat într-un byte, fiind 262 (93h + 73h = 106h = 147 + 115 = 262), care este mai mare ca 255 (byte-ul fără semn ia valori între 0 și 255, byte-ul cu semn ia valori între -128 și 127)

<hr>

Calculatoarele, computerele, procesoarele lucreaza cu *reprezentări* în baza 2 ale numerelor în baza 10
*O reprezentare* în baza 2 are **două interpretări** în baza 10

<hr>

### Luăm exemplul de mai sus
`  1001 0011+`
`  0111 0011`
`___________`
`1 0000 0110`

#### Fără semn ar fi:
`147+`
`115`
`___`
`262`
**Aici trebuie să ținem cont de valoarea din *Carry Flag***

În interpretarea fără semn această operație provoacă depășire, marcată de setarea lui CF cu 1

#### Cu semn ar fi:
`-109+`
` 115`
`____`
`   6`
**Aici *NU* trebuie să ținem cont de valoarea din *Carry Flag***
***Dar* ținem cont de valoarea din *Overflow Flag***

În interpretarea cu semn această operație binară *nu* provoacă depășire, și setează OF cu 0

Dacă OF = 1 operația nu e corectă din punct de vedere matematic -> trebuie mai mulți octeți

<hr>

# ***!! Întrebări de examen !!***
### ==Cât este TF/IF/DF după o sumă ca în exemplul de mai sus?==
#### Răspuns: Același ca înainte, flag-urile acestea nu sunt afectate de operația de calcul

### ==Care este diferența dintre Carry Flag și Overflow Flag?==
#### Răspuns: woofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoofwoofwoofwoofwoofbarkbarkbarkwoofbarkwoof 🐺 :3 :3 :3 :3 

<hr>

## **Subiectul de data viitoare:** *Complementul Față De Doi* - [[Semestrul I/ASC/Curs/4 10 23/Curs 4]] (Important pt examen!!)

Pentru calculator, cifra '0' este pozitivă (între timp ce pentru noi este în propria categorie)
Categorii de numere pt oameni: negative, zero, pozitive


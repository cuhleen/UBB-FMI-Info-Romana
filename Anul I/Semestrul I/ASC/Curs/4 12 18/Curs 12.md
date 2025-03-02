==Vezi pdf!!==
# Reprezentarea instrucțiunilor mașină
Ce bytes generează asamblorul : [prefix] + cod + [ModRM] + [SIB] + [deplasament] + [imediat]

*ModRM = mod register / memory*
*SIB = Scale Index Base (formula de 2 noapte)*

==Sunt 4 tipuri de prefixe în limbajul de asamblare, vom discuta despre ele în ianuarie==

- Instruction prefix (`REP`, `REPE`, `REPNE`, ...)
- Segment override prefix - specificare explicită a unui registru segment
- Operand size prefix
- Address size prefix

## Instruction prefix:
`*rep* movsb` *F3:A4* | `rep` este F3
(Mai multe pe pdf suport)

## Segment override prefix:
CS = 2Eh
SS = 36h
DS = 3Eh
ES = 26h
FS = 64h
GS = 65h

`es xlat` *26:D7*

`mov eax, [ebx]` *8B03* | *03* este EBX, registru destinație | *8B* este de la `mov`
`mov eax, [SS:ebx]` - *36:8B03* | *36* e de la SS
`mov eax, [CS:ebx]` = *2E:8B03* | *2E* e de la CS
`es lodsb` *26:AC* | lods byte ptr ES:[ESI]
`es cmpsb` *26:A6* | cmps byte ptr ES:[ESI], byte ptr ES:[EDI]
`es stosb` *26:AA* | stos byte ptr ES:[EDI] - superfluous segment override prefix
`movsb` *A4* | movs byte ptr ES:[EDI], byte ptr *DS*:[ESI]
`es movsb` *26:A4* | movs byte ptr ES:[EDI], byte ptr *ES*:[ESI]
`es scasb` *26:AE* | scas byte ptr ES:[EDI] - superfluous segment override prefix

### **Operand override (din documentație)** - *66h*
Changes size of *data* expected by default mode of the instruction (e.g. 16-bit to 32-bit and vice-versa)

### **Address override (din documentație)** - *67h*
Changes size of *address* expected by the default mode of the instruction (e.g. 16-bit to 32-bit and vice-versa)

<hr>

`mov eax, [bx]`
Se poate pentru că respectă regula/formula de offset *pe 16 biți*.
#### Formula de offset *pe 16 biți*
adr_offset = [BX|BP] + [SI|DI] + [const]

<hr>

## Operand size prefix
### Bits32 - default mode of the below code
`cbw` *66:98* - deoarece rez. nu este pe 16 biți (AX)
`cwd` *66:99* - deoarece rez. este format din 2 reg. pe 16 biți (DX:AX)
`cwde` *98* - aici se respectă modul default pe 32 biți. Rez. în EAX
`push ax` *66:50* - deoarece se încarcă pe stivă o val. pe 16 biți, stiva fiind organizată implicit (default) pe 32 biți
`push eax` *50* - ok, utilizare consistentă cu modul default
`mov ax, a` *66:B8 0010* - deoarece rez. este pe 16 biți

### Bits16
`cbw` *98*
`cwd` *99*
`cwde` *66:98*

## Address size prefix
### Bits32
`mov eax, [bx]` *67:8B07* - deoarece DS:[BX] este adresare pe 16 biți

### Bits16
`mov bx, [eax]` *67:8B18* - deoarece DS:[EAX] este adresare pe 32 biți

`push dword [ebx]` *66:67:FF33* - Aici modulul default este Bits16. Deoarece adresarea [EBX] este pe 32 biți, apare *67*, și deoarece se face push la un operand dword în loc de unul pe 16 biți, apare *66* ca prefix
`push dword [CS:ebx]` *2E:66:67:FF33*
`rep push dword [CS:ebx]` *F3:2E:66:67:FF33*
`rep push dword ptr CS:[BP+DI]` - superfluous REPxx fix! OllyDbg!

<hr>

## ==Pe scurt ce trebuie să știm pt examen:==
Care sunt prefixele de instrucțiune (NU codificarea)
Să știm să spunem că primele două prefixe sunt mentionate explicit de programator 
Să spunem că a doua categorie de prefixe sunt cele implicite pe care NU le punem noi, ci asamblorul
+câteva exemple

<hr>

## *Definiție pompoasă - prefixe*
Prefixele de instrucțiuni sunt construcții ale limbajului de asamblare care apar opțional în componența unei linii sursă (prefixe explicite) sau apar în formatul intern al unei instrucțiuni (prefixe generate în mod implicit de către asamblor în două situații) și care modifică comportamentul standard al acelor instrucțiuni (în cazul prefixelor explicite), sau care semnalează procesorului modificarea dimensiunii implicite de reprezentare a operanzilor sau/și a adreselor , dimensiuni stabilite prin directive de asamblare (Bits16 sau Bits32)

==!! Important să poți să zici asta cu propriile cuvinte !!==

:]
- Teo wuz here



<hr>



==Vezi pdf!!==
# Programare modulară
Pentru fiecare sub-probleme există deja rezolvări disponibile?
*Reutilizare:*
- **Fișiere sursă**
	- Refolosire cod și date din asamblare: Directiva `%include` (*NU* este programare multimodul, deoarece la compilare ajunge *DOAR UN SINGUR MODUL* obținut prin concatenarea textuală a fișierelor incluse!!)
- **Fișiere binare**
	- Refolosire cod și date din asamblare
	- Cod și date din limbaje de nivel înalt
	- Biblioteci

*Existența de fișiere binare separate implică **compilare separată***

# Tehnici și instrumente
Era și ceva aici dar ngl mă uitam pe telefon
Vezi pdf lawl

Legare *statică* la *linkeditare* - sumar responsabilități
- reprocesor: **text => text**
- asamblor: **instrucțiuni (text) => codificare binară (fișier obiect)**
- compilator: **instrucțiuni (text) => codificare binară (fișier obiect)**
- linkeditor: **fișiere obiect => bibliotecă sau program

Vezi pdf pt. detalii

<hr>

==În C blocarea exportului implicit = "static"==

<hr>

ok
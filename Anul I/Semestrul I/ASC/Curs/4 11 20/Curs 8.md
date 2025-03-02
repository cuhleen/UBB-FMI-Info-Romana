# **Operatorii de tip**
*Def.:* Specifică *tipurile* unor expresii și a unor operanzi *păstrați în memorie*. *Sintaxa* pentru aceștia este:
`tip expresie`
`tip` este unul dintre: `byte`, `word`, `dword`, `qword`
Operatori de conversie (temporară) nedistructivă
Instrucțiuni de conversie distructivă: `cbw`, `cwd`, `cdq`

```asm
v d? ...
a d? ...
b d? ...

; ------

push v; instrucțiune corectă. Top of stack primește offset-ul variabilei v
push [v]; syntax error, ambiguitate, nu știm mărimea
push word [v]; ok!
push dword [v]; ok!
push byte [v]; syntax error, nu avem compatibilitate pentru stive pe 2 biți
push qword [v]; syntax error, nu avem compatibilitate pentru stive pe 64 biți
mov eax, [v]; corect! EAX definește sizeof, 4 octeți
push [eax]; eax ca valoare este folosit ca și valoare de a mă trimite în memorie, dar nu știm cât să luăm de acolo. Syntax error
push word [eax]; ok!
push dword [eax]; ok!
push 15; depinde de asamblor. Specific NASM: <<push 15>> este echivalent cu <<push dword 15>>

pop [v]; syntax error, ambiguitate, nu știm mărimea
pop word [v]; ok!
pop dword [v]; ok!
pop byte [v]; syntax error. Invalid combination of pop
pop qword [v]; syntax error. Instruction not supported in 32 bit mode
pop v; NU e ambiguitate de mărime, offset-ul e pe 32 de biți. <<v>> este o adresă CONSTANTĂ, mereu R value (NU L value), nu poate fi atribuibilă. Syntax error (vezi footnote 1)
pop dword b; syntax error. La fel ^
pop [eax]; syntax error. Operation size not specified
pop word [eax]; syntax error. Ia adresa
pop dword [eax]; syntax error. Ia adresa
pop byte [eax]; syntax error
pop qword [eax]; syntax error
pop 15; syntax error. <<15>> este R value
pop [15]; syntax error. Operation size not specified
pop word [15]; ok!
pop dword [15]; ok!
pop byte [15]; syntax error
pop qword [15]; syntax error

mov [v], 0; syntax error. Operation size not specified
mov byte [v], 0; ok!
mov word [v], 0; ok!
mov dword [v], 0; ok!
mov [v], byte 0; ok!
mov [v], word 0; ok!
mov [v], dword 0; ok!

div [v]; syntax error. Operation size not specified
div byte [v]; ok!
div word [v]; ok!
div dword [v]; ok!
imul [v + 2]; syntax error. Operation size not specified
imul byte [v + 2]; ok!
imul word [v + 2]; ok!
imul dword [v + 2]; ok!

mov a, b; syntax error. <<a>> și <<b>> sunt ambele R value. Nu sunt atribuibile
mov [a], b; syntax error. Operation size not specified
mov dword [a], b; ok! Pune offset-ul lui <<b>> în <<a>>. Corect pentru că avem pointer pe 32 de biți
mov word [a], b; ok! Pune jumatea low din offset-ul lui <<b>> în <<a>>. Corect pentru că avem pointer pe 16 biți
mov byte [a], b; syntax error. NU avem pointer pe 8 biți

; !!!

mov eax, v; ia valoarea offset-ului în întregime, pe 32 de biți. Ok!
mov ax, v; ia valoarea offset-ului pe 16 biți (truncheată, se ia partea low). Ok!
mov ah, v; syntax error. nu există offset pe 8 biți

; !!!

mov a, [b]; syntax error. <<a>> este R value. Nu este atribuibilă
mov [a], [b]; syntax error. O instrucțiune nu poate să aibă doi operanzi din memorie

mul v; syntax error. Sintaxa lui mul și imul este reg/mem
mul [v]; syntax error. Operation size not specified
mul dword [v]; ok!
mul eax; ok!
mul [eax]; syntax error. Operation size not specified
mul byte [eax]; ok!
mul word [eax]; ok!
mul dword [eax]; ok!
mul 15; syntax error. 15 e constantă
```

<sup>1</sup>[R și L value](<Semestrul I/ASC/Curs/4 11 6/Curs 6.md#Aritmetica de pointeri și L-value R-value>)



```asm
a db 17, 19
b dw 1234h, 5678h
c dd 12345678h

mov ah, [a]; ok!
mov ah [b]; ok!
mov ah [c]; ok!

mov ah, a; syntax error. Este offset, iar offset pe un byte nu există

; ---

mov ax, [a]; ok!
mov ax [b]; ok!
mov ax [c]; ok!

mov ax, a; ok! Este offset pe 16 biți
mov ax b; ok! Este offset pe 16 biți
mov ax c; ok! Este offset pe 16 biți

; ---

mov eax, [a]; ok!
mov eax [b]; ok!
mov eax [c]; ok!

mov eax, a; ok! Este offset pe 32 biți
mov eax b; ok! Este offset pe 32 biți
mov eax c; ok! Este offset pe 32 biți

```

**Concluzie:** nu au contat tipurile de dată în cazul ăsta
Spre deosebire de programarea pe 16 biți și de alte asambloare pe 16 biți, ==asamblorul NASM *nu* asociază un tip de dată niciunei variabile==
`db` `dw` `dd` `dq` reprezintă modalități de populare cu date a zonei de memorie

În codul de mai sus, `a`, `b`, `c` sunt adrese, offset-uri, pe 32 de biți

# **Clasificarea erorilor în informatică**
## Eroare de sintaxă
*Diagnosticată* de asamblor/compilator
Eroare de asamblare

## Run-time error
Eroare la execuție
Programul "crapă", program crash
*Programul se oprește*

==Singura operație expusă la crash este la împărțire
Memory Violation Error==

## Eroare logică
Programul *funcționează până la capăt* sau rămâne în *ciclu inifnit*, însă **GREȘIT d.p.d.v. LOGIC**, obținându-se cu totul alte rezultate decât cele așteptate

## Fatal: Linking Error
De exemply în cazul unei *definiții duble* de variabilă
17 module și o variabilă trebuie să fie definită *doar* într-un singur modul! Dacă ea este definită în 2 sau mai multe module se va obține `Fatal: Linking Error!!! - Duplicate definition for symbol A1 !!!`

# **The steps followed by a program from source code to run-time:**
- Syntactic checking (done by assembler/compiler/interpreter)
- OBJ files are *generated* by assembler/compiler
- Linking phase (performed by a *Linker* = a tool provided by the OS, which checks the possible *Dependencies* between the OBJ files/modules). The result -> `.exe` file
- You (the user) are activating your `.exe` file (by clicking or entering)
- The *Loader* of the OS is looking for the required RAM memory space for your `.exe` file. When finding it, it loads the `.exe` file AND performs *Addres Relocation*
- In the end the *Loader*  gives control to the processor by specifying *The Program's Entry Point* (ex.: the start label). The run-time phase begins *now*

Mark Zbirkowski - semnătură EXE = MZ

aaaaaaaaaaaaaaaaaaaaAAAAAAAAAAAAAAAAAAAAAAAAAAA

he lost it - generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes generating bytes
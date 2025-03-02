; programare multi-modul (asm + asm)
; Se citește de la tastatură un șir de numere în baza 10, cu semn.
; Sa se determine valoarea minimă din șir și să se afișeze în fișierul min.txt (fișierul va fi creat) valoarea minimă, în baza 16. 

; NESPECIFICAT: Până când se citește de la tastatură?
; Presupunem că citim și un "n" ce reprezintă numărul de numere din șir

bits 32
global start
extern compare
extern exit, fopen, fclose, scanf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll

segment data use32 class=data

    n dd 0
    nrCitit dd 0
    formatCitire db "%d", 0
    
    numeFisier db "min.txt", 0
    descriptorFisier dd 0
    modAcces db "w", 0
    formatAfisare db "%x", 0

segment code use32 class=code
    start:

    ; Deschidem fișierul | fopen(numeFisier, modAcces)
    
    push dword modAcces
    push dword numeFisier
    call [fopen]
    add esp, 4 * 2
    
    mov [descriptorFisier], eax
    cmp eax, 0
    je iesireProgram
    
    ; Citim n
    push dword n
    push dword formatCitire
    call [scanf]
    add esp, 4 * 2
    
    mov ecx, [n]
    mov ebx, 7FFFFFFFh; Numărul inițial de comparat. Comparăm ca numărul nostru să fie mai mic sau egal decât cel curent, iar la început luăm valoarea maximă
    
    ; Citim elementele șirului

    citireTastatura:
        jecxz scrieFisier
        dec ecx
        
        push ecx; Se strică și îl pierdem altfel
        
        push dword nrCitit
        push dword formatCitire
        call [scanf]
        add esp, 4 * 2
        
        mov eax, [nrCitit]
        push eax; Punem numărul curent pe stivă pentru a-l trimite la modul
        call compare
        add esp, 4 * 1
        
        pop ecx; Reinstituim ECX
        
        jmp citireTastatura

    scrieFisier:
        push ebx; Valoarea minimă se află în EBX
        push dword formatAfisare
        push dword [descriptorFisier]
        call [fprintf]
        add esp, 4 * 3

    final:
        ; Închidem fișierul | fclose(descriptorFisier)
        push dword [descriptorFisier]
        call [fclose]
        add esp, 4 * 1

    iesireProgram:
    
    push dword 0
    call [exit]

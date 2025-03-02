;Se citesc cifre de la tastatură până la întâlnirea caracterului "!"
; Să se determine cel mai mare număr format prin folosirea fiecărei cifre impare citite o singură dată.
; Scrieți numărul obținut într-un fișier numit result.txt

bits 32

global start
extern exit, fopen, fclose, scanf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll

segment data use32 class=data

    cifra db 0              ; Cifra citită
    formatCitire db "%c", 0 ; Format pentru citire un caracter
    unu db 0                ; Flag pentru cifra 1
    trei db 0               ; Flag pentru cifra 3
    cinci db 0              ; Flag pentru cifra 5
    sapte db 0              ; Flag pentru cifra 7
    noua db 0               ; Flag pentru cifra 9

    modAcces db "w", 0
    numeFisier db "result.txt", 0
    descriptorFisier dd 0
    formatScriere db "Cel mai mare numar este: %d", 0

segment code use32 class=code
    start:
    
    ; Deschidem fișierul | fopen(numeFisier, modAcces)
    
    push dword modAcces
    push dword numeFisier
    call [fopen]
    add esp, 4 * 2
    
    mov [descriptorFisier], eax
    
    ; Verificăm dacă fișierul s-a deschis cu succes
    
    cmp eax, 0
    je final
    
    ; Începem să citim de la tastatură | scanf(formatCitire, cifra)
    
    citire_numar:
        lea eax, [cifra]
        push eax
        push formatCitire
        call [scanf]
        add esp, 4 * 2
    
        ; Verificăm dacă am citit caracterul de final "!"
        movzx eax, byte [cifra]
        cmp eax, '!'
        je calcul_numar
    
        ; Procesăm cifrele impare
        cmp eax, '1'
        je set_unu
        cmp eax, '3'
        je set_trei
        cmp eax, '5'
        je set_cinci
        cmp eax, '7'
        je set_sapte
        cmp eax, '9'
        je set_noua
        
        jmp citire_numar

    set_unu:
        mov byte [unu], 1
        jmp citire_numar

    set_trei:
        mov byte [trei], 1
        jmp citire_numar

    set_cinci:
        mov byte [cinci], 1
        jmp citire_numar

    set_sapte:
        mov byte [sapte], 1
        jmp citire_numar

    set_noua:
        mov byte [noua], 1
        jmp citire_numar

    calcul_numar:
        ; Construim cel mai mare număr posibil
        xor ecx, ecx ; ECX va fi numărul rezultat
        
        mov al, [noua]
        test al, al
        jz skip_9
        imul ecx, ecx, 10
        add ecx, 9
    skip_9:
        mov al, [sapte]
        test al, al
        jz skip_7
        imul ecx, ecx, 10
        add ecx, 7
    skip_7:
        mov al, [cinci]
        test al, al
        jz skip_5
        imul ecx, ecx, 10
        add ecx, 5
    skip_5:
        mov al, [trei]
        test al, al
        jz skip_3
        imul ecx, ecx, 10
        add ecx, 3
    skip_3:
        mov al, [unu]
        test al, al
        jz skip_1
        imul ecx, ecx, 10
        add ecx, 1
    skip_1:

        ; Scriem rezultatul în fișier | fprintf(descriptorFisier, formatScriere, ecx)
        push ecx
        push dword formatScriere
        push dword [descriptorFisier]
        call [fprintf]
        add esp, 4 * 3
    
        ; Închidem fișierul
        push dword [descriptorFisier]
        call [fclose]
    
    final:
        push dword 0
        call [exit]

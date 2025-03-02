; Se citește de la tastatură un număr natural n, urmat de mai multe numere pozitive (ultimul număr citit va fi 0).
; Să se scrie în fișierul output.txt numai numerele care conțin exact n cifre impare.

bits 32
global start
extern exit, scanf, fopen, fclose, fprintf
import exit msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll

segment data use32 class=data

    n dd 0
    formatCitire db "%d", 0
    formatScriere db "%d ", 0
    modAcces db "w", 0
    numeFisier db "output.txt", 0
    descriptorFisier dd 0
    numar dd 0

segment code use32 class=code
    start:

    ; Deschidem fișierul | fopen(numeFisier, modAcces)
    
    push dword modAcces
    push dword numeFisier
    call [fopen]
    add esp, 4 * 2

    mov [descriptorFisier], eax

    ; Verificăm dacă fișierul a fost deschis cu succes
    
    cmp eax, 0
    je final

    ; Citim n | scanf(formatCitire, &n)
    
    lea eax, [n]
    push eax
    push dword formatCitire
    call [scanf]
    add esp, 4 * 2

    ; Citim un număr | scanf(formatCitire, &numar)
    
    citire_numar:
        lea eax, [numar]
        push eax
        push dword formatCitire
        call [scanf]
        add esp, 4 * 2

        ; Verificăm dacă numărul este 0
        
        mov eax, [numar]
        cmp eax, 0
        je finalizare

    ; Verificăm câte cifre impare are numărul
    
    mov ecx, 0; Contor pentru cifre impare
    mov ebx, eax; Salvăm numărul în ebx

    verifica_cifre:
        cmp ebx, 0
        je compara_cifre

        ; Extragem ultima cifră
        
        mov edx, 0
        mov eax, ebx
        mov edi, 10
        div edi; eax = ebx / 10, edx = ebx % 10

        ; Verificăm dacă cifra este impară
        
        test edx, 1
        jz cifra_para

        inc ecx; Incrementăm numărul de cifre impare

    cifra_para:
        mov ebx, eax; Trecem la următoarea cifră
        jmp verifica_cifre

    ; Comparăm numărul de cifre impare cu n
    
    compara_cifre:
        mov eax, [n]
        cmp ecx, eax
        jne citire_numar

    ; Scriem numărul în fișier | fprintf(descriptorFisier, formatScriere, numar)
    push dword [numar]
    push dword formatScriere
    push dword [descriptorFisier]
    call [fprintf]
    add esp, 4 * 3

    jmp citire_numar

    finalizare:
    ; Închidem fișierul | fclose(descriptorFisier)
    push dword [descriptorFisier]
    call [fclose]

    final:
    push dword 0
    call [exit]

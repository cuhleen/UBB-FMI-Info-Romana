; Se citeste de la tastatura un nume de fisier, un caracter special s (orice caracter in afara de litere si cifre) si un numar n reprezentat pe octet.
; Fisierul contine cuvinte separate prin spatiu. Sa se scrie in fisierul output.txt aceleasi cuvinte transformate astfel:
; al n-lea caracter din fiecare cuvant se transforma in caracterul special.
; (Daca numarul de caractere al cuvantului este mai mic decat n, cuvantul se va prefixa cu caracterul special s )
;
; Exemplu:
; nume fisier:input.txt
; continut fisier: mere pere banane mandarine
; s: +
; n: 6
; output.txt: ++mere ++pere banan+ manda+

bits 32

global start
extern exit, fopen, fclose, scanf, fread, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fread msvcrt.dll
import fprintf msvcrt.dll

segment data use32 class=data

    numeFisier resb 100
    modAcces db "r", 0
    descriptorFisier dd 0
    len equ 100; lungimea textului din fișier
    text times len db 0
    
    numeFisierOutput dd "output.txt", 0
    modAccesOutput db "a", 0
    descriptorFisierOutput dd 0
    formatAfisareOutput db "%s", 0
    
    s db 0; caracterul special
    formatCitireString db "%s", 0
    
    n dd 0
    formatCitireNumar db "%d", 0
    
segment code use32 class=code
    start:
    
    ; Citim numele fișierului | scanf(formatCitireString, numeFisier)
    
    push dword numeFisier
    push dword formatCitireString
    call [scanf]
    add esp, 4 * 2
    
    ; Deschidem fișierul din care citim | fopen(numeFisier, modAcces)
    
    push dword modAcces
    push dword numeFisier
    call [fopen]
    add esp, 4 * 2
    
    ; Verificăm dacă fișierul s-a deschis cu succes
    
    cmp eax, 0
    je final
    
    mov [descriptorFisier], eax
    
    ; Deschidem fișierul în care afișăm | fopen(numeFisierOutput, modAccesOutput)
    
    push dword modAccesOutput
    push dword numeFisierOutput
    call [fopen]
    add esp, 4 * 2
    
    ; Verificăm dacă fișierul s-a deschis cu succes
    
    cmp eax, 0
    je final
    
    mov [descriptorFisierOutput], eax
    
    ; Citim caracterul special | scanf(formatCitireString, s)
    
    push dword s
    push dword formatCitireString
    call [scanf]
    add esp, 4 * 2
    
    ; Citim n | scanf(formatCitireNumar, n)
    
    push dword n
    push dword formatCitireNumar
    call [scanf]
    add esp, 4 * 2
    
    ; Începem să citim din fișier | fread(text, 1, len, descriptorFisier)
    
    push dword [descriptorFisier]
    push dword len
    push dword 1
    push dword text
    call [fread]
    add esp, 4 * 4
    
    ; Luăm caracter cu caracter ce se află în text, numărăm câte caractere sunt în fiecare cuvânt
 
    mov edx, 1; Folosesc EDX drept un toggle pentru ultimul cuvânt (vezi eticheta "ultimul_cuvant")
 
    mov ecx, len; folosit ca numărător
    lea esi, [text]; ESI indică începutul buffer-ului text
    
    xor ebx, ebx; resetăm numărul de caractere al cuvântului curent
    
    citire_cuvant:
        mov al, [esi]; caracterul curent
        
        cmp al, " "; dacă am citit complet un cuvânt
        je verifica_nr_litere
        cmp al, 0; sau dacă am ajuns la finalul textului
        je ultimul_cuvant
        
        inc ebx
        
    urmatorul_caracter:
        inc esi; trecem la următorul caracter
        loop citire_cuvant
    
    verifica_nr_litere:
        mov ecx, [n]
        cmp ebx, ecx
        ja sufix
        
        ; PRIMUL CAZ, PREFIX
        ; Scriem de două ori caracterul special, apoi cuvântul
        
        ; Scriere în fișier fprintf(descriptorFisierOutput, formatAfisareOutput, string)
        
        push dword s
        push dword formatAfisareOutput
        push dword descriptorFisierOutput
        call [fprintf]
        add esp, 4 * 3
        
        push dword s
        push dword formatAfisareOutput
        push dword descriptorFisierOutput
        call [fprintf]
        add esp, 4 * 3
        
        sufix:
        
        ; AL DOILEA CAZ, SUFIX
        ; Scriem caracterul special în loc de al n-lea caracter
        
        
        
        
        
        cmp edx, 0
        je inchide_fisiere
        xor ebx, ebx
        jmp citire_cuvant
    
    ultimul_cuvant:
        xor edx, edx
        jmp verifica_nr_litere
    
    inchide_fisiere:
    
        ; Închidem fișierele | fclose(descriptorFisier) | fclose(descriptorFisierOutput)
       
        push dword [descriptorFisier]
        call [fclose]
        add esp, 4 * 1
       
        push dword [descriptorFisierOutput]
        call [fclose]
        add esp, 4 * 1
    
    final:
    
    push dword 0
    call [exit]
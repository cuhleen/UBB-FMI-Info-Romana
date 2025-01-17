; Să se scrie un prgram în limbaj de asamblare care citește un nume de fișier (maxim 50 de caractere) de la tastatură.
; Programul va citi, apoi, conținutul întregului fișier dat (maxim 100 de caractere), va determina și va afișa pe ecran, în baza 16, numărul de consoane.
; Fișierul conține litere mici, mari, numere, spații, și caractere speciale.
; Nu este permisă utilizarea specificatorului de format "%x" pentru a afișa în baza 16.

bits 32
global start
extern exit, fopen, fclose, scanf, printf, fread
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll
import fread msvcrt.dll

segment data use32 class=data

    numeFisier dd 0
    formatCitire db "%s", 0
    formatAfisare db "%x", 0
    modAcces db "r", 0
    descriptorFisier dd 0
    nrConsoane dd 0
    
    len equ 100
    text times len db 0

segment code use32 class=code
    start:
    
    ; citire de la tastatură a numelui de fișier | scanf(formatCitire, numeFisier)
    
    push dword numeFisier
    push dword formatCitire
    call [scanf]
    add esp, 4 * 2
    
    ; deschidem fișierul | fopen(numeFisier, modAcces)
    
    push dword modAcces
    push dword numeFisier
    call [fopen]
    add esp, 4 * 2
    
    mov [descriptorFisier], eax
    
    ; verificăm dacă funcția fopen a deschis fișierul cu succes (dacă EAX != 0)
 
    cmp eax, 0
    je final
 
    ; Începem să citim din fișier | fread(text, 1, len, descriptorFisier)
    
    push dword [descriptorFisier]
    push dword len
    push dword 1
    push dword text
    call [fread]
    add esp, 4 * 4
    
    ; Luăm caracter cu caracter ce se află în text, verificăm dacă caracterele sunt consoane
 
    mov ecx, len; folosit ca numărător
    lea esi, [text]; ESI indică începutul buffer-ului text
    xor eax, eax; resetăm numărul de consoane
    
    contor_consoane:
        mov al, [esi]; caracterul curent
        cmp al, 0; dacă am ajuns la sfârșitul textului
        je calculeaza_consoane

        ; verificăm dacă este literă mare sau mică
        cmp al, 'A'
        jl urmatorul_caracter
        cmp al, 'Z'
        jle verifica_consoana
        cmp al, 'a'
        jl urmatorul_caracter
        cmp al, 'z'
        jle verifica_consoana
        
    verifica_consoana:
        cmp al, 'a'
        je urmatorul_caracter
        cmp al, 'e'
        je urmatorul_caracter
        cmp al, 'i'
        je urmatorul_caracter
        cmp al, 'o'
        je urmatorul_caracter
        cmp al, 'u'
        je urmatorul_caracter
        cmp al, 'A'
        je urmatorul_caracter
        cmp al, 'E'
        je urmatorul_caracter
        cmp al, 'I'
        je urmatorul_caracter
        cmp al, 'O'
        je urmatorul_caracter
        cmp al, 'U'
        je urmatorul_caracter
        
        inc dword [nrConsoane]
        
    urmatorul_caracter:
        inc esi; trecem la următorul caracter
        loop contor_consoane
    
    calculeaza_consoane:
        ; printf(formatAfisare, nrConsoane)
        mov eax, [nrConsoane]
        push eax
        push dword formatAfisare
        call [printf]
        add esp, 4 * 2
        
    ; închidem fișierul | fclose(descriptorFisier)
    
    push dword [descriptorFisier]
    call [fclose]
    add esp, 4 * 1
        
    final:
 
    push dword 0
    call [exit]
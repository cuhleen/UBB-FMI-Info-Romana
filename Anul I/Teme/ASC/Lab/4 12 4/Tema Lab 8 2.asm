; operații cu fișiere text
; 26.
; Se dă un nume de fișier (definit în segmentul de date).
; Să se creeze un fisier cu numele dat, apoi să se citească de la tastatură cuvinte până când se citește de la tastatură caracterul '$'.
; Să se scrie în fișier doar cuvintele care conțin cel puțin o literă mare (uppercase). 

bits 32
global start
extern exit, fopen, fclose, scanf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
segment data use32 class=data

	numeFisier db "ex26.txt", 0
    modAcces db "w", 0
    descriptorFisier dd 0
    formatCitire db "%s", 0
    buffer times 256 db 0
    terminator db "$", 0
    formatAfisare db "%s ", 0
    
segment code use32 class=code
	start:
    
    ; deschidem fișierul | fopen(numeFisier, modAcces)
    
    push dword modAcces
    push dword numeFisier
    call [fopen]
    add esp, 4 * 2
    
    mov [descriptorFisier], eax
    
    ; verificăm dacă funcția fopen a creat cu succes fișierul (dacă EAX != 0)
    
    cmp eax, 0
    je iesireProgram
    
    ; citire | scanf(formatCitire, buffer)
    
    citireTastatura:
        push dword buffer
        push dword formatCitire
        call [scanf]
        add esp, 4 * 2
    
    ; verificare citire '$'
    
    mov esi, buffer; ESI pointează la începutul buffer-ului
    
    verificaTerminator:
        lodsb; încărcăm un caracter în AL
        cmp al, 0; verificăm dacă este sfârșirul șirului
        je gasesteLiteraMare
        cmp al, '$'; verificăm dacă caracterul este '$'
        je final
        jmp verificaTerminator; altfel continuăm
    
    gasesteLiteraMare:
        mov esi, buffer; resetăm pointerul pentru a verifica literele mari
    verificaLiteraMare:
        lodsb; încărcăm un caracter în AL
        test al, al; verificăm sfârșitul șirului
        je citireTastatura; dacă am parcurs tot șirul și nu am găsit litere mari, citim alt cuvânt
        cmp al, 'A'
        jb verificaLiteraMare; ; dacă e mai mic decât 'A' (adică dacă este altceva decăt literă mare), trecem la următorul caracter
        cmp al, 'Z'
        ja verificaLiteraMare; ; dacă e mai mare decât 'Z' (adică dacă este altceva decăt literă mare), trecem la următorul caracter
        jmp scrieFisier
    
    ; scriere în fișier | fprintf(descriptorFisier, formatAfisare, buffer)
    
    scrieFisier:
        push dword buffer
        push dword formatAfisare
        push dword [descriptorFisier]
        call [fprintf]
        add esp, 4 * 3
        
        jmp citireTastatura
    
    final:
        ; închidem fișierul | fclose(descriptorFisier)
        push dword descriptorFisier
        call [fclose]
        add esp, 4 * 1
    
    iesireProgram:
    
    ; Exit program
	push dword 0
	call[exit]
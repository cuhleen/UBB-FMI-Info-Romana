; instrucțiuni de comparare, salt condiționat și de ciclare. Operații pe șiruri
; 26.
; Se dă un șir de octeți S
; Să se determine maximul elementelor de pe pozițiile pare și minimul elementelor de pe pozițiile impare din S

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	s db 1, -1, 2, 3, 8, 4, 9, 5
    l equ $ - s
    max_poz_pare db 0
    min_poz_impare db 0
    
segment code use32 class=code
	start:
    
    mov ecx, l; Punem lungimea șirului în ECX, fiind registrul ce se incrementează/decrementează în instrucțiunile de ciclare
    jecxz final; Dacă lungimea șirului este 0, sărim direct la final
    cld; "Clear Direction Flag". Direction Flag devine 0, atsfel parcurgerea șirului o efectuăm de la stânga la dreapta
    mov esi, s; Șirul sursă, pe care facem operațiile
    
    ; Primul element
    
    mov al, [esi]; Punem primul element în AL
    mov [max_poz_pare], al; Fiind elementul pe poziția "0", poziție pară, este și cel mai mare
    inc esi; Trecem la următorul element

    dec ecx; Decrementăm ECX (am preluat deja primul element)
    jecxz final; În caz că șirul are un singur element
    
    ; Al doilea element

    mov al, [esi]; Punem al doilea element în AL
    mov [min_poz_impare], al; Fiind elementul pe poziția "1", poziție impară, este și cel mai mic
    inc esi; Trecem la următorul element
    
    dec ecx; Decrementăm ECX (am preluat deja al doilea element)
    
    ; Restul șirului
    
    bucla:
        jecxz final; Dacă ECX este zero, șirul s-a terminat

        mov al, [esi]; Elementul curent
        test esi, 1; Verificăm dacă poziția este pară sau impară
        jz pozitiePara; Dacă poziția este pară, sărim la `pozitiePara`

        ; Dacă nu a intrat în jump-ul de mai sus înseamnă că poziția este impară
        
        cmp al, [min_poz_impare]; Comparăm cu minimul curent
        jge urmatorulElement; Dacă este mai mare sau egal, trecem la următorul element
        mov [min_poz_impare], al; Altfel, actualizăm minimul
        mov bl, [min_poz_impare]; BL <- min_poz_impare. Să se vadă mai clar în Olly Debugger
        
        jmp urmatorulElement;

    pozitiePara:
        cmp al, [max_poz_pare]; Comparăm cu maximul curent
        jle urmatorulElement; Dacă este mai mic sau egal, trecem la următorul element
        
        mov [max_poz_pare], al; Altfel, actualizăm maximul
        mov dl, [max_poz_pare]; DL <- max_poz_pare. Să se vadă mai clar în Olly Debugger

    urmatorulElement:
        inc esi
        dec ecx
        jmp bucla
        
    ; max_poz_pare se găsește în DL
    ; min_poz_impare se găsește în BL

    final:
    ; Exit program
	push dword 0
	call[exit]
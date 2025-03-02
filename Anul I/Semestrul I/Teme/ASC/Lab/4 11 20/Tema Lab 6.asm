; operații pe șiruri de bytes/words/doublewords/quadwords
; 26.
; Se dă un șir de dublucuvinte.
; Să se obțină șirul format din octeții superiori ai cuvintelor inferioare din elementele șirului de dublucuvinte,
; care sunt multiplii de 10. 

; Exemplu:
; Se dă șirul de dublucuvinte:
; s DD 12345678h, 1A2B3C4Dh, FE98DC76h 
; Să se obțină șirul
; d DB 3Ch, DCh.

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	s dd 12345678h, 1A2B3C4Dh, 0FE98DC76h 
    l equ ($ - s) / 4
    d times l db 0
    zece db 10
    
segment code use32 class=code
	start:
    
    mov ecx, l; Punem lungimea șirului în ECX, fiind registrul ce se incrementează/decrementează în instrucțiunile de ciclare
    jecxz final; Dacă lungimea șirului este 0, sărim direct la final
    cld; "Clear Direction Flag". Direction Flag devine 0, atsfel parcurgerea șirului o efectuăm de la stânga la dreapta
    mov esi, s; Șirul sursă, pe care facem operațiile
    mov edi, d; Șirul destinație, unde adăugăm elementele adecvate
    
    repeta:
        lodsd; Încărcăm dublucuvântul elementul curent
        mov al, ah; Punem octeții superiori (cei care ne interesează) pe poziția octeților inferiori
        mov ah, 0; Golim AH pentru a nu avea reziduu când împărțim
        mov bl, al; Folosim bl drept un "aux", pentru a salva AL înainte de împărțire, ca apoi dacă este divizor al lui 10 să se poată pune în d
        div byte[zece]; AL <- AL <- AX / 10 (câtul); AH <- AX % 10 (restul)
        cmp ah, 0
        jnz sariFinalRepeta ; daca restul nu este 0, reluăm ciclul repeta. 
        
        mov [edi], bl; Punem în șirul d octeții ce se divid cu 10
        inc edi; Incrementăm adresa d
        
        sariFinalRepeta:
    loop repeta
        
    final:
    ; Exit program
	push dword 0
	call[exit]
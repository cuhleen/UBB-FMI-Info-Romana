; înmulțiri, împărțiri - a, b, c byte, d word
; 26. d + [(a + b) * 5 - (c + c) * 5]

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a DB 7
	b DB 3
    c DB 4
    d DW 8
    
    five DB 5
    
segment code use32 class=code
	start:
	
	mov AL, [a]; AL <- a = 7
    add AL, [b]; AL <- AL + b = 7 + 3 = 10
    
    mul byte [five]; AX <- AL * five = 10 * 5 = 50
    mov BX, AX; BX <- AX = 50
    mov AX, word 0; AX <- 0000 | eliberăm AX pentru a îl utiliza la următoarea înmulțire
    
    mov AL, [c]; AX <- c = 4
    add AL, [c]; AX <- AX + c = 4 + 4 = 8
    mul byte [five]; AX <- AL * five = 8 * 5 = 40
    
    sub BX, AX; BX <- BX - AX = 50 - 40 = 10
    
	add BX, [d]; BX <- BX + 8 = 10 + 8 = 18
    
	push dword 0
	call[exit]
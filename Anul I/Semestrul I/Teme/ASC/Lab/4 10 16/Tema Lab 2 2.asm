; adunări, scaderi - byte
; 26. (a + a) - (b + b) - c

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a DB 10
	b DB 3
    c DB 2
    
segment code use32 class=code
	start:
	
	mov AL, [a]; AL <- a = 10
    add AL, [a]; AL <- AL + a = 10 + 10 = 20
    
    mov AH, [b]; AH <- b = 3
    add AH, [b]; AH <- AH + b = 3 + 3 = 6
    
	sub AL, AH; AL <- AL - AH = 20 - 6 = 14
    sub AL, [c]; AL <- Al - c = 14 - 2 = 12
    
	push dword 0
	call[exit]
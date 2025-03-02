; adunări, scaderi - word
; 26. (a + c) - (b + b + d)

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a DW 10
	b DW 3
    c DW 2
    d DW 1
    
segment code use32 class=code
	start:
	
	mov AX, [a]; AX <- a = 10
    add AX, [c]; AX <- AX + c = 10 + 2 = 12
    
    mov BX, [b]; BX <- b = 3
    add BX, [b]; BX <- BX + b = 3 + 3 = 6
    add BX, [d]; BX <- BX + d = 6 + 1 = 7
    
	sub AX, BX; AX <- AX - BX = 12 - 7 = 5
    
	push dword 0
	call[exit]
; exerciții simple
; 26. 3 - 4

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a DB 3
	b DB 4
    
segment code use32 class=code
	start:
	
	mov AL, [a]; AL <- a = 3
	sub AL, [b]; AL <- AL - b = 3 - 4 = -1
    
	push dword 0
	call[exit]
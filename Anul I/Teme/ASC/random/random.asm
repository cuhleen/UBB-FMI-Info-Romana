bits 32
global start
extern exit, fopen, fclose, scanf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
segment data use32 class=data

	a dw 2c1dh
    b db 7ah, 3bh
    
segment code use32 class=code
	start:
    
    mov ax, [b]
    
    ; Exit program
	push dword 0
	call[exit]
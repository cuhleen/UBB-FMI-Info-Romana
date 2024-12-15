bits 32
global start
extern exit, fopen, fclose, scanf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
segment data use32 class=data

	a dd 10000h
    b dw 2
    
segment code use32 class=code
	start:
    
    mov al, -2
    mov bl, -128
    imul al
    
    ; Exit program
	push dword 0
	call[exit]
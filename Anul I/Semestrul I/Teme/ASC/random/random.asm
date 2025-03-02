bits 32
global start
extern exit, fopen, fclose, scanf, fprintf
import exit msvcrt.dll
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
segment data use32 class=data

    b dd 'sex'
    c dd 's'
    e dd 'ex'
    
segment code use32 class=code
	start:
    
    
    
    ; Exit program
	push dword 0
	call[exit]
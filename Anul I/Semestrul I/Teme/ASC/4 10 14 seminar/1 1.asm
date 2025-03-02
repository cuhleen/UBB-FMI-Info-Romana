; a +- b

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a DD 300
	b DD 213

segment code use32 class=code
	start:
	
	mov EAX, [a]; EAX <- a
	;add EAX, [b]; EAX <- EAX + b
    sub EAX, [b]; EAX <- EAX - b
    
	push dword 0
	call[exit]
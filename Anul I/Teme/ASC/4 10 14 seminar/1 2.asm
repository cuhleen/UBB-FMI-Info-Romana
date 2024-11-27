; (a + b) - (c + 10)

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a DB 6
	b DB 7
    c DB 2
    
segment code use32 class=code
	start:
	
	mov AL, [a]; AL <- a
	add AL, [b]; AL <- AL + b
    add [c], byte 10; c <- c + 10
    sub AL, [c]
    
	push dword 0
	call[exit]
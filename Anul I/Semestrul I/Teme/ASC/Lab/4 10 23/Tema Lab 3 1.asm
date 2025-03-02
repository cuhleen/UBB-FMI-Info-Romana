; adunări, scăderi
; a - byte, b - word, c - double word, d - qword | interpretare fără semn
; 26. (c - b + a) - (d + a) = c - b - d

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a db 3; a este nefolosit, fiind adunat o dată și scăzut o dată
    b dw 5
    c dd 10
    d dq 2
    
segment code use32 class=code
	start:
    
    ; c - b
	
	mov eax, dword [c]; EAX = c = 10
    mov ebx, 0
    mov bx, [b]; EBX = b
    sub eax, ebx; EAX = EAX - BX = 10 - 5 = 5
    
    ; c - b - d
    
    mov edx, [d]; EDX = partea inferioară a lui d
    mov ecx, [d + 4]; ECX = partea superioară a lui d
    
    sub ebx, edx; EBX = EBX - EDX = 8 - 5 = 3
    sbb ecx, 0; Scădem și din partea superioară a lui d, dacă este necesar
    
    ; Rezultatul se află în ECX:EBX = 0000 0000 0000 0003
    
	push dword 0
	call[exit]
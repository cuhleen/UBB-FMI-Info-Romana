; adunări, scăderi
; a - byte, b - word, c - double word, d - qword | interpretare cu semn
; 26. (c - d - a) + (b + b) - (c + a) = b + b - a - a - d

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a db 10
    b dw 5
    c dd 10; c este nefolosit, fiind adunat o dată și scăzut o dată
    d dq 2
    
segment code use32 class=code
	start:
    
    ; b + b
    
    mov eax, 0
    mov ax, [b]; AX = b = 5
    cwde; EAX = AX = 5
    mov ebx, eax; EBX = EAX = 5
    
    mov eax, 0
    mov ax, [b]; AX = b = 5
    cwde; EAX = AX = 5
    
    add ebx, eax; EBX = EBX + EAX = 5 + 5 = 10
    
    ; b + b - a - a
	
	mov al, [a]; AL = a = 10
    cbw; AX = AL = 10
    cwde; EAX = AX = 10
    sub ebx, eax; EBX = EBX - EAX = 10 - 10 = 0
    sub ebx, eax; EBX = EBX - EAX = 0 - 10 = -10 = FFFF FFF6
    
    ; b + b - a - a - d
    
    mov eax, ebx; EAX = EBX = -10 = FFFF FFF6
    cdq
    mov ebx, [d]; EBX = partea inferioară a lui d = 2
    mov ecx, [d + 4]; ECX = partea superioară a lui d = 0000 0000
    
    sub eax, ebx; EAX = EAX - EBX = -10 - 2 = -12 = FFFF FFF4
    sbb edx, ecx; EDX = FFFF FFFF
    
    ; Rezultatul se află în EDX:EAX = FFFF FFFF FFFF FFF4
    
	push dword 0
	call[exit]
; înmulțiri, împărțiri
; a - word, b - byte, c - word, d - doubleword, x - qword | interpretare fără semn
; 26. (a * a + b / c - 1) / (b + c) + d - x

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	a dw 20
    b db 5
    c dw 5
    d dd 300
    x dq 50
    rest1 dw 0
    rest2 dd 0
    
segment code use32 class=code
	start:
    
    ; a * a
    
    mov eax, 0
    mov ax, [a]; AX = a = 20
    mov bx, ax; BX = AX = 20
    mul bx; DX:AX = AX * BX = 20 * 20 = 400
    push dx
    push ax
    pop eax
    mov ebx, eax; EBX = EAX = 400

    ; b / c
    
    mov ax, 0
    mov al, [b]; AL = b = 5
    mov ecx, 0
    mov cx, [c]; CX = c = 5
    mov edx, 0
    div cx; DX:AX = AX / CX = 5 / 5 = 1
    mov [rest1], dx

    ; a * a + b / c - 1
    
    add eax, ebx; EAX = EAX + EBX = 1 + 400 = 401
    sub eax, 1; EAX = EAX - 1 = 401 - 1 = 400

    ; b + c
    
    mov ebx, 0
    mov bl, [b]; BL = b = 5
    add bx, [c]; BX = BX + c = 5 + 5 = 10

    ; (a * a + b / c - 1) / (b + c)
    
    mov edx, 0
    div ebx; EDX:EAX = EAX / EBX = 400 / 10 = 40
    mov [rest2], edx

    ; (a * a + b / c - 1) / (b + c) + d - x
    
    mov ebx, [x]
    mov ecx, [x + 4]
    
    add eax, dword [d]; EAX = EAX + d = 40 + 300 = 340
    cdq; EDX = 0000 0000
    sub eax, ebx; EAX = EAX - EBX = 340 - 50 = 290 = 0000 0122
    sbb ecx, edx; ECX = 0000 0000
    
    ; Rezultatul se află în ECX:EAX = 0000 0000 0000 0122
    
	push dword 0
	call[exit]
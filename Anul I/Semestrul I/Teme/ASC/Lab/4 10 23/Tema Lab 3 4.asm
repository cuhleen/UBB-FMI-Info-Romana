; înmulțiri, împărțiri
; a - word, b - byte, c - word, d - doubleword, x - qword | interpretare cu semn
; 26. (a * a + b / c - 1) / (b + c) + d - x

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

    a dw 12
    b db 3
    c dw -1
    d dd -100
    x dq -28
    rest1 dw 0; variabilă pentru păstrat restul lui b / c
    rest2 dd 0; variabilă pentru păstrat restul lui (a * a + b / c - 1) / (b + c)

segment code use32 class=code
    start:

    ; a * a
    
    mov eax, 0
    mov ax, [a]; AX = a = 12
    mov bx, ax; BX = AX = 12
    imul bx; DX:AX = AX * BX = 12 * 12 = 144
    push dx
    push ax
    pop eax
    cwde; EAX = AX = 144
    mov ebx, eax; EBX = EAX = 144

    ; b / c
    
    mov eax, 0
    mov al, [b]; AL = b = 3
    cbw; AX = AL = 3
    cwde; EAX = AX = 3
    mov ecx, 0
    push eax; îl punem pe EAX curent pe stivă
    mov ax, [c]; îl punem pe c pe ECX prin AX
    cwde; ^
    mov ecx, eax; ^
    pop eax; îl restituim pe EAX
    cdq; EDX:EAX = EAX pentru diviziune corectă cu semn
    
    idiv ecx; EDX:EAX = EAX / ECX = 3 / -1 = -3
    mov [rest1], dx

    ; a * a + b / c - 1
    
    add eax, ebx; EAX = EAX + EBX = -3 + 144 = 141
    sub eax, 1; EAX = EAX - 1 = 141 - 1 = 140
    mov ebx, eax; EBX = EAX = 140

    ; b + c
    mov eax, 0
    mov al, [b]; AL = b = 3
    cbw; AX = AL = 3
    cwde; EAX = AX = 3
    add ax, [c]; AX = AX + c = 3 + (-1) = 2

    ; (a * a + b / c - 1) / (b + c)
    
    mov ecx, eax; ECX = EAX = 2
    mov eax, ebx; EAX = EBX = 140
    
    mov edx, 0
    idiv ecx; EAX = EAX / EBX = 140/2 = 70
    mov [rest2], edx

    ; (a * a + b / c - 1) / (b + c) + d - x
    
    mov ebx, [x]
    mov ecx, [x + 4]
    
    add eax, dword [d]; EAX = EAX + d = 70 + (-100) = -30
    cdq; EDX = FFFF FFFF
    sub eax, ebx; EAX = EAX - EBX = -30 - (-28) = -30 + 28 = -2 = FFFF FFFE
    sbb ecx, edx; ECX = FFFF FFFF
    
    ; Rezultatul se află în ECX:EAX = FFFF FFFF FFFF FFFE

    ; Exit program
    push dword 0
    call [exit]

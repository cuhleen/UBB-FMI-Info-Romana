; înmulțiri, împărțiri - a, b, c, d byte, e, f, g, h word
; 26. (e + g - 2 * b) / c

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	e DW 10
    g DW 5
    b DB 3
    c DB 4
    
    two DB 2
    
segment code use32 class=code
	start:
	
	mov BX, [e]; BX <- e = 10
    add BX, [g]; BX <- BX + g = 10 + 5 = 15
    
    mov AL, [b]; AL <- b = 3
    mul byte [two]; AX <- AL * two = 3 * 2 = 6
    
    sub BX, AX; BX <- BX - AX = 15 - 6 = 9
    
    mov AX, BX; AX <- BX = 9 | mutat în AX pentru a efectua împărțirea
    div byte [c]; AL <- AX / c = 9 / 4 = 2 (câtul) | AH <- AX % c = 9 % 4 = 1 (restul)
    
	push dword 0
	call[exit]
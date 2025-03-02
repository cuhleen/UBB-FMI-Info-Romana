; Modulul "compare"
; Are un număr deja în EBX (EBX inițializat cu 7FFFFFFFh în main.asm)
; Primește prin stivă un număr
; Dacă numărul primit este mai mic decăt EBX, pune în EBX numărul primit
; Altfel, nu face nimic

bits 32
global compare
segment code use32 class=code public
    compare:
        mov eax, [esp + 4]
        cmp ebx, eax
        jle skip; signed
        
        mov ebx, eax
    skip:
        ret

bits 32

global _compare

segment code use32 class=code public
_compare:
    ; Parametrii funcției: 
    ; - currentMin (în eax)
    ; - num (în edx)

    ; Salvăm valorile de registru
    
    push ebx
    push ecx

    ; eax = currentMin, edx = num
    
    mov ebx, eax; Minim actual
    mov ecx, edx; Număr curent

    ; Comparăm dacă num < currentMin
    
    cmp ecx, ebx
    jge end_compare; Dacă num >= currentMin, nu facem nimic

    ; Actualizăm minimul cu num
    
    mov eax, ecx

end_compare:
    ; Restaurăm registrele
    pop ecx
    pop ebx

    ret

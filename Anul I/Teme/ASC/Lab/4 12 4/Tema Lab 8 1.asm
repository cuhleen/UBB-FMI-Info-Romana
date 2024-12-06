; apeluri de funcții sistem
; 26.
; Se citesc de la tastatură două numere, a și b.
; Să se calculeze valoarea expresiei (a-b)*k, k fiind o constantă definită în segmentul de date. Afișati valoarea expresiei (în baza 16). 

bits 32
global start
extern exit, scanf, printf
import exit msvcrt.dll
import scanf msvcrt.dll
import printf msvcrt.dll

segment data use32 class=data

	a dd 0
    b dd 0
    k dd 2
    rez1 dd 0
    rez2 dd 0

    formatCitire db "%d", 0; număr întreg
    formatAfisare db "%x%x", 0; număr hexa
    
segment code use32 class=code
	start:
    
    ; scanf(formatCitire, &a)
    
    push dword a
    push dword formatCitire
    ; push în ordine de la dreapta la stânga, pentru ca stack-ul să le scoată corect
    call [scanf]
    add esp, 4 * 2; resetăm poziția lui ESP după
    
    ; scanf(formatCitire, &b)
    
    push dword b
    push dword formatCitire
    call [scanf]
    add esp, 4 * 2
    
    ; (a - b) * k
    
    mov eax, [a]
    sub eax, [b]; EAX <- a - b
    imul dword [k]; EDX:EAX <- (a - b) * k
    
    mov [rez1], EDX
    mov [rez2], EAX
    
    ; printf(formatAfisare, rez1, rez2)
    
    push dword [rez2]
    push dword [rez1]
    push dword formatAfisare
    call [printf]
    add esp, 4 * 3
    
    ; Exit program
	push dword 0
	call[exit]
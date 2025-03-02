; operații pe biți
; 26.
; Se dau 2 dublucuvinte R și T. Să se obțină dublucuvântul Q astfel:
; biții 0-6 din Q coincid cu biții 10-16 a lui T
; biții 7-24 din Q concid cu biții obtinuți 7-24 în urma aplicării R XOR T
; biții 25-31 din Q coincid cu biții 5-11 a lui R

bits 32
global start
extern exit
import exit msvcrt.dll
segment data use32 class=data

	R dd 01110111010101111011011110101111b;  0111011 101010111101101111 0101111b
    T dd 10011011101111100100101111011110b;  1001101 110111110010010111 1011110b
    Q dd 0; până la final Q ar trebui să fie 0111101 011101001111111000 0010010b
    
segment code use32 class=code
	start:
    
    ; izolăm biții 10-16 ai lui T și îi punem pe pozițiile 0-6 din Q
    
    mov ebx, 0
    mov eax, [T]
    and eax, 00000000000000011111110000000000b; EAX = biții 10-16 ai lui T
    mov cl, 10
    ror eax, cl; deplasăm biții 10-16 la pozițiile 0-6
    or ebx, eax; adăugăm în Q

    ; calculăm R XOR T și izolăm bitii 7-24 din rezultat
    
    mov eax, [R]; EAX = R
    xor eax, [T]; EAX = R XOR T
    and eax, 00000001111111111111111110000000b; izolăm biții 7-24
    or ebx, eax; punem în Q, sunt deja pe pozițiile corecte

    ; izolăm biții 5-11 ai lui R și îi punem pe pozițiile 25-31 din Q
    mov eax, [R]
    and eax, 00000000000000000000111111100000b; izolăm bitii 5-11
    mov cl, 20
    rol eax, cl; mutăm biții 5-11 pe pozițiile 25-31
    or ebx, eax; adăugăm în Q

    ; punem rezultatul final în Q
    mov [Q], ebx
    
    ; Q încă se află în EBX
    ; EBX = 7AE9FC12h = 01111010111010011111110000010010b = numărul dorit
    
    ; Exit program
	push dword 0
	call[exit]

1.	Se dă un șir ASCIIZ de secvențe de litere separate prin caracterul spațiu în modulul a.asm. Scrieţi un program multimodul (asm+asm) care determină și afișează pe ecran secvențele de litere care au număr par de vocale și în același timp număr impar de consoane, și tipărește pe ecran în baza 16 numărul acestor secvențe, fără a utiliza specificatorii %x, %X. În acest scop, programul va apela o funcție denumită procesare definită în modulul b.asm, care primește ca parametru adresa de început a secvenței curente de prelucrat si verifică dacă secvența îndeplinește condițiile pentru afișare. Nu se va folosi nici o funcție de prelucrare de șiruri a limbajului C

# main.asm
```asm
bits 32 
global start        
 
extern exit, printf, procesare
import exit msvcrt.dll
Import printf msvcrt.dll

segment data use32 class=data
sir db 'Ana are mere Ada are fructul pasiunii Gigel are ananas Tudor are portocale si facem salata de fructe', 0
adresacuv dd 0
Nrcuv dd 0
Format db “%c”, 0
Final db 0
TabelaHexa db ‘0123456789ABCDEF’
Aux dd 0

segment code use32 class=code
    start:
       Mov esi, sir
       Cld
        Repeta:
              Mov [adresacuv], esi
              Push esi
               Call procesare
               Add esp,4*1
              Dec esi
              Cmp byte [esi], 0
              Jnz nu_e_final

             Mov byte [final], 1
             nu_e_final:
              Cmp eax, 1
             Jz urmatorul
                 Mov byte [esi], 0
                 Push dword [adresacuv]
                 Call [printf]
                 Add esp, 4*1
                 Inc dword [nrcuv]
            
           Urmatorul:
   Inc esi
   
  Cmp byte [final], 1
  Jnz repeta   

  Mov ecx, 0
  Mov eax,  [nrcuv]     
  Mov ebx, 16
   Impartire:
  Mov edx, 0
  Div ebx
  Push edx
  Inc ecx
 Cmp eax, 0
 Jnz impartire

Afisare:
Pop eax ; al = 0,1,2, .... 9, 10, ...,15
Mov ebx, tabelaHexa
Xlat ; => al=’0’, ‘1’, ‘2’, ... ‘9’, ‘A’, ‘B’,... ,‘F’
;v2 ‘0’=0+’0’; ‘7’=7+’0’, ... sau ‘A’=10+’A’-10, ‘E’=14+’A’-10

 Mov [aux], ecx
; in eax am caracterul corespunzator restului hexa
Push eax
Push dword format
Call [printf]
Add esp, 8
Mov ecx, [aux]
Loop Afisare

        push dword 0
        call [exit]
```

# modul.asm
```asm
bits 32 
global procesare

segment data use32 class=data
Vocale db ‘aeiouAEIOU’
 
segment code use32 class=code
procesare:
; esp -> adresa de revenire
; esp+4 -> adresa de inceput a secventei
Mov esi,[esp+4]
Mov ebx,0
Mov edx,0
Bucla:
Lodsb
Cmp al,” “
Je final
Cmp al,0
Je final
Mov edi,Vocale
Mov ecx,10
Repne scasb
Jnz consoane
Inc ebx
Jmp urmatorul
consoane:
Inc edx
urmatorul:
Jmp bucla
final:
Test ebx,1
Jnz nu_indeplineste_conditia
Test edx,1
Jz nu_indeplineste_conditia
Mov eax,1
Jmp final_procesare
nu_indeplineste_conditia:
Mov eax,0
final_procesare:
ret
```

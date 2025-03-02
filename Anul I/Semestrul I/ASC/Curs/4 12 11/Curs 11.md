# RECAP: Clasificarea conversiilor
## **Distructive sau Nedistructive**
### *Distructive*
```asm
cbw
cwd
cwde
cdq
movzx d, s
movsx d, s
mov ah, 0
mov dx, 0
mov edx, 0
```

(instrucțiuni)

### Nedistructive
```asm
byte
word
dword
qword
```

(operatori)

## *După semn*
### Cu semn

```asm
cbw
cwd
cwde
cdq
movsx d, s
```

### Fără semn
```asm
movzx d, s
mov ah, 0
mov dx, 0
mov ebx, 0
```

## *Prin Lărgire sau Îngustare*
### Lărgire
Toate cele distructive, word, dword, qword

### Îngustare
byte, word, dword

<hr>

`e = a + b + c` - integer to float = conversii implicite
Float tp integer se realizează NU prin  conversii ci prin aplicare de funcții predefinite ale limbajului (floor, ceil, trunc, etc.)

uwu

<hr>

# Contorul de locații și aritmetica de pointeri

==!! curs suport !!==

```asm
segment data...

	a db 1,2,3,4; 01 02 03 04
	la db $ - a; 04
	
	ld $ - data; syntax error în NASM, expression is not simple or relocatable
	ld1 a - data; syntax error, expression is not simple or relocatable
	ld2 dw data - a; asamblorul ne lasă însă obținem edoare la link-editare
```

==Pe scurt: numele de segmente NU au ce căuta în aritmetica de pointeri==

```asm
	c equ a - $; 0 - 6 = -6 = FA
	d equ a - $; 0 - 6 = FA
	e db a - $; 0 - 6 = FA
	x dw x; 01 10; x primește adresa lui x truncheată (dw)
	x db x; syntax error
	x1 dw x1; x1 = offset(x1) 09 00 la asamblare și în final 09 10

	db la - a; 04
	db a - la; -4 = FC
	db [$ - a]; expression syntax error
	db [la - a]; expression syntax error

	la1 equ la1; la1 = 0 | NASM consideră că e corect (dar e un bug din NASM)
	la1 equ la1 - a; la1 = 0 | Bug NASM
```

```asm
a dd a - start; syntax error | a definit aici, start definit altundeva | expression is not simple and relocatgable

dd start - a; merge! (Start definit altundeva și definit aici)
			; rezultatul este pointer
			; deoarece etichetele NU fac parte din același segment și este interpretată ca scădere FAR = pointer
dd start - start1; merge!
				 ; deoarece amândouă etichetele sunt definite în același segment
				 ; rezultatul este scalar
				 ; pentru că aici avem scădere de offset-uri definite în același segment
```

```asm
segment code...
	start
	
	mov ah, la1; AH = 0
	mov bh, c; BH = -6 = FA
	mov ch, la; obj format can handle only 16 or 32 byte relocation (offset NU încape pe un byte)
	
	mov ch, la - a; CH = 4
	mov ch, [la - a]; mov ch byte prt:DS[4] | memory access violation (cel mai probabil)
	
	mov cx, lg - a; CX = 4
	mov cx, [lg - a]; mov word ptr:DS[4] | memory access violation (cel mai probabil)
	
	mov cx, $ - a; invalid operand type | $ generat AICI, a altundeva
	mov cx, $$ - a; invalid operand type | aici avem $$ din code și a din data segment
	mov cx, a - $; ok! | a definit altundeva, $ definit aici
	mov ch, $ - a; invalid operand type | $ generat aici, a altundeva
	mov ch, a - $; obj format can handle only 16 or 32 byte relocation
	; a - $ este ok! Dar este pointer. Syntax erorr pentru că offset nu încape pe un byte
	
	mov cx, $ - start; ok!
	mov cx, start - $; ok!
	mov cx, a - start; ok!
	mov cx, start - a; invalid operand type
	
	mov ah, a + b; ok! Dar nu este adunare de pointeri, e adunare de scalari
				 ; a + b = [a - $$] + [b - $$]
	mov ax, b + a; ok! La fel ^
	mov ax, [a + b]; invalid effective address! Asta chiar e adunare de pointeri! Deci interzisă! Deci syntax error

	var1 dd a + b; syntax error | expression is not simple or relocatable
	
	; deci NASM nu permite ca "a + b" să apară într-o definiție de date ca expresie de inițializare, ci NUMAI ca OPERAND AL UNEI INSTRUCȚIUNI, cum se abordează mai sus
```

## Concluzie
Expresiile de tip `et1 - et2` (unde `et1` și `et2` sunt etichete - fie de cod, fie de date) sunt acceptate sintactic de către NASM

Fie dacă sunt definite în același segment
Fie dacă `et1` aparține unui segment diferit față de cel în care apare expresia, iar `et2` este definită în segmentul în care apare expresia. Într-un astfel de caz, TD asociat expresiei `et1 - et2` este *pointer* și *NU scalar*

<hr>
```asm
mov bx, [[v3 - v2] +- [v1 - v]]; ok! Adunarea și scăderea de valori imediate este corectă
```

Dacă adresa nu va putea fi încadrată într-o combinație validă de operații cu pointeri, vom obține eroare de sintaxă "invalid effective address"

```asm
mov bx, [v2 - v1 - v]; invalid effective address
mov bx, v2 - v1 - v; invalid operand type
; mov bx, v2 - (v1 + v) - de unde concluzionăm că v1 + v este acceptatăca și SCALAR în interpretarea a + b = (a - $$) + (b - $$) doar dacă apare DE SINE STĂTĂTOR sau în combinație cu alți SCALARI, dar NU și în combinație cu expresia de tip POINTER
```

I ***need*** paws rn

# Analiza instrucțiunii JMP
```asm
segment data...
	
	aici dd here
	
segment code...
	mov eax, [aici]
	mov ebx, aici
	
	jmp here; direct
	jmp [aici]; indirect
	jmp eax; direct
	jmp [ebx]; indirect

; toate sunt ok! Toate sunt NEAR
```

```asm
jmp [var_mem]; jmp dword ptr DS:[00401704] salt NEAR la offset-ul din DS:[00401704]

jmp [ebx]; jmp dword ptr DS:[EBX] salt NEAR la offset-ul din DS:[EBX]
jmp [ebp]; jmp dword ptr SS:[EBP] salt NEAR la offset-ul din SS:[EBP]

jmp here; jmp [SHORT] 00402024 - va face saltul DIRECT la offset-ul respectiv în code segment
```

*Chiar dacă folosim prefixarea explicită cu un registru segment a operandului destinație, saltul NU va fi unul FAR*

```asm
jmp [ss:ebx + 12]
jmp [ss:ebp + 12]; echivalent cu jmp [ebp + 12]
```

Saltul rămâne în continuare unul NEAR și se va efectua în cadrul aceluiași segment de cod, adică la `CS:valoare offset preluată din SS:[EBP + 12]`

Dacă dorim însă efectuarea saltului într-un segment diferit (salt FAR), trebuie să specificăm explicit acest lucru prin intermediul *operatorului de tip FAR*, care va impune tratarea operandului destinație a instrucțiunii `JMP` drept *o adresă FAR*

```asm
jmp far [ebx + 12]; => CS:EIP <- adresa far (48 biți = 6 octeți) echivalent cu
jmp far [DS:ebx + 12]; CS:EIP <- adresa far (48 biți = 6 octeți)

; (9B 7A 52 61 C2 65) -> EIP = 61 52 7A 9B | CS = 65 C2

; adică un fel de mov CS:EIP, [adr_far]
```



Mulțumesc de steluță Matei-Călin Botezat :)
Ce comentarii ai pe Reddit bă băiatule
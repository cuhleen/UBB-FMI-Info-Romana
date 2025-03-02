# Observație CDECL
==!!  printf și scanf necesită Direction Flag = 0 (`CLD`) !!==

# Exerciții

## 1. Funcție recursivă factorial
```c++
int factorial(int n){

	if(n == 0)
		return 1;

	return n * factorial(n - 1);

}
```

## 2.1. Ce va fi în AX?
```asm
mov AL, 2
mov BL, -2
imul BL
```

## 2.2. Ce va fi în AX?
```asm
mov AL, 2
mov BL, -2
mul BL
```

<hr>

# Rezolvări
## 1
```asm
fact:
	mov eax, [esp + 4]
	cmp eax, 0
	je oprire
	push eax
	dec eax
	push eax
	call fact
	add esp, 4; eax = fact(n - 1)
	pop edx
	mul edx
	ret
```

## 2.1
-1 = FF (interpretare cu semn)
-2 = FE (interpretare cu semn)
AL = 2 = 02
BL = -2 = FE (interpretare cu semn)
2 * (-2) = -4
-4 = FC (interpretare cu semn)
##### ==În AX se află FFFC==

## 2.2
-1 = FF (interpretare cu semn)
-2 = FE (interpretare cu semn)
FE = 254 (interpretare fără semn)
AL = 2 = 02
BL = 254 = FE (interpretare fără semn)
2 * 254 = 508
508 = 1FC (interpretare fără semn)
##### ==În AX se află 01FC==
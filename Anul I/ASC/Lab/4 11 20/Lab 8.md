# **Apeluri de funcții sistem**
### Call, Return
```asm
f:
	mov eax, 1
	ret

g:
	mov ebx, 1
	ret

start:
	call f
	inc eax
```

`CALL` dă push pe stivă la adresa liniei curente, `RET` dă pop la ultima chestie de pe stivă
==Atenție să nu se pună altceva pe stivă între timp fără să fie scos!!==

https://www.cs.ubbcluj.ro/~vancea/asc/ro-lab8-exemple.php
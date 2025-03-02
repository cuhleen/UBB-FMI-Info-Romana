# CMP
`cmp <reg d>, <reg s>`

# JMP
`jump <et>`
```asm
; cel mai simplu ciclu infinit

et:
	add eax, 1
jmp et

; indentările nu contează
```

# Jump-uri fără semn
1. JA (Jump if Above)
2. JB (Jump if Below)
3. JAE (Jump if Above or Equal)
4. JBE (Jump if Below or Equal)
5. JNA(Jump if Not Above)
6. JNB (Jump if Not Below)
7. JNAE (Jump if Not Above or Equal)
8. JNBE (Jump if Not Below or Equal)

# Jump-uri cu semn
1. JG (Jump if Greater)
2. JL (Jump if Lesser)
3. JGE (Jump if Greater or Equal)
4. JLE (Jump if Lesser or Equal)
5. JNG (Jump if Not Greater)
6. JNL (Jump if Not Lesser)
7. JNGE (Jump if Not Greater or Equal)
8. JNLE (Jump if Not Lesser or Equal)

# Jump-uri indiferent de semn
1. JE (Jump if Equal)
2. JO (Jump if Overflow)
3. JNO (Jump if Not Overflow)
4. JS (Jump if Signed)
5. JNS (Jump if Not Signed)
6. JZ (Jump is Zero)
7. JNZ (Jump if Not Zero)
8. JP (Jump if Parity)
9. JNP (Jump if Not Parity)
	1. JPE (Jump if Parity is Even (PF = 1))
	2. JPO (Jump if Parity is Odd (PF = 0))
10. JC (Jump if Carry)
11. JNC (Jump if Not Carry)

# Jump-uri misc.
1. `JECXZ <et>` (Jump if ECX is Zero)
2. `JCXZ <et>` (Jump if CX is Zero)

# LOOP
```asm
loop <et>
; este egal cu:
; ECX = ECX - 1
; JNZ <et>
```

```asm
START_LOOP:
;
;
;
loop START_LOOP:
end LOOP:
```

Decrementează ECX și verifică dacă nu este zero. Dacă condițiile sunt îndeplinite, sare la etichetă. Altfel iese din loop

# LOOPE (LOOP if Equal), LOOPZ (LOOP if Zero)
### LOOPE
Decrementează ECX și verifică dacă nu este zero, *și* dacă *ZF este setat*. Dacă condițiile sunt îndeplinite, sare la etchetă. Altfel iese din loop

### LOOPZ
Decrementează ECX și verifică dacă nu este zero, *și* dacă *ZF* **NU** *este setat*. Dacă condițiile sunt îndeplinite, sare la etichetă. ALtfel iese din loop


# **Operații pe șiruri**
ESI -> Registru sursă
EDI -> Registru destinație

```asm
s db 1,2,3,4
mov ESI, 0
; accesăm conținutul cu [s + ESI]

; sau

mov ESI, s
mov AL, [ESI]
; și incrementăm ESI
```

# **Instrucțiuni pentru transfer de date**
## LODS - Load Str
`LODSB/W/D`
Încarcă în AL, AX, sau EAX (depinde dacă am scris Byte, Word, sau Dword) ceea ce este in `[DS:ESI]`
Dacă DF<sup>*</sup> = 0, ESI se incrementează cu 1, 2, sau 4
Dacă DF = 1, ESI se decrementează cu 1, 2, sau 4

<sup>*</sup>DF = Direction Flag

## STOS - Store Str
`STOSB/W/D`
`[ES:EDI] <- Al/AX/EAX`
Dacă DF = 0, EDI se incrementează cu 1, 2, sau 4
Dacă DF = 1, EDI se decrementează cu 1, 2, sau 4

## MOVS - Move Str
`MOVSB/W/D`
`[ES:EDI] <- [DS:ESI]` byte/word/dword
Dacă DF = 0, ESI și EDI se incrementează cu 1, 2, sau 4
Dacă DF = 1, ESI și EDI se decrementează cu 1, 2, sau 4

# **Instrucțiuni penntru compararea și consultarea datelor**
## SCAS - Scan
`SCASB/W/D`
` = CMP AL/AX/EAX, [ES:EDI]`
Dacă DF = 0, EDI se incrementează cu 1, 2, sau 4
Dacă DF = 1, EDI se decrementează cu 1, 2, sau 4

## CMPS - Compare
`CMPSB/W/D`
` = CMP [DS:ESI], [ES:EDI]`
Dacă DF = 0, ESI și EDI se incrementează cu 1, 2, sau 4
Dacă DF = 1, ESI și EDI se decrementează cu 1, 2, sau 4

# **Prefixe de instrucțiuni**
`REP`
`REPE`
`REPZ`

## REPE - Repeat Equal
```asm
AGAIN:
	<instrucțiunea SCAS/CMPS pe șir>
LOOPE AGAIN

; ^ este același lucru cu V

REPE <instrucțiunea SCAS/CMPS pe șir>

```

## REP
```asm
AGAIN:
	<instrucțiunea MOVS/STOS pe șir>
LOOP AGAIN

; ^ este același lucru cu V

REP <instrucțiunea MOVS/STOS pe șir>

```

## REPNE - Repeat Not Equal
```asm
AGAIN:
	<instrucțiune pe șir>
LOOPNE AGAIN

; ^ este același lucru cu V

REPE <instrucțiune pe șir>
```


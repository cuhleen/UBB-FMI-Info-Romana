# *Operații pe biți*

### And
`and <reg>, <reg>/<mem>/<con>`
`and <mem>, <reg>/<con>`

|       | **0** | **1** |
| ----- | ----- | ----- |
| **0** | 0     | 0     |
| **1** | 0     | 1     |
### Or
`or <reg>, <reg>/<mem>/<con>`
`or <mem>, <reg>/<con>`

|       | **0** | **1** |
| ----- | ----- | ----- |
| **0** | 0     | 1     |
| **1** | 1     | 1     |
### Xor
`xor <reg>, <reg>/<mem>/<con>`
`xor <mem>, <reg>/<con>`

|       | **0** | **1** |
| ----- | ----- | ----- |
| **0** | 0     | 1     |
| **1** | 1     | 0     |

### Not
`not <reg>'
`not <mem>'

| **0** | 1   |
| ----- | --- |
| **1** | 0   |

### Neg
`neg AL; AL <- 0 - AL`

#### Neg cu behaviour de Not
`neg AL`
`add AL, 1`

<hr>

```asm
mov AL, 01101100b
mov BL, 10011101b

and AL, BL; AL <- AL & BL
          ; AL <- 00001100b

mov AL, 01101100b
or AL, BL; AL <- 11111101b
```

<hr>

### SHL (Shift Left)
`shl <reg>, <con>/cl`
`shl <mem>, <con>/cl`

```asm
mov AL = 0110110b
shl AL, 2; AL <- 1011000b
         ; ultimul bit se mută în carry flag
```

### SHR (Shift Right)
`shr <reg>, <con>/cl`
`shr <mem>, <con>/cl`

SHR este ca o înmulțire la puterea 2<sup>con/cl</sup>

```asm
mov AL = 10010011b
shr AL, 3; AL <- 00010010b
         ; 
```

#### SAR (Shift Arithmetic Right)
`sar <reg>, <con>/cl`
`sar <mem>, <con>/cl`

Pentru semn. Pune fie 0 fie 1 la stânga, depinzând de bit-ul de semn

#### SAL (Shift Arithmetic Left)
`sal <reg>, <con>/cl`
`sal <mem>, <con>/cl`

Face același lucru ca SHL, completează cu 0 la dreapta

### ROL (Rotate left)
`rol <reg>, <con>/cl`
`rol <mem>, <con>/cl`

```asm
; AL = 01101111b
rol AL, 3
; AL = 01111011b
```

### ROR (Rotate Right)
`ror <reg>, <con>/cl`
`ror <mem>, <con>/cl`

### RCL (Rotate With Carry Left)
`rcl <reg>, <con>/cl`
`rcl <mem>, <con>/cl`

```asm
; AL = 01110010b | CF = 1

rcl AL, 1

; Rotația se face ca și când ultimul bit este carry flag-ul
; 0111 0010 1
; 1110 0101 0
```

```asm
; AL = 01110010b | CF = 1

rcl AL, 3

; 0111 0010 1
; 1001 0101 1
```

### RCR (Rotate With Carry Right)
`rcr <reg>, <con>/cl`
`rcr <mem>, <con>/cl`

<hr>

### ***Observație***

`mov AL, 10011110b`
`shr AL, 32; = shr Al, 0`
`shr AL, 33; = shr AL, 1`
`shr AL, 16; face 0 peste tot`

<hr>

### TEST
`TEST <reg>, <reg>/<mem>/<con>`
`TEST <mem>, <reg>/<con>`

Face un *and* "fictiv", nu face nimic pe `<reg>/<mem>` sursă, dar activează carry flag-ul dacă un *and* normal l-ar activa

```asm
mov AL, 10101011b
test AL, 10000000b
; 0000 0000 ZF = 1
; 1         ZF = 0
```

<hr>

# [Exemplu](https://www.cs.ubbcluj.ro/~vancea/asc/ro-lab4-exemple.php)

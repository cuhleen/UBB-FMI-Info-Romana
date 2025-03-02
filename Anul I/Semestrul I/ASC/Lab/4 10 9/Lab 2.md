# Regiștri

Regiștrii generali: EAX, EBX, ECX, EDX
Regiștrii de index: ESI, EDI
Lucru cu stiva: EBP, ESI

## Declarare variabile
Cu valoare inițială:
a db 10 (b = byte (8))
b dw 20 (w = word (16))
c dd 011h (d = double word (32))
d dq 01100b (q = quad word (64))

Fără valoare inițială
a *res*b 1 (reserve byte)
b *res*w 2 (reserve word)
c *res*d 1 (reserve double word)
d *res*q 3 (reserve quad word)

e times 16 db 2

## Declarare constante
zece equ 10 ("zece" e numele constantei)

<hr>

## mov - mută

`mov <reg>, <reg>/<mem>/<con>`
`mov <mem>, <reg>/<con>`
primul <> e destinația, al doilea <> e locația

`mov <mem>, <mem>` ***NU*** merge
(reg = registru, mem = memorie, con = constantă)

Dar cum mutăm din memorie în memorie?
mov AL, 10
mov AL, [a]
mov [b], AL
mov [a], byte 10

Trebuie menționată mărimea memoriei în care mutăm
Ambii operanzi trebuie să aibă aceeași mărime
În loc de byte poate fi word/dword/...

<hr>

## add - adaugă

`add <reg>, <reg>/<mem>/<con>`
`add <mem>, <reg>/<con>`

mov AX, 10
mov BX, 20
add AX, BX; AX = 30, BX = 20

==tot ce este după ';' este comentariu==

<hr>

## sub - scădere

`sub <reg>, <reg>/<mem>/<con>`
`sub <mem>, <reg>/<con>`


<hr>

## mul - înmulțire

`mul <reg>/<mem>`

Un singur operant
Al doilea este în A

`mul <op8>; AX = AL * <op8>`
`mul <op16>; DX:AX = AX * <op16>`
(se pune jumătate în DX, altă jumătate în AX)
`mul <op32>; EDX:EAX = EAX * <op32>`

mov AL, 10
mov BL, 16
mul BL; AX <- 160

mul BX; DX:AX <- AX * BX

<hr>

## div - împărțire

`div <op8>; AL <- AX / <op8> (câtul); AH <- AX % <op8> (restul)`
`div <op16>; AX <- DX:AX / <op16>; DX <- DX:AX % <op16>`
`div <op32>; EAX <- EDX:EAX / <op32>; EDX <- EDX:EAX % <op32>`

<hr>

5 * 6 / 3

mov AX, 5
mov CX, 6

mul CX; DX:AX

mov CX, 3
div CX

<hr>

(a + b) * c

```asm

a db 10
b db 3
c db 6

mov AL, [a]
add AL, [b]; AL <- a + b
MUL BYTE[c]; AX

```
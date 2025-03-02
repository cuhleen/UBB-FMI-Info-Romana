## Little Endian

`a dd 12345678h`
Așa va arăta în memorie
78|56|34|12

'a' este doar un pointer care arată spre primul octet de memorie ce îl conține (de la "78")

Dacă facem `mov AX, [a]` va prelua doar atât cât încape în AX, adică 2 octeți (`AX = 5678h`)

```asm
a dd 12345678h
b dw 9ABCh
c dd 10
d dq 123456h
e db 8h
```

Așa va arăta memoria:
`78|56|34|12|BC|9A|0A|56|34|12|00|00|00|00|00|08`
`a^         b^    c^  d^                     e^`

```asm
mov AX, [b]
mov AX, [a + 4]
mov EAX, [b]; AX = 00 0A 9A BC
```

<hr>

## ADC (Add With Carry)

`adc <reg>, <reg>/<mem>/<con>`
`adc <mem>, <reg>/<con>`

```asm
a dq 1122334455667788h
b dq 8877665544332211h

; 'a' și 'b' sunt puse în memorie astfel:
; 88 77 66 55 44 33 22 11 11 22 33 44 55 66 77 88
; a^                     b^

; vrem să punem doar jumătatea inferioară a lui a în EAX
; fiindcă este în memorie astfel "8877665544332211", scriem doar mov EAX, [a]
mov EAX, [a]
; pentru jumătatea superioară scriem
mov EDX, [a + 4]

add EAX, [b]
adc EDX [b + 4]
```

## SBB (Sub With Borrow)

`sbb <reg>, <reg>/<mem>/<con>`
`sbb <mem>, <reg>/<con>`

```asm
mov EAX, [a]
mov EDX, [a + 4]
sub EAX, [b]
sbb EDX, [b + 4]
```

## IMUL & IDIV (MUL & DIV cu semn)

```asm
a dq -10
b dd 3

mov EAX, [a]
mov EDX, [a + 4]

idiv dword [b]; EAX = -3 (câtul) | EDX = 1 (restul)
```

## Instrucțiuni pentru conversia cu semn

#### CBW (Convert Byte To Word)
AX <- AL
#### CWD (Convert Word To Double)
DX:AX <- AX
#### CWDE (Convert Word to Double Extended)
EAX <- AX
#### CDQ (Convert Double To Quad)
EDX:EAX <- EAX

#### Exemplu

```asm
; cu semn

a db 5
mov AL, [a]
cbw
cwde
cdq
```

```asm
; fără semn

a db 5
mov EAX, 0
mov AL, [a]
```

<hr>

## Stack

`push <op16>`
`push <op32>; <op> = <reg>/<mem>/<con>`
`pop <op16>`
`pop <op32>; <op> = <reg>/<mem>`

`push <op>`
<=>
`sub ESP, 4`
`mov [ESP], <op>`

`pop <op>`
<=>
`mov <op>, [ESP]`
`add ESP, 4`

Mai sus în stivă = poziție mai mică

```asm
push dword 12345678h
pop AX; AX = 5678h
pop DX; DX = 1234h
```

==!! Little Endian !!==

|         |     |     |         |     |
| ------- | --- | --- | ------- | --- |
|         |     |     | ESP - 2 |     |
| ESP - 4 | 78  | 56  | 34      | 12  |
| ESP     |     |     |         |     |

`push dword 12345678

|         |     |     |     |     |
| ------- | --- | --- | --- | --- |
| ESP - 4 | 78  | 56  | 34  | 12  |
| ESP     |     |     |     |     |
`pop EAX; EAX = 12345678`

!! După ce lucrăm cu stiva este recomandat să o "curățăm" prin pop-uri !!

EAX -> DX:AX
```asm
push EAX
pop AX
pop DX
```

|         |        |     |
| ------- | ------ | --- |
| ESP - 4 | EAX:16 | AX  |
| ESP     |        |     |

DX:AX -> EAX
```asm
push DX
push AX
pop EAX
```

#### PUSHA / PUSHAD
Pune toți regiștrii pe stivă într-o ordine stabilită
#### POPA / POPAD
Scoate toți regiștrii pe stivă într-o ordine stabilită
# **ASC + C**
## *Codul de Apel*

|        |                    |
| ------ | ------------------ |
| ESP -> | Adresă de revenire |
|        | Parametri          |
|        | Resurse Volatile   |
|        | Adrese Mari        |

## *Codul de Intrare*

|        |                     |
| ------ | ------------------- |
| ESP -> | Regiștri nevolatili |
|        | Variabile locale    |
| EBP -> | EBP apelat          |
|        | Adresa de revenire  |
```asm
push ebp
mov ebp, esp
```

## *Codul de Ieșire*
Restaurarea resurselor nevolatile
Eliberarea variabilelor locale
Revenirea din funcție și eliberarea argumentelor

## *Cod*
```asm
	; resurse volatile
push eax
push ecx
push edx
; sau pushad

	; parametrii
push dword parametru3
push dword parametru2
push dword parametru1

call func
```

```asm
	; pregătire cadrul de stivă
push ebp
mov ebp, esp

	; alocăm spațiu pe stivă
sub esp, 12

	; accesarea parametrilor
mov eax, [ebp + 4 * 2]; p1
mov edx, [ebp + 4 * 3]; p2

	; returnare din funcție
mov eax, 10
mov esp, ebp
pop ebp
ret
```

|        |                    | explicație                                                            |
| ------ | ------------------ | --------------------------------------------------------------------- |
|        | ?                  |                                                                       |
| ESP -> |                    | Căsuța asta goală a fost formată odată cu instrucțiunea `sub esp, 12` |
|        |                    | Căsuța asta goală a fost formată odată cu instrucțiunea `sub esp, 12` |
|        |                    | Căsuța asta goală a fost formată odată cu instrucțiunea `sub esp, 12` |
|        | EBP                |                                                                       |
|        | Adresa de revenire |                                                                       |
|        | parametru1         |                                                                       |
|        | parametru2         |                                                                       |
|        | parametru3         |                                                                       |

<hr>

https://www.cs.ubbcluj.ro/~vancea/asc/ro-lab12-teorie.php
*"Utilizarea procedurilor definite în asamblare în cadrul unui program C"*

<hr>


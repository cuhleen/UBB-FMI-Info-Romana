!!! Curs pdf !!!

# ***Complementul față de 2. Discuție și exemple***

[Reminder Complement](<Lab 1.md#Complementul>)

## Definiție
Matematic, *reprezentarea* unui număr *negativ* în *complementul față de doi*, este valoarea <span style="background-color:#8a6624">2<sup>n</sup> - V</span>, unde V este valoarea absolută a numărului reprezentat

## Motto
Cu complementul față de 2 **"interpretăm reprezentări și reprezentăm interpretări"**

## Comentariu
Dacă am utiliza doar numere întregi pozitive, nu am avea nevoie de nici o metodă sofisticată de reprezentare a numerelor în calculator, ci simpla utilizare a bazelor de enumerație 2 și 10 ar rezolva absolut tot

# Exemplu
10010011b = 93h = 147, în interpretarea fără semn
Cât este interpretat cu semn? ==Este complementul==
*Pentru metodele complementului, vezi* **Reminder Complement**
```
1 0000 0000b -   2^n - 
  1001 0011b     V
_____________    ____
  0110 1101b  =  6Dh   = 96 + 13 = 109 (deci complementul față de 2 pe 8 biți al numărului 147 este 109)
```

Ca urmare, valoarea în interpretarea *cu semn* a numărului 10010011b este -109

<hr>

# ==Întrebare de examen type beat==

Care este valoare în interpretarea *cu semn* a valorilor din prima listă? Asociază un răspuns din a doua listă.
<ol style="list-style-type: lower-roman">
<li>1001 0011b</li>
<li>193h</li>
<li>147</li>
</ol>

<ol style="list-style-type: lower-alpha">
<li>0110 1101b</li>
<li>-109</li>
<li>6Dh</li>
<li>+147</li>
<li>-147</li>
</ol>

Răspunsuri:
<ol style="list-style-type: lower-roman">
<li>b)</li>
<li>b)</li>
<li>Întrebarea este greșită, 147 este deja o interpretare</li>
</ol>

<hr>

| Reprezentarea *(întotdeauna în b2)* | Interpretarea *(întotdeauna în b10)* | Valoare *(b10)* |
| ----------------------------------- | ------------------------------------ | --------------- |
| Numere care încep cu 0              | fără semn                            | pozitivă        |
| Numere care încep cu 0              | cu semn                              | pozitivă        |
| Numere care încep cu 1              | fără semn                            | pozitivă        |
| Numere care încep cu 1              | cu semn                              | negativă        |

Valoare negativă - Care este reprezentarea în baza 2?
-109 -> +147 -> 93h -> 10010011b

###### b) Reprezentarea binară a valorii $-\overline{abc}$ este complementul față de doi a configurații binare inițiale
01101101b = 109
10010011b = -109

*-147 nu se poate reprezenta pe un octet*

+147 = 0000 0000 1001 0011b
-147 = 1111 1111 0110 1101b = FF6Dh = 65389 fără semn

# Întrebare de examen
==Care este numărul minim de biți pe care se poate reprezenta -147?==
Răspuns: 9
Explicație: de-abia pe 9 biți se poate reprezenta intervalul [-256, 255]
woofbarkbarkwoof :3
==Care este numărul minim de biți pe care se poate reprezenta 3?==
2
==Care este numărul minim de biți pe care se poate reprezenta -3?==
3

:3c
-w-
ow<
TwT
OnO
:>
:3
:]
',;3
;-;
D:
:0
`>w<`
`>///A///<`
`>///<`
-.-
:p
x3
xD
^^
^w^
*o///o*
**mental illness ^**
*im just a furry -w-*
**lock her up**
*what if I like that huh o///o*
**im gonna take yo to a farm up north lil bro**
*ono*
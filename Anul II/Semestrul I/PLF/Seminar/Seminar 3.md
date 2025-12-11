	# **Liste eterogene în Prolog**

`[1, 2, 10, [10, 'a'], 'b', [20, 40]]`

Noi vom considera că sublistele sunt liniare

Facem verificări dacă elementul curent este cifră, simbol, sau listă cu predicate speciale
`is_list(H)` - *true* dacă H e listă, *false* altfel
`number(H)` - *true* dacă H e număr, *false* altfel
`atom(H)` - *true* dacă H e simbol, string, sau listă vidă, *false* altfel

<hr>

1) Se dă o listă eterogenă formată din numere și liste de numere. Să se determine numărul sublistelor cu aspect de munte

*Soluție:*
Predicat care verifică dacă o listă liniară are aspect de munte
*Idee de rezolvare:*
Parcurgem cât timp l1 < l2, apoi verificăm ca restul elementelor să fie în ordine strict descrescătoare

```
flag = <  0 , crescător
	    \ 1 , descrescător
						   / munte(l2, l3, ..., ln, flag) , dacă l1 < l2 și flag = 0
munte(l1, .., ln, flag) = <  munte(l2, l3, ..., ln, 1) , dacă l1 > l2 și flag = 0
						   | true , n = 1 și flag = 1
						   \ false , altfel
```

```
main_munte(l1, ..., ln) = <  munte(l1, ..., ln, 0) , dacă l1 < l2
						   \ false , altfel
```

```
% model de flux (i, i)
munte([E1,E2|T], 0):-
	E1 < E2,
	munte([E2|T], 0).

munte([E1,E2|T], _):-
	E1 > E2,
	munte([E2|T], 1).
	
% model de flux(i)
main_munte([E1,E2|T]):-
	E1 < E2,
	munte([E1,E2|T], 0).
```

```
numar_munti(l1, ..., ln) = <  0 , l e vidă
						    | 1 + număr_munți(l2, ..., ln) , l1 listă și main_munte(l1)
						    \ număr_munți(l2, ..., ln) , altfel
```

```
numar_munti([], 0).

numar_munti([H|T], CNT):-
	is_list(H),
	main_munte(H), !,
	numar_munti(T, CNT2),
	CNT is CNT2 + 1.
	
numar_munti([H|T], CNT):-
	numar_munti(T, CNT).
```

<hr>

2) Se dă o listă eterogenă care conține numere și liste de numere. Se cere ca din fiecare sublistă să se elimine palindromurile

```
invers(n, ogl) = <  ogl, n = 0
				   \ invers(n/10, ogl * 10 + n % 10) , altfel

invers_main(n) = invers(n, 0)
```

```
% (i, i, o), (i, i, i)
invers(N, OG, R):-
	N > 0,
	NF is N // 10,
	OGR is 10 * OG + N mod 10,
	invers(NF, OGR, R).
invers(N, OG, R):-
	N = 0,
	R = OG.
	
% (i, o), (i, i)
invers_main(N, OG):-
	invers(N, 0, OG).
```

```
					    / [] , dacă l e vidă
elimina(l1, ..., ln) = <  elimină(l2, ..., ln) , dacă invers_main(l1) = l1
						\ l1 ⊕ elimină(l2, ..., ln), altfel
```

```
% (i, o), (i, i)
elimina([], []).
elimina([E|T], R):-
	invers_main(E, E),!,
	elimina(T,R).

elimina([E|T], [E|R]):-
	elimina(T, R).
```

```
						   / [] , dacă l e vidă
transforma(l1, ..., ln) = <  l1 ⊕ transforma(l2, ..., ln) , dacă l1 nu e listă
						   \ elimina(l1) ⊕ transforma(l2, ..., ln) , altfel
```

```
transforma([], []).

transforma([H|T], R):-
	is_list(H), !,
	elimina(H, RE),
	transforma(T, RT),
	R = [RE|RT].
transforma([H|T], R):-
	transforma(T, RT),
	R = [H|RT].
```
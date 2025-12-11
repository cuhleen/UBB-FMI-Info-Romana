# **Liste în Prolog**
1) Să se scrie un predicat care elimină din listă elementele care apar o singura dată
ex.:[1,2,1,4,1,3,4] => [1,1,4,4,1,4]

### *Metoda 1*
Memorăm într-o listă elementele întâlnite până acum
Predicat auxiliar care verifică existență unui element într-o listă
Pentru fiecare element: verificăm dacă există în restul listei sau în lista elementelor întâlnite până acum

```
există(x, l1, ..., ln) = < false, l = mulțimea vidă
			             | true, dacă l1 = x
			             | există(x, l2, ..., ln) altfel
			             \
```

```
elimina(x, l1, ..., ln, viz) = < mulțimea vidă, l = mulțimea vidă
		      	               | l1 ⨁ elimina(l2, ..., ln, viz), dacă există(l, viz) = true
			                   | l1 ⨁ elimina(l2, ..., ln, l1 ⨁ viz), dacă există(l1, l2, ..., ln)=true
			                   | eliminare(l2, ..., ln, viz), altfel
			                   \
```

```prolog
% există(E - element, L - lista)

exista(Element, L):-
	L=[H|_],
	H=Element.
	
exista(Element, L):-
	L=[H|T],
	H\=Element,
	exista(Element, T).
	
/*
	
SAU

exista(Element, [H|_]):-
	H=Element.

exista(Element, [H|T]):-
	H\=Element.
	exista(Element, T).
*/

% există(E - element, L - lista, Rez - lista)
% model de flux (i, i, o), (i, i, i)

elimina(L, Viz, Rez):-
	L=[],
	Rez=[].
	
elimina([H|T], Viz, Rez):-
	exista(H, Viz),
	elimina(T, Viz, Rez2),
	Rez=[H|Rez2].
	
elimina([H|T], Viz, Rez):-
	exista(H, T),
	not(exista(H, Viz)),
	elimina(T, [H|Viz], Rez2),
	Rez=[H|Rez2].
	
elimina([H|T], Viz, Rez):-
	not(exista(H, Viz)),
	not(exista(H, T)),
	elimina(T, Viz, Rez).
```

`?-elimina([1,2,1,4,1,3,4], [], Rez).`

Pentru a obține predicate deterministe:
- scriem toate condițiile necesare pe fiecare clauză
- predicat special "cut" (*!*)

```prolog
elimina(L, Viz, Rez):-
	L=[],
	Rez=[].
	
elimina([H|T], Viz, Rez):-
	exista(H, Viz), !,
	elimina(T, Viz, Rez2),
	Rez=[H|Rez2].
	
elimina([H|T], Viz, Rez):-
	exista(H, T), !,
	elimina(T, [H|Viz], Rez2),
	Rez=[H|Rez2].
	
elimina([H|T], Viz, Rez):-
	elimina(T, Viz, Rez).
```

### *Metoda 2*
Predicat auxiliar care calculează numărul de apariții ale unui element într-o listă

```
nr_ap(x, l1, ..., ln) = < 0, dacă l = mulțimea vidă
					    | 1 + nr_ap(x, l2, ..., ln), dacă l1 = x
						| nr_ap(x, l2, ..., ln), altfel
```

```
elimina(l1, ..., ln, copie) = < mulțimea vidă, dacă l = mulțimea vidă
							  | l1 ⨁ elimină(l2 ..., ln, copie), dacă nr_ap(l1, l2, ..., ln)
							  | elimină(l2, ..., ln, copie), altfel
```

```
% nr_aparitii(X - Elementul, L - Lista, Rez - int)
% model de flux (i, i, o), (i, i, i)

nr_aparitii(X, L, Rez):-
	L=[], Rez=0.
nr_aparitii(X, [X|T], Rez):-
	nr_aparitii(X, T, Rez2),
	Rez is Rez2 + 1.
	
nr_aparitii(X, [H|T], Rez):-
	nr_aparitii(X, T, Rez2),
	Rez is Rez2 + 1.
	
nr_aparitii(X, [H|T], Rez):-
	X\=H, nr_aparitii(X, T, Rez).
```

`?- nr_aparitii(5, [1,2,3,4,5,5], Rez)`

![[Pasted image 20251014123715.png|400]]

<hr>

## Problema 2

Dându-se o listă numerică să se șteargă toate secvențele de numere strict crescătoare
ex: [1, 2, 4, 6, 5, 7, 8, 2, 1] -> [2, 1]

```
elimina(l1, ..., ln, ok) = < mulțimea vidă, dacă l = mulțimea vidă
						   | elimină(l2, ..., ln, 1), dacă l1 < l2
						   | elimină(l2, ..., ln, 0), dacă ok = 1, l1 > l2
						   | l1 ⨁ elimină(l2, ..., ln, 0), altfel
						   | [l1], dacă n = 1 și ok = 0
						   | mulțimea vidă, dacă n = 1 și ok = 1
```

```
eliminaMain(l1, ..., ln) = { elimina(l1, ..., ln, 0)
```


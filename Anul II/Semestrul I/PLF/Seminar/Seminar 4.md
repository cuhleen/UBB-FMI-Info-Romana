Se dă o listă formată din numere întregi distincte. Să se genereze lista tuturor submulțimilor ordonate cu aspect de vale
*Ex.*:
[5,4,3,6,7,-1,-2,-3] => [6,5,7], [7,5,6]. [5,4,3,6,7]
*Soluție posibilă*:
Generarea tuturor submulțimilor ordonate, urmată de filtrarea celor care respectă proprietatea -> ineficient (**NU E ACCEPTAT LA EXAMEN**)
*Soluție mai eficientă*:
Combinăm generarea soluției cu verificarea condiției
Folosim o soluție candidat C (listă în care adăugăm elemente din lista originală cât timp lista C mai poate deveni o vale)
Adaugăm în listă la început, soluțiile se vor construi de la dreapta la stânga
Variabilă auxiliară flag: 0 partea stângă, 1 partea dreaptă
E element generat din lista originală, primul element din C, lista candidat care va deveni soluția
- E > C1, flag = 0 => continuăm generarea soluției cu flag = 0
- E < C1, flag = 1 => continuăm generarea soluției cu flag = 1
- E > C1, flag = 1 => continuăm generarea soluției cu flag = 0
- E < C1, flag = 0 => oprim generarea
C este o soluție validă când flag = 0
trebuie tratat cazul special (vezi sem. anterior)
vom genera primele 2 elemente în ordinea corectă

<hr>

Predicat care generează toate elementele dintr-o listă

Model matematic

```
candidat(l1, ..., ln) = 1. l1                               , dacă n > 0
						2. candidat(l2, ..., ln)            , dacă n > 0
```

```
% (i, o), (i, i)

candidat([H|_], H).

candidat([_|T], R):-
	candidat(T, R).
```

<hr>

Predicat nedeterminist care generează liste cu aspect de vale

Model matematic

```
vale(l1, ..., ln, flag, c1, ..., cm) =
	1. vale(l1, ..., ln, 1, e, c1, ..., cm)
		unde e = candidat(l1, ..., ln)
		dacă e < c1, flag = 1
	2. vale(l1, ..., ln, 0, e ⨁ c1, ..., cm)
		unde e = candidat(l1, ..., ln)
		dacă e > c1, e nu există în c1, ..., cm
	3. c1, ..., cm
		dacă flag = 0
```

```
% vale(L, F, C, R)
% (i, i, i, o), (i, i, i, i)

vale([], 0, C, C).

vale(L, 1, [C1|T], R):-
	candidat(L, E),
	E < C1
	vale(L, 1, [E, C1|T], R).

vale(L, _, [C|T], R):-
	candidat(L, E)            % (i, o)
	not candidat([C1|T], E),   % (i, i)
	E > C1,
	vale(L, 0, [E, C1|T], R).
```

Modelul matematic pentru predicatul care inițializează C și flag

```
vale_main(l) =
	1. vale(l, 1, [e1, e2]),
		e1 = candidat(l),
		e2 = candidat(l)
		e1 < e2
```

```
%vale_main(L, R)
% (i, o), (i, i)

vale_main(L, R):-
	candidat(L, E1),
	candidat(L, E2),
	E1 < E2,
	vale(L, 1, [E1, E2], R).

```

Variantă alternativă: în "candidat" returnăm și lista fără elementul generat

Model matematic

```
candidat(l1, ..., ln) =
	1. [l1, l2, ..., ln]
		dacă n > 0
	2. [e, l1 ⨁ r]
		unde [e, r] = candidat(l2, ..., ln)
```

```
% (i, o, o), (i, i, o), (i, o, i) (i, i, i)
candidat([H|T], H, T).
candidat([H|T], E, [H|R]):-
	candidat(T, E, R).
```

<hr>

Predicat care colectează toate listele generate de `vale_main`

```
%(i, o), (i, i)
solutii(L, R):-
	findall(Rez, vale_main(L, Rez) R).
```
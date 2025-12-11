
# 1

Se dau N puncte in plan (prin coordonatele lor). Se cere sa se determine toate submultimile de puncte coliniare

```
% Candidat nedeterminist – generează un element din listă

% (i, o), (i, i)

% TESTE

% ?- candidat([a,b,c], X).
% X = a ;
% X = b ;
% X = c.
% ?- candidat([a,b,c], b).
% true.
% ?- candidat([a,b,c], x).
% false.

candidat([H|_], H).
candidat([_|T], R) :- candidat(T, R).

% Test de coliniaritate pentru 3 puncte

% (i, i, i)

% TESTE

% ?- coliniare((0,0),(1,1),(2,2)).
% true.
% ?- coliniare((0,0),(1,1),(2,3)).
% false.


coliniare((X1,Y1),(X2,Y2),(X3,Y3)) :-
    (X2 - X1) * (Y3 - Y1) =:= (Y2 - Y1) * (X3 - X1).

% Compatibil: toate punctele existente trebuie să rămână coliniare

% (i, i)

% TESTE

% ?- compatibil([(0,0),(1,1)], (2,2)).
% true.
% ?- compatibil([(0,0),(1,1)], (2,3)).
% false.
% ?- compatibil([], (5,7)).
% true.
% ?- compatibil([(1,2)], (3,9)).
% true.

compatibil([], _). 
compatibil([_], _).
compatibil([P1, P2 | _], E) :-
    coliniare(P1, P2, E).

% Construirea nedeterministă a unei submulțimi coliniare

% (i, i, o), (i, i, i)

% TESTE

% ?- coliniare_back([(0,0),(1,1),(2,2),(3,5)], [(0,0),(1,1)], Rez).
% Rez = [(2,2),(1,1),(0,0)] ;
% Rez = [(3,5),(1,1),(0,0)] ;
% false.   % 3,5 nu e compatibil, deci nu rămâne
% ?- coliniare_back([(0,0),(1,1),(2,2)], [(0,0),(1,1)], [(2,2),(1,1),(0,0)]).
% true.
% ?- coliniare_back([(0,0),(1,1),(2,2)], [(0,0),(1,1)], [(3,5),(1,1),(0,0)]).
% false.

coliniare_back(L, C, Rez) :-
    candidat(L, E),
    \+ member(E, C),
    compatibil(C, E),
    coliniare_back(L, [E|C], Rez).

coliniare_back(_, C, C). % nu mai putem extinde -> returnăm soluția

% Inițializarea cu două puncte distincte

% (i, o), (i, i)

% TESTE

% ?- coliniare_main([(0,0),(1,1),(2,2)], Rez).
% Rez = [(2,2),(1,1),(0,0)] ;
% false.
% ?- coliniare_main([(0,0),(1,1),(2,3)], Rez).
% Rez = [(1,1),(0,0)] ;
% Rez = [(2,3),(0,0)] ;
% Rez = [(2,3),(1,1)] ;
% false.
% ?- coliniare_main([(0,0),(1,1),(2,2)], [(2,2),(1,1),(0,0)]).
% true.
% ?- coliniare_main([(0,0),(1,1),(2,3)], [(2,3),(1,1),(0,0)]).
% false.

coliniare_main(L, Rez) :-
    candidat(L, P1),
    candidat(L, P2),
    P1 \= P2,
    coliniare_back(L, [P1,P2], Rez).

% Validare: acceptăm doar submulțimi cu minim 3 puncte

% (i)

% TESTE

% ?- valid([a,b,c]).
% true.
% ?- valid([a,b]).
% false.

valid(L):-
	length(L, N),
	N >= 3.

% Colectarea tuturor soluțiilor coliniare de minim 3 puncte

% (i, o), (i, i)

% TESTE

% ?- solutii([(0,0),(1,1),(2,2)], R).
% R = [[(2,2),(1,1),(0,0)]].
% ?- solutii([(0,0),(1,1),(2,3),(2,2)], R).
% R = [[(2,2),(1,1),(0,0)]].
% ?- solutii([(0,0),(1,1),(2,2)], [[(2,2),(1,1),(0,0)]]).
% true.
% ?- solutii([(0,0),(1,1),(2,2)], []).
% false.

solutii(L, R) :-
    findall(S, (coliniare_main(L, S), valid(S)), R).
```



# Modele matematice

```
candidat(l1, ..., ln) = 1. l1                                       , dacă n > 0
						2. candidat(l2, ..., ln)                    , dacă n > 0
```

```
Trei puncte P1 = (x1, y1), P2 = (x2, y2), P3 = (x3, y3)
Trei puncte sunt coliniare dacă determinantul format de vectorii P1 P2 și P1 P3 este 0

coliniare(P1, P2, P3) = 1. adevărat                    , dacă (x2-x1)*(y3-y1)=(y2-y1)*(x3-x1)
						2. fals                        , altfel
```

```
C listă de puncte
C = {P1, P2, ...}
E punct nou

compatibil(C, E) = 1. adevărat                          , dacă C = []
				   2. adevărat                          , dacă C = [P1]
				   3. coliniare(P1, P2, E)              , dacă C = [P1, P2, ...]
```

```
L lista inițială
C soluție candidat
Rez soluția finală

coliniare_back(L, C) = 1. coliniare_back(L, E ⊕ C)
							unde E = candidat(L), E nu aparține C, compatibil(C, E)
					   2. C
					        dacă nu se mai poate extinde
```

```
coliniare_main(L) = 1. coliniare_back(L, [P1, P2])
							unde P1 = candidta(L), P2 = candidat(L), P1 =/= P2
```

```
valid(C) = 1. adevărat                         , dacă |C| >= 3
		   2. fals                             , altfel
```

```
solutii(L) = {S | coliniare_main(L) ^ valid(S)}
```

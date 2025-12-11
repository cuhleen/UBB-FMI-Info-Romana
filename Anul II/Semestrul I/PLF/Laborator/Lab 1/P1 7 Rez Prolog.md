
# 7 a)

Să se scrie un predicat care întoarce reuniunea a doua mulțimi.

```
% cauta(E: Integer, N: List)
% (i, i)

% exemplu
% ?-cauta([1,2,3], 3)
% true
% ?-cauta([1,2,3], 4)
% false

cauta([E | _], E):-!.

cauta([_ | T], E):-
    cauta(T, E).

% reuniune(M: List, N: List, Rez: List)
% (i, i, o)

% exemplu:
% ?-reuniune([1,2,3], [2,3,4], Rez)
% Rez=[1,2,3,4]

reuniune([], N, N):-!.

reuniune([H | T], N, Rez):-
    cauta(N, H), !,
    reuniune(T, N, Rez).

reuniune([H | T], N, [H | Rez]):-
    reuniune(T, N, Rez).

```



<hr>



# 7 b)

Să se scrie un predicat care, primind o listă, întoarce mulțimea tuturor perechilor din listă. De exemplu, `[a, b, c, d]` va produce `[[a, b], [a, c], [a, d], [b, c], [b, d], [c, d]]`

```
% perechi(N: Lista, Perechi: Lista)
% (i, o), (i, i), (o, i)

% exemplu
% ?-perechi([1,2,3], Perechi)
% Perechi = [[1, 2], [1, 3], [2, 3]]
% ?-perechi([1,2,3], [[1,2],[1,3],[2,3]])
% true
% ?-perechi([1,2,3], [[1,2],[1,3]])
% false

perechi([], []).
perechi([X | Rest], Perechi):- 
    combina(X, Rest, XPerechi),
    perechi(Rest, RestPerechi),
    adauga(XPerechi, RestPerechi, Perechi).

% combina(X: Integer, Rest: Lista, Perechi: Lista)
% (i, i, o), (i, o, i), (o, i, i), (i, i, i)

% exemplu
% ?- combina(1, [2,3,4], Perechi)
% Perechi = [[1, 2], [1, 3], [1, 4]]

combina(_, [], []). 
combina(X, [Y | Rest], [[X, Y] | Perechi]) :-
    combina(X, Rest, Perechi).

% adauga(XPerechi: Lista, RestPerechi: Lista, R: Lista)
% (i, i, o), (i, o, i), (o, i, i)

% exemplu
% ?-adauga([1,2,3], [4,5,6], R)
% R = [1,2,3,4,5,6]

adauga([], L, L).
adauga([H | T], L, [H | R]):-
	adauga(T, L, R).
```


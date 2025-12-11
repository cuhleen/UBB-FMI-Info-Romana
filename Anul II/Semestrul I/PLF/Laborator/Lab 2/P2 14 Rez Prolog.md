
# 14 a)

Definiti un predicat care determina predecesorul unui numar reprezentat cifra cu cifra intr-o lista. De ex: [1 9 3 6 0 0] => [1 9 3 5 9 9]

```
% reverseList(L: List, Acc: List, Rez: List)
% (i, i, o), (i, i, i)

% Teste

% model flux (i, i, o)
% ?- reverseList([1,2,3], [], Rez)
% Rez = [3,2,1]
% ?- reverseList([1,2,3,4,5], Rez)
% Rez = [5,4,3,2,1]

% model flux (i, i, i)
% ?- reverseList([1,2,3], [], [3,2,1])
% true
% ?- reverseList([9,8,7,6,5], [], [5,6,7,8,9])
% true
% ?- reverseList([1,2,3], [], [1,2,3])
% false

reverseList([H|T], Acc, Rez):-
    reverseList(T, [H|Acc], Rez).
reverseList([], Acc, Acc).

% scadeUnu(L: List, Ok: Integer, Acc: List, Rez: List)
% (i, i, i, o), (i, i, i, i)

% Teste

% model flux (i, i, i, o)
% ?- scadeUnu([3,2,1], 1, [], Rez)
% Rez = [1,2,2]
% ?- scadeUnu([0,2,1], 1, [], Rez)
% Rez = [1,1,9]
% ?- scadeUnu([0,0,0], 1, [], Rez)
% false

% model flux (i, i, i, i)
% ?- scadeUnu([3,2,1], 1, [], [1,2,2])
% true
% ?- scadeUnu([0,2,1], 1, [], [1,1,9])
% true
% ?- scadeUnu([3,2,1], 1, [], [1,2,3])
% false

scadeUnu([], 0, Acc, Acc).

scadeUnu([0|T], 1, Acc, Rez):-
    !,
	scadeUnu(T, 1, [9|Acc], Rez).

scadeUnu([H|T], 1, Acc, Rez):-
	H1 is H-1,
   	!,
	scadeUnu(T, 0, [H1|Acc], Rez).

scadeUnu([H|T], 0, Acc, Rez):-
	scadeUnu(T, 0, [H|Acc], Rez).
	
% scadeUnuMain(L: List, Rez: List)
% (i, o), (i, i)

% Teste

% model flux (i, o)
% ?- scadeUnuMain([1,2,3], Rez)
% Rez = [1,2,2]
% ?- scadeUnuMain([1,2,0], Rez)
% Rez = [1,1,9]
% ?- scadeUnuMain([0,0,0], Rez)
% false

% model flux (i, i)
% ?- scadeUnuMain([1,2,3], [1,2,2])
% true
% ?- scadeUnuMain([1,2,0], [1,1,9])
% true
% ?- scadeUnuMain([1,2,3], [3,2,1])
% false

scadeUnuMain(List, Rez):-
    !,
	reverseList(List, [], List1),
	scadeUnu(List1, 1, [], Rez).

scadeUnuMain([], []).
```




<hr>



# 14 b)

Se da o lista eterogena, formata din numere intregi si liste de cifre. Pentru fiecare sublista sa se determine predecesorul numarului reprezentat cifra cu cifra de lista respectiva. De ex: [1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6] => [1, [2, 2], 4, 5, [6, 7, 8], 10, 11, [1, 1, 9] 6]

```
% reverseList(L: List, Acc: List, Rez: List)
% (i, i, o), (i, i, i)

% Teste

% model flux (i, i, o)
% ?- reverseList([1,2,3], [], Rez)
% Rez = [3,2,1]
% ?- reverseList([1,2,3,4,5], Rez)
% Rez = [5,4,3,2,1]

% model flux (i, i, i)
% ?- reverseList([1,2,3], [], [3,2,1])
% true
% ?- reverseList([9,8,7,6,5], [], [5,6,7,8,9])
% true
% ?- reverseList([1,2,3], [], [1,2,3])
% false

reverseList([H|T], Acc, Rez):-
    reverseList(T, [H|Acc], Rez).
reverseList([], Acc, Acc).

% scadeUnu(L: List, Ok: Integer, Acc: List, Rez: List)
% (i, i, i, o), (i, i, i, i)

% Teste

% model flux (i, i, i, o)
% ?- scadeUnu([3,2,1], 1, [], Rez)
% Rez = [1,2,2]
% ?- scadeUnu([0,2,1], 1, [], Rez)
% Rez = [1,1,9]
% ?- scadeUnu([0,0,0], 1, [], Rez)
% false

% model flux (i, i, i, i)
% ?- scadeUnu([3,2,1], 1, [], [1,2,2])
% true
% ?- scadeUnu([0,2,1], 1, [], [1,1,9])
% true
% ?- scadeUnu([3,2,1], 1, [], [1,2,3])
% false

scadeUnu([], 0, Acc, Acc).

scadeUnu([0|T], 1, Acc, Rez):-
    !,
	scadeUnu(T, 1, [9|Acc], Rez).

scadeUnu([H|T], 1, Acc, Rez):-
	H1 is H-1,
   	!,
	scadeUnu(T, 0, [H1|Acc], Rez).

scadeUnu([H|T], 0, Acc, Rez):-
	scadeUnu(T, 0, [H|Acc], Rez).
	
% scadeUnuMain(L: List, Rez: List)
% (i, o), (i, i)

% Teste

% model flux (i, o)
% ?- scadeUnuMain([1,2,3], Rez)
% Rez = [1,2,2]
% ?- scadeUnuMain([1,2,0], Rez)
% Rez = [1,1,9]
% ?- scadeUnuMain([0,0,0], Rez)
% false

% model flux (i, i)
% ?- scadeUnuMain([1,2,3], [1,2,2])
% true
% ?- scadeUnuMain([1,2,0], [1,1,9])
% true
% ?- scadeUnuMain([1,2,3], [3,2,1])
% false

scadeUnuMain(List, Rez):-
    !,
	reverseList(List, [], List1),
	scadeUnu(List1, 1, [], Rez).

scadeUnuMain([], []).

% functie(L: List, Rez: List)
% (i, o), (i, i)

% Teste

% model flux (i, o)
% ?- functie([1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6], Rez)
% Rez = [1, [2, 2], 4, 5, [6, 7, 8], 10, 11, [1, 1, 9], 6]

% model flux (i, i)
% ?- functie([1, [2, 3], 4, 5, [6, 7, 9], 10, 11, [1, 2, 0], 6], [1, [2, 2], 4, 5, [6, 7, 8], 10, 11, [1, 1, 9], 6])
% true

functie([], []).

functie([H|T], Rez):-
    is_list(H),
    !,
    scadeUnuMain(H, Rscadere),
    functie(T, Rfunctie),
    Rez = [Rscadere|Rfunctie].

functie([H|T],R):-
    functie(T, Rfunctie),
    R = [H|Rfunctie].
```
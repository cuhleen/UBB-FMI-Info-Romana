
# **Programarea recursivă în LISP**
În problemele noastre dacă se menționează că lista e liniară, înțelegm că poate avea atomi numerici sau nenumerici. Exemplu: (A 3 2 B 10)
Dacă se menționează că lista e neliniară, înțeleg că poate avea atomi numerici, nenumerici, sau subliste. Exemplu: ((A 3) ((2)) B 10)
Dacă nu se menționează, presupunem că e neliniară

O funcție f(a, b) este apelată în list astfel: `(f a b)`
## *Metode de construcție ale listelor*
cons, append, list

| param.               | *cons*      | *append*      | *list*              |
| -------------------- | ----------- | ------------- | ------------------- |
| 'A 'B                | (A.B)       | eroare        | (A B)               |
| 'A '(B C)            | (A B C)     | eroare        | (A (B C))           |
| '(A B) '(C D)        | ((A B) C D) | (A B C D)     | ((A B) (C D))       |
| '(A B) 'D            | ((A B).D)   | ((A B) D)     | ((A B) D)           |
| 'A 'B 'C             | eroare      | eroare        | (A B C)             |
| '(A B) '(C D) '(E F) | eroare      | (A B C D E F) | ((A B) (C D) (E F)) |
| '(A B) 'C '(E F) 'D  | eroare      | eroare        | ((A B) C (E F) D)   |
| '(A B) '(E F) 'D     | eroare      | (A B E F.D)   | ((A B) (E F) D)     |
| 'A NIL               | (A)         | eroare        | (A ())              |
| '(A) NIL             | ((A))       | (A)           | ((A) ())            |

(A B) = `[A|-]->[B|x]`
(A.B) = `[A|B]`

cons ia *doar* două variabile
append *necesită* ca toate mai puțin ultima (ultima e opțională) variabilele să fie liste
## *Probleme*
### Eliminați toate aparițiile unui atom dintr-o listă neliniară
Ex.: ((A) B 3 (A C))
Eliminăm A
(() B 3 (C))

*Modelul matematic*
```
elimina(l1, ..., ln, elem) =
	1. []
		dacă l = []
	2. elimina(l2, ..., ln, elem)
		dacă l1 = elem
	3. elimina(l1, elem) ⨁ elimina(l2, ..., ln), elem)
		dacă l1 e listă
	4. l1 ⨁ elimina(l2, ..., ln, elem)
		dacă l1 != elem și nu e listă
```

*Cod*
``` lisp
(defun elimina(l elem)
	(cond
		((null l) NIL)
		((equal(car l) elem) (elimina(cdr(l) elem))
		(listp(car l) (cons (elem (car l) elem) (elimina(cdr l) elem))) (+ (cons(car l)(elimina(cdr l) elem)))
	)
)
```

### Problema 2
Se dă o listă liniară. Să se construiască o listă cu pozițiile pe care se află elementul minim
Exemplu: (a *1* 2 3 *1* 5 a *1* b) => (2 5 8)

*Modelul matematic*
```
min(l1, ..., ln, m) =
	1. m
		dacă l e vidă
	2. min(l2, ..., ln, m)
	   dacă l1 > m și l1 e număr
	3. min(l2, ..., ln, l1)
	   dacă l1 < m și l1 e număr
	4. min(l2, ..., ln, m)
	   altfel
	   
min_main(l1, ..., ln) = min(l1, ..., ln, infinit)

list_index(l, i, el) =
	1. []
	   dacă l e vidă
	2. i ⨁ list_index(l2, ..., ln, i + 1, el)
	   dacă l1 = el
	3. list_index(l2, ..., ln, i, el)
	   altfel
	   
list_index_main(l) = list_index(l, 1, min_main(l))
```

*Cod*
``` lisp
(defun min(l min_curent)
	(cond
		((null l) min_curent)
		((AND(numberp(car l)(>(car l) min_current))(min(car l) min_current))
		((AND(numberp(car l)(<=(car l) min_current))(min(cdr l)(car l)))
		(T (min (car l) min_current))
	)
)

(defun min_aux(L)
	(min L most-positive-double-float)
)

```

### Problema 3
Interclasarea a două liste care sunt crescătoare cu eliminarea dublurilor
*a)* listele inițiale au elemente distincte
Exemplu: (1 2 3 5) (1 4 5 6) => (1 2 3 4 5 6)
ez pz
*b)* listele inițiale pot avea dubluri
Exemplu: (1 2 2 3 5) (1 4 4 4 4 4 4 4 4 4 4 4 5 6) => (1 2 3 4 5 6)
Două metode:
1. Parcurgem o dată prin ambele liste ca să eliminăm dublurile, apoi interclasăm normal
2. Reținem ultimul număr adăugat, facem o verificare să nu fie același


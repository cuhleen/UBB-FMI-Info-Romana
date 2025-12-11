
# **Funcții MAP**
- *MAPCAR*
```lisp
(MAPCAR #'f '(l11 l12 ... l1n) '(l21 l22 ... l2n) ... (lm1 lm2 ... lmn))
(LIST (f l11 l21 ... l1n) (f l12 l22 ... l2n) ... (f lm1 lm2 ... lmn))
```

Caz particular. `(MAPCAR #'f '(l1 ... ln))` => `(LIST (f l1) (f l2) ... (f ln))`

- *MAPCAN*
Grupează rezultatele folosing NCONC (concatenare in-place)

- *MAPLIST*
- *MAPCON*

## ==La colocviu intră și MAPLIST și MAPCON==

## *Probleme*
### Să se returneze numărul nodurilor de pe niveluri pare într-un arbore n-ar, reprezentat subs forma (răd (subarb1) (subarb2) ... (subarb n)). Rădăcina este considerată pe nivelul 1

Ex.: (A (B (D (E) (F))) (C) (G)) => 5
![[Pasted image 20251209102440.png]]

```
nrNoduriPare(l, niv) =
	1. 0
	    dacă l e atom și niv e par
	2. 1
	    dacă l e atom și niv e impar
	3. sumă cu i de la 1 la n din nrNoduriPare(li, niv + 1)
	    altfel

nrNoduriPareMain(l) = nrNoduriPare(l, 0)
```

```
nrNoduriPare2(l1, ..., ln, niv) = 
	1. 0 + sumă cu i de la 2 la n din nrNoduriPare2(li, niv + 1),
	    dacă niv e impar
	2. 1 + sumă cu i de la 2 la n din nrNoduriPare(li, niv + 1)
	    dacă niv e par
	3. 0
	    dacă n = 0

nrNoduriPare2Main(l) = nrNoduriPare2(l, 1)
```

```lisp
(defun nrNoduriare (l niv)
	(cond
		((AND (atom l) (add niv)) 0)
		((AND (atom l) (even niv)) 1)
		(+ (apply '+ (MAPCAR #' (lambda(x)(nrNoduriPare x (+ niv 1))) l)))
	)
)
```

### Se dă o listă neliniară L. Să se formeze o listă conținând toate sublistele lui L în care primul atom numeric de la stânga la dreapta este 5

Ex.: (A 5 (B C D) 2 (G (5 H) 7) 11) => ((A 5 (B C D) 2 (G (5 H) 7) 11))...
*Sol:*
- o funcție care verifică proprietatea
- o funcție care construiește rezultatul - se poate folosi fcț. MAP

```
verifica(l) = 
	1. 0
	    dacă l1 = 5
	2. 1
	    dacă l1 e număr diferit de 5
	3. 2
	    dacă n = 0
	4. verifica(l2, ..., ln)
	    dacă l1 e atom nenumeric
	5. 0
	    dacă l1 e listă și verifică(l1) = 0
	6. 1
	    dacă l1 e listă și verifică(l1) = 1
	7. verifica(l2, ..., ln)
	    altfel

Sau mai pe scurt

verifica(l) =
	1. 2
		dacă n = 0
	2. 0
		dacă l1 = 5
	3. 1
	    dacă l1 e număr diferit de 5
	4. verifica(l1)
	    dacă verifica(l1) =/= 2, l1 e listă
	5. verifica(l2, ..., ln)
	    altfel
```

```lisp
(defun verifica(l)
	(cond
		((null l) 2)
		((equal (nr l) 5) 0)
		((numberp (car l)) 1)
		((AND (listp (car l)) ))
		zzz upsi
	)
)
```

*Metoda 2*
Transformăm lista inițială: liniarizare și eliminarea atomilor nenumerici

```
transformare(l) =
	1. (l)
		dacă l este atom numeric
	2. []
		dacă l este atom nenumeric
	3. Reuniune cu i de la 1 la n din transformare(l indice i)
		altfel	   
```

``` lisp
(defun transformare (l)
	(cond
		((numberp l) (LIST l))
		((atom l) NIL)
		((MAPCAN #'transformare l))
	)
)
```


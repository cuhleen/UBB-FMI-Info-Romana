# **Să se scrie o funcție care întoarce intersecția a doua mulțimi**

### Lisp

```lisp
(DEFUN intersectie (l p)
	(COND
	    ((null l) nil)
	    ((existaInP p (CAR l)) (CONS (CAR l) (intersectie (CDR l) p)))
	    (T (intersectie (CDR l) p))
	)
)

(DEFUN existaInP (p e)
    (COND
    ((null p) nil)
    ((equal (CAR p) e) T)
    (T (existaInP (CDR p) e))
    )
)
(print (intersectie '(1 4 3) '(5 3 1 2))) ; => (1 3)

(print (intersectie '(1 2 3 4) '(3 4 5 6))) ; => (3 4)

(print (intersectie '(a b c) '(c a d e))) ; => (a c)

(print (intersectie '(7 7 7) '(7))) ; => (7 7 7)

(print (intersectie '() '(1 2 3))) ; => NIL
```

### Modele Matematice

```
existaInP(p1, ..., pn, e) =
    1. fals,
         dacă p = []

    2. adevărat,
         dacă p1 = e

    3. existaInP(p2,…,pn , e),
         altfel
```

```
intersectie(l1, ..., ln, p) =
    1. [],
         dacă l = []

    2. [l1] ⨁ intersectie(l2,…,ln , p),
         dacă existaInP(p , l1)

    3. intersectie(l2,…,ln , p),
         altfel
```

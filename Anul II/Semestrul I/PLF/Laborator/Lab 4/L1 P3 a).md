
# **Definiți o funcție care întoarce produsul a doi vectori**

### Lisp

```lisp
(DEFUN produs (l p)
    (COND
        ((OR (null l) (null p)) 0)
        (T (+ (* (CAR l) (CAR p)) (produs (CDR l) (CDR p))))
    )
)

(print (produs '(2 1 2 3) '(1 3 3 4 6))) ; 2*1 + 1*3 + 2*3 + 3*4 = 2 + 3 + 6 + 12 = 23

(print (produs '(1 2 3) '(4 5 6))) ; 1*4 + 2*5 + 3*6 = 32

(print (produs '(0 0 0) '(5 6 7))) ; 0

(print (produs '() '(1 2 3))) ; 0

(print (produs '(5) '(10 20 30))) ; 5*10 = 50

```

### Model Matematic

```
produs(l1, ..., ln , p1, ..., pm) =
    1. 0,
        dacă l = [] sau p = []

    2. l1 * p1 + produs(l2, …, ln , p2, …, pm),
        altfel
```

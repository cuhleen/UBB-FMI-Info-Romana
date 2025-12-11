# **Definiți o funcție care sortează fără păstrarea dublurilor o listă liniară**

### Lisp

```lisp
(DEFUN sortare (l)
    (COND
        ((null l) nil)
        (T (insert (sortare (CDR l)) (CAR l)))
    )
)

(DEFUN insert (l e)
    (COND
        ((null l) (list e))
        ((equal e (CAR l)) l) ; Fără această linie se păstrează dublurile
        ((< e (CAR l)) (CONS e l))
        (T (CONS (CAR l) (insert (CDR l) e)))
    )
)

(print(sortare '(3 2 7 5 1 4)))
```

### Modele Matematice

```
sortare(l1, ..., ln) =
    1. [],
        dacă l = []

    2. insert(sortare(l2, ..., ln), l1),
        altfel
```

```
insert(l1, ..., ln , e) =
    1. [e],
        dacă l = []

    2. [l1, ..., ln],
        dacă e = l1

    3. [e] ⨁ [l1, ..., ln],
        dacă e < l1

    4. [l1] ⨁ insert(l2, ..., ln , e),
        altfel
```
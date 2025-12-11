# **Să se construiască o funcție care întoarce adâncimea unei liste**

### Lisp

```lisp
(DEFUN adancime (l maxAdancime)
    (COND
        ((null l) maxAdancime)
        ((listp (CAR l)) (MAX (adancime (CAR l) (+ maxAdancime 1)) (adancime (CDR l) maxAdancime)))
        (T (adancime (CDR l) maxAdancime))
    )
)

(print (adancime '(1 2 3) 0)) ; => 0

(print (adancime '((1 2) 3 4) 0)) ; => 1

(print (adancime '(1 (2 (3))) 0)) ; => 2

(print (adancime '((a b) (c d (e))) 0)) ; => 2

(print (adancime '(2 1 (2 (3 4) ((5 6) 7)) (7 8) 9) 0)) ; => 3
```

### Model Matematic

```
adancime(l, maxAdancime) =
    1. maxAdancime
       dacă l = []

    2. max(adancime(car(l), maxAdancime + 1), adancime(cdr(l), maxAdancime))
       dacă car(l) este listă

    3. adancime(cdr(l), maxAdancime)
       dacă car(l) nu este listă
```

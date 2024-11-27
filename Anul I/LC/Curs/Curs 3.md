# Logica propozițiilor

### Propoziție
Propozițiile logice sunt modele ale afirmațiilor propoziționale care sunt fie adevărate fie false

### Sintaxa
Parte gramaticală care studiază regulile privitoare la îmbinarea cuvintelor în propoziții și a propozițiilor în fraze

### Semantica
Ramură a lingvisticii care se ocupă de sens sau ceva idfk

# Sintaxa logicii propozițiilor
- alfabetul
	- Σ<sub>p</sub> = Var_propoz ∪ conective ∪ `{[.]}`
	- Var_propoz = {p, q, r, p<sub>1</sub> p<sub>2</sub>, ...}
	- conective = {- (negativ), v (și), ʌ (sau), → (implică), ↔ (echivalent), ↑ (not and), ↓ (not or), ⊕ (xor)}

# Reguli de formare a formulelor propoziționale
- F<sub>p</sub> 

***Bradăre
Scriu doar titluri de pe slide-uri/chestii scurte fuck this shit
Curs 3 pe moodle idk momentan nu e pus da va fi eventual***

# Interpretarea (Def.)

# Concepte Semantice (Def.)
Interpretare adevărată = model
Interpretare falsă = antimodel
Formula propozițională U s.n.
- consistentă, dacă are cel puțin un model
- validă, dacă este adevărată în orice propoziție
- inconsistentă, dacă nu are niciun model, adică dacă este mereu falsă
- **mai e una dar a dat skip prea repede bruh fuck you**

# Exemplu
U(p, q, r) = (-p v q) ʌ (r v p)
V(p, q, r) = (-p ʌ q) v (q ʌ r) v (q ʌ p)


|               | p   | q   | r   | -p v q | r v p | U(p, q, r) | V(p, q, r) | p ↑ -p | p ↑ -p |
| ------------- | --- | --- | --- | ------ | ----- | ---------- | ---------- | ------ | ------ |
| i<sub>1</sub> | T   | T   | T   | T      | T     | T          | T          | T      | F      |
| i<sub>2</sub> | T   | T   | F   | T      | T     | T          | T          | T      | F      |
| i<sub>3</sub> | T   | F   | T   | F      | T     | F          | F          | T      | F      |
| i<sub>4</sub> | T   | F   | F   | F      | T     | F          | F          | T      | F      |
| i<sub>5</sub> | F   | T   | T   | T      | T     | T          | T          | T      | F      |
| i<sub>6</sub> | F   | T   | F   | T      | F     | F          | F          | T      | F      |
| i<sub>7</sub> | F   | F   | T   | T      | T     | T          | T          | T      | F      |
| i<sub>8</sub> | F   | F   | F   | T      | F     | F          | F          | T      | F      |
Modele pt U: 1, 2, 5, 7
p ↑ -p = tautologie
p ↑ -p = inconsstentă
U, V = contingente, consistente

# Metasimboluri

# Echivalențe logice în logica propozițională
### Legile

# Principiul dualității
# Forme normale în logica propozițiilor
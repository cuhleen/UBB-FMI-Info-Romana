Identifică 3 relații 1-n și o relație m-n
Inserează 10 intrări în fiecare
Fă un tabel nou cu: NumeTabela1, NumeTabela2, IDTabela1, IDTabela2, (OPȚIONAL DAR MERGE) DescriereModificare, TimestampModificare
Creează proceduri stocate GENERICE pentru modificările pe tabele: 1-n -> n-1 , n-1 -> 1-n , m-n -> 1-n , 1-n -> m-n , 1-n -> 1-1
Utilizează Backup pentru a salva stadiile bazei de date

# 1-n -> n-1

Un ingredient este în mai multe ceaiuri
```
	Ingredients
Iid
...

	Teas
Cid
...
Iid
```

Un ceai folosește mai multe ingrediente
```
	Ingredients
Iid
...
Cid

	Teas
Cid
...
```

drop FK
drop coloana Iid din Teas
add coloana Cid în Ingredients
add FK Cid în Ingredients

# 1-n -> m-n

```
	Teas
Cid
...
Sid

	Possibilities
Cid
...
```

```
	Teas
Cid
...

	Possibilities
Sid
...

	PossibilitiesTeas
Sid
Cid
```

drop FK Sid din Teas
drop col Sid din Teas
creează tabela PossibilitiesTeas (Sid FK, Cid FK, constrângere PK)

# m-n -> 1-n

opusul
ștergem tabela intermediară
adăugăm coloana respectivă pentru FK
```
	Shops
Pid
...

	Teas
Cid
...

	ShopsTeas
Pid
Cid
```

```
	Shops
Pid
...

	Teas
Cid
...
Pid
```

# 1-n -> 1-1

```
	Managers
Mid
...

	Shops
Pid
...
Mid
```

```

```

Exemplu:
Manager X manage-uiește KFC, Starbucks, și Panemar
Luăm ultima intrare unde e FK și facem PK
Dar acum ne dispar intrările de KFC și Starbucks, NU vrem asta
Soluție: mai adăugăm 2 intrări în tabela de Managers și să îi legăm la restaurantele respective

SAU

Add new column în Shops + update valorile Mid (Managers -> set FK + PK)
?


# **Subiect Eliminatoriu**
==Presupunem că se cere ca sortările să facă șirul crescător, un șir de lungime n==

## *Bubble Sort*
Luăm primul element, `i = 0`. Îl comparăm cu cel din dreapta lui, elementul pe poziția `i + 1`. Dacă este mai mare, le interschimbăm. Dacă nu, incrementăm `i` cu 1. Inițial, `i` crește până la `n - 1`, după care se resetează la 0, și crește până la `n - 2`. Se repetă până când `n` ajunge să fie 1.
Complexitate timp: O(n<sup>2</sup>)
Complexitate spațiu: O(1)

```python
def bubbleSort(arr):
	for n in range(len(arr) - 1, 0, -1):
		for i in range(0, n):
			if arr[i] > arr[i + 1]:
				arr[i], arr[i + 1] = arr[i + 1], arr[i]

	return arr
```

## *Insertion Sort*
Luăm al doilea element, `i = 1`. Îl comparăm cu cel din stânga lui, elementul pe poziția `i - 1`, poziție pe care o notăm cu `j`. Dacă este mai mic, le interschimbăm și scădem `j`. Repetăm până când elementul ajunge la locul potrivit sau `j` devine 0. Apoi trecem la următorul element și repetăm procesul. Continuăm până la ultimul element, astfel încât partea din stânga lui `i` să fie mereu sortată.
Complexitate timp: O(n<sup>2</sup>)
Complexitate spațiu: O(1)

```python
def insertionSort(arr):
	for i in range(1, len(arr)):
		elementCurent = arr[i]
		j = i - 1
		while j >= 0 and elementCurent < arr[j]:
			arr[j + 1] = arr[j]
			j -= 1
		arr[j + 1] = elementCurent

	return arr
```

## *Selection Sort*
Luăm primul element, `i = 0`. Căutăm cel mai mic element din listă începând de la `i` până la final. Dacă îl găsim, îl interschimbăm cu elementul de pe poziția `i`. Incrementăm `i` și repetăm procesul pentru restul listei. Continuăm până când `i` ajunge la `n - 1`, moment în care lista este sortată.
Complexitate timp: O(n<sup>2</sup>)
Complexitate spațiu: O(1)

```python
def selectionSort(arr):
	for i in range(0, len(arr) - 1):
		indexCelMaiMicElement = i
		for j in range(i + 1, len(arr)):
			if arr[j] < arr[indexCelMaiMicElement]:
				indexCelMaiMicElement = j
		arr[i], arr[indexCelMaiMicElement] = arr[indexCelMaiMicElement], arr[i]

	return arr
```

## *Merge Sort*
Împărțim lista în două părți egale. Aplicăm recursiv Merge Sort pe fiecare jumătate. Când ajungem la liste de un singur element, începem să le interclasăm. Luăm primul element din fiecare jumătate și îl punem pe cel mai mic într-o listă nouă. Repetăm până când toate elementele sunt intercalate corect. Continuăm acest proces până reconstruim lista sortată.
Complexitate timp: O(n log(n))
Complexitate spațiu: O(n) (pentru că se folosesc liste auxiliare pentru interclasare)

```python
def mergeSort(arr):
	if len(arr) <= 1:
		return arr

	mijloc = len(arr) // 2
	listaStanga = arr[:mijloc]
	listaDreapta = arr[mijloc:]

	mergeSort(listaStanga)
	mergeSort(listaDreapta)

	return merge(listaStanga, listaDreapta)

def merge(listaStanga, listaDreapta):
	listaSortata = []
	i = j = 0

	while i < len(listaStanga) and j < len(listaDreapta):
		if listaStanga[i] < listaDreapta[j]:
			listaSortata.append(listaStanga[i])
			i += 1
		else:
			listaSortata.append(listaDreapta[j])
			j += 1

	while i < len(listaStanga):
		listaSortata.append(listaStanga[i])
		i += 1
	while j < len(listaDreapta):
		listaSortata.append(listaDreapta[j])
		j += 1
		
	"""
	^ echivalent cu
	listaSortata.extend(listaStanga[i:])
	listaSortata.extend(listaDreapta[j:])
	"""

	return listaSortata
```

## *Quick Sort*
Alegem un pivot, un element aleatoriu din listă. Mutăm toate elementele mai mici decât pivotul în stânga lui și toate elementele mai mari în dreapta. Aplicăm recursiv Quick Sort pe partea stângă și pe partea dreaptă. Continuăm până când fiecare sublistă are un singur element, moment în care lista este sortată.
Complexitate timp: O(n log(n))
Complexitate spațiu: O(log(n))

```python
def quickSort(arr):
	if len(arr) <= 1:
		return arr

	pivot = random.choice(arr)
	listaStanga = [element for element in arr if element < pivot]
	listaMijloc = [element for element in arr if element == pivot]
	listaDreapta = [element for element in arr if element > pivot]

	return quickSort(listaStanga) + listaMijloc + quickSort(listaDreapta)
```

## *Căutare Binară*
Luăm `stanga = 0`, `dreapta = n - 1`, `mijloc = (stanga + dreapta) // 2`. Dacă elementul căutat este egal cu `arr[mijloc]`, l-am găsit. Dacă elementul căutat este mai mic, căutăm în jumătatea stângă (`dreapta = mijloc - 1`). Dacă este mai mare, căutăm în jumătatea dreaptă (`stanga = mijloc + 1`). Repetăm acest proces până găsim elementul sau intervalul de căutare devine invalid (`stanga > dreapta`).
Complexitate timp: O(log n)
Complexitate spațiu: O(1)

### Recursiv
```python
# funcția se apelează astfel:
# cautareBinara(arr, 0, n - 1, element)

def cautareBinara(arr, stanga, dreapta, element):
	if stanga > dreapta:
		return -1

	mijloc = (stanga + dreapta) // 2
	if element == arr[mijloc]:
		return mijloc
	elif element < arr[mijloc]:
		return cautareBinara(arr, stanga, mijloc - 1, element)
	else:
		return cautareBinara(arr, mijloc + 1, dreapta, element)
```

### Iterativ
```python
def cautareBinara(arr, element):
	stanga = 0
	dreapta = len(arr) - 1
	mijloc = (stanga + dreapta) // 2

	while stanga <= dreapta:
		if arr[mijloc] == element:
			return mijloc
		elif element < arr[mijloc]:
			dreapta = mijloc - 1
		else
			stanga = mijloc + 1

	return -1
```

<hr>

# **Programare Dinamică**

[link](https://www.pbinfo.ro/articole/20677/subsir-crescator-de-lungime-maxima)

_Se dă un șir cu `n` elemente, numere naturale. Determinați un cel mai lung subșir crescător al șirului._

De exemplu, pentru `n=10` și șirul `A=(5, 10, 7, 4, 5, 8, 9, 8, 10, 2)`, cel mai lung subșir crescător va conține `5` elemente `(5, 7, 8, 9, 10)` sau `(4, 5, 8, 9, 10)`.

Problema este una clasică și se rezolvă prin programare dinamică. Subșirul cerut se mai numește și _subșir crescător maximal_.

## *O soluție O(n^2)*

### Determinarea lungimii maxime

Pentru a determina lungimea maximă a unui subșir crescător al lui `A`, vom construi un șir suplimentar `LG[]`, cu proprietatea că `LG[i]` este lungimea maximă a unui subșir care se începe în `A[i]`. Atunci lungimea maximă a unui subșir crescător va fi cel mai mare element din tabloul `LG`.

Vom construi șirul `LG` progresiv, începând de la ultimul element din `A`, bazându-ne pe următoarele observații:

- `LG[i] ≥ 1`, `∀i`
- `LG[n] = 1`
- vom determina `LG[i]` astfel: analizăm toate elementele `A[j]`, cu `j>i` și îl alegem pe acela pentru care `LG[j]` este maxim și `A[i]≤A[j]`. Fie `jmax` indicele acestui element. Atunci `LG[i]` devine `LG[i] = LG[jmax] + 1` – elementul `A[i]` se lipește de subșirul care începe în `A[jmax]`.

Secvență C++:
```cpp
LG[n] = 1;
for(int i = n - 1 ; i > 0 ; i --)
{
    LG[i] = 1;
    for(int j = i + 1 ; j <= n; ++ j)
        if(A[i] <= A[j] && LG[i] < LG[j] + 1)
            LG[i] = LG[j] + 1;
}
```

După construirea șirului `LG`, lungimea subșirului maximal se determină ca fiind cea mai mare valoare din tabloul `LG`.
```cpp
int pmax = 1;
for(int i = 2 ; i <= n ; i ++)
    if(LG[i] > LG[pmax])
        pmax = p;
cout << LG[pmax];
```

### Reconstituirea subșirului

Există mai multe modalități de reconstituire a subșirului maximal. De asemenea, trebuie spus că pot exista mai multe șiruri maximale; în unele probleme poate fi determinat oricare, în altele trebuie determinat un subșir cu proprietăți suplimentare.

O soluție constă în construirea unui șir suplimentar, `Next` cu următoarea semnificație: `Next[i]` este următorul element în subșirul crescător maximal care începe cu `A[i]`. Dacă nu există un element următor, atunci `LG[i] = -1`. Acest tabloul se construiește în același timp cu `LG`, astfel:
```cpp
LG[n] = 1, Next[n] = -1;
for(int i = n - 1 ; i > 0 ; i --)
{
    LG[i] = 1 , Next[n] = -1;
    for(int j = i + 1 ; j <= n; ++ j)
        if(A[i] <= A[j] && LG[i] < LG[j] + 1)
            LG[i] = LG[j] + 1, Next[i] = j;
}
```

Pentru a afișa subșirul, vom extrage informațiile necesare din șirul `Next`, pornind de la indicele `pmax` determinat mai sus:
```cpp
int p = pmax;
do
{
    cout << p << " ";
    p = Next[p];
}
while(p != -1);
```

Putem reconstitui subșirul fără a construi șirul `Next`. La fiecare pas al structurii repetitive de mai sus vom determina un indice `pUrm` cu proprietatea că `LG[pUrm]==LG[p]-1` și `A[p] ≤ A[pUrm]`:

```cpp
int p = pmax;
do
{
    cout << p << " ";
    int pUrm = p + 1;
    while(pUrm <= n && ! (A[pUrm] >= A[p] && LG[pUrm] == LG[p] - 1))
        pUrm ++;
    if(pUrm <= n)
        p = pUrm;
    else
        p = -1;
}
while(p != -1);
```

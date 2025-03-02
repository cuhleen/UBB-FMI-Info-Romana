
# **Enunț**
### Agenție de Turism
O aplicație pentru *gestiunea pachetelor de călătorie* oferite de o agenţie de turism. Aplicația stochează pachete de călătorie oferite clienţilor după cum urmează: *data de început* și *cea de sfârșit* a călătoriei, *destinația* și *prețul*. Aplicația permite *adăugare*, *ștergere* individuală și după criterii, *filtrare* după criterii, și *rapoarte*.

# **Lista de Funcționalități**
### *1. Adăugare*
- Adaugă pachet de călătorie.
- Modifică pachet de călătorie.
	- Modificări individuale ale fiecărui criteriu al unui pachet.

### *2. Ştergere*
- Ştergerea tuturor pachetelor de călătorie disponibile pentru o destinaţie dată.
- Ştergerea tuturor pachetelor de călătorie care au o durată mai scurtă decât un număr de zile dat.
- Ştergerea tuturor pachetelor de călătorie care au prețul mai mare decât o sumă dată.

### *3. Căutare*
- Tipărirea pachetelor de călătorie care presupun un sejur într-un interval dat (e.g. 10/08/2018 - 24/08/2018 - un pachet valid este cel a cărui dată de început este aceeași sau după de data de început citită şi data de sfârşit este înainte sau aceeași cu data de sfârşit introdusă de la tastatură).
- Tipărirea pachetelor de călătorie cu o destinație dată și cu preţ mai mic decât o sumă dată.
- Tipărirea pachetelor de călătorie cu o anumită dată de sfârşit.

### *4. Rapoarte*
- Tipărirea numărului de oferte pentru o destinație dată.
- Tipărirea tuturor pachetelor disponibile într-o anumită perioadă citită de la tastatură (vezi 3.i.) în ordinea crescătoare a prețului. Tipărirea mediei de preț pentru o destinaţie dată.

### *5. Filtrare*
- Eliminarea ofertelor care au un preț mai mare decât cel dat și o destinaţie diferită de cea citită de la tastatură.
- Eliminarea ofertelor în care sejurul presupune zile dintr-o anumită lună (citită de la tastatură sub forma unui număr natural între 1 şi 12).

### *6. Undo*
- Refacerea ultimei operații care a modificat lista de oferte.

# **Plan de Iterații**

| *Iterație* | *Obiectiv*                                                           | *Funcționalități implementate*                                             | *Durată*                    |
| ---------- | -------------------------------------------------------------------- | -------------------------------------------------------------------------- | --------------------------- |
| *1*        | Implementare CRUD<br><br>Implementarea căutării<br><br>Testare       | Adăugare, Ștergere<br><br>Căutare după interval, destinație, data sfârștit | 17 Octombrie - 24 Octombrie |
| *2*        | Implementare a rapoartelor și filtrării după criterii<br><br>Testare | Rapoarte, Filtrare                                                         | 24 Octombrie - 31 Octombrie |
| *3*        | Implementarea funcției de Undo<br><br>Testare                        | Undo                                                                       | 31 Octombrie - 7 Noiembrie  |

# **Scenarii de Rulare**
### *Scenariul 1*: Adăugarea unui nou pachet de călătorie
- *Input:* Adăugare pachet cu data de început 2025/04/10, data de sfârșit 2025/04/25, destinația "Amsterdam", preț 480.
- *Așteptat:* Pachetul este adăugat în baza de date și este afișat la listarea pachetelor disponibile.
### *Scenariul 2*: Ștergerea pachetelor pentru o destinație dată
- *Input:* Ștergere pachete pentru destinația "Brasov".
- *Așteptat:* Toate pachetele cu destinația "Brasov" sunt eliminate din lista pachetelor disponibile.
### *Scenariul 3*: Afișarea pachetelor într-un interval de timp specificat
#### (Căutare)
- *Input:* Căutare pachete de călătorie între 2025/01/01 și 2026/01/01.
- *Așteptat:* Aplicația afișează toate pachetele disponibile în acest interval, fără a modifica lista pachetelor disponibile.
### *Scenariul 4*: Afișarea mediei de preț pentru o destinație specificată
#### (Rapoarte)
- *Input:* Afișarea mediei de preț pentru pachetele disponibile pentru "Brasov".
- *Așteptat:* Aplicația calculează suma prețurilor pachetelor cu destinația "Brasov" și o împarte la numărul pachetelor cu destinația "Brasov", afișând rezultatul.
### *Scenariul 5*: Refacerea ultimei operații
#### (Undo)
- *Input:* Se efectuează o operație de ștergere, apoi se apelează funcția de Undo.
- *Așteptat:* Lista de pachete revine la starea dinaintea operației de ștergere.



# **Enunț**
O aplicație pentru o *bibliotecă*. Aplicația stochează cărți (*titlu*, *descriere*, *autor*) și clienți (*nume*, *cnp*). Aplicația permite *căutarea cărților* după autor, *căutarea clienților* după data nașterii, închiriere și returnare a cărților. Aplicație permite vizualizarea *rapoartelor* pentru cele mai închiriate cărți, clienții cu cărți închiriate, și afișarea primilor 20% dintre cei mai activi clienți. Aplicația permite *adăugare*, *ștergere* individuală și după criterii.

# **Lista de Funcționalități**
### *1. Adăugare*
- Adaugă carte.
- Adaugă client.
- Adăugarea unei închirieri, o legătură între o carte și un client.
- Modifică carte.
	- Modificări individuale ale fiecărui criteriu al unei cărți.
- Modifică client.
	- Modificări individuale ale fiecărui criteriu al unui client.

Modificările cărților sau a clienților actualizează automat și închirierile.

### *2. Ştergere*
- Ștergerea cărților după ID.
- Ștergerea clientilor după ID.
- Ștergerea unei închirieri după ID-ul clientului și a cărții

### *3. Filtre
- Afișarea cărților ce au un autor dat.
- Afișarea clienților ce sunt născuți între două date specificate.

### *4. Rapoarte*



# **Plan de Iterații**

| *Iterație* | *Obiectiv*                                                     | *Funcționalități implementate*                                                                  | *Durată*                    |
| ---------- | -------------------------------------------------------------- | ----------------------------------------------------------------------------------------------- | --------------------------- |
| *1*        | Implementare CRUD<br><br>Implementarea căutării<br><br>Testare | Adăugare, Ștergere, Update<br><br>Căutare carte după autor<br>Căutare client după data nașterii | 17 Noiembrie - 14 Noiembrie |
| *2*        | Închiriere/Returnare<br><br>Testare                            | Închirieri și returnări per carte și per client                                                 | 14 Noiembrie - 21 Noiembrie |
| *3*        | Rapoarte<br><br>Testare                                        | Rapoartele cerute                                                                               | 21 Noiembrie - 28 Noiembrie |

# **Scenarii de Rulare**

| Input | Output așteptat                                                          | Output afișat                                                                     | Date Interne                                                            |
| ----- | ------------------------------------------------------------------------ | --------------------------------------------------------------------------------- | ----------------------------------------------------------------------- |
|       | O confirmare a funcționalităților aplicației                             | "Testele au rulat cu succes!"                                                     | Verificări interne a nivelelor arhitecturii (Repo, Domain, Service, UI) |
|       | Un meniu ce arată utilizatorului toate opțiunile valabile ale aplicației | Meniu principal                                                                   | Repozitoriile sunt goale                                                |
| 11    | Câmpuri de completat adecvate pentru adăugarea unei cărți                | Câmpuri pentru introducere a ID-ului, titlului, descrierii, și autorului a cărții | Se fac verificări pentru datele introduse                               |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |
|       |                                                                          |                                                                                   |                                                                         |

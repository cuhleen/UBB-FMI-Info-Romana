#pragma once
#include "../Domain/cheltuiala.h"
typedef Cheltuiala TipElement;
typedef struct {
    TipElement* elemente;
    int dimensiune;
    int capacitate;
} Lista;

/**
    @brief Funcție care creează o listă goală
    @return lista goală
*/
Lista creeazaListaGoala();

/**
    @brief Funcție care dealocă o listă
*/
void distrugeLista(Lista* lista);

/**
    @brief Funcție care returnează elementul din listă de pe o poziție dată
    @param lista lista de elemente în care căutăm
    @param pozitieElementInLista poziția elementului în listă
    @return elementul
*/
TipElement getElement(Lista* lista, int pozitieElementInLista);

/**
    @brief Funcție care setează și returnează elementul din listă de pe o poziție dată
    @param lista lista de elemente în care căutăm
    @param pozitieElementInLista poziția elementului în listă
    @param cheltuiala elementul nou pe care îl setăm
    @return elementul
*/
TipElement setElement(Lista* lista, int pozitieElementInLista, Cheltuiala cheltuiala);

/**
    @brief Funcție care returnează lungimea unei liste date
    @param lista lista de elemente
    @return lungimea listei
*/
int size(Lista* lista);

/**
    @brief Funcție care adaugă un element dat într-o listă dată
    @param lista lista de elemente în care adăugăm
    @param element elementul pe care îl adăugam
*/
void adaugaElementInLista(Lista* lista, TipElement element);

/**
    @brief Funcție care se uită dacă lungimea listei date va depăși capacitatea sa, și o mărește dacă este cazul
    @param lista lista de elemente în care căutăm
    @return elementul
*/
int asiguraCapacitate(Lista* lista);

/**
    @brief Funcție care modifică după id o cheltuială
    @param lista lista de elemente în care căutăm
    @param cheltuialaModificata cheltuiala nouă
    @return 0 dacă a reușit să modifice, 1 altfel
*/
int modificaElementDinLista(Lista* lista, Cheltuiala cheltuialaModificata);

/**
    @brief Funcție care șterge elementul cu id dat din lista dată
    @param lista lista de elemente în care căutăm
    @param id id-ul după care se caută
    @return 0 dacă a reușit să șteargă, 1 altfel
*/
int stergeElementDinLista(Lista* lista, int id);

/**
    @brief Funcție care caută elementul cu id dat din lista dată
    @param lista lista de elemente în care căutăm
    @param id id-ul după care se caută
    @return 10 dacă a reușit să îl găsească, 11 altfel
*/
int cautaDupaId(Lista* lista, int id);

/**
    @brief Funcție care copiază lista dată
    @return o listă care are aceleași componente ca cea inițială
*/
Lista copiazaLista(Lista* lista);
#pragma once
#include "../Domain/medicament.h"
typedef void* TipElement;

typedef void(*DestroyFunction)(TipElement);
typedef TipElement(*CopyFunction)(TipElement);

typedef struct {
    TipElement* elemente;
    int dimensiune;
    int capacitate;
    DestroyFunction destroyFct;
} Lista;

/**
    @brief Funcție care creează o listă goală
    @return lista goală
*/
Lista* creeazaListaGoala(DestroyFunction destroyFct);

/**
    @brief Funcție care dealocă o listă
*/
void distrugeLista(Lista* lista);

TipElement getElement(Lista* lista, int pos);
TipElement setElement(Lista* lista, int pos, TipElement medicament);

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
    @brief Funcție care modifică după id un medicament
    @param lista lista de elemente în care căutăm
    @param medicamentModificata medicament nouă
    @return 0 dacă a reușit să modifice, 1 altfel
*/
int modificaElementDinLista(Lista* lista, TipElement medicamentModificat);

/**
    @brief Funcție care șterge elementul cu id dat din lista dată
    @param lista lista de elemente în care căutăm
    @param id id-ul după care se caută
    @return 0 dacă a reușit să șteargă, 1 altfel
*/
int stergeElementDinLista(Lista* lista, int id);

/**
    @brief Funcție care șterge ultimul element din lista dată
    @param lista
    @return lista conformă
 */
TipElement stergeUltimulElementDinLista(Lista* lista);

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
Lista* copiazaLista(Lista* lista, CopyFunction copyFct);
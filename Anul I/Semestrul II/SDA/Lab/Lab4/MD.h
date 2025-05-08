#pragma once
#include<vector>
#include<utility>

using namespace std;

typedef int TCheie;
typedef int TValoare;

typedef std::pair<TCheie, TValoare> TElem;

class IteratorMD;

class MD {
	friend class IteratorMD;

private:
	struct Node {
		TElem element;
		int next;
		int prev;
	};

	Node *nodes;
	int capacity;
	int head;
	int firstFree;
	int size;

	void resize() {
		int newCapacity = 2 * capacity;
		Node* newNodes = new Node[newCapacity];

		// Copiem vechile noduri
		for (int i = 0; i < capacity; ++i) {
			newNodes[i] = nodes[i];
		}

		// Initializam noile noduri libere
		for (int i = capacity; i < newCapacity; ++i) {
			newNodes[i].next = i + 1;
			newNodes[i].prev = -1;
		}
		newNodes[newCapacity - 1].next = -1;

		// Actualizam firstFree pentru a pointa la prima pozitie noua
		firstFree = capacity;
		capacity = newCapacity;

		// Eliberam vechea memorie si actualizam pointerul
		delete[] nodes;
		nodes = newNodes;
	}

	int allocate() {
		if (firstFree == -1) {
			resize();
		}
		int newIndex = firstFree;
		firstFree = nodes[newIndex].next;
		return newIndex;
	}

	void deallocate(int index) {
		nodes[index].next = firstFree;
		nodes[index].prev = -1;
		firstFree = index;
	}

public:
	// constructorul implicit al MultiDictionarului
	MD();

	// adauga o pereche (cheie, valoare) in MD
	void adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza vectorul de valori asociate
	vector<TValoare> cauta(TCheie c) const;

	//sterge o cheie si o valoare
	//returneaza adevarat daca s-a gasit cheia si valoarea de sters
	bool sterge(TCheie c, TValoare v);

	//returneaza numarul de perechi (cheie, valoare) din MD
	int dim() const;

	//verifica daca MultiDictionarul e vid
	bool vid() const;

	// se returneaza iterator pe MD
	IteratorMD iterator() const;

	// destructorul MultiDictionarului
	~MD();

	// returneaza valoarea care apare cel mai frecvent în multidicționar. Dacă mai multe valori apar cel mai frecvent, se returnează una (oricare) dintre ele.
	// Dacă multidicționarul este vid, operațiea returnează NULL_TVALOARE
	TValoare ceaMaiFrecventaValoare() const;
};

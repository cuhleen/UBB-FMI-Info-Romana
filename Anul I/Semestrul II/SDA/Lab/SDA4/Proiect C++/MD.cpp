#include "MD.h"
#include "IteratorMD.h"
#include <exception>
#include <iostream>
#include <unordered_map>

using namespace std;

/// Complexitate: Theta(1)
MD::MD() {
	capacity = 10;
	nodes = new Node[capacity];
	for (int i = 0; i < capacity; i++) {
		nodes[i].next = i + 1;
	}
	nodes[capacity - 1].next = -1;
	head = -1;
	firstFree = 0;
	size = 0;
}

/// Complexitate: Theta(1)
void MD::adauga(TCheie c, TValoare v) {
	int index = allocate();
	nodes[index].element = make_pair(c, v);
	nodes[index].next = head;
	head = index;
	size++;
}

/// Complexitate: O(n)
/// Caz defavorabil: Theta(n) cand elementul se afla la final sau nu exista
/// Caz favorabil: Theta(1) cand elementul se afla la inceput
bool MD::sterge(TCheie c, TValoare v) {
	int current = head;
	int prev = -1;
	while (current != -1) {
		if (nodes[current].element.first == c && nodes[current].element.second == v) {
			if (prev == -1) {
				head = nodes[current].next;
			} else {
				nodes[prev].next = nodes[current].next;
			}
			deallocate(current);
			size--;
			return true;
		}
		prev = current;
		current = nodes[current].next;
	}
	return false;
}

/// Complexitate: O(n)
/// Caz defavorabil: Theta(n) cand elementul se afla la final sau nu exista
/// Caz favorabil: Theta(1) cand elementul se afla la inceput
vector<TValoare> MD::cauta(TCheie c) const {
	vector<TValoare> values;
	int current = head;
	while (current != -1) {
		if (nodes[current].element.first == c) {
			values.push_back(nodes[current].element.second);
		}
		current = nodes[current].next;
	}
	return values;
}

/// Complexitate: Theta(1)
int MD::dim() const {
	return size;
}

/// Complexitate: Theta(1)
bool MD::vid() const {
	return size == 0;
}

/// Complexitate: Theta(1)
IteratorMD MD::iterator() const {
	return IteratorMD(*this);
}

/// Complexitate: Theta(n)
/// Preconditii: MD-ul nu e vid
/// Postconditii: se returneaza cea mai frecventa valoare
/// subalg ceaMaiFrecventaValoare(MD m)
///		maxFreq<-0
///		mostFrequentValue<-0
///		pentru i<- m.head, i != -1, i<-m.nodes[i].next executa
///			freqMap[m.nodes[i].element.second]<-freqMap[m.nodes[i].element.second] + 1
///			daca freqMap[m.nodes[i].element.second] > maxFreq atunci
///				maxFreq<-freqMap[m.nodes[i].element.second]
///				mostFrequentValue<-m.nodes[i].element.second
///			sf daca
///		sf pentru
///		returneaza mostFrequentValue
/// sf subalg
TValoare MD::ceaMaiFrecventaValoare() const {
	unordered_map<TValoare, int> freqMap;
	int maxFreq = 0;
	TValoare mostFrequentValue = 0;

	for (int i = head; i != -1; i = nodes[i].next) {
		freqMap[nodes[i].element.second]++;
		if (freqMap[nodes[i].element.second] > maxFreq) {
			maxFreq = freqMap[nodes[i].element.second];
			mostFrequentValue = nodes[i].element.second;
		}
	}

	return mostFrequentValue;
}

/// Complexitate: Theta(1)
MD::~MD() {
	delete[] nodes;
}

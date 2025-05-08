#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("grafPb1.txt");

void MooreAlgorithm(int adjList[100][100], int source, int vertices) {
    int dist[vertices]; // Distanțele inițializate
    int parent[vertices]; // Pentru reconstruirea drumului
    int queue[vertices]; // Coada pentru BFS
    bool visited[vertices]; // Marcaj pentru vizitare

    for (int i = 0; i < vertices; i++) {
        dist[i] = 99999;
        parent[i] = -1;
        visited[i] = false;
    }

    int front = 0, rear = 0;
    queue[rear++] = source;
    dist[source] = 0;
    visited[source] = true;

    while (front < rear) {
        int u = queue[front++];

        for (int v = 0; v < vertices; v++) {
            if (adjList[u][v] == 1)
            if (!visited[v]) {
                dist[v] = dist[u] + 1;
                parent[v] = u;
                queue[rear++] = v;
                visited[v] = true;
            }
        }
    }

    // Afișare rezultate
    cout << "Cel mai scurt drum de la nodul " << source << " la fiecare nod este:\n";
    for (int i = 0; i < vertices; i++) {
        if (dist[i] == 99999) {
            cout << "Nu există drum către " << i << "\n";
        } else {
            cout << "Distanta la " << i << " este " << dist[i] << "\n";

            // Reconstruim drumul
            int path[vertices], path_index = 0;
            for (int v = i; v != -1; v = parent[v]) {
                path[path_index++] = v;
            }
            cout << "Drumul: ";
            for (int j = path_index - 1; j >= 0; j--) {
                cout << path[j] << " ";
            }
            cout << "\n";
        }
    }
}

int main() {

    int vertices, u, v;
    fin >> vertices;
    int adjList[100][100];

    while (fin >> u >> v) {
        adjList[u][v] = 1;
    }
    fin.close();

    int source;
    cout << "Introduceti nodul sursa: ";
    cin >> source;

    MooreAlgorithm(adjList, source, vertices);

    return 0;
}

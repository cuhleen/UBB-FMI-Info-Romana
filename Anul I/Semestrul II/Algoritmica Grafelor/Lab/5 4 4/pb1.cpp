#include <fstream>
#include <vector>

using namespace std;

typedef struct
{
    int x, y, w;
}arc;

vector <arc> a;
int V, E, S;

void BellmanFord(int dist[], int u) {

    for (int i = 0; i < V; i++)
        dist[i] = 999999;

    dist[u] = 0;

    for (int i = 1; i <= V - 1; i++)
        for (int j = 0; j < E; j++)
            if (dist[a[j].x] != 999999 && dist[a[j].x] + a[j].w < dist[a[j].y])
                dist[a[j].y] = dist[a[j].x] + a[j].w;

    for (int i = 0; i < E; i++)
        if (dist[a[i].x] != 999999 && dist[a[i].y] + a[i].w < dist[a[i].y])
            return;
}

int main(int argc, char *argv[]) {

    ifstream fin(argv[1]);
    ofstream fout(argv[2]);

    fin>>V>>E>>S;

    arc citire;

    for (int i = 0; i < E; i++) {
        fin>>citire.x>>citire.y>>citire.w;
        a.push_back(citire);
    }

    int dist[V];

    BellmanFord(dist, S);

    for (int i = 0; i < V ; i++)
        if (dist[i] == 999999)
            fout<<"INF"<<" ";
        else fout<<dist[i]<<" ";

    fin.close();
    fout.close();

    return 0;
}
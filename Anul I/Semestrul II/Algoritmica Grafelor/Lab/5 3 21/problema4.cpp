#include <vector>
#include <iostream>
#include <fstream>
#include <queue>

using namespace std;

ifstream fin("grafPb4.txt");

vector<int> v[105]; // Vector de vectori pentru reprezentarea grafurilor
queue<pair<int, int>> q; // coada cu elemente (nod, distanță)
int n, viz[105];

void bfs(int nod)
{
    q.push({ nod, 1 });
    viz[nod] = 1;
    while (!q.empty())
    {
        int crt = q.front().first;
        int poz = q.front().second;
        cout <<"Nodul "<< crt << " a fost al " << poz <<"-lea nod descoperit de BFS"<< '\n';
        viz[crt] = 1; 
        for (int i = 0; i < v[crt].size(); i++)
        {
            if (viz[v[crt][i]] == 0) 
            {
                viz[v[crt][i]] = 1; 
                q.push({ v[crt][i], poz + 1 }); 
            }
        }
        q.pop();
    }
}

void functie()
{
    int x, y, s; 
    fin >> n; 
    while (fin >> x >> y) 
    {
        v[x].push_back(y);
        //v[y].push_back(x); // graf neorientat. Pentru graf orientat comenteaza linia asta
    }
    cout << "Nodul sursa: ";
    cin >> s; 
    bfs(s); 
}

int main() {
    functie();
    return 0;
}
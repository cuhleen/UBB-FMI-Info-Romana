#include <vector>
#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("grafPb4.txt");

vector<int> v[105]; // Vector de vectori pentru reprezentarea grafurilor
int n, viz[105];

void dfs(int nod, int nivel)
{
    viz[nod] = 1;
    cout << "Nodul " << nod << " a fost al " << nivel << "-lea nod descoperit de DFS" << '\n';

    for (int i = 0; i < v[nod].size(); i++)
    {
        if (!viz[v[nod][i]])
        {
            dfs(v[nod][i], nivel + 1);
        }
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
    dfs(s, 1);
}

int main()
{
    functie();
    return 0;
}

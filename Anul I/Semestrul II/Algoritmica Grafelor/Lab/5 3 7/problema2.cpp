/*
2. Fie un graf reprezentat sub o anumita forma (graful este citit dintr-un
fisier). Sa se rezolve: a. sa se determine nodurile izolate dintr-un graf. b. sa
se determine daca graful este regular. c. pentru un graf reprezentat cu matricea
de adiacenta sa se determine matricea distantelor. d. pentru un graf reprezentat
cu o matrice de adiacenta sa se determine daca este conex.
*/

#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("matrice2.txt");

int a[101][101], n, fisier, grade[101];

void citire()
{
    fin>>n;
    for (int i = 0; i < n; i++)
    {
        grade[i] = 0;
        for (int j = 0; j < n; j++)
        {
            fin>>a[i][j];
            if (a[i][j] != 0)
            {
                grade[i]++;
            }
        }
    }

}

void noduriIzolate()
{
    for (int i = 0; i < n; i++)
    {
        int nodIzolat = 1;
        for (int j = 0; j < n; j++)
        {
            if (a[i][j] != 0 || a[j][i] != 0)
            {
                nodIzolat = 0;
                break;
            }
        }
        if (nodIzolat)
        {
            cout<<"Nodul "<<i<<" este izolat\n";
        }
    }

}

void grafRegular()
{
    int grad = grade[0];
    for (int i = 0; i < n; i++)
    {
        if (grade[i] != grad)
        {
            grad = -1;
        }
    }
    if (grad == -1)
    {
        cout<<"Graful nu este regular\n";
    }
    else
    {
        cout<<"Graful este regular\n";
    }
    cout<<"\n";
}

void matriceaDistantelor()
{
    int matriceaDistantelor[101][101];
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            matriceaDistantelor[i][j] = a[i][j];
            if (matriceaDistantelor[i][j] == 0)
            {
                matriceaDistantelor[i][j] = 9999;
            }
            if (i == j)
            {
                matriceaDistantelor[i][j] = 0;
            }
        }
    }
    // Algoritm Roy-Floyd
    for (int k = 0; k < n; k++)
    {
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (matriceaDistantelor[i][k] != 9999 &&
                    matriceaDistantelor[k][j] != 9999)
                {
                    if (matriceaDistantelor[i][k] + matriceaDistantelor[k][j] <
                        matriceaDistantelor[i][j])
                    {
                        matriceaDistantelor[i][j] =
                            matriceaDistantelor[i][k] + matriceaDistantelor[k][j];
                    }
                }
            }
        }
    }
    cout<<"Matricea distantelor: \n";
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < n; j++)
        {
            if (matriceaDistantelor[i][j] == 9999)
            {
                cout<<"- ";
            }
            else
            {
                cout<<matriceaDistantelor[i][j]<<" ";
            }
        }
        cout<<"\n";
    }
    cout<<"\n";
}

int viz[101];

void DFS(int x)
{
    viz[x] = 1;
    for (int i = 0; i < n; i++)
    {
        if (a[x][i] == 1 && viz[i] == 0)
        {
            DFS(i);
        }
    }
}

int Conex()
{
    for (int i = 0; i < n; i++)
        viz[i] = 0;

    DFS(0);

    for (int i = 0; i < n; i++)
    {
        if (viz[i] == 0)
            return 0;
    }
    return 1;
}

void grafConex()
{
    if (Conex())
    {
        cout<<"Graful este conex\n";
    }
    else
    {
        cout<<"Graful nu este conex\n";
    }
}

int main()
{
    citire();
    noduriIzolate();
    cout<<"\n";
    grafRegular();
    cout<<"\n";
    matriceaDistantelor();
    cout<<"\n";
    grafConex();
    cout<<"\n";

    return 0;
}

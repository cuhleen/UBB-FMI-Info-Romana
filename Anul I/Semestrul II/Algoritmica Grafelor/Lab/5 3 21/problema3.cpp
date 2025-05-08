#include <iostream>
#include <fstream>
#include <cstring>

using namespace std;

ifstream f3("labirint_1.txt");

int a[5000][5000];
int n, m, xs, ys, xf, yf;
char s[5000]; // pentru citirea unei linii din fisier

void bordare()
{
    for (int i = 0; i <= n + 1; i++)
        a[i][0] = a[i][m + 1] = -1;
    for (int i = 0; i <= m + 1; i++)
        a[0][i] = a[n + 1][i] = -1;
}

void lee(int x, int y)
{
    if (x == xf && y == yf)
        return;
    
    //jos
    if (a[x + 1][y] == 0 || a[x][y] + 1 < a[x + 1][y])
    {
        a[x + 1][y] = a[x][y] + 1;
        lee(x + 1, y);
    }
    //sus
    if (a[x - 1][y] == 0 || a[x][y] + 1 < a[x - 1][y])
    {
        a[x - 1][y] = a[x][y] + 1;
        lee(x - 1, y);
    }
    //dreapta
    if (a[x][y + 1] == 0 || a[x][y] + 1 < a[x][y + 1])
    {
        a[x][y + 1] = a[x][y] + 1;
        lee(x, y + 1);
    }
    //stanga
    if (a[x][y - 1] == 0 || a[x][y] + 1 < a[x][y - 1])
    {
        a[x][y - 1] = a[x][y] + 1;
        lee(x, y - 1);
    }
    
}

void labirint()
{
    while (f3.getline(s, 5000))
    {
        n++; 
        m = strlen(s);

        for (int i = 0; i < strlen(s); i++)
        {
            if (s[i] == '1')
                a[n][i + 1] = -1;
            else if (s[i] == ' ')
                a[n][i + 1] = 0;
            else if (s[i] == 'S')
            {
                a[n][i + 1] = 1; // poziția de start
                xs = n;
                ys = i + 1;
            }
            else
            {
                xf = n; // poziția de finish
                yf = i + 1;
            }
        }
    }

    bordare();

    lee(xs, ys);

    cout << "Drumul are lungime " << a[xf][yf] << '\n';

    int x, y;
    x = xf;
    y = yf;

    // reconstruirea drumului
    while (x != xs || y != ys)
    {
        if (a[x - 1][y] + 1 == a[x][y])
        {
            a[x][y] = -2;
            x = x - 1;
        }
        else if (a[x + 1][y] + 1 == a[x][y])
        {
            a[x][y] = -2;
            x++;
        }
        else if (a[x][y - 1] + 1 == a[x][y])
        {
            a[x][y] = -2;
            y--;
        }
        else if (a[x][y + 1] + 1 == a[x][y])
        {
            a[x][y] = -2;
            y++;
        }
    }

    // marcam poziția finală cu -3
    a[xf][yf] = -3;

    // Afișăm labirintul rezolvat
    for (int i = 1; i <= n; i++)
    {
        for (int j = 1; j <= m; j++)
        {
            if (a[i][j] == -1)
                cout << '1';
            else if (a[i][j] == 1)
                cout << 'S';
            else if (a[i][j] == -2)
                cout << 'x';
            else if (a[i][j] == -3)
                cout << 'F';
            else
                cout << ' ';
        }
        cout << '\n';
    }
}

int main() {
    
    labirint();
    
    return 0;
}


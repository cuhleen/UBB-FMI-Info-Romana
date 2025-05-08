#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("matrice2.txt");

int n, matAdj[101][101];

void citire(){
    fin>>n;

    for(int i = 0; i < n; i++)
        for(int j = 0; j < n; j++)
            fin>>matAdj[i][j];

}

void inchidereTranzitiva(){
    for(int k = 0; k < n; k++)
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(matAdj[i][j] == 0 && matAdj[i][k] == 1 && matAdj[k][j] == 1)
                    matAdj[i][j] = 1;

}

void afisare(){
    for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++)
          cout<<matAdj[i][j]<<" ";
        cout<<"\n";
    }
}

int main()
{
    citire();
    inchidereTranzitiva();
    afisare();
}
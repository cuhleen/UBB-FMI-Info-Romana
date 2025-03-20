#include <iostream>
#include <fstream>

using namespace std;

ifstream fin("matrice.txt");

int nrNoduri, nrMuchii, matAdiacenta[20][20], matIncidenta[20][1000], listaAdiacenta[20][20], listaAdiacentaNrVecini[20];

void adaugareMatAdiacenta(int nod1, int nod2)
{
  matAdiacenta[nod1][nod2] = 1;
  matAdiacenta[nod2][nod1] = 1;
}

void adaugareMatIncidenta(int nod1, int nod2, int nrMuchie)
{
  matIncidenta[nod1][nrMuchie] = 1;
  matIncidenta[nod2][nrMuchie] = 1;
}

void generareListaAdiacenta()
{
  for(int i = 1; i <= nrNoduri; i++)
  {
    for(int j = 1; j <= nrNoduri; j++)
    {
      if(matAdiacenta[i][j] == 1)
      {
        listaAdiacentaNrVecini[i]++;
        listaAdiacenta[i][listaAdiacentaNrVecini[i]] = j;
      }
    }
  }
}

void citire()
{

  fin>>nrNoduri>>nrMuchii;

  int nod1, nod2, nrMuchieCurenta = 1;

  for(int i = 0; i < nrMuchii; i++)
  {
    fin>>nod1>>nod2;

    adaugareMatAdiacenta(nod1, nod2);
    adaugareMatIncidenta(nod1, nod2, nrMuchieCurenta);
    nrMuchieCurenta++;

  }

  generareListaAdiacenta();

}

void afisare()
{
  cout<<"Matricea de adiacenta:\n";
  for(int i = 1; i <= nrNoduri; i++)
  {
    for(int j = 1; j <= nrNoduri; j++)
      cout<<matAdiacenta[i][j]<<" ";
    cout<<"\n";
  }

  cout<<"\n";

  cout<<"Matricea de incidenta:\n";
  for(int i = 1; i <= nrNoduri; i++)
  {
    for(int j = 1; j <= nrMuchii; j++)
      cout<<matIncidenta[i][j]<<" ";
    cout<<"\n";
  }

  cout<<"\n";

  cout<<"Lista de adiacenta:\n";
  for(int i = 1; i <= nrNoduri; i++)
  {
    for(int j = 1; j <= listaAdiacentaNrVecini[i]; j++)
      cout<<listaAdiacenta[i][j]<<" ";
    cout<<"\n";
  }

}

int main(){

  citire();
  afisare();

  return 0;
}
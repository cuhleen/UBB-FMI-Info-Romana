#include <stdio.h>

int n;

int prim(int x)
{
   /*  DESCRIERE
   *Funcția verifică dacă un număr este prim.
   *
   *  PARAM
   *x - numărul de verificat.
   *
   *  CONDIȚII
   *x trebuie să fie un număr întreg.
   *
   *  REZULTAT
   *1 dacă numărul dat este prim, 0 altfel.
   */
  if(x < 2 || x % 2 == 0 && x > 2)
    return 0;

  for(int d = 3; d * d <= x; d += 2)
    if(x % d == 0)
      return 0;

  return 1;

}

int primNext(int x)
{
  /*  DESCRIERE
   *Funcția primește un număr și returnează primul număr prim mai mare decât el.
   *
   *  PARAM
   *x - numărul de la care se pornește.
   *
   *  CONDIȚII
   *x trebuie să fie un număr întreg.
   *
   *  REZULTAT
   *Se returnează primul număr prim mai mare decât numărul dat.
   */
  x++;
  while(!prim(x))
    x++;

  return x;
}

void functie(int n)
{
  /*  DESCRIERE
   *Funcția afișează două numere prime a căror sumă este numărul dat ca parametru
   *
   *  PARAM
   *n - numărul căruia i se caută suma
   *
   *  CONDIȚII
   *n trebuie să fie un număr par
   *
   *  REZULTAT
   *Se afișează pe ecran cele două numere prime găsite
   */
  for(int i = 2; i < n; i = primNext(i))
    for(int j = i; j < n; j = primNext(j))
      if(i + j == n)
      {
        printf("%d + %d = %d\n", i, j, n);
        return;
      }

  printf("Nu exista?");

}

int main()
{

  printf("\nIntroduce un numar intreg par\n");

    while(scanf("%d",&n) && n != -1)
    {

      printf("\n");
      functie(n);
      printf("\nIntroduce un numar intreg par, sau '-1' pentru a iesi din aplicatie");
    }

    return 0;
}

#include <stdio.h>

int n;

void citire()
{
    /*  DESCRIERE
     *Citește numărul întreg de la tastatură.
     *
     *  CONDIȚII
     *Trebuie introdus un număr întreg valid.
     *
     *  REZULTAT
     *Variabila globală n se actualizează cu numărul citit.
     */
    printf("Introduce un numar:\n");
    scanf("%d", &n);
}

void afisare(int i, int j)
{
    /*  DESCRIERE
     *Afișează o secvență de numere consecutive.
     *
     *  PARAM
     *i - limita inferioară a intervalului închis.
     *j - limita superioară a intervalului închis.
     *
     *  CONDIȚII
     *i trebuie să fie mai mic sau egal decât j.
     *
     *  REZULTAT
     *Se afișează pe ecran secvența de numere de la `i` la `j` inclusiv.
     */
    for (int k = i; k <= j; k++)
        printf("%d ", k);
}

void parcurgere(int n)
{
    /*  DESCRIERE
     *Determină și afișează toate modurile prin care un număr poate fi exprimat ca sumă de termeni consecutivi pozitivi.
     *
     *  PARAM
     *n - numărul întreg de verificat.
     *
     *  CONDIȚII
     *n trebuie să fie un număr întreg.
     *
     *  REZULTAT
     *Se afișează pe ecran toate modurile în care parametrul n poate fi exprimat ca sumă de numere consecutive pozitive.
     *Dacă nu există nicio modalitate, se afișează un mesaj corespunzător.
     */
    int gasit = 0;
    for (int i = 1; i <= n / 2; i++)
    {
        int s = i;
        for (int j = i + 1; j <= n / 2 + 1; j++)
        {
            s += j;
            if (s > n)
                break;
            if (s == n)
            {
                afisare(i, j);
                gasit = 1;
            }
        }
    }

    if (gasit == 0)
        printf("Nu exista.");

    printf("\n\n");
}

int menu()
{
    int isRunning = 1;

    while (isRunning)
    {
        char optiuneMenu = ' ';

        printf("    Alegeti o optiune:\n");
        printf("1. Cerinta 3\n");
        printf("E. Iesire din aplicatie\n");

        scanf(" %c", &optiuneMenu);

        switch (optiuneMenu)
        {
        case 'e':
        case 'E':
            isRunning = 0;
            break;
        case '1':
            printf("\n");
            citire();
            parcurgere(n);
        }
    }
    return 0;
}

int main()
{

    printf("\n");
    menu();

    return 0;
}

#include <stdio.h>

// functia declarata in temaLab12modul.asm
int compare(int currentMin, int num);

int main() {
    int n, i, nrCitit, minim;
    FILE *file;

    // Deschidem fisierul pentru scriere

    file = fopen("min.txt", "w");

    if (file == NULL) {
        printf("Eroare la deschiderea fisierului\n");
        return 1;
    }

    // Citim numarul de elemente

    printf("Numarul de elemente: ");
    scanf("%d", &n);

    // Initializam minimul la cea mai mare valoare semnata

    minim = 2147483647;

    // Citim elementele sirului si actualizam minimul

    for (i = 0; i < n; i++) {
        printf("Elementul %d: ", i + 1);
        scanf("%d", &nrCitit);

        // Apelam functia in asamblare pentru a actualiza minimul
        minim = compare(minim, nrCitit);
    }

    // Scriem minimul in fisier, in baza 16

    fprintf(file, "%d\n", minim);

    // inchidem fisierul

    fclose(file);

    printf("Minimul a fost scris in fisierul min.txt\n");

    return 0;
}

#include <stdio.h>

int main(){

    int n, x, s = 0;

    printf("Cate cifre doresti sa aduni?\n");
    scanf("%d", &n);

    while(n < 0){
        printf("Nu se poate citi un număr negativ de numere!\n");
        printf("Cate cifre doresti sa aduni?\n");
        scanf("%d", &n);
    }

    printf("Introduce numerele de adunat:\n");
    for(int i = 0; i < n; i++){
        scanf("%d", &x);
        s += x;
    }

    printf("Suma cifrelor este %d", s);

    return 0;

}
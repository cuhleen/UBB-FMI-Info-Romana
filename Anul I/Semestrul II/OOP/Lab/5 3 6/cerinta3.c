#include <stdio.h>

int main(){

    int n;

    printf("Introduce un numar:\n");
    scanf("%d", &n);

    for(int i = 1; i <= n/2; i++){
        int s = i;
        for(int j = i + 1; j <= n/2 + 1; j++){
            s += j;
            if(s > n)
                break;
            if(s == n){
                for(int k = i ; k <= j; k++)
                    printf("%d ", k);
                printf("\n");
            }
        }
    }

    return 0;

}
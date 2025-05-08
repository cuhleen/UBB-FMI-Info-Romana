/*
Se dă ca argument un număr n
Să se genereze n+1 copii
Dacă pid-ul copilului este par, copilul generează un număr random impar
Dacă pid-ul copilului este impar, copilul generează un număr random par
Copiii trimit numărul lor prin pipe să se facă și afișeze suma
*/

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main(int argc, char *argv[]){

	if(argc != 2){
	perror("Te rog scrie fix un argument!");
	exit(1);
	}

	int n = atoi(argv[1]);
	int child2sum[2];
	pipe(child2sum);	

	for(int i = 0; i < n + 1; i++){
		int f = fork();	
		if(f == -1)
			perror("Eroare la furculiță");
		else if(f == 0){
			close(child2sum[0]);
			int random = rand() % 100;
			if(getpid() % 2 == 0){
				if(random % 2 == 0)
					random++;
			}else if(random % 2 == 1)
				random++;
			write(child2sum[1], &random, sizeof(int));
			close(child2sum[1]);	
			exit(0);
		}
	}

	for(int i = 0; i < n; i++)
		wait(0);

	close(child2sum[1]);
	int sum = 0, nr;
	while(read(child2sum[0], &nr, sizeof(int))>0)
		sum += nr;
	printf("Suma numerelor generate de copii: %d\n", sum);
	return 0;

}

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void functie(int n){
	if(n > 0){
		int f = fork();
		if(f == -1){
			perror("Eroare la furculiță!");
			exit(1);
		}else if(f == 0){
			printf("ID părinte %d | ID copil %d", getppid(), getpid());
			functie(n - 1);
		}
	}
	wait(0);
	exit(0);
	
}

int main(int argc, char* argv[]){

	if(argc != 2){
		perror("Se acceptă fix un argument!");
		exit(1);
	}

	int n = atoi(argv[1]);

	functie(n);

	return 0;

}

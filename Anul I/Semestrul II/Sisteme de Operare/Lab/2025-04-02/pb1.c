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
	
	for(int i = 0; i < n; i++){
		int f = fork();
		if(f == -1)
			perror("Eroare la furculiță");
		else if(f == 0){
			printf("Sunt un copil! ID-ul meu este %d iar ID-ul părintelui meu este %d\n", getpid(), getppid());
			exit(0);
		}
	}

	for(int i = 0; i < n; i++)
		wait(0);

	return 0;

}

#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char* argv[]){

	if(argc != 2){
	perror("Se acceptă fix un argument!");
	exit(1);
	}

	int n = atoi(argv[1]);

	for(int i = 0; i < n; i++){
		int f = fork();
		if(f == -1)
			perror("Eroare la furculiță!");
		else if(f == 0)
			printf("ID părinte %d | ID copil %d\n", getppid(), getpid());
		else{
		//	printf("ID părinte %d | ID copil %d\n", getpid(), f);
		wait(0);
		exit(0);
		}
	}
	return 0;

}

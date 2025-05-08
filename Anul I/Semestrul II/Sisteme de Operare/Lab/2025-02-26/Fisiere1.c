#include <stdio.h>
#include <stdlib.h>

int main(){

	FILE* fptrRead;

	fptrRead = fopen("readFile.txt", "r");

	if(fptrRead == NULL){
		printf("File did not open :(");
		exit(0);
	}

	FILE* fptrWrite;

	fptrWrite = fopen("writeFile.txt", "w");

	if(fptrWrite == NULL){
		printf("File did no open :(");
		exit(0);
	}

	int i = 0, arr[100];

	while(fscanf(fptrRead, "%d", &arr[i]) == 1){
		i += 1;
	}

	for(int j = 0; j < i; j++)
		fprintf(fptrWrite, "%d ", arr[j]);

	return 0;

}

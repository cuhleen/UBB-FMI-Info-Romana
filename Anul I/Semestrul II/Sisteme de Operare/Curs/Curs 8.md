
# **Mecanisme de sincronizare**

Joc de X și 0

### Mutex
Simplu, lock și unlock.
```c
#include <stdio.h>
#include <pthread.h>

int turn = 0;
int board[3][3] = {{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};

pthread_mutex_t m0;
pthread_mutex_t m1;

void show(){
	int i, j;
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
			print("%3d", board[i][j]);
		}
		printf("\n");
	}
	printf("\n")
}

void* ju0(void *a){
	int i, j, full;
	while(1){
		pthread_mutex_lock(&m0);
		if(turn == 0){
			full = 1;
			for(i = 0; i < 3; i++){
				for(j = 0; j < 3; j++){
					if(board[i][j] == -1){
						full = 0;
						board[i][j] = 0;
						show();
						break;
					}
					if(!full)
						break;
				}
			}
			turn = 1;
			if(full){
				pthread_mutex_unlock(&m1);
				break;
			}
		}
		pthread_mutex_unlock(&m1);
	}
	void (a);
	return NULL;
}

void* ju1(void *a){
	int i, j, full;
	while(1){
		pthread_mutex_lock(&m1);
		if(turn == 1){
			full = 1;
			for(i = 0; i < 3; i++){
				for(j = 0; j < 3; j++){
					if(board[i][j] == -1){
						full = 0;
						board[i][j] = 1;
						show();
						break;
					}
					if(!full)
						break;
				}
			}
			turn = 0;
			if(full){
				break;
				pthread_mutex_unlock(&m0);
			}
		}
		pthread_mutex_unlock(&m0);
	}
	void (a);
	return NULL;
}

int main(){

	pthread_t t0, t1;
	pthread_mutex_init(&m0, NULL);
	pthread_mutex_init(&m1, NULL);
	pthread_mutex_lock(&m1);
	pthread_create(&t0, NULL, ju0, NULL);
	pthread_create(&t1, NULL, ju1, NULL);
	pthread_join(t0, NULL);
	pthread_join(t1, NULL);
	pthread_mutex_destroy(&m0);
	pthread_mutex_destroy(&m1);

	return 0;

}
```

### Semafoare
Ca Mutex dar puțin mai complicat.
```c
#include <stdio.h>
#include <pthread.h>
#include <semaphore.h>

int turn = 0;
int board[3][3] = {{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};

sem_t s0;
sem_t s1;

void show(){
	int i, j;
	for(int i = 0; i < 3; i++){
		for(int j = 0; j < 3; j++){
			print("%3d", board[i][j]);
		}
		printf("\n");
	}
	printf("\n")
}

void* ju0(void *a){
	int i, j, full;
	while(1){
		sem_wait(&s0);
		if(turn == 0){
			full = 1;
			for(i = 0; i < 3; i++){
				for(j = 0; j < 3; j++){
					if(board[i][j] == -1){
						full = 0;
						board[i][j] = 0;
						show();
						break;
					}
					if(!full)
						break;
				}
			}
			turn = 1;
			if(full){
				sem_post(&s1);
				break;
			}
		}
		sem_post(&s1);
	}
	void (a);
	return NULL;
}

void* ju1(void *a){
	int i, j, full;
	while(1){
		sem_wait(&s1);
		if(turn == 1){
			full = 1;
			for(i = 0; i < 3; i++){
				for(j = 0; j < 3; j++){
					if(board[i][j] == -1){
						full = 0;
						board[i][j] = 1;
						show();
						break;
					}
					if(!full)
						break;
				}
			}
			turn = 0;
			if(full){
				break;
				sem_post(&s0);
			}
		}
		sem_post(&s0);
	}
	void (a);
	return NULL;
}

int main(){

	pthread_t t0, t1;
	sem_init(&s0, 0, 1);
	sem_init(&s1, 0, 1);
	sem_wait(&s1);
	pthread_create(&t0, NULL, ju0, NULL);
	pthread_create(&t1, NULL, ju1, NULL);
	pthread_join(t0, NULL);
	pthread_join(t1, NULL);
	sem_destroy(&s0);
	sem_destroy(&s1);

	return 0;

}
```

### Bariere
Așteaptă până toate procesele ajung la barieră, după care le eliberează simultan.

Adunare paralelă
`1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16`
`3   7   11  15  19   23    27    31`
`10      26      42         58`
`36              100`
`136`

crazy

```c
#include <stdio.h>
#include <pthread.h>

int arr[16] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
pthread_barrier_t b;

void* f(void* a){
	int id = (int)(long)a, k = 2, n = 8;
	while(n > 0){
		if(id < n){
			arr[k * id] += arr[k * id + k / 2];
		}
		pthread_barrier_wait(&b);
		k *= 2;
		n /= 2;
	}
	return NULL;
}

int main(){

	int i;
	pthread_t t[0];
	pthread_barrier_init(&b, NULL, 8);

	for(i = 0; i < 8; i++){
		pthread_create(&t[i], NULL, f, (void*)(long)i);
	}
	
	for(i = 0; i < 8; i++){
		pthread_join(&t[i], NULL);
	}

	pthread_barrier_destroy(&b);
	printf("%d\n", arr[0]);

	return 0;

}
```
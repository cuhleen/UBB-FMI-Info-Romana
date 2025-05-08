
Comanda `env` - variabile de mediu
Cum știe `ls` sau alte comenzi unde se află comenzile? Se uită prin toate path-urile din `PATH=` din `env`

| ***`exec`***  |        | **Caută în PATH**                                                             | **Caută în PATH**                                                                      |
| ------------- | ------ | ----------------------------------------------------------------------------- | -------------------------------------------------------------------------------------- |
|               |        | Da                                                                            | NU                                                                                     |
| **Argumente** | Vector | `char* a[] = {"grep", "-E", "abc", "a.txt", NULL}`<br><br>`execvp("grep", a)` | `char* a[] = {"\bin\grep", "-E", "abc", "a.txt", NULL}`<br><br>`execv("/bin/grep", a)` |
| **Argumente** | Liste  | `execlp("grep", "grep", "-E", "abc", "a.txt", NULL)`                          | `execl("\bin\grep", "\bin\grep", "-E", "abc", "a.txt", NULL)`                          |

Exemplu

```c
#include <stdio.h>
#include <unistd.h>

int main(){
	print("a\n");
	execlp("echo", "echo", "b" NULL);
	printf("c\n");

	return 0;
}
```

Se afișează doar "ab", fără "c"

`exec`, dacă găsește programul dat (în cazul de sus `echo`), rulează programul nou (`echo`) și ignoră tot restul (`printf("c\n")`)
Dacă *nu* găsește programul, doar dă un cod de eroare

Dar cum facem să nu dispară totul de după? (+ verificare cod de eroare)\

```c
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(){
	print("a\n");
	if(fork() == 0)
		if(execlp("echo", "echo", "b" NULL) < 0)
			exit(1);
			
	printf("c\n");
	wait(0);

	return 0;
}
```

Se afișează "acb" pentru că durează timp până se încarcă comanda din `exec` în memorie

Adunare în mai puțini pași cu furculițe
```c
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(){
	int a[4] = {1, 2, 3, 4};
	
	if(fork() == 0){
		a[2] += a[3]
		exit(0);
	}
	
	a[0] += a[1];
	wait(0);
	a[0] += a[2];
	
	printf("%d\n". a[0]);

	return 0;
}
```

Se afișează 6. De ce? Pentru că adunarea din `if` se face în procesul fiu, și rămâne acolo

# Pipe

```c
int p[2];
pipe(p);
```
În `p` se află acum doi descriptori de fișier. Primul este al pipe-ului înspre kernel (scrie), al doilea este dinspre kernel (citește)

```c
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main(){
	int a[4] = {1, 2, 3, 4};
	int p[2];
	
	pipe(p);
	
	if(fork() == 0){
		close(p[0]);
		a[2] += a[3]
		write(p[1], &a[2]. sizeof(int));
		close(p[1]);
		exit(0);
	}
	close(p[1]);
	a[0] += a[1];
	read(p[0], &a[2], sizeof(int));
	close(p[0]);
	wait(0);
	a[0] += a[2];
	printf("%d\n". a[0]);

	return 0;
}
```

#### *Două "reguli" pt pipe:*
Prin `pipe` pot să comunice doar procesele care moștenesc descriptorii de acces la `pipe`
Închideți capetele `pipe`-ului cât mai curând posibil. Dacă nu, programul va îngheța fără să înțelegeți de ce

<hr>

## Program care pasează un număr n în cerc între 3 procese și îl scade și afișează

```c
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/wait.h>

int main(){

	int p2a[2], a2b[2], b2p[2], n;
	pipe(p2a);
	pipe(a2b);
	pipe(b2p);

	if(fork() == 0){ // A
		close(p2a[1]);
		close(a2b[0]);
		close(b2p[0]);
		close(b2p[1]);

		while(1){
			if(read(p2a[0], &n, sizeof(int)) < 0){
				break;
			}
			if(n <= 0)
				break;
			
			printf("A: %d -> %d\n", n, n-1);
			n--;
			write(a2b[1], &n, sizeof(int));
		}
		close(p2a[0]);
		close(a2b[1]);

		exit(0);
	}

	if(fork() == 0){ // B
		close(a2b[1]);
		close(b2p[0]);
		close(p2a[0]);
		close(p2a[1]);

		while(1){
			if(read(a2b[0], &n, sizeof(int)) < 0){
				break;
			}
			if(n <= 0)
				break;
			
			printf("B: %d -> %d\n", n, n-1);
			n--;
			write(b2p[1], &n, sizeof(int));
		}
		close(a2b[0]);
		close(b2p[1]);
		
		exit(0);
	}

	close(p2b[1]);
	close(p2a[0]);
	close(a2b[0]);
	close(a2b[1]);

	n = 7;
	write(p2a[1], &n, sizeof(int));

	while(1){
		if(read(b2p[0], &n, sizeof(int)) < 0){
			break;
		}
		if(n <= 0)
			break;
		
		printf("P: %d -> %d\n", n, n-1);
		n--;
		write(p2a[1], &n, sizeof(int));
	}
	close(b2p[0]);
	close(p2a[1]);

	wait(NULL);
	wait(NULL);

}
```

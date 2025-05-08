
# FIFO
==Ca pipe dar comunici între două *programe*==
`mkfifo [nume]`
În `ls -l` primul caracter al său va fi `p`, as opposed to `-` pentru file sau `d` pentru directoare

## Aproape la fel ca [programul din ultimul curs](<Semestrul II/Sisteme de Operare/Curs/Curs 5.md#Program care pasează un număr n în cerc între 3 procese și îl scade și afișează>) dar cu FIFO și sunt doar 2 procese

`mkfifo a2b`
`mkfifo b2a`

**a.c**
```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(){
	int a2b, b2a, n = 20;
	printf("Deschidere a2b\n");
	a2b = open("a2b", O_WRONLY);
	printf("Deschidere b2a\n");
	b2a = open("b2a", O_RDONLY);
	printf("FIFO-uri deschise\n");

	write(a2b, &n, sizeof(int));

	while(1){
		if(read(b2a, &n, sizeof(int)) <= 0)
			break;
		
		if(n <= 0)
			break;
		printf("A %d -> %d\n", n, n-1);
		n--;
		write(a2b, &n, sizeof(int));
	}

	close(a2b);
	close(b2a);

	return 0;

}
```

**b.c**
```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main(){
	int a2b, b2a, n = 20;
	printf("Deschidere a2b\n");
	a2b = open("a2b", O_RDONLY);
	printf("Deschidere b2a\n");
	b2a = open("b2a", O_WRONLY);
	printf("FIFO-uri deschise\n");

	while(1){
		if(read(a2b, &n, sizeof(int)) <= 0)
			break;
		
		if(n <= 0)
			break;
		printf("A %d -> %d\n", n, n-1);
		n--;
		write(b2a, &n, sizeof(int));
	}

	close(a2b);
	close(b2a);

	return 0;

}
```

Deschidem 2 terminale
Compilăm a.c într-unul și b.c în celălalt
🤯

Dacă scriem în a.c
```c
a2b = open("a2b", O_WRONLY);
b2a = open("b2a", O_RDONLY);
```
și în b.c
```c
b2a = open("b2a", O_WRONLY);
a2b = open("a2b", O_RDONLY);
```
(am inversat a2b și b2a în b.c)
atunci FIFO-urile așteaptă una după alta, și programul rămâne blocat în nimic

## popen() pclose()
Vezi în manual lil bro

**bottles.c**
```c
#include <stdio.h>

int main(){

	int i;

	FILE* f = popen("less", "w");
	for(int i = 99; i > 0 i++){
		fprintf("%d bottles of actimel on the wall, %d bottles of actimel\n", i, i);
		fprintf("You take onw down and pass it around, %d bottles of actimel on the wall\n\n", i-1);
	}

	pclose(f)

	return 0;

}
```

Alt exemplu: `popen("sort -n | grep -E '.' | less", "w")`
Fără linii goale și sortate numeric

## dup() dup2()

`            ps -ef |    grep -E "^root\>"    | awk '{print $2}'        | tail -n 5          `
`dup2(p2g[1], 1)   p2g    dup2(p2g[0], 0)    g2a    dup2(g2a[1], 0)    a2t    dup2(a2t[0], 0)`
`                         dup2(g2a[1], 1)           dup2(a2t[1], 1)                          `

unFelDeBash.c
```c
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>

int main(){

	int p2g[2], g2a[2], a2t[2];
	pipe(p2g);
	pipe(g2a);
	pipe(a2t);

	if(fork() == 0){ // ps
		close(p2g[0]);

		close(g2a[0]);
		close(g2a[1]);
		
		close(a2t[0]);
		close(a2t[1]);

		dup2(p2g[1], 1);
		execlp("ps", "ps", "-ef", NULL);

		exit(1);
	}
	
	if(fork() == 0){ // grep
		close(p2g[1]);
		
		close(g2a[0]);

		close(a2t[0]);
		close(a2t[1]);

		dup2(p2g[0], 0);
		dup2(g2a[1], 1);
		execlp("grep", "grep", "-E", "'^root\\>", NULL); // două backslash-uri pentru că C e obișnuit cu "\n" și prostii de genul. "\\" îi zice că erm actually chiar e backslash

		exit(1);
	}
	
	if(fork() == 0){ // awk
		close(p2g[0]);
		close(p2g[1]);
		
		close(g2a[1]);
		
		close(a2t[0]);

		dup2(g2a[0], 0);
		dup2(a2t[1], 1);
		execlp("awk", "awk", "{print $2}", NULL); // fără apostrof la {print $2} pentru că e deja string aici, nu ca în bash unde nu știe ce e fără apostrof/ghilimele

		exit(1);
	}
	
	if(fork() == 0){ // tail
		close(p2g[0]);
		close(p2g[1]);
		
		close(g2a[0]);
		close(g2a[1]);
		
		close(a2t[1]);
		
		dup2(a2t[0], 0);
		execlp("tail", "tail", "-n", "5", NULL);

		exit(1);
	}
	
	close(p2g[0]);
	close(p2g[1]);
	
	close(g2a[0]);
	close(g2a[1]);
	
	close(a2t[0]);
	close(a2t[1]);

	wait(0);
	wait(0);
	wait(0);
	wait(0);

	return 0;

}
```



# **Furculițe**

```c
#include <stdio.h>
#include <unistd.h>

int main(){
	printf("înainte\n");
	fork();
	printf("după\n");

	return 0;
}
```
Afișează "după" de două ori 🤯

```c
#include <stdio.h>
#include <unistd.h>

int main(){
	int i;
	for(i = 0; i < 3; i++){
		fork();
		printf("abcd\n %d %d", getpid(), getppid());
		//get process ID, get parent process ID
	}
	
	return 0;
}
```
14 afișări
![[Pasted image 20250320201408.png]]

<hr>

## Fork bomb :3c
```c
#include <stdio.h>
#include <unistd.h>

int main(){
	while(1)
		fork();

	return 0;
}
```

<hr>

```c
#include <stdio.h>
#include <unistd.h>

int main(){
	int i;
	for(i = 0; i < 3; i++)
		if(fork() == 0)
			printf("abcd\n %d %d", getpid(), getppid());
	
	return 0;
}
```
7 afișări
==Doar fiul trece de `if(fork() == 0)`, pentru că fork returnează 0 doar în fiu==

```c
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(){
	int i;
	for(i = 0; i < 3; i++){
		if(fork() == 0){
			printf("abcd\n %d %d", getpid(), getppid());
			exit(0);
		}
		wait(NULL);
	}
	
	return 0;
}
```
Câte procese fiu rulează deodată?
Maxim 1
Nu există concurență cu `exit(0)` și `wait(NULL)`/`wait(0)`

Fii care concurează:
```c
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(){
	int i;
	for(i = 0; i < 3; i++){
		if(fork() == 0){
			printf("abcd\n %d %d", getpid(), getppid());
			exit(0);
		}
		for(i = 0; i < 3; i++)
			wait(NULL);
	}
	
	return 0;
}
```

==Fiu zombie==
```c
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(){
	if(fork() == 0){
		sleep(10);
		exit(0);
	}

	sleep(15);
	wait(0);
	sleep(5);
	
	return 0;
}
```

Ca să vedem asta real time
`while true; do clear; ps -f -u [nume] | grep -v -E "systemd|sshd|bas|vim|ps -f|sd-pan"; sleep 1; done`
<sub>sau ceva de genu</sub>

## Semnale de întrerupere
Normal putem întrerupe un program în terminal cu `ctrl`+`C`
Dar cu codul de jos, nu va merge
```c
#include <stdio.h>
#include <signal.h>

void f(int sgn){
	ptinf("Jajaja, mai încearcă\n");
	(void)sgn;
}

int main(){
	signal(SIGINT, f);
	while(1);

	return 0;
}
```
Alte moduri de a opri un program sunt:
- `kill -s SIGINT [PID]` - dar nu merge pentru programul de sus
- `kill -2 [PID]` - nici asta
- `kill -9 [PID]` - *asta merge*

Folosim `signal(SIGCHILD, f)` pentru a opri copiii zombie, unde funcția `f` este
```c
void f(int sgn){
	wait(NULL);
}
```

Sau, mai simplu: `signal(SIGCHILD, SIG_IGN)` (IGN de la ignore)

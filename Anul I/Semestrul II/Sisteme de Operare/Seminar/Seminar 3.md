
==Carte: Operating Systems: Three Easy Pieces==

# Procese UNIX
*Proces* - program aflat în execuție
*Program* - o succesiune de instrucțiuni
*Kernel (Nucleu)* - kernel-ul sistemul de operare întreține permanent o tabelă de procese (putem vedea tabela de procese cu `ps -e`/`ps -f -p 1`/`ps -F -u [user]`)

# Furculițe
`fork()` returnează:
- -1, în caz de eroare (ori nu mai e memorie, ori avem procese limiatte, ori altceva)
- 0, în copil
- un număr natural, child PID (Process ID), în părinte

Într-o secvență de tipul
```cpp
int main(){

	fork(); //1
	fork(); //2
	//...
	fork(); //n
	
	return 0;
}
```
În total vor fi 2<sup>n</sup> procese, 2<sup>n</sup>-1 copii + 1 părinte

`getpid()` - ia PID-ul tău
`getppid()` - ia PID-ul părintelui tău

==!! Părintele și copilul nu au vreo ordine specifică de execuție, e random !!==

<hr>

Un orfan este un proces al cărui părinte se termină înaintea lui. Părintele nou al său va fi procesul cu PID 1
Un zombie este un proces care se termină înaintea părintelui, însă părintele este prea ocupat să îl verifice (?)
*Cu `wait` în părinte se scapă și de orfani și zombi*

<hr>

# Familia de funcții `exec`
```c
#include <unistd.h>

int execl(const char *path, const char *arg, ...);
int execlp(const char *file, const char *arg, ...);
int execle(const char *path, const char *arg, ..., const *char envp[]);

int execv(const char *path, const char *argv[]);
int execvp(const char *file, const char *argv[]);
int execve(const char *path, const char *argv, const *char envp[]);
```

Lansează în execuție un nou program (fișier executabil)
Imaginea în memorie a procesului curent este înlocuită cu imaginea celui lansat în execuție
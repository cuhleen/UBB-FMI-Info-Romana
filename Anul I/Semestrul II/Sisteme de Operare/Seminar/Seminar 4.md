
# **Comunicarea între procese**
## Între procese înrudite (părinți și copii)
`pipe()`

```cpp
int main(){
	int pd[2];
	//pd = {67540, 68745}
	//pd[0] - read | pd[1] - write
	int res = pipe(pd);
	if(res == -1)
		//...

	int pid = fork();
	write(pd[1], &n, sizeof(int)); // write citește octeți, și returnează instant. Maxim 80. După care e problema ta să mai transmiți dacă ai mai mult de 80
	read(pd[0], &n, sizeof(int)); // Read așteaptă până citește măcar un octet. Dacă așteaptă la un pipe gol atunci ups așteaptă la infinit
}
```

Pentru comunicare read-write *bidirecțională* se folosesc două pipe-uri, `parent2child[2]` și `child2parent[2]`

==!! Ce face mai exact popen și pclose !!==
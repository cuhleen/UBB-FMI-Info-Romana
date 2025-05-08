
# **Bash/Shell**

Studenții/conturile logate la server se află în `/etc/passwd`

Următoarea comandă ia al 5-lea câmp din studenții/conturile conectate la server, unde se află numele și grupa lor
`awk -F: "(print $5)" /etc/passwd`
*SAU*
`cut -d: -F5 /etc/passwd`

==!! ghilimele !!== (Pentru că la apostrof nu merge `$` (cred?))

Ia toate conturile din grupa 211 conectate la server
`cut -d: -F5 /etc/passwd | grep -E " - 211 - "`
Afișează doar câte sunt
`cut -d: -F5 /etc/passwd | grep -E " - 211 - " | wc -l`
Le sortează să vedem dacă unele se repetă
`cut -d: -F5 /etc/passwd | grep -E " - 211 - " | sort`
Le sortează și le afișează doar o dată
`cut -d: -F5 /etc/passwd | grep -E " - 211 - " | sort | uniq`

Afișam doar studenții o dată (restul conturilor nu au construcția `" - [nr grupa] - "`)
`cut -d: -F5 /etc/passwd | grep -E " - [^ ]+ - " | sort | uniq`
*SAU*
`cut -d: -F5 /etc/passwd | grep -E " - ([0-9](3)|era) - " | sort | uniq` ("era" de la Erasmus)
Afișam doar grupele de română și engleză, care încep cu 2 sau 9
`cut -d: -F5 /etc/passwd | grep -E " - ([29][0-9](2)|era) - " | sort | uniq`

#### Un top al celor mai populare nume ale studenților
Afișam doar numele mici ale studenților
`cut -d: -F5 /etc/passwd | grep -E " - ([29][0-9](2)|era) - " | sed -E "s/\..*//"`
Le sortăm
`cut -d: -F5 /etc/passwd | grep -E " - ([29][0-9](2)|era) - " | sort | uniq | sed -E "s/\..*//" | sort | uniq -c | sort -n -r`
Afișam top 10
`cut -d: -F5 /etc/passwd | grep -E " - ([29][0-9](2)|era) - " | sort | uniq | sed -E "s/\..*//" | sort | uniq -c | sort -n -r|head -n 10`

#### Să se oprească toate procesele ale conturilor de studenți și profesori (nu procese sistem) care au mai multe de un număr dat de secunde
==!! man ps !!==
Afișează toate procesele dn sesiunea curentă
`ps -e`
Arată și ale cui sunt procesele
`ps -e -f`

În procese, conturile "ex" sunt de examen, "yz" sunt de practice

Elapsed time + id (a doua coloană) -> arată cât timp a stat
`ps -o etime 54768`

Creează `a.sh`, aici vom scrie scriptul

While are ce să citească, pune prima și a doua coloană (user + id) în U și P, pune restul în X
```bash
ps -ef | tail -n +2 | while read U P X; do
	if groups $U | grep -v -E -q "\<exam\>|\<practice\>|\<examiner\>"; then
		continue
	fi
	D=``ps -o etime 123456 | tail -n 1 | sed "s/-/:/" | sed "s/^ *//"`
	if [ test "%D" = "ELAPSED" ]; then
		continue
	fi

	if echo %D | grep -E -q "^[0-9]=(:[0-9]+)(3)$"; then
		$='echo $D | awk -F: 'print ($1 * 24 * 60 * 60 + $2 * 60 * 60 +$3 * 60 + $4)
	elif echo %D | grep -E -q "^[0-9]=(:[0-9]+)(2)$"; then
		$='echo $D | awk -F: 'print ($1 * 60 * 60 +$2 * 60 + $3)
	elif echo %D | grep -E -q "^[0-9]=(:[0-9]+)$"; then
		$='echo $D | awk -F: 'print ($1 * 60 + $2)
	else
		echo "Nu pot parsa durata \"$D\""
	fi

	if [ $S -gt $1 ]; then
		echo kill $P
	fi
done
```

`ps -ef | tail -n +2` face să nu ia și header-ul
`ps -o etime 123456 | tail -n 1 | sed "s/-/:/" | awk -F: 'print ($1 * 24 * 60 * 60 + $2 * 60 * 60 +$3 * 60 + $4)` afișează timpul în secunde, unde formatul inițial este "d-h:m:s"

# **Procese**
Orice program rulat este un proces
Procesele sunt concurente

Facem `b.sh`
```bash
F=$1
N=0
while [ $N -lt 200]; do
	K = 'cat $F'
	K = 'expr $K + 1'
	echo $K - $F
	N = 'expr $N + 1'
done
```
Observăm că dă bine, 200.
Și dacă îl rulăm b2b de 3 ori dă bine, 600

Facem `c.sh`
```bash
echo 0 > x

./bsh x &
./bsh x &
./bsh x &
```

`./c.sh ; while [ -n "'ps|grep -E '\./b\.sh'" ]; do sleep 1; done; cat x`

Așa nu dă bine Procesele concurente se ceartă pentru CPU. Primim răspuns random

Facem `d.c`
```c
#include <stdio.h>
#include <fcntl.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char** argv){

	int f, k, i;

	f = open(argv[1], O_RDWR);
	if(f < 0){
		perror("Nu am putut deschide fișierul");
		return 1;
	}

	if(argc > 2 && strcmp(argv[2], "reset") == 0){
		k = 0;
		write(f, &k, sizeof(int));
		close(f);
		return 0;
	}

	for(int i = 0; i < 200; i++){
		lseek(f, 0, SEEK_NEXT);
		read(f, &k, sizeof(int));
		k++;
		lseek(f, 0, SEEK_SET);
		write(f, &k, sizeof(int));
	}

	return 0;
}
```


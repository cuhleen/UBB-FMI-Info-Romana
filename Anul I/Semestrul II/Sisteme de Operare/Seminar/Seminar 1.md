<sub>Seminar făcut în weekend idk din ce motiv</sub>

Vezi drepturi de acces: `ls -l`
`-rwxrwxrwx`
Primele 3 - permisiunile ownerului (`o`)
Următoarele 3 - permisiunile pentru grupul ownerului (`g`)
Ultimele 3 - permisiuni pentru others (`u`)

Reprezentare numerică a drepturilor de acces
*r*ead = 4
*w*rite = 2
e*x*ecute = 1
Modificarea drepturilor după acces
`chmod 755 file`
`chmod 644 file`
`chmod +x file` (pune dreptul de execute la toți)
`chmod a+x file` (`a` vine de la "all", pt toți, la fel ca și comanda de sus)
`chmod a-x file` (scoate dreptul tuturor de execuție)
`chmod g+r file` (dă grupului permisiuni de read)
`chmod u+rw, g+r-w, g+r file`
`chmod u=rwx, g=rw, o=r file`

*Comenzi externe* (`cat`, etc.) - cele în `man`
*Comenzi interne* (actually bash (`pwd`, `read`, `bg`, `fg`, etc.))- cele în `help`

# Exerciții
**Acordă permisiune de execuție**
`chmod +x [nume fila].sh`

- script_1
```bash
#!/bin/bash

# afișez directorul curent 
pwd

# afișez conținutul directorului curent
ls -l

#arătăm că s-a terminat programul conform, ca "return 0"
exit 0

```

<hr>

Paranteză

**Se recomandă ca variabilele să aibă litere mari, în principal pentru vizibilitate**
`NAME=Vasile`
Ca să vedem variabila
`echo $NAME` - se afișează "Vasile"
Dacă scriem `echo NAME` primim înapoi "NAME"
`NAME="Ion Popescu"` - pentru string cu spațiu punem ghilimele "" sau apostrof ''
`echo $NAME` - se afișează "Ion Popescu"
Ca să "ștergem" o variabilă scriem `unset $NAME`
`echo $NAME` - nu afișează nimic
`echo abc; dcefg` - afișează "abc" și apoi zice `-bash: dcefg: command not found`
**Dacă echo vede APOSTROF '' ignoră VARIABILELE CU DOLAR**
`echo Numele introdus este $NAME` - "Numele introdus este Ion Popescu"
`echo "Numele introdus este $NAME"` - "Numele introdus este Ion Popescu"
`echo 'Numele introdus este $NAME'` - *"Numele introdus este $NAME"*
`echo 'Numele introdus este ' $NAME` - "Numele introdus este Ion Popescu"

Citat Daniel Boța: "Echo e un fraier care doar repetă ce e după el"

`$0` - numele fișierului lansat în execuție
`$1,...,$9` - argumente furnizate în linia de comandă
`$#` - nr. de argumente furnizate în linia de comandă
`$*` - șirul de argumente furnizate în linia de comandă
`$@` - argumentele individuale ca listă, argumente furnizate în linia de comandă
`$?` - codul de ieșire al ultimei comenzi executate
`$$` - PID-ul procesului curent
`$!` - PID-ul ultimei comenzi lansate în background

<hr>

- 2
```bash
#!/bin/bash

#Să se scrie un script bash care primește ca argument un nr. natural N și generează N fișiere de tip text astfel:
#- numele fișierelor vor fi de forma: file_X.txt, unde X = {1, 2, ..., N}
#- fiecare fișier generat va conține doar liniile de la X la X + 5 ale fișierului /etc/passwd

#verific nr. de argumente
if [ $# -eq 0 ]
then
	echo "Trebuie să dai un număr natural!"
	echo "$0 numar_natural"
	exit 1
fi

for X in $(seq 1 $1)
do
	#creez fișierul
	touch file_$X.txt

	#scriu conținutul în fișier
	sed -E -n ''$X',+5p' /etc/passwd >"file_$X.txt"
done

exit 0
```

Alte moduri de a scrie în fișier
`sed -E -n "$X,+5p" /etc/passwd >"file_$X.txt"`
`echo text >> file`

- 3
```bash
#!/bin/bash

#2. Pentru fiecare parametru din linia de comandă:
#Dacă e fișier, se vor afișa numele nr. de caractere și de linii din el (în această ordine)
#iar dacă e director, numele și câte fișiere conține (inclusiv în subdirectoarele sale)
#(comenzi: test, wc, awk, find)

for NUME in $@
do
	if [ -f $NUME ]
	then
		#command substitution
		NRC=$(wc -m <$NUME)
		NRL=$(wc -l <$NUME)
		echo 'Fișier: ' $NUME linii: $NL caractere: $NC
		#exact același lucru cu
		#echo 'Fișier: ' $NUME 'linii: ' $(wc -m <$NUME) 'caractere: ' $(wc -l <$NUME)
	elif [ -d $NUME ]
	then
		echo 'Director: ' $NUME 'fisiere: ' $(find $NUME -type f | wc -l)
	else
		echo Nu stiu ce e $NUME
	fi
done
```

- 4
```bash
#!/bin/bash

#3. Afișați primele 5 linii și ultimele 5 linii ale tuturor fișierelor de tip text din directorul curent
#Dacă un fișier are mai puțin de 10 linii, atunci se va afișa întreg conținutul său.
#(comenzi: head, tail, find, file, wc)

#for FISIER in $(find . -type f)
#sau
LISTA_FISIERE=$(find . -type f)
for FISIER in $LISTA_FISIERE
do
	echo Fisier: $FISIER
	if file $FISIER | grep -E -q 'ASCII text$'
	then
		NRL=(wc -l <$FISIER)
		if test $NRL -lt 10
		then
			cat -n $FISIER
		else
			head -5 $FISIER
			tail -5 $FISIER
		fi
	else
		echo Fisierul $FISIER nu este de tip text
	fi
done

exit 0
```


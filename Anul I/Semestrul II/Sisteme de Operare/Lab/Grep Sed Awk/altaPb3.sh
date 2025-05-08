#!/bin/bash

if [[ ! $# -eq 1 ]]; then
	echo "Număr greșit de argumente!"
	exit 1
fi

nrLinii=0
nrFile=0

for File in $(find "$1" -type f -name "*.txt"); do
	((nrFile++))
	commandSubstitutionForLineCount=$(wc -l < "$File")
	((nrLinii+=$commandSubstitutionForLineCount))
done

rez=0
((rez=$nrLinii/$nrFile))
echo "Numărul mediu de linii ale filelor .txt din directorul $1 este $rez"

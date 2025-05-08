#!/bin/bash

if [[ ! $# -eq 1 ]]; then
	echo "Număr greșit de argumente!"
	exit 1
fi

for File in $(find "$1" -type f); do
	count=$(grep -E -o "[0-9]{6,}" "$File" | wc -l)
	if [[ $count -gt 0 ]]; then
		fileName=$(echo "$File" | sed -E "s/.*\///")
		# alternativ, sed -E "@.*/@@
		echo "Fila $fileName conține un număr cu peste 5 cifre"
	fi
done

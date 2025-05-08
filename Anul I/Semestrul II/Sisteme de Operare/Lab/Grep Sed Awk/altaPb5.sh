#!/bin/bash

if [[ $# -eq 0 ]]; then
	echo "Introduce cel puțin un argument!"
	exit 1
fi

for chestie in $@; do
	numeChestie=$(echo "$chestie" | sed -E "s@.*/@@")
	if [[ -f "$chestie" ]]; then
		numarCaractere=$(wc -c < "$chestie")
		numarLinii=$(wc -l < "$chestie")
		echo "Fișierul $numeChestie are $numarCaractere caractere pe $numarLinii linii"
	else
		numarObiecteInauntru=$(find "$chestie" | wc -l)
		echo "Directorul $numeChestie conține $numarObiecteInauntru file și subdirectoare în el"
	fi
done

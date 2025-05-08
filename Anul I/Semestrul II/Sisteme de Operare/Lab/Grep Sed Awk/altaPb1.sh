#!/bin/bash

if [[ ! $# -eq 1 ]]; then
	echo "Trebuie sa fie doar un singur argument!"
	exit 1
fi

for ((i=1;i<=$1;i++)); do
	touch file_$i.txt
	sed -n "$i,+5p" passwd.fake > file_$i.txt
done

#!/bin/bash

if [[ ! $# -eq 1 ]]; then
	echo "Număr de argumente greșit"
	exit 1
fi

for File in $(find "$1" -type f -name "*.txt"); do
	numberOfLines=$(wc -l "$File" | awk '{print $1}')
	echo "Number of lines in file $File is $numberOfLines"
	if [[ "$numberOfLines" -gt 5 ]]; then
		echo head -n -3 "$File"
		grep tail -n -3 "$File"
	else
		cat "$File"
	fi
done
